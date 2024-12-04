package edu.du.api_test.test;

import org.json.JSONObject;
import org.json.XML;
import java.io.*;
import java.net.*;

public class ApiExplorer {
    public static void main(String[] args) throws IOException {
        // 서비스 키를 외부에서 읽어오는 방식으로 변경할 수 있습니다.
        String serviceKey = "SP3wJEpLefDpmxgJzS1VvBxA3KCHo1lMpp8AXy%2F8jjHjSFzPcVzl5r1fO1zOBfyY7XWTzd3QV7FRN7%2FSkM01zg%3D%3D";

        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire");
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey); // Service Key
        urlBuilder.append("&" + URLEncoder.encode("Q0", "UTF-8") + "=" + URLEncoder.encode("서울특별시", "UTF-8")); // 주소(시도)
        urlBuilder.append("&" + URLEncoder.encode("Q1", "UTF-8") + "=" + URLEncoder.encode("광진구", "UTF-8")); // 주소(시군구)
        urlBuilder.append("&" + URLEncoder.encode("QT", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); // 월~일요일(1~7)
        urlBuilder.append("&" + URLEncoder.encode("ORD", "UTF-8") + "=" + URLEncoder.encode("NAME", "UTF-8")); // 순서
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); // 페이지 번호
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); // 목록 건수

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");

        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();

        String xmlResponse = sb.toString();

        // XML 응답에 데이터가 없을 경우 처리
        if (xmlResponse.contains("<totalCount>0</totalCount>") || !xmlResponse.contains("<item>")) {
            System.out.println("데이터가 없습니다. 요청 조건을 확인하세요.");
        } else {
            // XML을 JSON으로 변환
            JSONObject jsonResponse = XML.toJSONObject(xmlResponse);
            System.out.println(jsonResponse.toString(4)); // 예쁘게 JSON 출력 (들여쓰기 4칸)
        }
    }
}