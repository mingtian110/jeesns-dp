<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>个人中心 - ${SITE_NAME} - Powered By cubc-luntan</title>
    <meta name="keywords" content="${SITE_KEYS}"/>
    <meta name="description" content="${SITE_DESCRIPTION}"/>
    <meta name="author" content="cubc-luntan"/>
    <link href="${basePath}/res/common/css/zui.min.css" rel="stylesheet">
    <link href="${basePath}/res/common/css/bootstrap.min.css" rel="stylesheet">
    <#--<link href="${basePath}/res/common/css/font-awesome.min.css" rel="stylesheet">-->
    <link href="${basePath}/res/common/css/jeesns.css" rel="stylesheet">
    <link href="${basePath}/res/common/css/jeesns-skin.css" rel="stylesheet">
    <link href="${basePath}/res/plugins/emoji/css/emoji.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${basePath}/res/common/js/html5shiv.min.js"></script>
    <script src="${basePath}/res/common/js/respond.min.js"></script>
    <![endif]-->
    <script src="${basePath}/res/common/js/jquery-2.1.1.min.js"></script>
    <script src="${basePath}/res/common/js/bootstrap.min.js"></script>
    <script src="${basePath}/res/plugins/layer/layer.js"></script>
    <script src="${basePath}/res/common/js/jquery.form.js"></script>
    <script src="${basePath}/res/common/js/jeesns.js"></script>
    <script src="${basePath}/res/modules/weibo.js"></script>
    <script src="${basePath}/res/plugins/emoji/js/underscore-min.js"></script>
    <script src="${basePath}/res/plugins/emoji/js/editor.js"></script>
    <script src="${basePath}/res/plugins/emoji/js/emojis.js"></script>
    <script src="${basePath}/res/common/js/extendPagination.js"></script>
</head>

<body class="gray-bg">
<#include "/member/common/header.ftl"/>
<div class="wrapper wrapper-content">
    <div class="member-banner" style="background-image: url(${basePath}/res/common/images/member_banner.png);">
        <div class="attempts"></div>
        <div class="container">
            <div class="container content">
                <div class="left">
                    <div class="avatar">
                        <img src="${uploadCubcPath}${basePath}${loginUser.avatar}" class="img-circle" width="80px" height="80px"/>
                    </div>
                    <div class="info">
                        <div class="name">
                        ${loginUser.name}
                        <#if loginUser.sex=='女'>
                            <span class="sex"><i class="fa fa-venus"></i></span>
                        <#elseif loginUser.sex=='男'>
                            <span class="sex"><i class="fa fa-mars"></i></span>
                        <#else>
                            <span class="sex"><i class="fa fa-intersex"></i></span>
                        </#if>
                        </div>
                        <p>${loginUser.website}</p>
                        <p><i class="icon-spin icon-star main-text-color"></i>说明:${loginUser.introduce}</p>
                        <p class="operator">
                            <a class="btn btn-info btn-outline member-follows" href="${basePath}/member/editInfo">
                                <i class="fa fa-edit"></i> 编辑个人资料
                            </a>
                        </p>
                    </div>
                </div>
                <div class="right">
                    <div class="follows">
                        <span>关注</span>
                        <a href="${basePath}/u/${loginUser.id}/home/follows">${loginUser.follows}</a>
                    </div>
                    <div class="fans">
                        <span>粉丝</span>
                        <a href="${basePath}/u/${loginUser.id}/home/fans">${loginUser.fans}</a>
                    </div>
                    <div class="follows">
                        <span>积分</span>
                        <a href="${basePath}/member/scoreDetail/list">${loginUser.score}</a>
                    </div>
                    <div class="login-info">
                        加入时间:${loginUser.createTime?string('yyyy-MM-dd')}
                        最近登录:<#if loginUser.currLoginTime??>${loginUser.currLoginTime?string('yyyy-MM-dd')}<#else>未登陆过</#if>

                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="container">
        <div class="row">
            <div class="ibox">
                <div class="ibox-content float-left">
                    <div class="col-sm-2">
                        <div class="float-e-margins">
                            <div class="feed-activity-list">

                                <a href="${basePath}/member/message">
                                    <div class="feed-element">
                                        <div class="media-body">
                                            私信
                                        </div>
                                    </div>
                                </a>
                                <a href="${basePath}/u/${loginUser.id}">
                                    <div class="feed-element">
                                        <div class="media-body">
                                            动态
                                        </div>
                                    </div>
                                </a>
                                <a href="${basePath}/u/${loginUser.id}/home/fans">
                                    <div class="feed-element">
                                        <div class="media-body">
                                            粉丝
                                        </div>
                                    </div>
                                </a>
                                <a href="${basePath}/u/${loginUser.id}/home/notice">
                                    <div class="feed-element">
                                        <div class="media-body">
                                            通知 <#if unReadNoticeNum &gt; 0><i class="fa icon-bell" style="color: red;"></i>${unReadNoticeNum}</#if>
                                        </div>
                                    </div>
                                </a>
                                <a href="${basePath}/u/${loginUser.id}/home/follows">
                                    <div class="feed-element">
                                        <div class="media-body">
                                            关注
                                        </div>
                                    </div>
                                </a>
                                <a href="${basePath}/u/${loginUser.id}/home/article">
                                    <div class="feed-element">
                                        <div class="media-body">
                                            文章
                                        </div>
                                    </div>
                                </a>
                                <a href="${basePath}/u/${loginUser.id}/home/groupTopic">
                                    <div class="feed-element">
                                        <div class="media-body">
                                            群帖
                                        </div>
                                    </div>
                                </a>
                                <a href="${basePath}/u/${loginUser.id}/home/weibo">
                                    <div class="feed-element">
                                        <div class="media-body">
                                        ${WEIBO_ALIAS}
                                        </div>
                                    </div>
                                </a>
                                <a href="${basePath}/u/${loginUser.id}/home/group">
                                    <div class="feed-element">
                                        <div class="media-body">
                                            关注群组
                                        </div>
                                    </div>
                                </a>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-10">
                        <div class="ibox float-e-margins">
                            <div class="ibox-title">
                                <h5>动态</h5>
                            </div>
                            <div>
                                <div class="feed-activity-list">
                                <#list actionLogModel.data as actionLog>
                                    <div class="feed-element">
                                        <a href="${basePath}/u/${actionLog.member.id}" class="pull-left">
                                            <img alt="image" class="img-circle"
                                                 src="${uploadCubcPath}${basePath}${actionLog.member.avatar!''}">
                                        </a>
                                        <div class="media-body ">
                                            <small class="pull-right text-navy">${actionLog.createTime?string('yyyy-MM-dd HH:mm:ss')}</small>
                                            <strong>${actionLog.member.name}</strong>${actionLog.action.log}：<br/>
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
                                            <br>
                                            <div class="actions">
                                            </div>
                                        </div>
                                    </div>
                                </#list>
                                    <div class="box-footer clearfix">
                                        <ul class="pagination pagination-sm no-margin pull-right"
                                            url="${basePath}/member/"
                                            currentPage="${actionLogModel.page.pageNo}"
                                            pageCount="${actionLogModel.page.totalPage}">
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
<#include "/member/common/footer.ftl"/>
<script type="text/javascript">
    $(function () {
        $(".pagination").jeesns_page("jeesnsPageForm");
    });
</script>
</body>
</html>