package com.zach.manage.service.impl;

import com.zach.manage.mapper.ItemCatMapper;
import com.zach.manage.pojo.ItemCat;
import com.zach.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemCatServiceImpl extends BaseServiceImpl<ItemCat> implements ItemCatService {

    @Autowired
    private ItemCatMapper itemCatMapper;

}
