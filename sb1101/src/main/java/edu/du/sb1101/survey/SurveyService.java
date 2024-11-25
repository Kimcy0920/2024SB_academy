package edu.du.sb1101.survey;

import edu.du.sb1101.registerMember.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class SurveyService {

    @Autowired
    private AnsweredDataRepository ansRepository;

    @PersistenceUnit
    private EntityManagerFactory emf;

    public void save(AnsweredData data) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            log.info("Saving AnsweredData: {}", data);

            // 세션에서 가져온 Member를 영속성 컨텍스트로 로드
            Member mem = em.find(Member.class, data.getMember().getId());
            if (mem == null) {
                throw new IllegalArgumentException("Member not found in the database.");
            }
            data.setMember(mem); // 영속 상태의 Member로 설정

            // Respondent도 Member 설정
            Respondent respondent = data.getRes();
            respondent.setMember(mem);

            // 데이터 저장
            em.persist(respondent);  // Respondent 저장
            em.persist(data);        // AnsweredData 저장

            transaction.commit();
            log.info("Data saved successfully.");
        } catch (Exception e) {
            transaction.rollback();
            log.error("Error occurred while saving data", e);
        } finally {
            em.close();
        }
    }

    public Map<String, Double> getStatistics() {
        List<AnsweredData> answers = ansRepository.findAll();
        Map<String, Integer> responseCounts1 = new HashMap<>();
        Map<String, Integer> responseCounts2 = new HashMap<>();
        int totalResponses = 0;

        // 첫 번째 질문의 응답 통계 계산
        for (AnsweredData answer : answers) {
            String response1 = answer.getResponses().get(0); // 첫 번째 질문의 응답
            responseCounts1.put(response1, responseCounts1.getOrDefault(response1, 0) + 1);

            // 두 번째 질문의 응답 통계 계산
            String response2 = answer.getResponses().get(1); // 두 번째 질문의 응답
            responseCounts2.put(response2, responseCounts2.getOrDefault(response2, 0) + 1);

            totalResponses++;
        }

        // 응답 비율 계산
        Map<String, Double> responseRates = new HashMap<>();

        // 첫 번째 질문 응답 비율 계산
        for (Map.Entry<String, Integer> entry : responseCounts1.entrySet()) {
            double percentage = (entry.getValue() / (double) totalResponses) * 100;
            responseRates.put("Q1_" + entry.getKey(), Double.parseDouble(String.format("%.2f", percentage)));
        }

        // 두 번째 질문 응답 비율 계산
        for (Map.Entry<String, Integer> entry : responseCounts2.entrySet()) {
            double percentage = (entry.getValue() / (double) totalResponses) * 100;
            responseRates.put("Q2_" + entry.getKey(), Double.parseDouble(String.format("%.2f", percentage)));
        }

        return responseRates;
    }
}