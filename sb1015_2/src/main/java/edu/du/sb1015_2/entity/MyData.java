package edu.du.sb1015_2.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "mydata")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 200)
    private String email;

    private Integer age;

    private String memo;
}
