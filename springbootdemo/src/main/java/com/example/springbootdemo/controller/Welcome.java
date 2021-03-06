package com.example.springbootdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @创建时间：2018/6/20
 * @描述：访问页
 */
@Controller
public class Welcome {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @RequestMapping("/")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/find")
    public String find(HttpServletRequest request) {
        List list = jdbcTemplate.queryForList("select * from  DE_EN_BASE_INF");
        request.setAttribute("list", list);
        return "account";
    }

    @RequestMapping("/provider/welcomes")
    @ResponseBody
    public String welcomes() {
        return "welcome-GETmapping";
    }
}
