<div id="wrapper">
    <div id="page-wrapper" class="gray-bg">
        <div class="row border-bottom">
            <nav class="navbar navbar-static-top" role="navigation">
                <div class="container">
                    <div class="navbar-header m-navbar-header">
                        <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                                data-target="#navbar" aria-expanded="false" aria-controls="navbar">
                            <span class="sr-only">cubc-luntan</span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand jeesns-logo" href="${basePath}/"><img
                                src="${uploadCubcPath}${basePath}${SITE_LOGO}" height="50px"
                                style="border-radius:8px;"/></a>
                    </div>
                    <div id="navbar" class="navbar-collapse collapse">
                        <ul class="nav navbar-nav">
                            <li id="sy"><a id="sya" href="${basePath}/" style="font-size: medium"><i
                                    class="icon-home main-text-color" style="vertical-align: middle"></i>&nbsp;首页</a>
                            </li>
                            <li id="wz"><a id="wza" href="${basePath}/article/list" style="font-size: medium"><i
                                    class="icon-list main-text-color" style="vertical-align: middle"></i>&nbsp; 文章</a>
                            </li>
                            <li id="gc"><a id="gca" href="${basePath}/weibo/list" style="font-size: medium"><i
                                    class="icon-compass  main-text-color"
                                    style="vertical-align: middle"></i>&nbsp;广场</a></li>
                            <li id="qz"><a id="qza" href="${basePath}/group/" style="font-size: medium"><i
                                    class="icon-stack  main-text-color"
                                    style="vertical-align: middle"></i>&nbsp;${GROUP_ALIAS}</a></li>
                            <li id="dt"><a id="dta" href="${basePath}/action/list" style="font-size: medium"><i
                                    class="icon-plane main-text-color" style="vertical-align: middle"></i>&nbsp;动态</a>
                            </li>
                            <li id="tz"><a id="tza" href="${basePath}/group/topicList" style="font-size: medium"><i
                                    class="icon-tags  main-text-color" style="vertical-align: middle"></i>&nbsp;帖子</a>
                            </li>
                            <li id="sx"><a id="sxa" href="${basePath}/member/message" style="font-size: medium"><i
                                    class="icon-envelope main-text-color" style="vertical-align: middle"></i>&nbsp;私信
                                &nbsp;<#if unReadMessageNum &gt; 0><i class="fa icon-comments"></i></#if>
                            <li id="zr"><a id="zra" href="${basePath}/member/search" style="font-size: medium"><i
                                    class="icon-search main-text-color" style="vertical-align: middle"></i>&nbsp;找人</a>
                            </li>
                        </ul>
                        <ul class="nav navbar-top-links navbar-right">
                            <div class="nav navbar-nav navbar-nav-right">
                            <#if loginUser == null>
                                <li><a href="${basePath}/member/login">登录</a></li>
                                <li><a href="${basePath}/member/register">注册</a></li>
                            <#else>
                                <div class="btn-group nav-username">
                                    <a href="/member/" style="background: #393D49;" ><img src="${uploadCubcPath}${basePath}${loginUser.avatar}" class="img-circle"
                                                                                          width="25px" height="25px" style="margin-top: 1px;margin-right:5px;"/></a>
                                    <a class="header-action-link" href="/member/" style="background: #393D49;">
                                    ${loginUser.name}
                            <#--<#if unReadMessageNum &gt; 0><i class="icon-comments"></i></#if>-->
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
//                                window.setInterval(showalert, 500);
//                                function showalert()
//                                {
//                                    $.ajax({
//                                        type : "GET",
//                                        async:false,
//                                        url : "http://localhost:8086/member/messageNum?mid="+1,
//                                        success : function(result){
//                                        },
//                                        error:function(){
//                                        }
//                                    });
//                                }
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