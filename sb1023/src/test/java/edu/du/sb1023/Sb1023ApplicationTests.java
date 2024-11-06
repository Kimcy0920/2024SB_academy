package edu.du.sb1023;

import edu.du.sb1023.entity.Member;
import edu.du.sb1023.entity.Team;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import java.util.List;

@SpringBootTest
class Sb1023ApplicationTests {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Test
    void test_save() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Team team1 = new Team("team1", "팀1");
        em.persist(team1);

        Member mem1 = new Member("member1", "회원1");
        mem1.setTeam(team1);
        em.persist(mem1);

        Member mem2 = new Member("member2", "회원2");
        mem2.setTeam(team1);
        em.persist(mem2);

        transaction.commit();
    }

    @Test
    void test_find() { // 정보 출력 쿼리 객체용
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        // Member는 테이블명이 아닌 entity에 있는 member 객체를 말하고, m.team t는
        String query = "select m from Member m join m.team t where t.name = :teamName";
        List<Member> result = em.createQuery(query, Member.class)
                        .setParameter("teamName", "팀1")
                        .getResultList();
        for (Member member : result) {
            System.out.println(member);
        }

        transaction.commit();
    }

    @Test
    void test_find2() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Member member = em.find(Member.class, "member1");
        System.out.println(member);
        System.out.println(member.getTeam());

        transaction.commit();
    }

    @Test
    void test_update() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        
        // team2 생성
        Team team2 = new Team("team2", "팀2");
        em.persist(team2);

        // 기존 member1의 team을 team2로 변경(업데이트)
        em.find(Member.class, "member1").setTeam(team2);
        
        transaction.commit();
    }

    @Test
    void 연관관계제거() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Member member1 = em.find(Member.class, "member1");
        member1.setTeam(null); // team에 null값을 넣어 제거함.

        transaction.commit();
    }

    @Test
    void 연관된_엔티티_삭제() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Team team = em.find(Team.class, "team1");
        Member member2 = em.find(Member.class, "member2");
        em.remove(team); // remove 사용
        member2.setTeam(null);

        transaction.commit();
    }

    @Test
    void test_양방향_탐색() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Team team = em.find(Team.class, "team1");
        team.getMembers().forEach(t -> System.out.println("member.username: " + t.getUsername()));

        for (Member member : team.getMembers()) {
//            System.out.println("member.username: " + member.getUsername());
            System.out.println(member); // @ToString.Exclude를 사용해야함
            // member와 team은 서로 순환하는 구조여서 ToString으로는 읽다가
            // team<->member를 계속 순환하기 때문에 메모리 초과 에러 발생함.
        }


        transaction.commit();
    }

    @Test
    void 객체테스트() {
        Member member1 = new Member("member1", "회원1");
        Member member2 = new Member("member2", "회원2");
        Team team = new Team("team1", "팀1");
        member1.setTeam(team);
        member2.setTeam(team);
        System.out.println(member1);
        System.out.println(member2);
    }

}
