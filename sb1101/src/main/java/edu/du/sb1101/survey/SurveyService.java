package edu.du.sb1101.survey;

import edu.du.sb1101.registerMember.entity.Member;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

@Service
@Log4j2
public class SurveyService {

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
}