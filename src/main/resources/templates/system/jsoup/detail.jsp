<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <%@ include file="../../common/common/head-bootstrap.jsp" %>
    <title></title>
</head>
<body>
<div class="container visible-lg-block">

    <div class="row margin-top-10">
        <div class="col-md-12">
            <table class="table table-bordered">
                <thead>
                <th>传阅标题</th>
                <th>创建人</th>
                </thead>
                <tbody>
                <c:forEach items="${articles}" var="article">
                    <tr>
                        <td>
                            <a href="${article.articleHref}">${article.articleTitle}</a>
                        </td>
                        <td>
                            <a href="${article.authorHref}">${article.author}</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
<script type="text/javascript">
    function openWin(id) {
        //window.open("http://oa.ewsd.cn:9999/iOffice/prg/if/ifShowMsgAddUp.aspx?sysFlag=1&msgserid=" + id, '传阅详情', "fullscreen=1");
        var url = "/system/jsoup/detail?sysFlag=1&msgserid=" + id;                             //转向网页的地址;
        var name = '传阅详情';                            //网页名称，可为空;
        var iWidth = 1280;                          //弹出窗口的宽度;
        var iHeight = 600;                         //弹出窗口的高度;
        //获得窗口的垂直位置
        var iTop = (window.screen.availHeight - 30 - iHeight) / 2;
        //获得窗口的水平位置
        var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
        window.open(url, name, 'height=' + iHeight + ',,innerHeight=' + iHeight + ',width=' + iWidth + ',innerWidth=' + iWidth + ',top=' + iTop + ',left=' + iLeft + ',status=no,toolbar=no,menubar=no,location=no,resizable=no,scrollbars=0,titlebar=no');
        // window.open("AddScfj.aspx", "newWindows", 'height=100,width=400,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
        //window.location.href = url;
    }

    function openWin2(id) {
        var obj = new Object();
        obj.value = "3";
        obj.name = "4";
        obj.sew = "5";
        var url = "http://oa.ewsd.cn:9999/iOffice/prg/if/ifShowMsgAddUp.aspx?sysFlag=1&msgserid=" + id;
        str = window.showModalDialog(url, obj, "dialogWidth=1200px;dialogHeight=600px");
    }
</script>
</body>
</html>