package edu.du.proj_g2e.fileUpload_AWS;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor // final 필드에 대한 생성자를 자동 생성
@RestController // 이 클래스는 REST API를 처리하는 컨트롤러로 사용됩니다.
public class S3Controller {

    private final S3ImageService s3ImageService; // S3 이미지 업로드 서비스 주입

    @PostMapping("/s3/upload") // POST 요청을 처리하고 이미지를 업로드합니다.
    public ResponseEntity<?> s3Upload(@RequestPart(value = "image", required = false) MultipartFile image) {
        String profileImage = s3ImageService.upload(image); // S3에 이미지 업로드
        return ResponseEntity.ok(profileImage); // 업로드된 이미지의 S3 URL 반환
    }

    @GetMapping("/s3/delete") // GET 요청을 처리하고 이미지를 삭제합니다.
    public ResponseEntity<?> s3delete(@RequestParam String addr) {
        s3ImageService.deleteImageFromS3(addr); // S3에서 이미지 삭제
        return ResponseEntity.ok(null); // 성공 응답 반환
    }
}

