package com.demo.homemate.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = Ranking.COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Ranking {

    public final static String COLLECTION_NAME = "ranking";

    @Id
    @Column(name = "rank_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rankId;

    private String name;

    private String description;

    private double minSpend;

    private double discount;

    @OneToMany(mappedBy = "ranking")
    private List<Member> members;


}
