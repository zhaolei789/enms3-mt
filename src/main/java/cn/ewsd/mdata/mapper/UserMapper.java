package cn.ewsd.mdata.mapper;

import cn.ewsd.mdata.model.User;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {

    List<User> getPageSet(@Param("filterSort") String filterSort);

    Integer updateByUuidAndIsDel(String uuid);

    int updateByPasswordAndUserNameId(@Param("password") String password, @Param("userNameId") String userNameId);

    Integer updateByPasswordAndUuid(@Param("password") String enPassword, @Param("uuid") String s);

    User getListByUserNameId(String userNameId);

    User getListByUserNameIdAndUuid(@Param("userNameId") String userNameId, @Param("uuid") String uuid);

    User getListById(String id);

    Integer updateByUsernameAndNameAndUuid(String username, String name, String uuid);

    User getUserByCuuid(String myUuid);

    String getDataAuth(String currentUserNameId);

    String getUserGroup(String userNameId);

    User getUserByOrgIdAndPosition(String orgId, String position);

    User getListByUserDutyNameAndOrgId(String userDutyName, String userOrgId);

    User getUserByUserNameId(String userNameId);

    Integer getCountByHql(String userNameId, String passwordEncode);

    Integer getCountByRemoteUser(String remoteUser);

    User getDetailByHql(String hql);

    User getDetailByLikeUserAndPass(String userNameId, String passwordEncode);

    List<User> getLimitedListByHql(int i, int i1, int i11);

    List<User> getListByHql(String hql);

    List<User> getLimitedListByQ(String q);

    Integer getCountBySql(@Param("userNameId") String userNameId, @Param("passwordEncode") String passwordEncode);

    List<User> selectAllList();

    /**
     * 根据uuid修改性别，年龄，电子邮箱，固定电话，联系电话
     * @Description TODO
     * @Param uuid
     * @Param sex
     * @Param age
     * @Param telephone
     * @Param email
     * @Param cellphon
     * @Return java.lang.Integer
     * @Author zyj<zhuyongjing@zuoyoutech.com>
     * @Date 2019-01-26 14:40
     */
    Integer updateSexAndAgeAndTelephoneAndEmailAndCellphonByUuid(@Param("uuid") String uuid, @Param("sex") String sex, @Param("age") int age, @Param("telephone") String telephone, @Param("email") String email, @Param("cellphon") String cellphon);

    User getUserByOpenid(@Param("openid") String openid);

    Integer updateCellphone(@Param("openid") String openid, @Param("cellphone") String cellphone);

    List<User> getDataByTablesAndField(@Param("tables") String tables, @Param("field") String field);

    User getUserByOpenid2(String openid);

    Integer updateUserOpenid(@Param("openid") String openid, @Param("uuid") String uuid);

    Integer cleanOpenidByOpenid(@Param("openid") String openid);

    List<User> getListByIds(Object[] var1);

    List<User> getListByUserNameIds(Object[] var1);

    Integer updateTokenByUserId(@Param("assessToken") String assessToken, @Param("refreshToken") String refreshToken, @Param("uuid") String uuid);

    Integer updateTokenAndTenantIdByUserId(@Param("assessToken") String assessToken, @Param("refreshToken") String refreshToken, @Param("tenantId") String tenantId, @Param("uuid") String uuid);

    String getNewAssessTokenByTenantId(@Param("tenantId") String tenantId);

    Integer executeDeleteUserByTenantId(@Param("uuids") String[] uuids,@Param("tenantId") String tenantId);

    Integer executeUnDeleteUserByTenantId(@Param("uuids") String[] uuids,@Param("tenantId") String tenantId);

    User getUserByUnionId(@Param("unionid") String unionid);
}
