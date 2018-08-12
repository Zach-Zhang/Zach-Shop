package com.zach.manage.controller;

import com.zach.common.vo.DataGridResult;
import com.zach.manage.pojo.Item;
import com.zach.manage.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/item")
@Controller
public class ItemControll {

    @Autowired
    private ItemService itemService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> saveItem(Item item, @RequestParam(value = "desc", required = false)String desc) {

        try {
            //保存商品基本信息和描述信息
            itemService.saveItem(item, desc);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void updateItem(Item item, @RequestParam(value = "desc", required = false)String desc){

        try {
            //更新商品基本信息和描述信息
            itemService.updateItem(item, desc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据商品标题分页查询商品
     */
    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity<DataGridResult> queryItemList(@RequestParam(value="title",required = false)String title,
                                                        @RequestParam(value="page",defaultValue = "1")Integer page,
                                                        @RequestParam(value="rows",defaultValue = "30")Integer rows){
        try {
            //保存商品基本信息和描述信息
            DataGridResult dataGridResult = itemService.queryItemList(title,page,rows);

            return ResponseEntity.ok(dataGridResult);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回500
        return  ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
