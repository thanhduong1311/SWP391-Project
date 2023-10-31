package com.demo.homemate.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class MemberKey implements Serializable {

    @Column(name = "rank_id")
    int rankId;

    @Column(name = "customer_id")
    int customerId;


}
