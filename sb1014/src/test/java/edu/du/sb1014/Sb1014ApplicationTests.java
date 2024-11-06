package edu.du.sb1014;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
class Sb1014ApplicationTests {

    @Autowired
    MemoRepository memoRepository;

    @Test
    void insert_Memo() {
        // System.out.println(memoRepository.getClass().getName()); // --> jdk.proxy3.$Proxy115

        IntStream.range(0, 10).forEach(i -> { // 람다식 표현, for 문 0~10까지
//            System.out.println(i);
//            Memo memo = Memo.builder().build(); --> Memo memo = new Memo(); 와 같은 코드

            Memo memo = Memo.builder().memoText("샘플 " + i)
                    .build();
//            System.out.println(memo);
            memoRepository.save(memo); // insert 문 구동됨 -> DB 확인
        });
    }

    @Test
    void select_Memo() {
        // 하나의 자료만 가져오는 코드
        Long mno = 9L; // id 9번
        Optional<Memo> result = memoRepository.findById(mno);
//        System.out.println(result.get()); // .get() : 결과에서 Optional 등 잡다한 것 떼냄.
        // *** properties 에서 create 로 구동하면 drop 하고 시작하기 때문에 update 로 변경 후 구동 ***
        if (result.isPresent()) {
            Memo memo = result.get();
            System.out.println(memo);
        } else {
            System.out.println("=== Not Found ===");
        }
    }

    @Test
    void findAll_Memo() {
        // 모든 자료를 가져오는 코드
        List<Memo> memos = memoRepository.findAll();
        for (Memo memo : memos) {
            System.out.println(memo);
        }
    }

    @Test
    void update_Memo() {
        Memo memo = Memo.builder().id(1L).memoText("샘플100").build();
//        Memo memo = Memo.builder().id(기존 ID 값).memoText(변경할 ID 값).build();
        memoRepository.save(memo);
    }

    @Test
    void delete_Memo() {
        Long mno = 1L;
        memoRepository.deleteById(mno);
    }

    @Test
    void 테스트_쿼리메소드1() {
        List<Memo> memos = memoRepository.findByIdBetween(2L, 7L);
        // select * from memo where id between 2 and 7;
        for (Memo memo : memos) {
            System.out.println(memo);
        }
    }

    @Test
    void 테스트_쿼리메소드2() {
        List<Memo> memos = memoRepository.findByIdBetweenOrderByIdDesc(2L, 7L);
        // select * from memo where id between 2 and 7 order by id desc;
        for (Memo memo : memos) {
            System.out.println(memo);
        }
    }

    @Test
    void 테스트_쿼리어노테이션() {
        System.out.println(memoRepository.getCount() + "건의 자료가 있습니다."); // 테스트_쿼리어노테이션2
        // select count(m) from memo;
        List<Memo> memos = memoRepository.getListDesc();
        // select * from memo order by id desc;
        for (Memo memo : memos) {
            System.out.println(memo);
        }
    }

    @Test
    void 테스트_쿼리어노테이션3() {
        System.out.println(memoRepository.getById(3L));
    }

}

/*
save, findById, findAll, delete
*/