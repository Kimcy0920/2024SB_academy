package edu.du.sb1018;

import edu.du.sb1018.entity.Dept;
import edu.du.sb1018.entity.Emp;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.persistence.*;
import java.util.List;

@SpringBootTest
class Sb1018ApplicationTests {

    @PersistenceUnit // EntityManager
    private EntityManagerFactory emf; // Factory, 트랜젝션도 사용할 수 있음.

    @PersistenceContext // 2교시
    private EntityManager em; // select는 트랜젝션이 필요 없어서 단독으로 선언해 사용.

    /*
    영속성 컨텍스트(Persistence Context) 영속성의 경우 트랜젝션을 무조건 사용해야함.
    find를 하면 영속(Managed) 상태가 되어 영속성을 갖게 됨.
    준영속(detachd)를 하여 영속성이 사라져 commit할 때 update가 적용되지 않음.
    commit(2차)는 영속성 컨텍스트(1차)와 DB 데이터를 비교, 데이터가 다르면 변경사항 적용.
    */

    @Test // 1교시
    void 영속성_find_테스트() {
        EntityManager em = emf.createEntityManager(); // EntityManager를 하나 생성함.

        EntityTransaction transaction = em.getTransaction();
        transaction.begin(); // EntityManager로 트랜젝션을 하나 생성함.

        // sql쿼리문을 사용하지 않고 DB를 사용
        Dept dept = em.find(Dept.class, 10); // find는 select와 같음.
        dept.setDname("서울"); // 10번 부서 dname이 서울로 변경됨. update
        System.out.println(dept); // 10번 부서 정보가 출력됨.
        transaction.commit();
    }
// --------------------------------------------------------------------------------------

    @Test // 2교시
    void 영속성_merge_테스트() {
        EntityManager em = emf.createEntityManager(); // EntityManager를 하나 생성함.

        EntityTransaction transaction = em.getTransaction();
        transaction.begin(); // EntityManager로 트랜젝션을 하나 생성함.

        // sql쿼리문을 사용하지 않고 DB를 사용
        Dept dept = em.find(Dept.class, 10); // find는 select라 생각, 1차
        dept.setDname("ACCOUNTS"); // 10번 부서 dname이 서울로 변경됨. update
        System.out.println(dept); // 10번 부서 정보가 출력됨.
        em.detach(dept); // 준영속, 영속성 없어짐.
        em.merge(dept); // merge는 update라 생각, 다시 영속성을 생기게 함.
        transaction.commit(); // DB에 변경사항이 적용됨, commit는 2차, 안 쓰면 롤백됨.
    }

    @Test
    void 영속성_persist_테스트() {
        // 위 3줄짜리 Entity코드를 2줄로 줄임.
        EntityManager em = emf.createEntityManager(); // EntityManager를 하나 생성함.
        em.getTransaction().begin(); // EntityManager로 트랜젝션을 하나 생성함.

        // Dept Entity 객체 생성
        Dept newDept = new Dept();
        newDept.setDeptno(50);
        newDept.setDname("연구소");
        newDept.setLoc("서울");

        // Entity를 DB에 저장
        em.persist(newDept); // persist는 insert라 생각, persist에 의해 영속성을 갖게 됨.
        em.getTransaction().commit();
    }

    @Test
    void 영속성_remove_테스트() {
        EntityManager em = emf.createEntityManager(); // EntityManager를 하나 생성함.
        EntityTransaction transaction = em.getTransaction();
        transaction.begin(); // EntityManager로 트랜젝션을 하나 생성함.

        // Dept Entity 객체 생성
        Dept dept = em.find(Dept.class, 50);
        System.out.println(dept);
        em.remove(dept); // remove는 delete라 생각, 영속성을 잃음
        transaction.commit();
    }

    @Test
    void update_테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Dept dept = em.find(Dept.class, 10);
        dept.setLoc("SEOUL");
        dept.setDname("ACCOUNTING");
        transaction.commit();
    }

    @Test
    void createQuery_테스트() {
        Dept dept = em.find(Dept.class, 10); // 1건의 데이터를 가져옴
        System.out.println(dept);
        System.out.println("========================================================");

        // 여러 건의 데이터를 가져올 때 사용
        TypedQuery<Dept> query = em.createQuery("SELECT d FROM Dept d", Dept.class);
        List<Dept> depts = query.getResultList();
        for (Dept d : depts) {
            System.out.println(d);
        }
        System.out.println("========================================================");

        TypedQuery<Emp> query2 = em.createQuery("SELECT e FROM Emp e", Emp.class);
        List<Emp> Emps = query2.getResultList();
        for (Emp e : Emps) {
            System.out.println(e);
        }
        // 쿼리문이 있지만 DB를 사용한게 아니고, 영속성으로 테이블을 사용한 것.
    }

    @Test
    void createQuery_영속성테스트() {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        System.out.println("========================================================");
        TypedQuery<Dept> query = em.createQuery("SELECT d FROM Dept d", Dept.class);
        List<Dept> depts = query.getResultList(); // 여러 건의 데이터를 가져올 때 사용
        for (Dept d : depts) {
            System.out.println(d);
        }
        System.out.println("========================================================");

        System.out.println(depts.get(0)); // depts 리스트의 0번째를 get해서 출력
        depts.get(0).setLoc("서울"); // 0번째 loc를 서울로 변경 update
        transaction.commit();
    }

// --------------------------------------------------------------------------------------

    @Test // 3교시
    void createQuery_테스트2() {
        String jpql = "SELECT d FROM Dept d WHERE d.dname = :aaa";
        TypedQuery<Dept> query = em.createQuery(jpql, Dept.class);
        query.setParameter("aaa", "ACCOUNTING"); // PreparedStatement
        List<Dept> depts = query.getResultList();
        Dept dept = depts.get(0);
        System.out.println(dept);
        System.out.println("========================================================");

        String jpql2 = "SELECT e FROM Emp e WHERE e.deptno = :deptNo"; // Emp는 테이블이 아닌 Entity
        TypedQuery<Emp> query2 = em.createQuery(jpql2, Emp.class);
        query2.setParameter("deptNo", dept.getDeptno());
        List<Emp> Emps = query2.getResultList();
        for (Emp e : Emps) {
            System.out.println(e);
        }
    }

    @Test
    void find_Emp() { // select
        TypedQuery<Emp> query = em.createQuery("SELECT e FROM Emp e", Emp.class);
        List<Emp> emps = query.getResultList();
        for (Emp d : emps) {
            System.out.println(d);
        }
    }
}
