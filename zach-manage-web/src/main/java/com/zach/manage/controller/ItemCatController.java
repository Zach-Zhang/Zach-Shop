package com.zach.manage.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zach.manage.pojo.ItemCat;
import com.zach.manage.service.ItemCatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService ;

    /**
     * 根据商品类目的id查询该类目
     */
    @RequestMapping(value="/{categoryId}",method=RequestMethod.GET)
    public ResponseEntity<ItemCat> queryItemCatByCategoryId(@PathVariable Long categoryId) {
        try {
            ItemCat itemCat =  itemCatService.queryById(categoryId);
            return ResponseEntity.ok(itemCat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回状态码
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 分页查询商品类目表
     */
    @RequestMapping(value = "/query/{pageNo}",method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListInPage(
            @PathVariable Integer pageNo,
            @RequestParam(value="rows",defaultValue = "20") Integer rows
    ){
        try {
            List<ItemCat> list =  itemCatService.queryListByPage(pageNo,rows);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();

        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据商品类目父id查询该类目下的所有的子类目;并在树组件加载数据
     * @param parentId
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ItemCat>> queryItemCatListByParentId(@RequestParam(value="id",defaultValue = "0")Long parentId){
        List<ItemCat> list = null;
        try {
            ItemCat itemCat = new ItemCat();
            itemCat.setParentId(parentId);
            list = itemCatService.queryListWhere(itemCat);
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }
}
