package edu.du.sb1101.survey;

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
        // 트랜잭션 시작
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            log.info("Saving AnsweredData: {}", data);

            // Respondent와 AnsweredData 저장 (Member는 Cascade로 저장되므로 필요 없음)
            Respondent respondent = data.getRes();
            em.persist(respondent);  // Respondent 저장
            em.persist(data);        // AnsweredData 저장

            // 트랜잭션 커밋
            transaction.commit();
            log.info("Data saved successfully.");
        } catch (Exception e) {
            // 오류 발생 시 롤백
            transaction.rollback();
            log.error("Error occurred while saving data", e);
        } finally {
            em.close();  // EntityManager 종료
        }
    }
}
