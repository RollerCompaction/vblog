package com.vincent.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.vincent.entity.Category;
import com.vincent.service.CategoryService;
import com.vincent.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;
import java.util.List;

/**
 * 项目启动类
 * 项目已启动就会进行初始化
 */
@Component
public class ContextStartup implements ApplicationRunner, ServletContextAware {
    @Autowired
    CategoryService categoryService;

    ServletContext servletContext;

    @Autowired
    PostService postService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 分类信息，项目已启动就初始化分类信息
        List<Category> categories = categoryService.list(new QueryWrapper<Category>()
                .eq("status", 0)
        );
        servletContext.setAttribute("categorys",categories);

        //初始化本周热议缓存
        postService.initWeekRank();
    }

    @Override
    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }
}
