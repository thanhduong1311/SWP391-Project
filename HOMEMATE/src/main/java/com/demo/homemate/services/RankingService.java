package com.demo.homemate.services;

import com.demo.homemate.dtos.notification.MessageOject;
import com.demo.homemate.entities.Customer;
import com.demo.homemate.entities.Member;
import com.demo.homemate.entities.MemberKey;
import com.demo.homemate.entities.Ranking;
import com.demo.homemate.repositories.CustomerRepository;
import com.demo.homemate.repositories.MemberRepository;
import com.demo.homemate.repositories.RankRepository;
import com.demo.homemate.services.interfaces.IRankIngService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.Date;
import java.util.List;


@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class RankingService implements IRankIngService {

    private final MemberRepository memberRepository;
    private final RankRepository rankRepository;
    private final CustomerRepository customerRepository;


    public MessageOject checkRank(Customer customer){
        Member member = memberRepository.findByCustomerID(customer.getCustomerId());
        double totalSpend = customer.getTotalSpend();
        List<Ranking> listRank = rankRepository.findAll();
            if (member!=null){
                System.out.println("da co member roi");
                if (totalSpend >= member.getRanking().getMinSpend() &&
                        (member.getRanking().getRankId() == listRank.size() || totalSpend < listRank.get(member.getRanking().getRankId()).getMinSpend()))
                {
                    return new MessageOject("Failed","You are still in your rank",null);
                }
            }
            for (int i=0;i<listRank.size();i++){
                if (totalSpend<listRank.get(0).getMinSpend()){
                    return new MessageOject("Failed","You can not up rank now",null);
                }
                if (i==(listRank.size()-1)){
                    if(totalSpend>=listRank.get(i).getMinSpend()){
                        return upRank(customer,listRank.get(i).getRankId());
                    }
                }
                if (totalSpend>=listRank.get(i).getMinSpend()
                        &&totalSpend<listRank.get(i+1).getMinSpend()){
                    System.out.println(listRank.get(i).getName());
                    return upRank(customer,listRank.get(i).getRankId());
                }
            }
         return new MessageOject("Failed","You can not up rank now",null);
    }
    @Override
    public MessageOject upRank(Customer customer,int rankId) {
       Member member = new Member();
        MemberKey memberKey = new MemberKey();
        memberKey.setRankId(rankId);
        memberKey.setCustomerId(customer.getCustomerId());
        member.setId(memberKey);
        member.setRanking(rankRepository.findById(rankId));
        member.setCustomer(customer);
        member.setUpRank_date(new Date());
        memberRepository.save(member);
        return new MessageOject("Success","Up Rank successfully",null);
    }

    @Override
    public MessageOject downRank(Customer customer) {
        return null;
    }

    @SneakyThrows
    @Override
    public Ranking getRank(String username) {
        Ranking ranking =new Ranking();
       try {
           Customer customer = customerRepository.findByUsername(username);
        Member member = memberRepository.findByCustomerID(customer.getCustomerId());
        ranking = rankRepository.findById(member.getRanking().getRankId());
        return ranking;
       } catch (Exception e) {
           System.out.println(e.getMessage());
       }
       return ranking;
    }
}


