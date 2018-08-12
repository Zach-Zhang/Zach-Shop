package com.zach.manage.service.impl;

import com.zach.manage.mapper.TestMapper;
import com.zach.manage.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService{

    @Autowired
    private TestMapper testMapper;
    @Override
    public String queryCurrentDate() {

        return testMapper.queryCurrentDate();
    }
}
