package com.zach.manage.service;

import com.zach.manage.pojo.ContentCategory;

import java.util.List;

public interface ContentCategoryService extends BaseService<ContentCategory> {
    /**
     * 保存内容分类；并更新其父节点为父节点（isParent为true）
     * @param contentCategroy 内容分类
     * @return
     */
    ContentCategory saveContentCategory(ContentCategory contentCategroy);

    /**
     * 删除内容分类
     * @param contentCategroy 内容分类
     * @return
     */
    void deleteContentCategory(ContentCategory contentCategroy);
}
