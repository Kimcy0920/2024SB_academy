package edu.du.sb1015_2;

import edu.du.sb1015_2.entity.MyData;
import edu.du.sb1015_2.repository.MyDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
@RequiredArgsConstructor
public class Sb10152Application {

    // 1번. @Autowired 필드 주입
//    @Autowired // autowired 필드 사용 권장X 성능저하
//    MyDataRepository repository;

    // 3번. @RequiredArgsConstructor 어노테이션 사용. final 붙임
    final MyDataRepository repository;

    // 2번. 생성자 주입 방식
//    public Sb10152Application(MyDataRepository repository) {
//        this.repository = repository; // 생성자 만듦
//    }

    public static void main(String[] args) {
        SpringApplication.run(Sb10152Application.class, args);
    }

    @PostConstruct // 초기값 insert
    public void init() {
        MyData d1 = new MyData();
        d1.setName("차범근");
        d1.setAge(75);
        d1.setEmail("bkCha@korea.com");
        d1.setMemo("레버쿠젠");
        repository.saveAndFlush(d1);

        MyData d2 = new MyData();
        d2.setName("박지성");
        d2.setAge(45);
        d2.setEmail("jsPark@korea.com");
        d2.setMemo("맨체스터 유나이티드");
        repository.saveAndFlush(d2);

        MyData d3 = new MyData();
        d3.setName("박주영");
        d3.setAge(40);
        d3.setEmail("jyPark@korea.com");
        d3.setMemo("아스널");
        repository.saveAndFlush(d3);

        MyData d4 = new MyData();
        d4.setName("손흥민");
        d4.setAge(30);
        d4.setEmail("hmSon@korea.com");
        d4.setMemo("토트넘 훗스퍼");
        repository.saveAndFlush(d4);

        // save(), findById(), findAll() - select
        // deleteById() - delete
    }

}
