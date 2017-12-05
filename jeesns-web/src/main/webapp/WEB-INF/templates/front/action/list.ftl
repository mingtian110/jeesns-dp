<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>动态 - ${SITE_NAME} - Powered By cubc-luntan</title>
    <meta name="keywords" content="${SITE_KEYS}"/>
    <meta name="description" content="${SITE_DESCRIPTION}"/>
    <meta name="author" content="cubc-luntan"/>
    <link href="${basePath}/res/common/css/zui.min.css" rel="stylesheet">
    <link href="${basePath}/res/front/css/app.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${basePath}/res/common/js/html5shiv.min.js"></script>
    <script src="${basePath}/res/common/js/respond.min.js"></script>
    <![endif]-->
    <script src="${basePath}/res/common/js/jquery-2.1.1.min.js"></script>
    <script src="${basePath}/res/common/js/bootstrap.min.js"></script>
    <script src="${basePath}/res/plugins/layer/layer.js"></script>
    <script src="${basePath}/res/common/js/jquery.form.js"></script>
    <script src="${basePath}/res/common/js/jeesns.js"></script>
    <script src="${basePath}/res/common/js/extendPagination.js"></script>
    <script src="${basePath}/res/modules/mem.js"></script>
    <script src="${basePath}/res/plugins/js-emoji/emoji.js"></script>
    <script src="${basePath}/res/common/js/jquery.timeago.js"></script>
</head>

<body class="gray-bg">
<#include "/${frontTemplate}/common/header.ftl"/>
<div class="container">
    <div class="main-content">
        <div class="row">
            <div class="col-md-12 white-bg m-b-10">
                <div class="items">
                <#if model.page.totalCount==0>
                <p class="text-muted text-center" ></br></br></br>
                    <i class="icon-edit-sign"></i>
                    <i class="icon-edit"></i>
                    <i class="icon-2x"></i>
                    <i class="icon-3x"></i>
                    <i class="icon-4x"></i>
                    <i class="icon-5x"></i>
                    <i class="icon-adjust"></i>
                    <i class="icon-align-justify"></i>
                    <i class="icon-align-left"></i>
                    <i class="icon-alipay"></i>
                    <i class="icon-alipay-square"></i>
                    <i class="icon-anchor"></i>
                    <i class="icon-android"></i>
                    <i class="icon-angle-down"></i>
                    <i class="icon-angle-left"></i>
                    <i class="icon-angle-right"></i>
                    <i class="icon-apple"></i>
                    <i class="icon-archive"></i>
                    <i class="icon-area-chart"></i>
                    <i class="icon-backward"></i>
                    <i class="icon-ban-circle"></i>
                    <i class="icon-arrow-down"></i>
                    <i class="icon-barcode"></i>
                    <i class="icon-beaker"></i>
                    <i class="icon-"></i>
                    暂无关注...</p>
                <#else>
                    <#list model.data as actionLog>
                        <div class="comment">
                            <a href="${basePath}/u/${actionLog.member.id}" class="avatar" target="_blank">
                                <img src="${uploadCubcPath}${basePath}${actionLog.member.avatar!''}" class="icon-camera-retro icon-2x">
                            </a>
                            <div class="content">
                                <div class="pull-right text-muted timeago" datetime="${actionLog.createTime?string('yyyy-MM-dd HH:mm:ss')}"></div>
                                <div>
                                    <a href="${basePath}/u/${actionLog.member.id}" target="_blank">
                                        <strong><a href="${basePath}/u/${actionLog.member.id}">${actionLog.member.name}</a> </strong>于${actionLog.createTime?string('yyyy-MM-dd HH:mm')}${actionLog.action.log}：<br/>
                                    </a>
                                </div>
                                <div class="text emoji-render-content">
                                    <#if actionLog.type==1>
                                        <a href="${basePath}/article/detail/${actionLog.foreignId}"
                                           target="_blank">${actionLog.remark}</a>
                                    <#elseif actionLog.type==2>
                                        <p>${actionLog.remark}</p>
                                        <a href="${basePath}/weibo/detail/${actionLog.foreignId}"
                                           target="_blank">查看</a>
                                    <#elseif actionLog.type==4>
                                        <a href="${basePath}/group/topic/${actionLog.foreignId}"
                                           target="_blank">${actionLog.remark}</a>
                                    </#if>
                                </div>
                            </div>
                        </div>
                    </#list>
                </#if>
                </div>
                <ul class="pager pagination pagination-sm no-margin pull-right"
                    url="${basePath}/action/list"
                    currentPage="${model.page.pageNo}"
                    pageCount="${model.page.totalPage}">
                </ul>
            </div>
        </div>
    </div>
</div>
<#include "/${frontTemplate}/common/footer.ftl"/>
<script type="text/javascript">
//    $("#dt").addClass("active")
//    $("#dta").css("border-radius","25px")
    $("#dta").css("color","#fff")
    $(function () {
        $(".pagination").jeesns_page("jeesnsPageForm");
    });
</script>
</body>
</html>