package com.zach.test;

import com.zach.manage.mapper.TestMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext-*.xml" })
public class Test {

    @Autowired
    private TestMapper testMapper;

    @org.junit.Test
    public void test(){
        String str =  testMapper.queryCurrentDate();

        System.out.println(str);

    }
}
