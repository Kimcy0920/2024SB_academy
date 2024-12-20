package edu.du.proj_g2e.member.config;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3ImageService {

  private final AmazonS3 amazonS3;

  @Value("${cloud.aws.s3.bucketName}")
  private String bucketName;

  public String upload(MultipartFile image) {
    if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
      throw new IllegalArgumentException("The uploaded file is empty or invalid.");
    }
    return this.uploadImage(image);
  }

  private String uploadImage(MultipartFile image) {
    validateImageFileExtension(image.getOriginalFilename());
    try {
      return this.uploadImageToS3(image);
    } catch (IOException e) {
      log.error("Error uploading image to S3", e);
      throw new RuntimeException("Failed to upload image due to I/O error.");
    }
  }

  private void validateImageFileExtension(String filename) {
    int lastDotIndex = filename.lastIndexOf(".");
    if (lastDotIndex == -1) {
      throw new IllegalArgumentException("File extension is missing.");
    }

    String extension = filename.substring(lastDotIndex + 1).toLowerCase();
    List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif");

    if (!allowedExtensions.contains(extension)) {
      throw new IllegalArgumentException("Invalid file extension. Allowed extensions: jpg, jpeg, png, gif.");
    }
  }

  private String uploadImageToS3(MultipartFile image) throws IOException {
    String originalFilename = image.getOriginalFilename();
    String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

    String s3FileName = UUID.randomUUID().toString().substring(0, 10) + "-" + originalFilename;

    InputStream inputStream = image.getInputStream();
    byte[] bytes = IOUtils.toByteArray(inputStream);

    ObjectMetadata metadata = new ObjectMetadata();
    metadata.setContentType("image/" + extension);
    metadata.setContentLength(bytes.length);

    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

    try {
      PutObjectRequest putObjectRequest =
              new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata);
      amazonS3.putObject(putObjectRequest);
    } finally {
      byteArrayInputStream.close();
      inputStream.close();
    }

    return amazonS3.getUrl(bucketName, s3FileName).toString();
  }

  public void deleteImageFromS3(String imageAddress) {
    String key = getKeyFromImageAddress(imageAddress);
    try {
      amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
    } catch (Exception e) {
      log.error("Error deleting image from S3", e);
      throw new RuntimeException("Failed to delete image from S3.");
    }
  }

  private String getKeyFromImageAddress(String imageAddress) {
    try {
      URL url = new URL(imageAddress);
      String decodedKey = URLDecoder.decode(url.getPath(), "UTF-8");
      return decodedKey.substring(1);
    } catch (MalformedURLException | UnsupportedEncodingException e) {
      log.error("Error decoding image address", e);
      throw new RuntimeException("Failed to decode image address.");
    }
  }
}
