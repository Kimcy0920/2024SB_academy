package edu.du.api_test.test02;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BusinessValidationService {

    private final String API_URL = "https://api.odcloud.kr/api/nts-businessman/v1/status";
    private final String API_KEY = "SP3wJEpLefDpmxgJzS1VvBxA3KCHo1lMpp8AXy/8jjHjSFzPcVzl5r1fO1zOBfyY7XWTzd3QV7FRN7/SkM01zg=="; // 발급받은 API 키를 입력하세요

    public boolean validateBusinessNumber(String businessNumber) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + API_KEY);

        // 요청 본문 작성
        String requestBody = "{\"b_no\": [\"" + businessNumber + "\"]}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        // API 호출
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(API_URL, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                String responseBody = response.getBody();
                // JSON 파싱해서 "계속사업자" 여부 확인
                return responseBody.contains("\"b_stt\":\"계속사업자\"");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}
