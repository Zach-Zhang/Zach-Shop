package com.zach.manage.controller;

import com.zach.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/test")
@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/date")
    @ResponseBody
    public String querCurrentDate(){
        return testService.queryCurrentDate();
    }

}
