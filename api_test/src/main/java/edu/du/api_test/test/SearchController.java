package edu.du.api_test.test;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
public class SearchController {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

    @GetMapping("/")
    public String searchPage() {
        return "search";
    }

    @PostMapping("/search")
    public String search(@RequestParam("query") String query, Model model) {
        try {
            String serviceKey = "SP3wJEpLefDpmxgJzS1VvBxA3KCHo1lMpp8AXy%2F8jjHjSFzPcVzl5r1fO1zOBfyY7XWTzd3QV7FRN7%2FSkM01zg%3D%3D";
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("Q0", "UTF-8") + "=" + URLEncoder.encode(query, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd;
            // 응답 코드가 200 ~ 300 범위일 때만 정상 응답으로 처리
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                throw new Exception("API 호출 오류: " + conn.getResponseCode());
            }

            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            String xmlResponse = sb.toString();

            // XML -> JSON 변환
            JSONObject jsonResponse = XML.toJSONObject(xmlResponse);

            // 필요한 데이터만 추출 (병원 정보)
            String hospitals = jsonResponse
                    .getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items")
                    .toString();

            // 검색 기록이 중복되지 않도록 확인
            if (searchHistoryRepository.findByQuery(query) == null) {
                SearchHistory searchHistory = new SearchHistory();
                searchHistory.setQuery(query);
                searchHistory.setResponse(hospitals);
                searchHistory.setSearchDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                searchHistoryRepository.save(searchHistory);
            }

            // 모델에 결과 추가
            model.addAttribute("result", hospitals);

        } catch (Exception e) {
            model.addAttribute("error", "검색 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "search";
    }

}