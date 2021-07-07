package com.example.demo.controller;

import com.example.demo.service.FeedbackService;
import com.example.demo.service.ReportGenImp;
import com.example.demo.service.ReportGenService;

public class ReportGenController {
    public static void main(String[] args) {
        ReportGenService reportGenImp = new ReportGenImp();
        reportGenImp.reportGen();
        FeedbackService feedbackService = new ReportGenImp();
        feedbackService.feedBack();
        ReportGenImp reportGenImp1 = new ReportGenImp();
        reportGenImp1.abstractPrint();
    }

}
