package com.vincent.template;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vincent.common.templates.DirectiveHandler;
import com.vincent.common.templates.TemplateDirective;
import com.vincent.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 自定义博客列表标签模板
 */
@Component
public class PostsTemplate extends TemplateDirective {
    @Autowired
    PostService postService;

    // 重写 getName（）和 excute（DirectiveHandler handler）
    @Override
    public String getName() {
        return "posts";
    }

    /**
     * 标签有几个参数：categoryId、pn、size、order。
     * 然后封装成了两个类：Page、QueryWrapper，
     * 都是 mybatis-plus 里面的封装类，用于分页和参数查询。
     * @param handler
     * @throws Exception
     */
    @Override
    public void execute(DirectiveHandler handler) throws Exception {
        Integer level = handler.getInteger("level");
        Integer pn = handler.getInteger("pn",1);
        Integer size = handler.getInteger("size",2);
        Long categoryId = handler.getLong("categoryId");

        IPage page = postService.paging(new Page(pn, size), categoryId, null, level, null, "created");

        handler.put(RESULTS,page).render();

    }
}
