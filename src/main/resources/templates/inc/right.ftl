<#--右边区域内容-->
<div class="layui-col-md4">


    <dl class="fly-panel fly-list-one">
        <dt class="fly-panel-title">本周热议</dt>
        <@hots>
            <#list results as post>
                <dd>
                    <a href="/post/${post.id}">${post.title}</a>
                    <span><i class="iconfont icon-pinglun1"></i> ${post.commentCount}</span>
                </dd>
            </#list>
        </@hots>
        <!-- 无数据时 -->
        <!--
        <div class="fly-none">没有相关数据</div>
        -->
    </dl>

    <div class="fly-panel">
        <div class="fly-panel-title">
            这里可作为广告区域
        </div>
        <div class="fly-panel-main">

        </div>
    </div>

    <div class="fly-panel fly-link">
        <h3 class="fly-panel-title">友情链接</h3>
        <dl class="fly-panel-main">

        </dl>
    </div>

</div>