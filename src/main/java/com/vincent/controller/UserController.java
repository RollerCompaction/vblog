package com.vincent.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.vincent.common.lang.Result;
import com.vincent.entity.Post;
import com.vincent.entity.User;
import com.vincent.entity.UserMessage;
import com.vincent.shiro.AccountProfile;
import com.vincent.util.UploadUtil;
import com.vincent.vo.UserMessageVo;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户控制器
 */
@Controller
public class UserController extends BaseController {

    @Autowired
    UploadUtil uploadUtil;

    /**
     * 用户中心
     * @return
     */
    @GetMapping("/user/home")
    public String home() {

        User user = userService.getById(getProfileId());
        List<Post> posts = postService.list(new QueryWrapper<Post>()
                .eq("user_id", getProfileId())
                // 30天内
                // .gt("created", DateUtil.offsetDay(new Date(), -30))
                .orderByDesc("created")
        );
        req.setAttribute("user", user);
        req.setAttribute("posts", posts);
        return "/user/home";
    }

    /**
     * 基本设置信息回显
     * @return
     */
    @GetMapping("/user/set")
    public String set() {
        User user = userService.getById(getProfileId());
        req.setAttribute("user", user);
        return "/user/set";
    }

    /**
     * 基本设置内容修改
     * @param user
     * @return
     */
    @ResponseBody
    @PostMapping("/user/set")
    public Result doSet(User user) {

        // 更新头像
        if (StrUtil.isNotBlank(user.getAvatar())) {
            User temp = userService.getById(getProfileId());
            temp.setAvatar(user.getAvatar());
            userService.updateById(temp);

            AccountProfile profile = getProfile();
            profile.setAvatar(user.getAvatar());

            SecurityUtils.getSubject().getSession().setAttribute("profile", profile);

            return Result.success().action("/user/set#avatar");
        }

        // 不更新头像则说明是要更新个人信息

        if (StrUtil.isBlank(user.getUsername())) {
            return Result.fail("昵称不能为空");
        }

        int count = userService.count(new QueryWrapper<User>()
            .eq("username", getProfile().getUsername())
            .ne("id", getProfileId()));

        if (count > 0) {
            return Result.fail("该昵称已被占用");
        }

        User temp = userService.getById(getProfileId());
        temp.setUsername(user.getUsername());
        temp.setGender(user.getGender());
        temp.setSign(user.getSign());
        userService.updateById(temp);

        AccountProfile profile = getProfile();
        profile.setUsername(temp.getUsername());
        profile.setSign(temp.getSign());
        SecurityUtils.getSubject().getSession().setAttribute("profile", profile);
        return Result.success().action("/user/set#info");
    }

    /**
     * 更新头像
     * @param file
     * @return
     * @throws IOException
     */
    @ResponseBody
    @PostMapping("/user/upload")
    public Result uploadAvatar(@RequestParam(value = "file") MultipartFile file) throws IOException {
        return uploadUtil.upload(UploadUtil.type_avatar, file);
    }

    // 修改密码
    @ResponseBody
    @PostMapping("/user/repass")
    public Result repass(String nowpass, String pass, String repass) {
        if(!pass.equals(repass)) {
            return Result.fail("两次密码不相同");
        }

        User user = userService.getById(getProfileId());

        String nowPassMd5 = SecureUtil.md5(nowpass);
        if(!nowPassMd5.equals(user.getPassword())) {
            return Result.fail("当前密码不正确");
        }

        user.setPassword(SecureUtil.md5(pass));
        userService.updateById(user);

        return Result.success().action("/user/set#pass");

    }

    /**
     * 个人中心首页
     * @return
     */
    @GetMapping("/user/index")
    public String index() {
        return "/user/index";
    }

    @ResponseBody
    @GetMapping("/user/public")
    public Result userP() {
        IPage page = postService.page(getPage(), new QueryWrapper<Post>()
                .eq("user_id", getProfileId())
                .orderByDesc("created"));

        return Result.success(page);
    }

    /**
     * 收藏文章
     * @return
     */
    @ResponseBody
    @GetMapping("/user/collection")
    public Result collection() {
        IPage page = postService.page(getPage(), new QueryWrapper<Post>()
                .inSql("id", "select post_id from user_collection where user_id=" + getProfileId())
        );
        return Result.success(page);
    }

    /**
     * 消息管理
     * @return
     */
    @GetMapping("/user/mess")
    public String mess() {
        IPage<UserMessageVo> page = messageService.paging(getPage(), new QueryWrapper<UserMessage>()
            .eq("to_user_id", getProfileId())
            .orderByDesc("created")
        );

        //把消息改成已读状态
        List<Long> ids = new ArrayList<>();
        for (UserMessageVo messageVo : page.getRecords()) {
            if (messageVo.getStatus() == 0) {
                ids.add(messageVo.getId());
            }
        }

        //批量修改成已读
        messageService.updateToReaded(ids);

        req.setAttribute("pageData", page);
        return "/user/mess";
    }

    /**
     * 删除消息
     * @param id
     * @param all
     * @return
     */
    @ResponseBody
    @PostMapping("/message/remove")
    public Result msgRemove(Long id,
                            @RequestParam(defaultValue = "false") Boolean all) {
        boolean remove = messageService.remove(new QueryWrapper<UserMessage>()
                .eq("to_user_id", getProfileId())
                .eq(!all, "id", id));
        return remove? Result.success(null):Result.fail("删除失败");
    }

    /**
     * 消息数量
     * @return
     */
    @ResponseBody
    @RequestMapping("/message/nums/")
    public Map msgNums() {
        int count = messageService.count(new QueryWrapper<UserMessage>()
            .eq("to_user_id", getProfileId())
            .eq("status", "0")
        );
        return MapUtil.builder("status", 0).put("count", count).build();
    }
}
