<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    .articles {
        font-size: 13px;
        list-style-image: none;
        list-style: none;
        padding-left: 0px;
    }
    .contact li{
        line-height: 30px;
    }

    .divider {
        height: 150px;
        border-left: dashed 1px #fff;
        padding-left: 20px;
        margin-left: 15px;
    }
</style>
<footer class="footer">
    <div class="container hidden-xs hidden-sm">
        <div class="row">
            <div class="col-lg-5">
                <h2><i class="fa fa-info-circle"></i> 关于我们</h2>
                <p style="font-size: 13px;line-height: 1.8em;">
                    佐佑科技秉承开拓创新的精神，研发优秀的产品，提供优质的服务，
                    致力于中国企业信息化建设，为您树立企业形象，建立自己的品牌，
                    在创意上紧紧贴合现代时尚，在设计上牢牢锁住目标用户群，
                    给用户以最直观的印象和深刻体验，我们将为您创造不一样的设计作品，期待与您合作！
                </p>
            </div>
            <div class="col-lg-5">
                <div class="divider">
                    <h2><i class="fa fa-id-card-o"></i> 联系我们</h2>
                    <ul class="articles contact">
                        <li><i class="fa fa-fax"></i> ${config_siteConfig.cfgCompanyTelephone}</li>
                        <li><i class="fa fa-phone"></i> ${config_siteConfig.cfgCompanyMobile}</li>
                        <li><i class="fa fa-envelope-o"></i> ${config_siteConfig.cfgCompanyEmail}</li>
                        <li><i class="fa fa-home"></i> ${config_siteConfig.cfgCompanyAdress}</li>
                    </ul>
                </div>
            </div>
            <div class="col-lg-2">
                <div class="divider">
                    <h2><i class="fa fa-weixin"></i> 微信公众号</h2>
                    <img src="/static/images/qrcode.png" width="120" height="120">
                </div>
            </div>
        </div>
    </div>
    <hr>
    <div class="container">
        <span class="pull-left">All Rights Reserved © 2017. ${config_siteConfig.cfgCompanyNameShort}</span>
        <span class="pull-right hidden-xs">
            Powered By <a href="${sysConfig.cfgSystemCompanyWebsite}" target="_blank">${sysConfig.cfgSystemCompanyNameShort}</a> .
            <a href="http://www.miibeian.gov.cn" target="_blank">${config_siteConfig.cfgSiteBeian}</a>
        </span>
    </div>
</footer>