<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>我的私信 - ${SITE_NAME} - Powered By cubc-luntan</title>
    <meta name="keywords" content="${SITE_KEYS}"/>
    <meta name="description" content="${SITE_DESCRIPTION}"/>
    <meta name="author" content="cubc-luntan"/>
    <link href="${basePath}/res/common/css/zui.min.css" rel="stylesheet">
    <link href="${basePath}/res/front/css/app.css" rel="stylesheet">

<#--<link href="${basePath}/res/common/css/bootstrap.min.css" rel="stylesheet">-->
<#--<link href="${basePath}/res/common/css/font-awesome.min.css" rel="stylesheet">-->
<#--<link href="${basePath}/res/plugins/emoji/css/emoji.css" rel="stylesheet">-->
    <link href="${basePath}/res/common/css/jeesns.css" rel="stylesheet">
<#--<link href="${basePath}/res/common/css/jeesns-skin.css" rel="stylesheet">-->
    <link href="${basePath}/res/plugins/gallery/css/blueimp-gallery.min.css" rel="stylesheet">
    <link href="${basePath}/res/plugins/wangeditor/wangEditor.css" rel="stylesheet">
    <!--[if lt IE 9]>
    <script src="${basePath}/res/common/js/html5shiv.min.js"></script>
    <script src="${basePath}/res/common/js/respond.min.js"></script>
    <![endif]-->
    <script src="${basePath}/res/common/js/jquery-2.1.1.min.js"></script>
    <script src="${basePath}/res/common/js/zui.min.js"></script>
    <script src="${basePath}/res/common/js/bootstrap.min.js"></script>
    <script src="${basePath}/res/plugins/layer/layer.js"></script>
    <script src="${basePath}/res/common/js/jquery.form.js"></script>
    <script src="${basePath}/res/common/js/jeesns.js"></script>
    <script type="text/javascript" src="${basePath}/res/plugins/wangeditor/wangEditor.js"></script>
    <script src="${basePath}/res/plugins/gallery/js/jquery.blueimp-gallery.min.js"></script>
    <script src="${basePath}/res/plugins/jquery-confirm.js"></script>

    <script type="text/javascript">
        var basePath = "${basePath}";
        var uploadServer = "${basePath}/uploadImage";
        var loginMemberId = ${loginUser.id};
        var base = "${basePath}";
        var uploadCubcPath = "${uploadCubcPath}";
    </script>
    <script src="${basePath}/res/modules/message.js"></script>
</head>
<body class="gray-bg">
<#include "/member/common/header.ftl"/>
<#--<div class="wrapper wrapper-content  animated fadeInRight">-->
<div class="container">
    <div class="main-content">
        <div class="row">
            <div class="col-md-12">
                <div class="ibox chat-view">
                    <div class="ibox-title">
                        <small class="pull-right text-muted" id="user-name"></small>
                    <#--<a id="refreshMessage"target="_parent" href="${basePath}/member/message?mid=0">私信</a>--><i class="icon-hand-right main-text-color "></i>(如对方长时间不回复,可点击提醒发送短信给对方,每日短信限量三条,请谨慎使用)

                    </div>
                    <div class="ibox-content">
                        <div class="row">
                            <div class="col-lg-3">
                                <div class="chat-users">
                                    <div class="users-list" id="users-list">

                                    </div>
                                    <div class="load-more">
                                        <a href="javascript:void(0)" class="contacts-load-more-a"
                                           onclick="contactsLoadMore()" style="display: none;">加载更多...</a>
                                    </div>
                                </div>

                            </div>
                            <div class="col-md-9 ">
                                <div class="chat-discussion">
                                    <div class="chat-load-more">
                                        <a href="javascript:void(0)" class="message-load-more-a"
                                           onclick="messageLoadMore()"
                                           style="display: none;">加载更多...</a>
                                    </div>
                                    <div class="chat-discussion-content">

                                    </div>
                                    <div class="no-message">
                                        <i class="icon-5x icon-cubes main-text-color" ></i>
                                        <br/>暂无聊天记录...</div>
                                </div>
                                <div class="send-message-area">
                                    <div id="div1" class="toolbar">
                                    </div>
                                    <div id="div2" class="text">
                                    </div>
                                    <textarea class="form-control message-input" name="content" id="content"
                                              placeholder="可以快捷键复制粘贴图片"></textarea>
                                    <button class="btn btn-info pull-right sendMessage" id="wangSend">发送</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<#--</div>-->
<#include "/member/common/footer.ftl"/>
<script type="text/javascript">
    function bbimg(o){
        var zoom=parseInt(o.style.zoom, 10)||100;zoom+=event.wheelDelta/12;if (zoom>0) o.style.zoom=zoom+'%';
        return false;
    }
    $(".navbar").css("padding-top", "0px")
//    $("#sx").addClass("active")
//    $("#sxa").css("border-radius", "25px")
    $("#sxa").css("color", "#fff")
    var E = window.wangEditor;
    var editor = new E('#div1', '#div2');
    // 为当前的editor配置密钥
//    editor.customConfig.mapAk = 'P5aSDyfWZhDlROBVyqzoozFlIS9FCoee';
    editor.customConfig.uploadImgServer = "${basePath}/uploadWangEditorImage";
    editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024;
    editor.customConfig.uploadImgMaxLength = 5
    editor.customConfig.debug = true
    editor.customConfig.uploadFileName = "file"
    var $content = $('#content');
    editor.customConfig.menus =
            [
                'head',  // 标题
                'bold',  // 粗体
                'italic',  // 斜体
                'underline',  // 下划线
                'strikeThrough',  // 删除线
                'foreColor',  // 文字颜色
                'backColor',  // 背景颜色
                'link',  // 插入链接
                'list',  // 列表
                'justify',  // 对齐方式
                'quote',  // 引用
                'image',  // 插入图片
                'emotion',
                'table',  // 表格
                'video',  // 插入视频
                'code',  // 插入代码
                'undo',  // 撤销
                'redo'  // 重复
            ]
    document.getElementById('wangSend').addEventListener('click', function () {
        editor.txt.clear()
    })
    editor.customConfig.uploadImgHeaders = {
        'Accept': '*/*'
    }
    editor.customConfig.onchange = function (html) {
        // 监控变化，同步更新到 textarea
        $content.val(html)
    }
    editor.create();
    editor.txt.append($content);
    $("#content").hide();

    var wangSend = document.getElementsByTagName("body");
    wangSend[0].onkeydown = keydownpassword;
    function keydownpassword(e) {
        e = e ? e : window.event;
        var keyCode = e.which ? e.which : e.keyCode;
        // console.log(keyCode);
        if (keyCode == 13) {
            if(memberid == -1){
                jeesnsDialog.errorTips("请先选择发送的对象");
                return;
            }
            var content =$("#content").val();
            if (content == "") {
                jeesnsDialog.errorTips("请输入私信内容");
                return;
            }
            $.ajax({
                url: base + "/member/sendMessage",
                type: "post",
                data: {
                    memberId:memberid,
                    content: content
                },
                cache: false,
                dataType: "json",
                timeout: 5000,
                beforeSend: function () {
                    index = jeesnsDialog.loading();
                },
                error: function () {
                    jeesnsDialog.close(index);
                    jeesnsDialog.errorTips("请求失败")
                },
                success: function (res) {
                    jeesnsDialog.close(index);
                    if (res.code == 0) {
                        jeesnsDialog.successTips(res.message);
                        messageRecords(1,1);
                    } else {
                        jeesnsDialog.errorTips(res.message);
                    }
                }
            });
            editor.txt.clear()
            $("iframe").contents().find("p").html("");
        }
    }
</script>
</body>
</html>