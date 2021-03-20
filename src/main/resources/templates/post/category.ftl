<#--文章分类-->
<#include "/inc/layout.ftl" />
<@layout "博客内容">
  <#include "/inc/header-panel.ftl" />
  <div class="layui-container">
    <div class="layui-row layui-col-space15">
      <div class="layui-col-md8">
        <div class="fly-panel" style="margin-bottom: 0;">

          <div class="fly-panel-title fly-filter">
            <a href="" class="layui-this">综合</a>
          </span>
          </div>

          <@posts categoryId=currentCategoryId pn=pn size=2>
            <ul class="fly-list">
                <#list results.records as post>
                  <@plisting post></@plisting>
                </#list>
            </ul>

            <@paging results></@paging>
          </@posts>
        </div>
      </div>
      <#include "/inc/right.ftl" />
    </div>
  </div>
  <script>
    layui.cache.page = 'jie'
  </script>
</@layout>


