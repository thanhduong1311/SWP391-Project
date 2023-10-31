package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Ranking;

public interface IRankIngService {
    MessageOject upRank(Customer customer,int rankID);
    MessageOject downRank(Customer customer);
    Ranking getRank(String username);
}
