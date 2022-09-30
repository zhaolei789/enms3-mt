<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="rapid" uri="http://www.rapid-framework.org.cn/rapid" %>
<%@ taglib prefix="rbac" uri="http://www.ewsd.cn/tags/rbac" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<rapid:override name="head">
    <link href="${staticServer}/static/plugins/ueditor/third-party/SyntaxHighlighter/shCoreDefault.css"
          rel="stylesheet" type="text/css"/>
</rapid:override>

<rapid:override name="title">${oaLeave.type}</rapid:override>

<rapid:override name="body">
    <div style="background-color: #f7f7f7">
        <div class="layui-container">
            <div class="layui-row">
                <div class="layui-col-md12">
                    <div class="layui-card">
                        <div class="layui-card-header align-center"><h3
                                class="font-bolder">${oaLeave.type}</h3></div>
                        <div class="layui-card-body">
                            <div class="list-group text-center">
                                <div class="list-group-item align-center">
                                    <span>
                                        <i class="fa fa-user"></i> ${oaLeave.creator}
                                    </span>
                                    <span>
                                        <i class="fa fa-clock-o"></i> <fmt:formatDate
                                            value="${oaLeave.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                    </span>
                                </div>
                            </div>
                            <div style="text-align: center">
                                    ${oaLeave.startTime}
                            </div>
                            <div style="text-align: center">
                                    ${oaLeave.endTime}
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</rapid:override>

<rapid:override name="script">
    <script type="text/javascript"
            src="${staticServer}/static/plugins/ueditor/third-party/SyntaxHighlighter/shCore.js"></script>
    <script type="text/javascript"
            src="${staticServer}/static/jquery/plugins/jquery.imgAutoSize.js"></script>
    <script type="text/javascript">
        $(function ($) {
            SyntaxHighlighter.all();
            $('.content').imgAutoSize(0);
        });
    </script>
</rapid:override>

<jsp:include page="../../common/common/base_layui.jsp" flush="true"></jsp:include>
