package edu.du.sb1023.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Team {
    @Id
    @Column(name = "team_id")
    private String id;

    private String name;

    @OneToMany(mappedBy = "team") // 일대다, mappedBy는 OneToMany에 사용.
    private List<Member> members = new ArrayList<>(); // 단방향이기 때문에 양방향을 하기 위해 mappedBy 사용
    // members 필드는 팀에 속한 멤버들의 리스트, 멤버를 추가할 수 있도록 초기화한 것.

    public Team(String id, String name) {
        this.id = id;
        this.name = name;
    }
}