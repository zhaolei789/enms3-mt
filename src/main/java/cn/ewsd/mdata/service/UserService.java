package cn.ewsd.mdata.service;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.model.User;

import java.util.List;

public interface UserService extends MdataBaseService<User, String> {

    /**
     * 根据uuid获得User
     *
     * @param uuid
     * @return
     */
     User getUserByUuid(String uuid);

    /**
     * 根据uuid删除User
     *
     * @param uuid
     * @return
     */
     Integer deleteUserByUuid(String uuid);

    /**
     * 根据userNameId获得roleId
     */
     String getRoleIdByUserNameId(String userNameId);

    /**
     * 根据userNameId检测用户是否存在
     *
     * @param userNameId
     * @return
     */
     Integer checkUserExistByUserNameId(String userNameId);

     User doUserLogin(User user);

    /**
     * @param orgId
     * @param position
     * @return cn.ewsd.ucenter.model.User
     * @functionName getUser
     * @description 根据组织机构ID和职位获取用户
     */
    User getUserByOrgIdAndPosition(String orgId, String position);

    User getListByNameAndOrgid(String userDutyName, String userOrgId);

    Integer updateByUuidAndIsDel(String uuid);

    int updateByPasswordAndUserNameId(String password, String userNameId);

    Integer updateByPasswordAndUuid(String enPassword, String s);

    /**
     * @MethodName getListByUserNameId 新增用户验证判断是否用户存在
     * @Description TODO
     * @Param userNameId 
     * @Return cn.ewsd.mdata.model.User
     */
    User getListByUserNameId(String userNameId);

    /**
     * @MethodName getListByUserNameIdAndUuid  更新时判断用户是否存在
     * @Description TODO
     * @Param userNameId 
 * @Param uuid 
     * @Return cn.ewsd.mdata.model.User
     */
    User getListByUserNameIdAndUuid(String userNameId, String uuid);

    User getListById(String id);

    Integer updateByUsernameAndNameAndUuid(String username, String name, String uuid);

    PageSet<User> getPageSet(PageParam pageParam, String filterSort);

     User getUserByUserNameId(String userNameId);

    User getUserByCuuid(String myUuid);

    String getListByDataAuth(String currentUserNameId);

    Integer getCountByRemoteUser(String remoteUser);

    User getDetailByHql(String hql);

    Integer getCountByHql(String userNameId, String passwordEncode);

    User getDetailByLikeUserAndPass(String userNameId, String passwordEncode);

    List<User> getLimitedListByHql(String q, int i, int i1);

    List<User> getListByHql(String hql);

    List<User> getLimitedListByQ(String q);

    Integer getCountBySql(String userNameId, String passwordEncode);

    List<User> selectAllList();

    /**
     * 根据uuid修改性别，年龄，电子邮箱，固定电话，联系电话
     * @Description TODO
     * @Param uuid
     * @Param sex
     * @Param age
     * @Param telephone
     * @Param email
     * @Param cellphon batchAuthorizationSave
     * @Return java.lang.Integer
     */
    Integer updateSexAndAgeAndTelephoneAndEmailAndCellphonByUuid(String uuid, String sex, int age, String telephone, String email, String cellphon);

    /**
     *批量授权
     * @param user 要批量授权的用户
     * @param dataAuth  数据权限
     * @param userGroup 角色权限
     * @return
     */
    Integer batchAuthorizationSave(String user, String[] dataAuth, String[] userGroup);

    /**
     * 根据openId查询用户信息
     * @param openid
     * @return
     */
    User getUserByOpenid(String openid);

    Integer updateCellphone(String openid, String cellphone);

    /**
     * @MethodName getDataByTablesAndField 根据字段和表查询数据
     * @Description TODO
     * @Param tables  表
     * @Param field  字段
     * @Return java.util.List<cn.ewsd.mdata.model.User>
     */
    List<User> getDataByTablesAndField(String tables, String field);

    User getUserByOpenid2(String openid);

    Integer updateUserOpenid(String openid, String uuid);

    Integer cleanOpenidByOpenid(String openid);

    List<User> queryListByFS(String filterSort);

    List<User> getListByIds(String[] uuids);

    List<User> getListByUserNameIds(String[] userNameIds);

    Integer updateTokenByUserId(String assessToken,String refreshToken,String uuid);

    Integer updateTokenAndTenantIdByUserId(String assessToken,String refreshToken,String tenantId,String uuid);

    String getNewAssessTokenByTenantId(String tenantId);

    Integer executeDeleteUserByTenantId(String[] uuids,String tenantId);

    Integer executeUnDeleteUserByTenantId(String[] uuids,String tenantId);

    User getUserByUnionId(String unionid);
}