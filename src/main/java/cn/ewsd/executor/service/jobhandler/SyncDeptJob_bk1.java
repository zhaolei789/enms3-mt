//package cn.ewsd.executor.service.jobhandler;
//
//import cn.ewsd.forest.client.MtDeptClient;
//import cn.ewsd.mdata.model.Organization;
//import cn.ewsd.mdata.model.SysTenant;
//import cn.ewsd.mdata.service.OrganizationService;
//import cn.ewsd.mdata.service.SysTenantService;
//import cn.ewsd.mdata.service.UserService;
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import com.xxl.job.core.context.XxlJobHelper;
//import com.xxl.job.core.handler.annotation.XxlJob;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class SyncDeptJob_bk1 {
//
//    @Autowired
//    private MtDeptClient mtDeptClient;
//    @Autowired
//    private SysTenantService sysTenantService;
//    @Resource
//    private UserService userService;
//    @Resource
//    private OrganizationService organizationService;
//
//
//    /**
//     * 同步组织机构信息
//     * @throws Exception
//     */
//    @XxlJob("deptSyncExecute")
//    public void deptSyncExecute() throws Exception {
//        XxlJobHelper.log("deptSyncExecute start.");
//
//        String access_token;
//        List<SysTenant> tenantList = sysTenantService.queryListByFS("is_del != 1 and status = 1");
//        for (int i = 0; i < tenantList.size(); i++) {
//            String cropid = tenantList.get(i).getUuid();
//            String tenantToken = userService.getNewAssessTokenByTenantId(cropid);
//            Organization organization_ins;
//            Organization organization_upd;
//            if(tenantToken!=null&&!"".equals(tenantToken)) {
//                access_token = tenantToken;
//                JSONObject deptTreeObj=JSON.parseObject(mtDeptClient.getDeptTreeList(cropid,access_token));
//                if(deptTreeObj.getString("msg").equals("ok")){
//                    JSONObject deptTreeData = deptTreeObj.getJSONObject("data");
//
//                    //校验一级部门
//                    String level1_code = deptTreeData.getString("deptId");
//                    Integer level1_id;
//                    Organization organization_one = organizationService.queryObject(deptTreeData.getString("deptId"));
//                    if(organization_one==null){
//                        level1_id = organizationService.getMaxById() + 1;
//                        organization_ins = new Organization();
//                        organization_ins.setUuid(level1_code);
//                        organization_ins.setCreateTime(new Date());
//                        organization_ins.setIsDel(0);
//                        organization_ins.setRemark("");
//                        organization_ins.setAttributes("");
//                        organization_ins.setChecked("");
//                        organization_ins.setTypeValue("公司企业");
//                        organization_ins.setTypeText("1");
//                        organization_ins.setId(level1_id);
//                        organization_ins.setPid(1);
//                        organization_ins.setLevelId(1);
//                        organization_ins.setState("closed");
//                        organization_ins.setText(deptTreeData.getString("deptName"));
//                        organization_ins.setSort(1);
//                        organization_ins.setCode(level1_code);
//                        organization_ins.setLeader("");
//                        organization_ins.setLeaderId("");
//                        organization_ins.setTempLeader("");
//                        organization_ins.setTempLeaderId("");
//                        organization_ins.setAuthStartDate(null);
//                        organization_ins.setAuthEndDate(null);
//                        organization_ins.setStatus("1");
//                        organization_ins.setIconCls("");
//                        organization_ins.setOrgType("company");
//                        organization_ins.setDeptCode("");
//                        organization_ins.setWorkTeam("");
//                        organization_ins.setDeptType2("");
//                        organization_ins.setTenantId(cropid);
//                        organization_ins.setCreateTime(new Date());
//                        organization_ins.setEntryWay(1);
//                        organizationService.insertSelective(organization_ins);
//                    }else {
//                        level1_id = organization_one.getId();
//                        //如果组织机构名称发生了改变
//                        if(organization_one.getIsDel()==1){
//                            organization_upd = new Organization();
//                            organization_upd.setUuid(level1_code);
//                            organization_upd.setIsDel(0);
//                            organizationService.updateByPrimaryKeySelective(organization_upd);
//                        }
//                        if(!deptTreeData.getString("deptName").equals(organization_one.getText())){
//                            organization_upd = new Organization();
//                            organization_upd.setUuid(level1_code);
//                            organization_upd.setText(deptTreeData.getString("deptName"));
//                            organizationService.updateByPrimaryKeySelective(organization_upd);
//                        }
//                    }
//
//                    //处理二级部门
//                    if(deptTreeData.getJSONArray("childDeptTreeResDTOS")!=null&&deptTreeData.getJSONArray("childDeptTreeResDTOS").size()>0){
//                        JSONArray childDeptLevelArray2 = deptTreeData.getJSONArray("childDeptTreeResDTOS");
//                        String [] level2IdArray = new String[childDeptLevelArray2.size()];
//                        for (int j = 0; j < childDeptLevelArray2.size(); j++) {
//                            JSONObject level2Obj = childDeptLevelArray2.getJSONObject(j);
//                            level2IdArray[j] = level2Obj.getString("deptId");
//                        }
//                        if(level2IdArray.length>0){
//                            organizationService.executeDeleteByTenantBatch(level2IdArray,cropid,2,level1_id);//删除不存在的部门
//                            for (int j = 0; j < childDeptLevelArray2.size(); j++) {
//                                JSONObject level2Obj = childDeptLevelArray2.getJSONObject(j);
//                                Integer level2_id;
//                                Organization organization_two = organizationService.queryObject(level2Obj.getString("deptId"));
//                                if(organization_two==null){
//                                    level2_id = organizationService.getMaxById() + 1;
//                                    organization_ins = new Organization();
//                                    organization_ins.setUuid(level2Obj.getString("deptId"));
//                                    organization_ins.setCreateTime(new Date());
//                                    organization_ins.setIsDel(0);
//                                    organization_ins.setRemark("");
//                                    organization_ins.setAttributes("");
//                                    organization_ins.setChecked("");
//                                    organization_ins.setTypeValue("");
//                                    organization_ins.setTypeText("");
//                                    organization_ins.setId(level2_id);
//                                    organization_ins.setPid(level1_id);
//                                    organization_ins.setLevelId(2);
//                                    organization_ins.setState("closed");
//                                    organization_ins.setText(level2Obj.getString("deptName"));
//                                    organization_ins.setSort(j+1);
//                                    organization_ins.setCode(level2Obj.getString("deptId"));
//                                    organization_ins.setLeader("");
//                                    organization_ins.setLeaderId("");
//                                    organization_ins.setTempLeader("");
//                                    organization_ins.setTempLeaderId("");
//                                    organization_ins.setAuthStartDate(null);
//                                    organization_ins.setAuthEndDate(null);
//                                    organization_ins.setStatus("1");
//                                    organization_ins.setIconCls("");
//                                    organization_ins.setOrgType("company");
//                                    organization_ins.setDeptCode("");
//                                    organization_ins.setWorkTeam("");
//                                    organization_ins.setDeptType2("");
//                                    organization_ins.setTenantId(cropid);
//                                    organization_ins.setCreateTime(new Date());
//                                    organization_ins.setEntryWay(1);
//                                    organizationService.insertSelective(organization_ins);
//                                }else {
//                                    level2_id = organization_two.getId();
//                                    if(organization_two.getIsDel()==1){
//                                        organization_upd = new Organization();
//                                        organization_upd.setUuid(level2Obj.getString("deptId"));
//                                        organization_upd.setIsDel(0);
//                                        organizationService.updateByPrimaryKeySelective(organization_upd);
//                                    }
//                                    //如果组织机构名称发生了改变
//                                    if(!level2Obj.getString("deptName").equals(organization_two.getText())){
//                                        organization_upd = new Organization();
//                                        organization_upd.setUuid(level2Obj.getString("deptId"));
//                                        organization_upd.setText(level2Obj.getString("deptName"));
//                                        organizationService.updateByPrimaryKeySelective(organization_upd);
//                                    }
//                                }
//
//                                //处理三级部门
//                                if(level2Obj.getJSONArray("childDeptTreeResDTOS")!=null&&level2Obj.getJSONArray("childDeptTreeResDTOS").size()>0){
//                                    JSONArray childDeptLevelArray3 = level2Obj.getJSONArray("childDeptTreeResDTOS");
//                                    String [] level3IdArray = new String[childDeptLevelArray3.size()];
//                                    for (int k = 0; k < childDeptLevelArray3.size(); k++) {
//                                        JSONObject level3Obj = childDeptLevelArray3.getJSONObject(k);
//                                        level3IdArray[k] = level3Obj.getString("deptId");
//                                    }
//                                    if(level3IdArray.length>0){
//                                        organizationService.executeDeleteByTenantBatch(level3IdArray,cropid,3,level2_id);//删除不存在的部门
//                                        for (int k = 0; k < childDeptLevelArray3.size(); k++) {
//                                            JSONObject level3Obj = childDeptLevelArray3.getJSONObject(k);
//                                            Integer level3_id;
//                                            Organization organization_three = organizationService.queryObject(level3Obj.getString("deptId"));
//                                            if(organization_three==null){
//                                                level3_id = organizationService.getMaxById() + 1;
//                                                organization_ins = new Organization();
//                                                organization_ins.setUuid(level3Obj.getString("deptId"));
//                                                organization_ins.setCreateTime(new Date());
//                                                organization_ins.setIsDel(0);
//                                                organization_ins.setRemark("");
//                                                organization_ins.setAttributes("");
//                                                organization_ins.setChecked("");
//                                                organization_ins.setTypeValue("");
//                                                organization_ins.setTypeText("");
//                                                organization_ins.setId(level3_id);
//                                                organization_ins.setPid(level2_id);
//                                                organization_ins.setLevelId(3);
//                                                organization_ins.setState("closed");
//                                                organization_ins.setText(level3Obj.getString("deptName"));
//                                                organization_ins.setSort(k+1);
//                                                organization_ins.setCode(level3Obj.getString("deptId"));
//                                                organization_ins.setLeader("");
//                                                organization_ins.setLeaderId("");
//                                                organization_ins.setTempLeader("");
//                                                organization_ins.setTempLeaderId("");
//                                                organization_ins.setAuthStartDate(null);
//                                                organization_ins.setAuthEndDate(null);
//                                                organization_ins.setStatus("1");
//                                                organization_ins.setIconCls("");
//                                                organization_ins.setOrgType("department");
//                                                organization_ins.setDeptCode("");
//                                                organization_ins.setWorkTeam("");
//                                                organization_ins.setDeptType2("");
//                                                organization_ins.setTenantId(cropid);
//                                                organization_ins.setCreateTime(new Date());
//                                                organization_ins.setEntryWay(1);
//                                                organizationService.insertSelective(organization_ins);
//                                            }else {
//                                                level3_id = organization_three.getId();
//                                                if(organization_three.getIsDel()==1){
//                                                    organization_upd = new Organization();
//                                                    organization_upd.setUuid(level3Obj.getString("deptId"));
//                                                    organization_upd.setIsDel(0);
//                                                    organizationService.updateByPrimaryKeySelective(organization_upd);
//                                                }
//                                                if(!level3Obj.getString("deptName").equals(organization_three.getText())){
//                                                    organization_upd = new Organization();
//                                                    organization_upd.setUuid(level3Obj.getString("deptId"));
//                                                    organization_upd.setText(level3Obj.getString("deptName"));
//                                                    organizationService.updateByPrimaryKeySelective(organization_upd);
//                                                }
//                                            }
//
//                                            //处理四级部门
//                                            if(level3Obj.getJSONArray("childDeptTreeResDTOS")!=null&&level3Obj.getJSONArray("childDeptTreeResDTOS").size()>0){
//                                                JSONArray childDeptLevelArray4 = level3Obj.getJSONArray("childDeptTreeResDTOS");
//                                                String [] level4IdArray = new String[childDeptLevelArray4.size()];
//                                                for (int l = 0; l < childDeptLevelArray4.size(); l++) {
//                                                    JSONObject level4Obj = childDeptLevelArray4.getJSONObject(l);
//                                                    level4IdArray[l] = level4Obj.getString("deptId");
//                                                }
//                                                if(level4IdArray.length>0){
//                                                    organizationService.executeDeleteByTenantBatch(level4IdArray,cropid,4,level3_id);//删除不存在的部门
//                                                    for (int l = 0; l < childDeptLevelArray4.size(); l++) {
//                                                        JSONObject level4Obj = childDeptLevelArray4.getJSONObject(l);
//                                                        Integer level4_id;
//                                                        Organization organization_four = organizationService.queryObject(level4Obj.getString("deptId"));
//                                                        if(organization_four==null){
//                                                            level4_id = organizationService.getMaxById() + 1;
//                                                            organization_ins = new Organization();
//                                                            organization_ins.setUuid(level4Obj.getString("deptId"));
//                                                            organization_ins.setCreateTime(new Date());
//                                                            organization_ins.setIsDel(0);
//                                                            organization_ins.setRemark("");
//                                                            organization_ins.setAttributes("");
//                                                            organization_ins.setChecked("");
//                                                            organization_ins.setTypeValue("");
//                                                            organization_ins.setTypeText("");
//                                                            organization_ins.setId(level4_id);
//                                                            organization_ins.setPid(level3_id);
//                                                            organization_ins.setLevelId(4);
//                                                            organization_ins.setState("open");
//                                                            organization_ins.setText(level4Obj.getString("deptName"));
//                                                            organization_ins.setSort(l+1);
//                                                            organization_ins.setCode(level4Obj.getString("deptId"));
//                                                            organization_ins.setLeader("");
//                                                            organization_ins.setLeaderId("");
//                                                            organization_ins.setTempLeader("");
//                                                            organization_ins.setTempLeaderId("");
//                                                            organization_ins.setAuthStartDate(null);
//                                                            organization_ins.setAuthEndDate(null);
//                                                            organization_ins.setStatus("1");
//                                                            organization_ins.setIconCls("");
//                                                            organization_ins.setOrgType("workgroup");
//                                                            organization_ins.setDeptCode("");
//                                                            organization_ins.setWorkTeam("");
//                                                            organization_ins.setDeptType2("");
//                                                            organization_ins.setTenantId(cropid);
//                                                            organization_ins.setCreateTime(new Date());
//                                                            organization_ins.setEntryWay(1);
//                                                            organizationService.insertSelective(organization_ins);
//                                                        }else {
//                                                            level4_id = organization_four.getId();
//                                                            if(organization_four.getIsDel()==1){
//                                                                organization_upd = new Organization();
//                                                                organization_upd.setUuid(level4Obj.getString("deptId"));
//                                                                organization_upd.setIsDel(0);
//                                                                organizationService.updateByPrimaryKeySelective(organization_upd);
//                                                            }
//                                                            if(!level4Obj.getString("deptName").equals(organization_four.getText())){
//                                                                organization_upd = new Organization();
//                                                                organization_upd.setUuid(level4Obj.getString("deptId"));
//                                                                organization_upd.setText(level4Obj.getString("deptName"));
//                                                                organizationService.updateByPrimaryKeySelective(organization_upd);
//                                                            }
//                                                        }
//
//                                                        //处理五级部门
//                                                        if(level4Obj.getJSONArray("childDeptTreeResDTOS")!=null&&level4Obj.getJSONArray("childDeptTreeResDTOS").size()>0){
//                                                            JSONArray childDeptLevelArray5 = level4Obj.getJSONArray("childDeptTreeResDTOS");
//                                                            String [] level5IdArray = new String[childDeptLevelArray5.size()];
//                                                            for (int m = 0; m < childDeptLevelArray5.size(); m++) {
//                                                                JSONObject level5Obj = childDeptLevelArray5.getJSONObject(m);
//                                                                level5IdArray[m] = level5Obj.getString("deptId");
//                                                            }
//                                                            if (level5IdArray.length>0){
//                                                                organizationService.executeDeleteByTenantBatch(level5IdArray,cropid,5,level4_id);//删除不存在的部门
//                                                                for (int m = 0; m < childDeptLevelArray5.size(); m++) {
//                                                                    JSONObject level5Obj = childDeptLevelArray5.getJSONObject(m);
//                                                                    Integer level5_id;
//                                                                    Organization organization_five = organizationService.queryObject(level5Obj.getString("deptId"));
//                                                                    if (organization_five==null){
//                                                                        level5_id = organizationService.getMaxById() + 1;
//                                                                        organization_ins = new Organization();
//                                                                        organization_ins.setUuid(level5Obj.getString("deptId"));
//                                                                        organization_ins.setCreateTime(new Date());
//                                                                        organization_ins.setIsDel(0);
//                                                                        organization_ins.setRemark("");
//                                                                        organization_ins.setAttributes("");
//                                                                        organization_ins.setChecked("");
//                                                                        organization_ins.setTypeValue("");
//                                                                        organization_ins.setTypeText("");
//                                                                        organization_ins.setId(level5_id);
//                                                                        organization_ins.setPid(level4_id);
//                                                                        organization_ins.setLevelId(5);
//                                                                        organization_ins.setState("open");
//                                                                        organization_ins.setText(level5Obj.getString("deptName"));
//                                                                        organization_ins.setSort(m+1);
//                                                                        organization_ins.setCode(level5Obj.getString("deptId"));
//                                                                        organization_ins.setLeader("");
//                                                                        organization_ins.setLeaderId("");
//                                                                        organization_ins.setTempLeader("");
//                                                                        organization_ins.setTempLeaderId("");
//                                                                        organization_ins.setAuthStartDate(null);
//                                                                        organization_ins.setAuthEndDate(null);
//                                                                        organization_ins.setStatus("1");
//                                                                        organization_ins.setIconCls("");
//                                                                        organization_ins.setOrgType("workgroup");
//                                                                        organization_ins.setDeptCode("");
//                                                                        organization_ins.setWorkTeam("");
//                                                                        organization_ins.setDeptType2("");
//                                                                        organization_ins.setTenantId(cropid);
//                                                                        organization_ins.setCreateTime(new Date());
//                                                                        organization_ins.setEntryWay(1);
//                                                                        organizationService.insertSelective(organization_ins);
//                                                                    }else {
//                                                                        level5_id = organization_five.getId();
//                                                                        if(organization_five.getIsDel()==1){
//                                                                            organization_upd = new Organization();
//                                                                            organization_upd.setUuid(level5Obj.getString("deptId"));
//                                                                            organization_upd.setIsDel(0);
//                                                                            organizationService.updateByPrimaryKeySelective(organization_upd);
//                                                                        }
//                                                                        if(!level5Obj.getString("deptName").equals(organization_five.getText())){
//                                                                            organization_upd = new Organization();
//                                                                            organization_upd.setUuid(level5Obj.getString("deptId"));
//                                                                            organization_upd.setText(level5Obj.getString("deptName"));
//                                                                            organizationService.updateByPrimaryKeySelective(organization_upd);
//                                                                        }
//                                                                    }
//                                                                }
//                                                            }
//                                                        }
//                                                    }
//                                                }
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//}
