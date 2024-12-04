package edu.du.api_test.test;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController {

    @Autowired
    private SearchHistoryRepository searchHistoryRepository;

//    @GetMapping("/search")
//    public String searchWithPagination(@RequestParam("query") String query,
//                                       @RequestParam(value = "page", defaultValue = "1") int page,
//                                       Model model) {
//        return search(query, page, model);
//    }
    @GetMapping("/search")
    public String searchWithPagination() {
        return "/search";
    }
    @PostMapping("/search")
    public String search(@RequestParam("query") String query,
                         @RequestParam(value = "page", defaultValue = "1") int page,
                         Model model) {
        try {
            String serviceKey = "SP3wJEpLefDpmxgJzS1VvBxA3KCHo1lMpp8AXy%2F8jjHjSFzPcVzl5r1fO1zOBfyY7XWTzd3QV7FRN7%2FSkM01zg%3D%3D";
            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire");
            urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + serviceKey);
            urlBuilder.append("&" + URLEncoder.encode("Q0", "UTF-8") + "=" + URLEncoder.encode(query, "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(page), "UTF-8"));
            urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("10", "UTF-8"));

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader rd;
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

            // 필요한 데이터만 추출
            JSONArray hospitalArray = jsonResponse
                    .getJSONObject("response")
                    .getJSONObject("body")
                    .getJSONObject("items")
                    .optJSONArray("item");

            int totalCount = jsonResponse
                    .getJSONObject("response")
                    .getJSONObject("body")
                    .getInt("totalCount");

            List<Map<String, String>> hospitalList = new ArrayList<>();

            if (hospitalArray != null) {
                for (int i = 0; i < hospitalArray.length(); i++) {
                    JSONObject hospital = hospitalArray.getJSONObject(i);

                    Map<String, String> hospitalData = new HashMap<>();
                    hospitalData.put("name", hospital.optString("dutyName"));
                    hospitalData.put("address", hospital.optString("dutyAddr"));
                    hospitalData.put("tel", hospital.optString("dutyTel1"));

                    // 운영시간을 09:00 ~ 17:00 형식으로 변환
                    String startTime = hospital.optString("dutyTime1s");
                    String endTime = hospital.optString("dutyTime1c");

                    // 운영시간이 없으면 공백으로 처리
                    String hours = "";
                    if (!startTime.isEmpty() && !endTime.isEmpty()) {
                        // 시작 시간, 종료 시간 각각 2자리 시:분 포맷으로 변환
                        String formattedStartTime = formatTime(startTime);
                        String formattedEndTime = formatTime(endTime);
                        hours = formattedStartTime + " ~ " + formattedEndTime;
                    }

                    hospitalData.put("hours", hours);
                    hospitalList.add(hospitalData);
                }
            }

            // 모델에 병원 리스트 및 페이징 정보 추가
            model.addAttribute("result", hospitalList);
            model.addAttribute("totalPages", (int) Math.ceil((double) totalCount / 10)); // 총 페이지 수
            model.addAttribute("currentPage", page); // 현재 페이지
            model.addAttribute("query", query); // 검색어

            // 페이지 번호 계산 (10개씩 보이도록)
            int startPage = (page - 1) / 10 * 10 + 1; // 시작 페이지 번호
            int endPage = Math.min(startPage + 9, (int) Math.ceil((double) totalCount / 10)); // 끝 페이지 번호
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);

            // 검색 기록 저장
            if (searchHistoryRepository.findByQuery(query) == null) {
                SearchHistory searchHistory = new SearchHistory();
                searchHistory.setQuery(query);
                searchHistory.setResponse(hospitalList.toString());
                searchHistory.setSearchDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                searchHistoryRepository.save(searchHistory);
            }

        } catch (Exception e) {
            model.addAttribute("error", "검색 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "search";
    }

    // 시간 포맷팅 함수
    private String formatTime(String time) {
        // 예: 0900 -> 09:00, 1700 -> 17:00
        if (time.length() == 4) {
            return String.format("%s:%s", time.substring(0, 2), time.substring(2, 4));
        }
        return time;
    }

}
