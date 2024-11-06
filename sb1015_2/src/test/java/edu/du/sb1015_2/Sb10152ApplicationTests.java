package edu.du.sb1015_2;

import edu.du.sb1015_2.entity.MyData;
import edu.du.sb1015_2.repository.MyDataRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestClassOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class Sb10152ApplicationTests {

    @Autowired
    MyDataRepository repository;

    @Test
    void 데이터_전부_가져오기() {
        List<MyData> list = repository.findAll();
        for (MyData myData : list) {

            System.out.println(myData);
        }
    }

    @Test
    void 데이터_하나_가져오기() {
        long id = 3l;
        Optional<MyData> list = repository.findById(id);
        MyData myData = list.get();

        System.out.println(myData);
    }

    @Test
    void 데이터_업데이트() {
        MyData myData = MyData.builder().id(3L).name("손흥민").memo("토트넘 핫스퍼").build();
        repository.save(myData);

        System.out.println(myData);
    }

    @Test
    void 데이터_삭제() {
        long id = 3l;
        repository.deleteById(id);

        List<MyData> list = repository.findAll();
        for (MyData myData : list) {
            System.out.println(myData);
        }
    }

    @Test
    void 데이터_등록() {
        MyData myData = MyData.builder().name("박주영").email("jyPark@korea.com").age(37).memo("아스널").build();
        repository.save(myData);

        System.out.println(myData);
    }
}
