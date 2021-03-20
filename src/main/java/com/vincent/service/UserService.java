package com.vincent.service;

import com.vincent.common.lang.Result;
import com.vincent.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.vincent.shiro.AccountProfile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Vincent
 * @since 2021-02-03
 */
public interface UserService extends IService<User> {

    Result register(User user);

    AccountProfile login(String username, String valueOf);

}
