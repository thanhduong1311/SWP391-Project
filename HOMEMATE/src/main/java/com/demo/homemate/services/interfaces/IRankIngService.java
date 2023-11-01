package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Ranking;

import java.util.List;

public interface IRankIngService {
    MessageOject changeRank(Customer customer, int rankID);
    List<Ranking> getRanks();
    Ranking getRank(String username);
}
