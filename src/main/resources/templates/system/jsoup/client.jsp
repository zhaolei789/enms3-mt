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
    <title></title>
</head>
<body>
<div class="container visible-lg-block">

    <div class="row margin-top-10">
        <div class="col-md-12">
            <%!
                void addCookie(HttpServletResponse response, String name, String value, String domain, String path) {
                    Cookie cookie = new Cookie(name.trim(), value.trim());
                    cookie.setDomain(domain);
                    cookie.setMaxAge(24 * 60 * 60);// 设置为24小时
                    cookie.setPath(path);
                    System.out.println("已添加===============");
                    response.addCookie(cookie);
                }
            %>

            <%
                addCookie(response, "JSESSIONID", request.getAttribute("JSESSIONID").toString(), ".ewsd.cn", "/");
                addCookie(response, "LtpaToken", request.getAttribute("LtpaToken").toString(), ".ewsd.cn", "/");
            %>
        </div>
    </div>
</div>
<script type="text/javascript">
    window.location.href = "http://portal.ewsd.cn/sys/portal/page.jsp";
</script>
</body>
</html>