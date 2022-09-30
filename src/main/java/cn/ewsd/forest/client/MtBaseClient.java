package cn.ewsd.forest.client;

import cn.ewsd.forest.model.corpInfo.CorpBasicInfoObj;
import cn.ewsd.forest.model.getToken.getTokenData;
import cn.ewsd.forest.model.getToken.getTokenObj;
import cn.ewsd.forest.model.getToken.refreshTokenObj;
import com.dtflys.forest.annotation.BaseRequest;
import com.dtflys.forest.annotation.Request;
import com.dtflys.forest.annotation.Var;

@BaseRequest(
        baseURL = "${mtServerUrl}",
        headers = {
                "Accept: */*",
                "clientType: webapp",
        })
public interface MtBaseClient {

        /**
         * userCode获取token
         * @param code
         * @return
         */
        @Request(
                url = "/api/oauth/getTokenByCode?code={code}&netType=2",
                dataType = "json"
        )
        getTokenObj getTokenByCode(@Var("code") String code);

        /**
         * 刷新token
         * @param refreshToken
         * @param accessToken
         * @return
         */
        @Request(
                url = "/api/refresh/token?refresh_token={refreshToken}",
                headers = {
                        "Authorization: Bearer ${accessToken}"
                },
                dataType = "json"
        )
        refreshTokenObj getRefreshToken(@Var("refreshToken") String refreshToken, @Var("accessToken") String accessToken);

        /**
         * 查询企业基础信息
         * @param corpId
         * @param accessToken
         * @return
         */
        @Request(
                url = "/api/contact/v1/corp/getCorpBasicInfo/{corpId}",
                headers = {
                        "Authorization: Bearer ${accessToken}"
                },
                dataType = "json"
        )
        CorpBasicInfoObj getCorpBasicInfo(@Var("corpId") String corpId, @Var("accessToken") String accessToken);


}
