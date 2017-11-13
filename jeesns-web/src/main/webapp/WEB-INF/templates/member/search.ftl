<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>找人 - ${SITE_NAME} - Powered By cubc-luntan</title>
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
                <div class="content-wrapper">
                    <section class="content-header">
                        <h1>会员列表(${model.page.totalCount})</h1>
                    </section>
                    <section class="content">
                        <div class="row">
                                <div class="col-md-4 float-left">
                                    <form action="/member/searchByName" method="get">
                                        <div class="input-group">
                                            <input type="text" class="form-control" name="key">
                                            <span class="input-group-btn">
                                                        <button class="btn btn-success" type="submit"><i class="icon-search"></i></button>
                                                    </span>
                                        </div>
                                    </form>
                                </div>
                            <div class="col-xs-12">
                                <div class="box box-primary">
                                    <div class="box-header">
                                        <h3 class="box-title">
                                        </h3>
                                    </div>
                                    <div class="box-body table-responsive no-padding">
                                        <table class="table table-hover">
                                            <thead>
                                            <tr>
                                                <th>用户名</th>
                                                <th>工号</th>
                                                <th>手机号码</th>
                                                <th>性别</th>
                                                <th>部门</th>
                                                <th>部门编码</th>
                                                <th>职位</th>
                                                <th>描述</th>
                                                <th>微信号</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <div class="items">
                                            <#list model.data as member>
                                            <tr>
                                                <td>
                                                    <div class="avatar btn-group nav-username">
                                                        <img src='${uploadCubcPath}${basePath}${member.avatar}' class="img-circle" width="25px" height="25px" >
                                                    </div>
                                                    <strong><a href="/u/${member.id}" target="_blank">${(member.name || member.email)?string(member.name,member.email)}</a></strong>
                                                </td>
                                                <td>${member.code}</td>
                                                <td>${member.phone}</td>
                                                <td>${member.sex}</td>
                                                <td>${member.deptName}</td>
                                                <td>${member.deptCode}</td>
                                                <td>${member.positions}</td>
                                                <td>${member.introduce}</td>
                                                <td>${member.wechat}</td>
                                            </tr>
                                            </#list>
                                            </div>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <ul class="pager pagination pagination-sm no-margin pull-right"
                                    url="/member/searchByName?key=${key}"
                                    currentPage="${model.page.pageNo}"
                                    pageCount="${model.page.totalPage}">
                                </ul>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
        </div>
    </div>
</div>
<#include "/${frontTemplate}/common/footer.ftl"/>
<script type="text/javascript">
//    $("#zr").addClass("active")
//    $("#zra").css("border-radius","25px")
    $("#zra").css("color","#fff")
    $(function () {
        $(".pagination").jeesns_page("jeesnsPageForm");
    });
</script>
</body>
</html>