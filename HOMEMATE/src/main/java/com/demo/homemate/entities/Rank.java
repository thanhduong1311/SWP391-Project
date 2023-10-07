package com.demo.homemate.entities;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = Rank.COLLECTION_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rank {

    public final static String COLLECTION_NAME = "rank";

    @Id
    @Column(name = "rank_id",unique = true,nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rankId;

    private String name;

    private String description;

    private double minSpend;

    private double discount;


}
