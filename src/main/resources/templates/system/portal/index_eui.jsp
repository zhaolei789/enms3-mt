<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="rbac" uri="http://www.ewsd.cn/tags/rbac" %>
<%@ taglib prefix="fun" uri="http://www.ewsd.cn/tags/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="x-ua-compatible" content="IE=5;IE=7;IE=8;"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="edge"/>
    <link type="text/css" rel="stylesheet" id="easyuiTheme"
          href="${staticServer}/static/topjui/themes/<c:out value="${cookie.easyuiThemeName.value}" default="default"/>/easyui.css">
    <link type="text/css" rel="stylesheet" href="${staticServer}/static/topjui/css/icon.css">

    <script type="text/javascript" src="${staticServer}/static/plugins/jquery/jquery.min.js"></script>
    <script type="text/javascript" src="${staticServer}/static/plugins/jquery/jquery.cookie.js"></script>
    <script type="text/javascript" src="${staticServer}/static/plugins/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${staticServer}/static/topjui/js/topjui.config.js"></script>
    <script type="text/javascript" src="${ctx}/static/topjui/js/topjui.all.min.js"></script>
    <script type="text/javascript" src="${staticServer}/static/plugins/easyui/extension/jquery.portal.js"></script>
    <script type="text/javascript" src="${staticServer}/static/plugins/echarts/echarts.min.js"></script>
    <style type="text/css">
        body {
            margin: 0;
            padding: 0;
            font: 14px/normal "microsoft yahei", "Times New Roman", "宋体", Times, serif;
            letter-spacing: 1px;
        }

        a {
            text-decoration: none;
        }

        .panel-header {
            border-radius: 5px 5px 0 0;
        }

        /* 面板标题 */
        .panel-title {
            font-size: 12px;
            height: 24px;
            line-height: 24px;
        }

        /* panel主体字体大小 */
        .panel-body {
            font-size: 14px;
        }

        /* 调整panel图标显示位置 */
        .panel-icon {
            margin-top: -9px;
        }

        .border_top_none {
            border-top: none;
        }

        .portal-list {
            padding: 4px 4px 4px 6px;
        }
    </style>
    <script>
        $(function () {
            $('#pp').portal({
                border: false
            });
            setTimeout(function () {
                $('#pp').portal({
                    border: false
                });
            }, 10);

            $(function () {
                // 页面加载完成后触发echarts图表加载事件
                $(this).trigger(topJUI.eventType.initUI.echarts);
            });
        });
    </script>
</head>

<body>
<!-- layout布局 开始 -->
<div data-toggle="topjui-layout" data-options="fit:true">
    <div data-options="region:'center',title:'',fit:true,split:true,border:false,bodyCls:'border_top_none'">
        <div id="pp" style="overflow: hidden;">
            <div style="width:35%;">
                <div title="公告通知" iconCls="icon-databases" style="height:210px;">
                    <c:forEach items="${notices}" var="notice">
                        <div class="portal-list">
                            <a href="javascript:window.parent.addParentTab({href:'${ctx}/system/article/detail?uuid=${notice.uuid}',title:'${notice.title}'})">${notice.title}</a>
                        </div>
                    </c:forEach>
                </div>
                <div title="最新文章" iconCls="icon-dic" style="height:210px;">
                    <c:forEach items="${documents}" var="document">
                        <div class="portal-list">
                            <a href="javascript:window.parent.addParentTab({href:'${ctx}/system/article/detail?uuid=${document.uuid}',title:'${document.title}'})">${document.title}</a>
                        </div>
                    </c:forEach>
                </div>
                <div title="知识文档" iconCls="icon-basket_put" style="height:210px;">
                    <c:forEach items="${archives}" var="archive">
                        <div class="portal-list">
                            <a href="javascript:window.parent.addParentTab({href:'${ctx}/system/archive/detail?uuid=${archive.uuid}',title:'${archive.title}'})">${archive.title}</a>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <div style="width:35%;">
                <div title="系统用户" iconCls="icon-vcard" style="height:210px;">
                    <table data-toggle="topjui-datagrid"
                           fit="true"
                           border="false"
                           singleSelect="true"
                           fitColumns="true"
                           idField="itemid" url="/ucenter/user/getPageSetData">
                        <thead>
                        <tr>
                            <th field="userNameId" width="100">用户名</th>
                            <th field="userName" width="100">姓名</th>
                            <th field="orgName" width="100">所属机构</th>
                            <th field="mobile" width="100" align="center">联系电话</th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div title="内容分布" iconCls="icon-chart_pie" style="height:430px;">
                    <div id="agePieChartData" data-toggle="topjui-echarts"
                         data-options="type:'pie',url:'${ctx}/system/portal/pieChartData'"
                         style="width:99%;height:99%;"></div>
                </div>
            </div>
            <div style="width:30%;">
                <div title="TopJUI前端框架" iconCls="icon-shield" style="height:210px;">
                    <div class="portal-list">TopJUI前端框架，不用写JS代码的EasyUI!</div>
                    <div class="portal-list">
                        纯HTML调用EasyUI功能组件，专注你的后端业务开发！
                    </div>
                    <div class="portal-list">在线演示：<a href="http://demo.topjui.com" target="_blank">静态演示</a>，可整合不同后端代码</div>
                    <div class="portal-list">开源下载：开源中国<a href="http://git.oschina.net/xvpindex/TopJUI" target="_blank">下载源代码</a></div>
                    <div class="portal-list">开发文档：<a href="http://www.topjui.com/document/index/index.html" target="_blank">官方在线开发文档</a></div>
                    <div class="portal-list">使用交流：技术交流QQ群：593783047</div>
                </div>
                <div title="关于本系统" iconCls="icon-shield" style="height:210px;">
                    <div class="portal-list">系统名称：${sysConfig.cfgSystemName}</div>
                    <div class="portal-list">
                        系统版本：${sysConfig.cfgSystemVersion}
                    </div>
                    <div class="portal-list">技术架构：基于TopJUI + SSHM构建 <a href="http://www.zuoyour.com/emis.html" target="_blank">了解详情</a></div>
                    <div class="portal-list">合作洽谈：service@ewsd.cn</div>
                    <div class="portal-list">技术支持：<a href="http://www.zuoyour.com" target="_blank">湖南佐佑时代科技有限公司</a>
                    </div>
                </div>
                <div title="服务器信息" iconCls="icon-application_xp_terminal"
                     style="height:210px;">
                    <div class="portal-list">服务器地址：${hostAddress}</div>
                    <div class="portal-list">JDK运行版本：${sysProperty.getProperty("java.version")}</div>
                    <div class="portal-list">服务器运行时区：${sysProperty.getProperty("user.timezone")}</div>
                    <div class="portal-list">服务器系统名称：${sysProperty.getProperty("os.name")}</div>
                    <div class="portal-list">服务器系统架构：${sysProperty.getProperty("os.arch")}</div>
                    <div class="portal-list">服务器系统版本：${sysProperty.getProperty("os.version")}</div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>