package com.example.demo.service;

public class ReportGenImp extends AbstractService implements ReportGenService , FeedbackService{
    @Override
    public void reportGen() {
        System.out.println("reportGen()");
    }

    @Override
    public void feedBack() {
        System.out.println("feedBack()");
    }

    @Override
    public void abstractPrint(){
        super.abstractPrint();
        System.out.println("override");
    }
}
