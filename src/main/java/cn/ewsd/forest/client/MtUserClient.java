package cn.ewsd.forest.client;

import cn.ewsd.forest.model.getAlluser.GetAllUserResObj;
import cn.ewsd.forest.model.getUserDetail.UserDetailObj;
import cn.ewsd.forest.model.tokenGetUserInfo.tokenUserObj;
import cn.ewsd.forest.model.tokenGetUserInfo2.tokenUserObj2;
import com.dtflys.forest.annotation.*;


@BaseRequest(
        baseURL = "${mtServerUrl}",
        headers = {
                "Accept: */*",
                "clientType: webapp",
        })
public interface MtUserClient {

    /**
     * 获取企业下用户列表
     * @param corpId
     * @param accessToken
     * @return
     */
    @Request(
            url = "/api/contact/v1/user/get_all_user?corpId={corpId}",
            headers = {
                    "Authorization: Bearer ${accessToken}"
            },
            dataType = "json"
    )
    GetAllUserResObj getAlluser(@Var("corpId") String corpId,@Var("accessToken") String accessToken);

    /**
     * 查询用户详情
     * @param userIds
     * @param cropId
     * @param accessToken
     * @return
     */
    @Post(
            url = "/api/contact/v1/user/get_detail",
            headers = {
                    "Authorization: Bearer ${accessToken}"
            },
            dataType = "json"
    )
    UserDetailObj getUserDetail(@JSONBody("userIds") String [] userIds, @JSONBody("cropId") String cropId, @Var("accessToken") String accessToken);

    /**
     * token获取用户信息(公网)
     * @param corpId
     * @param accessToken
     * @return
     */
    @Request(
            url = "/api/contact/v1/orInv/contactV2/get_my_info_organization?corpId={corpId}",
            headers = {
                    "Authorization: Bearer ${accessToken}"
            },
            dataType = "json"
    )
    tokenUserObj getTokenUserInfo1(@Var("corpId") String corpId, @Var("accessToken") String accessToken);

    /**
     * token获取用户信息(内网)
     * @param corpId
     * @param accessToken
     * @return
     */
    @Request(
            url = "/api/contact/v1/account/get_my_info?corpId={corpId}",
            headers = {
                    "Authorization: Bearer ${accessToken}"
            },
            dataType = "json"
    )
    tokenUserObj2 getTokenUserInfo2(@Var("corpId") String corpId, @Var("accessToken") String accessToken);


}
