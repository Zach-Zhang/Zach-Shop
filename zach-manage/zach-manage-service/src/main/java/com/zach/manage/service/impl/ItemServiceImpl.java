package com.zach.manage.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zach.common.vo.DataGridResult;
import com.zach.manage.mapper.ItemDescMapper;
import com.zach.manage.mapper.ItemMapper;
import com.zach.manage.pojo.Item;
import com.zach.manage.pojo.ItemDesc;
import com.zach.manage.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Service
public class ItemServiceImpl extends BaseServiceImpl<Item> implements ItemService {
    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private ItemDescMapper itemDescMapper;
    @Override
    public Long saveItem(Item item, String desc) {

        saveSelective(item);
        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(itemDesc.getCreated());
        itemDescMapper.insertSelective(itemDesc);
        return item.getId();
    }

    @Override
    public void updateItem(Item item,String desc) {
        updateSelective(item);

        ItemDesc itemDesc = new ItemDesc();
        itemDesc.setItemId(item.getId());
        itemDesc.setItemDesc(desc);
        itemDesc.setUpdated(new Date());
        itemDescMapper.updateByPrimaryKeySelective(itemDesc);
    }

    @Override
    public DataGridResult queryItemList(String title, Integer page, Integer rows) {
        //创建Example
        Example example = new Example(Item.class);


        try {
           if(StringUtils.isNoneBlank(title)){
               //解码
               title = URLDecoder.decode(title, "utf-8");
               //添加查询条件
               example.createCriteria().andLike("title","%"+title+"%");
           }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据更新时间降序排序
        example.orderBy("updated").desc();

        //分页
        PageHelper.startPage(page,rows);

        //执行查询
        List<Item> list = itemMapper.selectByExample(example);

        //转换为分页信息对象
        PageInfo<Item> pageInfo = new PageInfo<>(list);
        System.out.println(pageInfo.getList()+"======================================================");
        //返回DataGridResult
        return new DataGridResult(pageInfo.getTotal(),pageInfo.getList());
    }
}
