package edu.du.sb1101.item;

import edu.du.sb1101.registerMember.entity.Title;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemId;

    private String itemName;

    private int cost = 500;

    @OneToOne
    @JoinColumn(name = "title_id")
    private Title title;
}
