package com.demo.homemate.services;

import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Member;
import com.demo.homemate.entities.Ranking;
import com.demo.homemate.repositories.MemberRepository;
import com.demo.homemate.repositories.RankRepository;
import com.demo.homemate.services.interfaces.IRankIngService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.List;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RankingService implements IRankIngService {

    private final MemberRepository memberRepository;
    private final RankRepository rankRepository;

    @Override
    public MessageOject upRank(Customer customer) {
        return null;
    }

    @Override
    public MessageOject downRank(Customer customer) {
        return null;
    }

    @SneakyThrows
    @Override
    public Ranking getRank(Customer customer) {
//        try {
        Member member = memberRepository.findByCustomer(customer);

        Ranking ranking = rankRepository.findById(member.getRanking().getRankId());

        return ranking;
//        } catch (Exception e) {
//            throw new Exception(e.getMessage());
//        }}
    }
}
