package com.zach.manage.service.impl;

import com.zach.manage.mapper.ContentCategoryMapper;
import com.zach.manage.pojo.ContentCategory;
import com.zach.manage.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class ContentCategoryServiceImpl extends BaseServiceImpl<ContentCategory> implements ContentCategoryService  {
    @Autowired
    private ContentCategoryMapper contentCategoryMapper;

    @Override
    public ContentCategory saveContentCategory(ContentCategory contentCategroy) {
        //1、保存内容分类
        contentCategroy.setSortOrder(100);
        contentCategroy.setIsParent(false);//非父节点---叶子节点
        saveSelective(contentCategroy);

        //2、更新父节点的isParent为true
        ContentCategory parent = new ContentCategory();
        parent.setId(contentCategroy.getParentId());
        parent.setIsParent(true);
        updateSelective(parent);

        //3、返回
        return contentCategroy;
    }

    @Override
    public void deleteContentCategory(ContentCategory contentCategroy) {
        //1、删除当前节点及其子孙节点
        List<Long> ids = new ArrayList<Long>();
        ids.add(contentCategroy.getId());
        //1.1、递归获取当前节点的所有子孙节点
        getCategoryIds(ids, contentCategroy.getId());
        //1.2、批量删除
        deleteByIds(ids.toArray(new Long[]{}));

        //2、判断当前删除的节点的的父节点是否还为父节点（父节点是否还有其它子节点，如果没有其它子节点则更新父节点为叶子节点）
        ContentCategory param = new ContentCategory();
        param.setParentId(contentCategroy.getParentId());//查询兄弟节点
        int count = queryCountByWhere(param);
        if(count == 0) {
            //没有其它兄弟节点，也就是父节点没有其它子节点所以要更新父节点为叶子节点
            ContentCategory parent = new ContentCategory();
            parent.setId(contentCategroy.getParentId());
            parent.setIsParent(false);
            updateSelective(parent);
        }
    }

    /**
     * 根据分类id查询该分类的所有子孙分类的id，并设置到一个集合
     * @param ids id集合
     * @param categoryId 分类id
     */
    private void getCategoryIds(List<Long> ids, Long categoryId) {
        //查询当前节点的子节点
        ContentCategory param = new ContentCategory();
        param.setParentId(categoryId);
        List<ContentCategory> list = queryByWhere(param);
        if(list != null && list.size() > 0) {
            for (ContentCategory cc : list) {
                ids.add(cc.getId());
                getCategoryIds(ids, cc.getId());
            }
        }
    }
}
