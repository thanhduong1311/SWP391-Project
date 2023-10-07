package com.demo.homemate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class MemberKey implements Serializable {

    @Column(name = "rank_id")
    int rankId;

    @Column(name = "customer_id")
    int customerId;


}
