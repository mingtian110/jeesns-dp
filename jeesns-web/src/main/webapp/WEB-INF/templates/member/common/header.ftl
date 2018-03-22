<div id="wrapper">
    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation">
                <div class="container">
                    <div class="navbar-header jeesns-logo">
                        <button aria-controls="navbar" aria-expanded="false" data-target="#navbar"
                                data-toggle="collapse"
                                class="navbar-toggle collapsed" type="button">
                            <i class="fa fa-reorder"></i>
                        </button>
                        <a href="${basePath}/" class="navbar-brand"><img src="${uploadCubcPath}${basePath}${SITE_LOGO}"
                                                                         height="50px" style="border-radius:8px;"/></a>
                    </div>
                    <div class="navbar-collapse collapse" id="navbar">
                        <ul class="nav navbar-nav">
                            <li id="sy"><a id="sya" href="${basePath}/" style="font-size: medium"><i
                                    class="icon-home main-text-color" style="vertical-align: middle"></i>&nbsp;首页</a>
                            </li>
                            <li id="wz"><a id="wza" href="${basePath}/article/list" style="font-size: medium"><i
                                    class="icon-list main-text-color" style="vertical-align: middle;color: #0499e2"></i>&nbsp;
                                文章</a></li>
                            <li id="gc"><a id="gca" href="${basePath}/weibo/list" style="font-size: medium"><i
                                    class="icon-compass  main-text-color"
                                    style="vertical-align: middle;color: #0499e2"></i>&nbsp;广场</a></li>
                            <li id="qz"><a id="qza" href="${basePath}/group/" style="font-size: medium"><i
                                    class="icon-stack  main-text-color"
                                    style="vertical-align: middle;color: #0499e2"></i>&nbsp;${GROUP_ALIAS}</a></li>
                            <li id="dt"><a id="dta" href="${basePath}/action/list" style="font-size: medium"><i
                                    class="icon-plane main-text-color"
                                    style="vertical-align: middle;color: #0499e2"></i>&nbsp;动态</a></li>
                            <li id="tz"><a id="tza" href="${basePath}/group/topicList" style="font-size: medium"><i
                                    class="icon-tags  main-text-color"
                                    style="vertical-align: middle;color: #0499e2"></i>&nbsp;帖子</a></li>
                            <li id="sx"><a id="sxa" href="${basePath}/member/message" style="font-size: medium"><i
                                    class="icon-envelope maizzzzzzzzzn-text-color"
                                    style="vertical-align: middle;color: #0499e2"></i>&nbsp;私信
                                &nbsp;<#if unReadMessageNum &gt; 0><i class="fa icon-comments"></i></#if>
                            <li id="zr"><a id="zra" href="${basePath}/member/search" style="font-size: medium"><i
                                    class="icon-search main-text-color"
                                    style="vertical-align: middle;color: #0499e2"></i>&nbsp;找人</a></li>

                        <#--<li id="sy">-->
                        <#--<a id="sya" href="${basePath}/">首页</a>-->
                        <#--</li>-->
                        <#--<li id="wz">-->
                        <#--<a id="wza" href="${basePath}/article/list">文章</a>-->
                        <#--</li>-->
                        <#--<li id="gc">-->
                        <#--<a id="gca" href="${basePath}/weibo/list">广场</a>-->
                        <#--</li>-->
                        <#--<li id="qz">-->
                        <#--<a id="qza" href="${basePath}/group/">${GROUP_ALIAS}</a>-->
                        <#--</li>-->
                        <#--<li id="dt">-->
                        <#--<a id="dta"href="${basePath}/action/list">动态</a>-->
                        <#--</li>-->
                        <#--<li id="tz"><a id="tza" href="${basePath}/group/topicList">帖子</a></li>-->
                        <#--<li id="sx">-->
                        <#--<a id="sxa" href="${basePath}/member/message">私信-->
                        <#--&nbsp;<#if unReadMessageNum &gt; 0><i class="fa icon-comments"></i></#if>-->
                        <#--&lt;#&ndash;${(unReadMessageNum > 0)?string(unReadMessageNum,"")}&ndash;&gt;-->
                        <#--</a>-->
                        <#--</li>-->
                        <#--<li id="zr">-->
                        <#--&lt;#&ndash;<div class="col-md-4 float-left">&ndash;&gt;-->
                        <#--&lt;#&ndash;<form action="/member/searchByName" method="get">&ndash;&gt;-->
                        <#--&lt;#&ndash;<div class="input-group">&ndash;&gt;-->
                        <#--&lt;#&ndash;<input type="text" class="form-control" name="key">&ndash;&gt;-->
                        <#--&lt;#&ndash;<span class="input-group-btn">&ndash;&gt;-->
                        <#--&lt;#&ndash;<button class="btn btn-success" type="submit"><i class="icon-search"></i></button>&ndash;&gt;-->
                        <#--&lt;#&ndash;</span>&ndash;&gt;-->
                        <#--&lt;#&ndash;</div>&ndash;&gt;-->
                        <#--&lt;#&ndash;</form>&ndash;&gt;-->
                        <#--&lt;#&ndash;</div>&ndash;&gt;-->
                        <#--<a id="zra" href="${basePath}/member/search"> 找人</a>-->
                        <#--</li>-->
                        </ul>
                        <ul class="nav navbar-top-links navbar-right">
                            <div class="header-action-btn">
                            <#if loginUser == null>
                                <li><a href="${basePath}/member/login">登录</a></li>
                                <li><a href="${basePath}/member/register">注册</a></li>
                            <#else>
                                <div class="btn-group nav-username">
                                    <a href="/member/" style="background: #393D49;"><img
                                            src="${uploadCubcPath}${basePath}${loginUser.avatar}" class="img-circle"
                                            width="25px" height="25px" style="margin-top: 1px;margin-right:5px;"/></a>
                                    <a class="header-action-link" href="/member/" style="background: #393D49;">
                                    ${loginUser.name}
                                        <#if unReadNoticeNum &gt; 0><i class="fa icon-bell"
                                                                       style="color: red;"></i>${unReadNoticeNum}</#if>
                                    </a>
                                    <ul class="dropdown-menu">
                                        <li><a href="${basePath}/member/editInfo">设置</a></li>
                                        <#if loginUser?? && loginUser.isAdmin &gt; 0>
                                            <li><a href="${managePath}/" target="_blank">管理</a></li>
                                        </#if>
                                        <li class="divider"></li>
                                        <li><a href="${basePath}/member/logout">退出</a></li>
                                    </ul>
                                    <script>
                                        $(function () {
                                            $(".nav-username").hover(function () {
                                                $(this).addClass("open");
                                            }, function () {
                                                $(this).removeClass("open");
                                            });
                                        })


                                    </script>
                                </div>
                            </#if>
                            </div>
                        </ul>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</div>
<link rel="stylesheet" type="text/css" href="${basePath}/res/css/iconfont.css">
<link rel="stylesheet" type="text/css" href="${basePath}/res/css/waiter.css">
<div class="wuyou-waiter">
    <ul class="waiter-ul">
        <li class="wuyou-shop">
            <a class="icon-group" href="${basePath}/member/message?mid=1" target="_blank"></a>
            <div class="waiter-title">管理员</div>
        </li>
        <li class="wuyou-contact">
            <a class=" icon-qq" href="${basePath}/member/message?mid=1" target="_blank"></a>
            <div class="waiter-title">CUBC</div>
        </li>
        <li class="wuyou-contact">
            <a class=" icon-question" href="${basePath}/member/message?mid=1" target="_blank"></a>
            <div class="waiter-title">IT热线</div>
        </li>
        <li class="wuyou-top">
            <a class="iconfont icon-Upward" href="#"></a>
            <div class="waiter-title">回到顶部</div>
        </li>
    </ul>
</div>
