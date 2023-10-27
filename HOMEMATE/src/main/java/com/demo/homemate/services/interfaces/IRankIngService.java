package com.demo.homemate.services.interfaces;

import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Ranking;

public interface IRankIngService {
    MessageOject upRank(Customer customer);
    MessageOject downRank(Customer customer);
    Ranking getRank(Customer customer);
}
