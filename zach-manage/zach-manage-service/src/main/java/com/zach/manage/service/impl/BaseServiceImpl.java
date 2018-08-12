package com.zach.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.zach.manage.pojo.BasePojo;
import com.zach.manage.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class BaseServiceImpl<T extends BasePojo> implements BaseService<T>{

    @Autowired //从spring 4.x版本开始使用该注解的话；可以使用泛型依赖注入
    private Mapper<T> mapper;

    private Class<T> clazz;

    public BaseServiceImpl() {
        //this 表示当前被实例化的对象；如userServiceImpl
        ParameterizedType pt = (ParameterizedType)this.getClass().getGenericSuperclass();//如：BaseServiceImpl<User>

        clazz = (Class<T>)pt.getActualTypeArguments()[0];
    }

    @Override
    public List<T> queryAll() {
        return mapper.selectAll();
    }

    @Override
    public T queryById(Serializable id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> queryByWhere(T t) {
        return mapper.select(t);
    }

    @Override
    public int queryCountByWhere(T t) {
        return mapper.selectCount(t);
    }

    @Override
    public List<T> queryByPage(Integer page, Integer rows) {
        //设置分页
        PageHelper.startPage(page, rows);

        return mapper.selectAll();
    }

    @Override
    public void saveSelective(T t) {
        if(t.getCreated() == null) {
            t.setCreated(new Date());
            t.setUpdated(t.getCreated());
        } else if(t.getUpdated() == null) {
            t.setUpdated(new Date());
        }

        mapper.insertSelective(t);
    }

    @Override
    public void updateSelective(T t) {
        if(t.getUpdated() == null) {
            t.setUpdated(new Date());
        }

        mapper.updateByPrimaryKeySelective(t);
    }

    @Override
    public void deleteById(Serializable id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void deleteByIds(Serializable[] ids) {

        Example example = new Example(clazz);

        Example.Criteria criteria = example.createCriteria();

        criteria.andIn("id", Arrays.asList(ids));

        mapper.deleteByExample(example);
    }

}
