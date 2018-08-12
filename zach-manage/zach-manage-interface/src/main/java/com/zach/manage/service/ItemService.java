package com.zach.manage.service;

import com.zach.common.vo.DataGridResult;
import com.zach.manage.pojo.Item;

public interface ItemService extends BaseService<Item> {
    Long saveItem(Item item,String desc);
    void updateItem(Item item,String desc);
    DataGridResult queryItemList(String title,Integer page,Integer rows);
}
