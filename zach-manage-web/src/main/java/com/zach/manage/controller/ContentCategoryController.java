package com.zach.manage.controller;

import com.zach.manage.pojo.ContentCategory;
import com.zach.manage.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    /**
     * 删除内容分类
     * @param contentCategroy 内容分类
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResponseEntity<Void> deleteContentCategory(ContentCategory contentCategroy){
        try {
            contentCategoryService.deleteContentCategory(contentCategroy);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 更新内容分类
     * @param contentCategroy 内容分类
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Void> updateContentCategory(ContentCategory contentCategroy){
        try {
            contentCategoryService.updateSelective(contentCategroy);

            return ResponseEntity.ok(null);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 保存内容分类
     * @param contentCategroy 内容分类
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<ContentCategory> saveContentCategory(ContentCategory contentCategroy){
        try {

            ContentCategory cc = contentCategoryService.saveContentCategory(contentCategroy);

            return ResponseEntity.ok(cc);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    /**
     * 根据内容分类id查询该分类的所有子分类
     * @param parentId 父节点分类id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ContentCategory>> queryContentCategoryListByParentId(
            @RequestParam(value = "id", defaultValue = "0")Long parentId){
        try {
            ContentCategory contentCategory = new ContentCategory();
            contentCategory.setParentId(parentId);

            List<ContentCategory> list = contentCategoryService.queryByWhere(contentCategory);

            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
        }


        //返回500
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

}
