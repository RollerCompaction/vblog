package com.vincent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.vincent.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vincent.vo.CommentVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vincent
 * @since 2021-02-03
 */
public interface CommentService extends IService<Comment> {

    IPage<CommentVo> paging(Page page, Long postId, Long userId, String order);
}
