package com.zach.manage.controller;

import com.zach.manage.pojo.ItemDesc;
import com.zach.manage.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/item/desc")
@Controller
public class ItemDescController {

    @Autowired
    private ItemDescService itemDescService;

    /**
     * 更新商品基本和描述信息
     */
    @RequestMapping(value = "/{itemId}",method= RequestMethod.GET)
    public ResponseEntity<ItemDesc> queryItemDescByItemId(@PathVariable Long itemId) {

        try {
            ItemDesc itemDesc = itemDescService.queryById(itemId);

            return ResponseEntity.ok(itemDesc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回状态码500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }


}
