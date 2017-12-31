<div class="footer">
    <div class="container">
        <div class="row">
            <div class="col-sm-12">
                <div class="text-center">
                ${SITE_NAME}
                </div>
                <div class="text-center">
                    <strong>Powered By <a href="http://www.cubc.deppon.com" target="_blank">cubc-luntan</a> ${SITE_COPYRIGHT} ${SITE_ICP}</strong>
                    ${SITE_TONGJI}
                </div>
                <div id="pop" style="display:none;">
                    <style type="text/css">
                        *{margin:0;padding:0;}
                        #pop{background:#fff;width:260px;border:1px solid #e0e0e0;font-size:12px;position: fixed;right:10px;bottom:10px;}
                        #popHead{line-height:32px;background:#f6f0f3;border-bottom:1px solid #e0e0e0;position:relative;font-size:12px;padding:0 0 0 10px;}
                        #popHead h2{font-size:14px;color:#666;line-height:32px;height:32px;}
                        #popHead #popClose{position:absolute;right:10px;top:1px;}
                        #popHead a#popClose:hover{color:#f00;cursor:pointer;}
                        #popContent{padding:5px 10px;}
                        #popTitle a{line-height:24px;font-size:14px;font-family:'微软雅黑';color:#333;font-weight:bold;text-decoration:none;}
                        #popTitle a:hover{color:#f60;}
                        #popIntro{text-indent:24px;line-height:160%;margin:5px 0;color:#666;}
                        #popMore{text-align:right;border-top:1px dotted #ccc;line-height:24px;margin:8px 0 0 0;}
                        #popMore a{color:#f60;}
                        #popMore a:hover{color:#f00;}
                    </style>
                    <div id="popHead">
                        <a id="popClose" title="关闭">关闭</a>
                        <h2>温馨提示</h2>
                    </div>
                    <div id="popContent">
                        <dl>
                            <dt id="popTitle"><a href="http://yanue.info/" target="_blank">这里是参数</a></dt>
                            <dd id="popIntro">这里是内容简介</dd>
                        </dl>
                        <p id="popMore"><a href="http://yanue.info/" target="_blank">查看 »</a></p>
                    </div>
                </div>
                <!--右下角pop弹窗 end-->
                <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
            </div>
        </div>
    </div>
</div>
<script src="${basePath}/res/modules/yanue.pop.js"></script>
<script type="text/javascript">
    setInterval(function () {
        $.ajax({
            type: "GET",
            async: false,
            url: "/member/haveMsg",
            dataType: "json",
            success: function (result) {
                if (result.data == "0") {

                } else {
                    var pop = new Pop("${loginUser.name}您好",
                            "/member/message",
                            "您有新消息,请点击私信查阅");
                }
            },
            error: function () {
                return
            }
        });
    }, 3000);
</script>