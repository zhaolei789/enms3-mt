<html>
<head>
    <style type="text/css">
        body {
            margin: 1.0in 1.25in 1.0in 1.25in;
            font-size: 14pt;
        }

        .title {
            text-align: center;
            font-weight: bold;
            font-size: 16pt;
        }

        .p1 {
            text-indent: 2em;
            line-height: 1.5em;
        }

        table {
            width: 100%;
        }

        table tr {
            height: 30px;
        }

        .leftColumn {
            width: 50%;
            text-align: right
        }

        .field {
            color: orangered;
        }
    </style>
    <title>钉钉认证申请公函</title>
</head>
<body>
<p><img src="${serverUrl}/app/system/public/image/logo_banner_red.png"/></p>
<p class="title">钉钉认证申请公函导出</p>
<br/>
<p class="p1">
    本企业授权的企业管理员姓名为：<span class="field">${user.userName!}</span>，本企业承诺遵守钉钉服务协议和钉钉公约，保证填写信息真实有效并授权企业管理员代表本企业阅读并确认《钉钉认证申请公函》。
</p>
<br/><br/><br/><br/><br/>
<table>
    <tr>
        <td class="leftColumn">企业管理员姓名：</td>
        <td class="field">${user.userName!}</td>
    </tr>
    <tr>
        <td class="leftColumn">管理员身份证号：</td>
        <td class="field">${user.idCard!}</td>
    </tr>
    <tr>
        <td class="leftColumn">日期（盖公章）：</td>
        <td class="field">${date!}</td>
    </tr>
</table>
<br/><br/>
<table border="1" cellspacing="0" cellpadding="0" style="border-collapse:collapse">
    <tr>
        <td>企业管理员姓名：</td>
        <td>${user.userName!}</td>
        <td>企业管理员姓名：</td>
        <td>${user.userName!}</td>
    </tr>
    <tr>
        <td>管理员身份证号：</td>
        <td>${user.idCard!}</td>
        <td>管理员身份证号：</td>
        <td>${user.idCard!}</td>
    </tr>
    <tr>
        <td>日期（盖公章）：</td>
        <td>${date!}</td>
        <td>日期（盖公章）：</td>
        <td>${date!}</td>
    </tr>
    <tr>
        <td>企业管理员姓名：</td>
        <td>${user.userName!}</td>
        <td>企业管理员姓名：</td>
        <td>${user.userName!}</td>
    </tr>
    <tr>
        <td>管理员身份证号：</td>
        <td>${user.idCard!}</td>
        <td>管理员身份证号：</td>
        <td>${user.idCard!}</td>
    </tr>
    <tr>
        <td>日期（盖公章）：</td>
        <td>${date!}</td>
        <td>日期（盖公章）：</td>
        <td>${date!}</td>
    </tr>
</table>
</body>
</html>