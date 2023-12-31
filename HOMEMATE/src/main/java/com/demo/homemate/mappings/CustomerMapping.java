package com.demo.homemate.mappings;

import com.demo.homemate.dtos.customer.response.CustomerProfileRequest;
import com.demo.homemate.dtos.customerReport.responese.CustomerReportJob;
import com.demo.homemate.dtos.feedback.FeedbackRequest;
import com.demo.homemate.entities.*;
import com.demo.homemate.mappings.interfaces.ICustomerMapping;

import com.demo.homemate.repositories.CustomerRepository;

import com.demo.homemate.repositories.ReportRepository;
import com.demo.homemate.utils.JobTimer;

import com.demo.homemate.utils.UploadPicture;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class CustomerMapping implements ICustomerMapping {

    @SneakyThrows
    @Override
    public CustomerProfileRequest toCustomerProfile(Customer customer,Ranking rank) {
        try {

            UploadPicture uploadPicture = new UploadPicture();
            CustomerProfileRequest prolife = new CustomerProfileRequest();
            JobTimer jobTimer = new JobTimer();
            prolife.setName(customer.getFullName());
            prolife.setPhone(customer.getPhone());
            prolife.setEmail(customer.getEmail());
            prolife.setAddress(customer.getAddress_detail());
            prolife.setCity(customer.getCity());
            prolife.setDistrict(customer.getDistrict());
            prolife.setAvatar(customer.getAvatar());
            prolife.setUsername(customer.getUsername());
            prolife.setBalance(customer.getBalance());
            prolife.setDob(jobTimer.toBirthDay(customer.getDob()));
            prolife.setTotalSpend(customer.getTotalSpend());
            prolife.setRank(rank);
            return prolife;
        } catch (Exception e)  {
            throw new Exception(e.getMessage());
        }
    }
    @SneakyThrows
    public Customer toCustomerFromCustomerProfile(Customer c, CustomerProfileRequest cpr){
            c.setFullName(cpr.getName());
            c.setPhone(cpr.getPhone());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            c.setDob(sdf.parse(cpr.getDob()));
            c.setAddress_detail(cpr.getAddress());
            c.setCity(cpr.getCity());
            c.setDistrict(cpr.getDistrict());
        return c;
    }
    public FeedbackRequest tofeedbackRequest(Feedbacks fb) {
        FeedbackRequest rbR = new FeedbackRequest();
        rbR.setFeedbackId(fb.getFeedbackId());
        rbR.setCustomerId(fb.getCustomerId().getCustomerId());
        rbR.setJobId(fb.getJobId().getJobId());
        rbR.setPoint(fb.getPoint());
        rbR.setDetail(fb.getDetail());
 return rbR;
    }
    public Feedbacks tofeedback(FeedbackRequest fb, Customer c, Job j) {
        Feedbacks feedbacks = new Feedbacks();
        feedbacks.setFeedbackId(fb.getFeedbackId());
        System.out.println("ĐANG CHUẢN HÓA: "+fb.getFeedbackId());
        feedbacks.setCustomerId(c);
        feedbacks.setJobId(j);
        feedbacks.setPoint(fb.getPoint());
        feedbacks.setDetail(fb.getDetail());
        return feedbacks;
    }
    public Report toReport(CustomerReportJob crj,Report old, Customer c, Job j) {
        Report report = new Report();
        report.setCustomerId(c);
        report.setJobId(j);
        if (crj.getReason()!=null){
            report.setReason(crj.getReason());
        }
        if (old!=null){
            report.setReportId(old.getReportId());
            report.setCreateAt(old.getCreateAt());
            report.setUpdateAt(new Date());
        }else{
            report.setCreateAt(new Date());
        }
        return report;
    }





}
