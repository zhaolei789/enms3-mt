package cn.ewsd.forest.client;

import cn.ewsd.forest.model.getDeptList.getDeptListObj;
import com.dtflys.forest.annotation.*;

@BaseRequest(
        baseURL = "${mtServerUrl}",
        headers = {
                "Accept: */*",
                "clientType: webapp",
        })
public interface MtDeptClient {

    /**
     * 获取部门列表(树)
     * @param corpId
     * @param accessToken
     * @return
     */
    @Request(
            url = "/api/contact/v1/dept/get_tree?corpId={corpId}",
            headers = {
                    "Authorization: Bearer ${accessToken}"
            }
    )
    String getDeptTreeList(@Var("corpId") String corpId, @Var("accessToken") String accessToken);

    /**
     * 获取企业部门列表
     * @param cropId
     * @param accessToken
     * @return
     */
    @Post(
            url = "/api/contact/v1/dept/getDeptIdsByCorpId",
            headers = {
                    "Authorization: Bearer ${accessToken}"
            },
            dataType = "json"
    )
    getDeptListObj getDeptIdsByCorpId(@JSONBody("cropId") String cropId, @Var("accessToken") String accessToken);


}
