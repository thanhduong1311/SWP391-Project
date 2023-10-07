package com.demo.homemate.entities;

import jakarta.persistence.*;

import java.util.Date;

@Entity
public class Member {

    @EmbeddedId
    private MemberKey id;

    @ManyToOne
    @MapsId("customer_id")
    @JoinColumn(name = "customer_id",insertable=false, updatable=false )
    private Customer customer;


    @ManyToOne
    @MapsId("rank_id")
    @JoinColumn(name = "rank_id",insertable=false, updatable=false )
    private Ranking ranking;

    private Date upRank_date;
}
