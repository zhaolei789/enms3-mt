package cn.ewsd.mdata.service.impl;

import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.common.utils.easyui.PageUtils;
import cn.ewsd.mdata.mapper.UserMapper;
import cn.ewsd.mdata.model.User;
import cn.ewsd.mdata.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("userServiceImpl")
public class UserServiceImpl extends MdataBaseServiceImpl<User, String> implements UserService {


    @Resource
    private UserMapper userMapper;

//    @Resource
//    public void setUserDao(IUcenterBaseDao<User, String> userDao) {
//        super.setBaseDao(userDao);
//    }

    @Override
    public User getUserByUuid(String uuid) {
        return userMapper.selectByPrimaryKey(uuid);
    }

    @Override
    public Integer deleteUserByUuid(String uuid) {
        return userMapper.delete(getUserByUuid(uuid));
    }

    @Override
    public String getRoleIdByUserNameId(String userNameId) {
        return userMapper.getUserGroup(userNameId);
    }

    @Override
    public Integer checkUserExistByUserNameId(String userNameId) {
        //  String hql = "from User where userNameId = '" + userNameId + "'";
        User user = userMapper.getListByUserNameId(userNameId);
        if (null != user) {
            return 1;
        }
        return 0;
    }

    @Override
    public User doUserLogin(User user) {
        return null;
    }

    @Override
    public User getUserByOrgIdAndPosition(String orgId, String position) {
        // String hql = "from User where orgId = '" + orgId + "' and orgName = '" + position + "'  " ;
        return userMapper.getUserByOrgIdAndPosition(orgId, position);
    }

    @Override
    public User getListByNameAndOrgid(String userDutyName, String userOrgId) {
        //String hql = "from User where orgName = '" + userDutyName + "' and orgId = '" + userOrgId + "'";
        return userMapper.getListByUserDutyNameAndOrgId(userDutyName, userOrgId);

    }

    @Override
    public Integer updateByUuidAndIsDel(String uuid) {
        return userMapper.updateByUuidAndIsDel(uuid);
    }

    @Override
    public int updateByPasswordAndUserNameId(String password, String userNameId) {
        return userMapper.updateByPasswordAndUserNameId(password, userNameId);
    }

    @Override
    public Integer updateByPasswordAndUuid(String enPassword, String s) {
        return userMapper.updateByPasswordAndUuid(enPassword, s);
    }

    @Override
    public User getListByUserNameId(String userNameId) {
        return userMapper.getListByUserNameId(userNameId);
    }

    @Override
    public User getListByUserNameIdAndUuid(String userNameId, String uuid) {
        return userMapper.getListByUserNameIdAndUuid(userNameId,uuid);
    }


    @Override
    public User getListById(String id) {
        return userMapper.getListById(id);
    }

    @Override
    public Integer updateByUsernameAndNameAndUuid(String username, String name, String uuid) {
        return userMapper.updateByUsernameAndNameAndUuid(username, name, uuid);
    }

    @Override
    public PageSet<User> getPageSet(PageParam pageParam, String filterSort) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getRows());
        List<User> list = userMapper.getPageSet(filterSort);
        PageInfo<User> pageInfo = new PageInfo<>(list);
        return PageUtils.getPageSet(pageInfo);
    }

    @Override
    public User getUserByUserNameId(String userNameId) {
        return userMapper.getUserByUserNameId(userNameId);
    }

    @Override
    public User getUserByCuuid(String myUuid) {
        return userMapper.getUserByCuuid(myUuid);
    }

    @Override
    public String getListByDataAuth(String currentUserNameId) {
        // String hql = "select  dataAuth from User where userNameId = '" + currentUserNameId + "'";
        return userMapper.getDataAuth(currentUserNameId);

    }

    @Override
    public Integer getCountByRemoteUser(String remoteUser) {
        return userMapper.getCountByRemoteUser(remoteUser);
    }

    @Override
    public User getDetailByHql(String hql) {
        return userMapper.getDetailByHql(hql);
    }

    @Override
    public Integer getCountByHql(String userNameId, String passwordEncode) {
        return userMapper.getCountByHql(userNameId, passwordEncode);
    }

    @Override
    public User getDetailByLikeUserAndPass(String userNameId, String passwordEncode) {
        return userMapper.getDetailByLikeUserAndPass(userNameId, passwordEncode);
    }

    @Override
    public List<User> getLimitedListByHql(String q, int i, int i1) {
        return userMapper.getLimitedListByHql(1, i, i1);
    }

    @Override
    public List<User> getListByHql(String hql) {
        return userMapper.getListByHql(hql);
    }

    @Override
    public List<User> getLimitedListByQ(String q) {
        return userMapper.getLimitedListByQ(q);
    }

    @Override
    public Integer getCountBySql(String userNameId, String passwordEncode) {
        return userMapper.getCountBySql(userNameId, passwordEncode);
    }

    @Override
    public List<User> selectAllList() {
        return userMapper.selectAllList();
    }

    @Override
    public Integer updateSexAndAgeAndTelephoneAndEmailAndCellphonByUuid(String uuid, String sex, int age, String telephone, String email, String cellphon) {
        return userMapper.updateSexAndAgeAndTelephoneAndEmailAndCellphonByUuid(uuid, sex, age, telephone, email, cellphon);
    }

    /**
     * 批量授权
     *
     * @param user      要批量授权的用户
     * @param dataAuth  数据权限
     * @param userGroup 角色权限
     * @return
     * @throws Exception
     */
    @Override
    public Integer
      batchAuthorizationSave(String user, String[] dataAuth, String[] userGroup) {
        Gson gson = new Gson();
        java.lang.reflect.Type t = new TypeToken<List<User>>() {
        }.getType();
        List<User> list = gson.fromJson(user, t);
        int result = 0;
        for (User user1 : list) {//遍历用户信息
//            if(dataAuth != null){
//                for (String da : dataAuth) {//遍历数据权限
//                    user1.setDataAuth(user1.getDataAuth().concat("," + da));
//                }
//            }
//            if(userGroup != null){
//                for (String ug : userGroup) {//遍历用户权限
//                    user1.setUserGroup(user1.getUserGroup().concat("," + ug));
//                }
//            }


//            String[] test1 = user1.getUserGroup().split(",");
            if(userGroup != null && userGroup.length > 0 ) {
                user1.setUserGroup(StringUtils.join(getDistinct(userGroup), ","));
            }
//            String[] testDa = user1.getDataAuth().split(",");
            if(dataAuth != null && dataAuth.length > 0 ) {
                user1.setDataAuth(StringUtils.join(getDistinct(dataAuth), ","));
            }
            result = userMapper.updateByPrimaryKeySelective(user1);
            result++;
        }
        return result > 0 ? 1 : 0;
    }

    @Override
    public User getUserByOpenid(String openid) {
        return userMapper.getUserByOpenid(openid);
    }

    @Override
    public Integer updateCellphone(String openid, String cellphone) {
        return userMapper.updateCellphone(openid,cellphone);
    }


    //去重
    static String[] getDistinct(String num[]) {
        List<String> list = new java.util.ArrayList<String>();
        for (int i = 0; i < num.length; i++) {
            if (!list.contains(num[i])) {
                list.add(num[i]);
            }
        }
        return list.toArray(new String[0]);
    }

    @Override
    public List<User> getDataByTablesAndField(String tables, String field) {
        return userMapper.getDataByTablesAndField(tables,field);
    }

    @Override
    public User getUserByOpenid2(String openid) {
        return userMapper.getUserByOpenid2(openid);
    }

    @Override
    public Integer updateUserOpenid(String openid, String uuid) {
        return userMapper.updateUserOpenid(openid,uuid);
    }

    @Override
    public Integer cleanOpenidByOpenid(String openid) {
        return userMapper.cleanOpenidByOpenid(openid);
    }

    @Override
    public List<User> queryListByFS(String filterSort) {
        return userMapper.getPageSet(filterSort);
    }

    @Override
    public List<User> getListByIds(String[] uuids) {
        return userMapper.getListByIds(uuids);
    }

    @Override
    public List<User> getListByUserNameIds(String[] userNameIds) {
        return userMapper.getListByUserNameIds(userNameIds);
    }

    @Override
    public Integer updateTokenByUserId(String assessToken, String refreshToken, String uuid) {
        return userMapper.updateTokenByUserId(assessToken, refreshToken, uuid);
    }

    @Override
    public Integer updateTokenAndTenantIdByUserId(String assessToken, String refreshToken, String tenantId, String uuid) {
        return userMapper.updateTokenAndTenantIdByUserId(assessToken, refreshToken, tenantId, uuid);
    }

    @Override
    public String getNewAssessTokenByTenantId(String tenantId) {
        return userMapper.getNewAssessTokenByTenantId(tenantId);
    }

    @Override
    public Integer executeDeleteUserByTenantId(String[] uuids, String tenantId) {
        return userMapper.executeDeleteUserByTenantId(uuids, tenantId);
    }

    @Override
    public Integer executeUnDeleteUserByTenantId(String[] uuids, String tenantId) {
        return userMapper.executeUnDeleteUserByTenantId(uuids, tenantId);
    }

    @Override
    public User getUserByUnionId(String unionid) {
        return userMapper.getUserByUnionId(unionid);
    }

}