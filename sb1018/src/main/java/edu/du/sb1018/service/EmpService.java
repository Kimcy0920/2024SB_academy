package edu.du.sb1018.service;

import edu.du.sb1018.entity.Emp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmpService { // 3교시

    @PersistenceUnit // EntityManager
    private EntityManagerFactory emf;

    @PersistenceContext
    private EntityManager em;

    public List<Emp> find_Emp() { // SELECT ALL
        TypedQuery<Emp> query = em.createQuery("SELECT e FROM Emp e", Emp.class);
        List<Emp> emps = query.getResultList();
        for (Emp emp : emps) {
            System.out.println(emp);
        }
        return emps;
    }

    public Optional<Emp> find_Empno(Integer empno) { // SELECT ONE
        TypedQuery<Emp> query = em.createQuery("SELECT e FROM Emp e WHERE e.empno = :empno", Emp.class);
        query.setParameter("empno", empno);
        return query.getResultStream().findFirst();
    }

    public void insert_Emp(Emp emp) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(emp);
        em.getTransaction().commit();
    }

    public void update_Emp(Emp emp) { // UPDATE
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();
        Emp emp1 = em.find(Emp.class, emp.getEmpno());
        emp1.setEname(emp.getEname());
        emp1.setJob(emp.getJob());
        emp1.setMgr(emp.getMgr());
        emp1.setSal(emp.getSal());
        emp1.setComm(emp.getComm());
        emp1.setDeptno(emp.getDeptno());
        emp1.setHiredate(emp.getHiredate());
        em.merge(emp1);
        transaction.commit();
    }

    public void remove_Emp(int empno) { // DELETE
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Emp emp = em.find(Emp.class, empno);
        em.remove(emp);
        transaction.commit();
    }
}
