package edu.du.proj_g2e.fileUpload_AWS;

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

@Slf4j // 로깅을 위한 Lombok 애너테이션
@RequiredArgsConstructor // 필드에 final이 붙은 경우, 해당 필드를 초기화하는 생성자를 자동 생성
@Component // Spring Bean으로 등록되는 클래스임을 나타냅니다.
public class S3ImageService {

  private final AmazonS3 amazonS3; // AmazonS3 객체 주입

  @Value("${cloud.aws.s3.bucketName}") // S3 버킷 이름을 properties에서 주입받습니다.
  private String bucketName;

  public String upload(MultipartFile image) {
    // 이미지가 비어 있거나 파일 이름이 유효하지 않은 경우 예외 발생
    if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
      throw new IllegalArgumentException("The uploaded file is empty or invalid.");
    }
    return this.uploadImage(image); // 이미지 업로드 처리
  }

  private String uploadImage(MultipartFile image) {
    // 업로드 전에 파일 확장자 검증
    validateImageFileExtension(image.getOriginalFilename());
    try {
      return this.uploadImageToS3(image); // S3에 이미지 업로드
    } catch (IOException e) {
      log.error("Error uploading image to S3", e);
      throw new RuntimeException("Failed to upload image due to I/O error.");
    }
  }

  private void validateImageFileExtension(String filename) {
    // 파일 확장자를 추출하고 유효성을 검증
    int lastDotIndex = filename.lastIndexOf(".");
    if (lastDotIndex == -1) {
      throw new IllegalArgumentException("File extension is missing.");
    }

    String extension = filename.substring(lastDotIndex + 1).toLowerCase(); // 확장자를 소문자로 변환
    List<String> allowedExtensions = Arrays.asList("jpg", "jpeg", "png", "gif"); // 허용되는 확장자 목록

    if (!allowedExtensions.contains(extension)) {
      // 허용되지 않은 확장자인 경우 예외 발생
      throw new IllegalArgumentException("Invalid file extension. Allowed extensions: jpg, jpeg, png.");
    }
  }

  private String uploadImageToS3(MultipartFile image) throws IOException {
    // 파일 이름과 확장자 추출
    String originalFilename = image.getOriginalFilename();
    String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();

    // S3에 저장할 고유 파일 이름 생성
    String s3FileName = UUID.randomUUID().toString().substring(0, 10) + "-" + originalFilename;

    // 파일 데이터를 InputStream으로 읽어오고 크기를 설정
    InputStream inputStream = image.getInputStream();
    byte[] bytes = IOUtils.toByteArray(inputStream);

    ObjectMetadata metadata = new ObjectMetadata(); // S3에 저장할 메타데이터 설정
    metadata.setContentType("image/" + extension); // MIME 타입 설정
    metadata.setContentLength(bytes.length); // 파일 크기 설정

    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

    try {
      // S3에 파일 업로드 요청 생성 및 실행
      PutObjectRequest putObjectRequest =
              new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata);
      amazonS3.putObject(putObjectRequest);
    } finally {
      // 스트림 리소스 닫기
      byteArrayInputStream.close();
      inputStream.close();
    }

    // 업로드된 파일의 S3 URL 반환
    return amazonS3.getUrl(bucketName, s3FileName).toString();
  }

  public void deleteImageFromS3(String imageAddress) {
    // 삭제할 S3 객체의 키를 추출
    String key = getKeyFromImageAddress(imageAddress);
    try {
      // S3에서 해당 객체 삭제
      amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
    } catch (Exception e) {
      log.error("Error deleting image from S3", e);
      throw new RuntimeException("Failed to delete image from S3.");
    }
  }

  private String getKeyFromImageAddress(String imageAddress) {
    try {
      // S3 URL에서 파일 키를 추출
      URL url = new URL(imageAddress);
      String decodedKey = URLDecoder.decode(url.getPath(), "UTF-8");
      return decodedKey.substring(1); // 파일 키 반환 (첫 번째 슬래시 제외)
    } catch (MalformedURLException | UnsupportedEncodingException e) {
      log.error("Error decoding image address", e);
      throw new RuntimeException("Failed to decode image address.");
    }
  }
}
