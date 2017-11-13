#jeesns与pddl整合失败处理方式
1.jeesns的模块依赖均为编译时,去掉限制
2.mybatis的版本为3.3,提升为3.4
3.pddl和hibernate均依赖了jboss-vfs,去掉了pddl对jboss这的依赖
4.tomcat启动失败:端口占用:1.启动端口:8080,2关闭端口:8005

#长量化
1.请求地址可配置
2.安装至测试环境:tomcat

#问题
concurrent query is not supported on transaction group, transaction group is: CUBC-PINGAN-GROUP
#解决
去掉事务注解

#嵌入式iframe内容获取
$("iframe").contents().find("p").text()
$("iframe").contents().find("p").html()

#对象-字符串
JSON.stringify(content)

#字符串-对象
$.parseJSON( jsonstr )


