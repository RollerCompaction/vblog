package com.vincent.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vincent.entity.Post;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vincent.vo.PostVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vincent
 * @since 2021-02-03
 */
public interface PostService extends IService<Post> {

    //1分页信息 2分类 3用户 4置顶 5精选 6排序
    IPage paging(Page page, Long categoryId, Long userId, Integer level, Boolean recommend, String order);

    PostVo selectOnePost(QueryWrapper<Post> wrapper);

    void initWeekRank();

    void incrCommentCountAndUnionForWeekRank(long postId, boolean isIncr);

    void putViewCount(PostVo vo);

}
