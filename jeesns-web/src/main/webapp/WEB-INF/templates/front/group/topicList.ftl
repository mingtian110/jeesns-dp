<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${GROUP_ALIAS} - ${SITE_NAME} - Powered By cubc-luntan</title>
    <meta name="keywords" content="${SITE_KEYS}"/>
    <meta name="description" content="${SITE_DESCRIPTION}"/>
    <meta name="author" content="cubc-luntan"/>
    <link rel="shortcut icon" href="favicon.ico">
    <link href="${basePath}/res/common/css/zui.min.css" rel="stylesheet">
    <link href="${basePath}/res/front/css/app.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${basePath}/res/common/js/html5shiv.min.js"></script>
    <script src="${basePath}/res/common/js/respond.min.js"></script>
    <![endif]-->
    <script src="${basePath}/res/common/js/jquery-2.1.1.min.js"></script>
    <script src="${basePath}/res/common/js/zui.min.js"></script>
    <script src="${basePath}/res/plugins/layer/layer.js"></script>
    <script src="${basePath}/res/common/js/jquery.form.js"></script>
    <script src="${basePath}/res/common/js/jeesns.js"></script>
    <script src="${basePath}/res/common/js/extendPagination.js"></script>
</head>
<body class="gray-bg">
<#include "/${frontTemplate}/common/header.ftl"/>
<div class="container">
    <div class="main-content">
        <div class="row">
            <div class="col-md-8">
                <div class="items">
                <#list groupTopicList.data as groupTopic>
                    <div class="item article-list shadow">
                        <div class="item-content">
                            <#if groupTopic.thumbnail??>
                                <div class="media pull-left">
                                    <a href="${basePath}/group/topic/${groupTopic.id}">
                                        <img src="${uploadCubcPath}${basePath}${groupTopic.thumbnail}"
                                             alt="${groupTopic.title}" height="80px" width="120px" style="border-radius:10px;">
                                    </a>
                                </div>
                            </#if>
                            <div class="text">
                                <a href="${basePaht}/group/detail/${groupTopic.group.id}">
                                    <div class="pull-right label label-success">${groupTopic.group.name}</div>
                                </a>
                                <h3><a href="${basePath}/group/topic/${groupTopic.id}">${groupTopic.title}</a></h3>
                                <#if groupTopic.isTop==1>
                                    <span class="label label-badge label-primary">置顶</span>
                                <#elseif groupTopic.isTop==2>
                                    <span class="label label-badge label-success">超级置顶</span>
                                </#if>
                                <#if groupTopic.isEssence==1>
                                    <span class="label label-badge label-danger">精华</span>
                                </#if>
                                <p>
                                    <span class="text-muted"><i class="icon-eye-open" style="color: #00a7d0"></i> ${groupTopic.viewCount} &nbsp; <i
                                            class="icon-time"></i> ${groupTopic.createTime?string('yyyy-MM-dd HH:mm')} <i class="icon-heart" style="color: crimson"></i> ${groupTopic.favor}</span>
                                </p>
                            </div>
                        </div>
                    </div>
                </#list>
                </div>
            </div>

            <div class="col-md-4 float-left">
                <form action="${basePath}/group/topicList" method="get">
                    <div class="input-group">
                        <input type="text" class="form-control" name="key">
                        <span class="input-group-btn">
                            <button class="btn btn-success" type="submit"><i class="icon-search"></i></button>
                        </span>
                    </div>
                </form>
                <div class="panel">
                    <div class="panel-heading">
                        热门帖子
                    </div>
                    <div class="panel-body article-hot-list">
                        <ul>
                        <@group_topic_list cid=0 num=15 day=100; groupTopic>
                            <#list groupTopicList as groupTopic>
                                <li><i class="icon-hand-right main-text-color"></i> <a
                                        href="${basePath}/group/topic/${groupTopic.id}">${groupTopic.title}</a></li>
                            </#list>
                        </@group_topic_list>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "/${frontTemplate}/common/footer.ftl"/>
<script type="text/javascript">
//    $("#tz").addClass("active")
//    $("#tza").css("border-radius","25px")
    $("#tza").css("color","#fff")
    $(function () {
        $(".pagination").jeesns_page("jeesnsPageForm");
    });
</script>
</body>
</html>