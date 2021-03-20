<#--网站头部-->
<div class="fly-header layui-bg-black" xmlns:shiro="http://java.sun.com/xml/ns/javaee">
    <div class="layui-container">
        <a class="fly-logo" href="/">
            <span style="font-size: 23px; color: #5DB276">Vincent 博客网站</span>
        </a>

        <ul class="layui-nav fly-nav-user">

            <!-- 未登入的状态 -->
            <@shiro.guest>
            <li class="layui-nav-item">
                <a class="iconfont icon-touxiang layui-hide-xs" href="/login"></a>
            </li>
            <li class="layui-nav-item">
                <a href="/login">登入</a>
            </li>
            <li class="layui-nav-item">
                <a href="/register">注册</a>
            </li>
            </@shiro.guest>

            <!-- 登入后的状态 -->
            <@shiro.user>
            <li class="layui-nav-item">
              <a class="fly-nav-avatar" href="javascript:;">
                <cite class="layui-hide-xs"><@shiro.principal property="username" /></cite>
                <i class="iconfont icon-renzheng layui-hide-xs"></i>
                <img src="<@shiro.principal property="avatar" />">
              </a>
              <dl class="layui-nav-child">
                <dd><a href="/user/set"><i class="layui-icon">&#xe620;</i>基本设置</a></dd>
                <dd><a href="/user/mess"><i class="iconfont icon-tongzhi" style="top: 4px;"></i>我的消息</a></dd>
                <dd><a href="/user/home"><i class="layui-icon" style="margin-left: 2px; font-size: 22px;">&#xe68e;</i>我的主页</a></dd>
                <hr style="margin: 5px 0;">
                <dd><a href="/user/logout/" style="text-align: center;">退出</a></dd>
              </dl>
            </li>
            </@shiro.user>
        </ul>
    </div>
</div>