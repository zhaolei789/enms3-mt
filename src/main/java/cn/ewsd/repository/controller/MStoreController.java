package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MStore;
import cn.ewsd.repository.service.MInService;
import cn.ewsd.repository.service.MStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/mStore")
public class MStoreController extends RepositoryBaseController {

    @Autowired
    private MStoreService mStoreService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private MInService mInService;


    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "repository/mStore/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MStore> pageSet = mStoreService.getPageSet(pageParam, filterSort.replace("ORDER BY ", "ORDER BY s."));
        return pageSet;
    }

    @RequestMapping("/edit")
    public String edit() {
        return "repository/mStore/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@ModelAttribute MStore mStore) {
        String storeNo = mStore.getStoreNo();
        String storeName = mStore.getStoreName();
        String storeType = mStore.getStoreType();
        String storeLevel = mStore.getStoreLevel();

        String errorInfo = "";
        ArrayList<String[]> condArr1 = new ArrayList<String[]>();
        condArr1.add(new String[]{"store_no", "=", storeNo});
        ArrayList<String[]> condArr2 = new ArrayList<String[]>();
        condArr2.add(new String[]{"store_name", "=", storeName});
        if("".equals(storeNo)){
            errorInfo = "仓库编号不能为空！";
        }else if(storeNo.length() > 2){
            errorInfo = "仓库编号不能大于2个字符！";
        }else if("".equals(storeName)){
            errorInfo = "仓库名称不能为空！";
        }else if(utilService.checkColumnDataExist("m_store", condArr1)){
            errorInfo = "该仓库编码已经存在，请重新输入！";
        }else if(utilService.checkColumnDataExist("m_store", condArr2)){
            errorInfo = "该仓库名称已经存在，请重新输入！";
        }else if("".equals(storeType) || storeType==null){
            errorInfo = "请选择仓库类型！";
        }else if("".equals(storeLevel) || storeLevel==null){
            errorInfo = "请选择仓库级别！";
        }
        if(!"".equals(errorInfo)){
            return failure(errorInfo);
        }
        if(mStore.getMngTeam()==null){
            mStore.setMngTeam(0);
        }
        if(mStore.getReserve1()==null){
            mStore.setReserve1("0");
        }

        int result = mStoreService.executeSave(getSaveData(mStore));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    public Object getDetailByUuid(String uuid) {
        MStore mStore = mStoreService.queryObject(uuid);
        return mStore;
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(@ModelAttribute MStore mStore) {
        String storeName = mStore.getStoreName();
        String storeType = mStore.getStoreType();
        String storeLevel = mStore.getStoreLevel();

        String errorInfo = "";
        ArrayList<String[]> condArr = new ArrayList<String[]>();
        condArr.add(new String[]{"store_no", "!=", mStore.getStoreNo()});
        condArr.add(new String[]{"store_name", "=", storeName});
        if("".equals(storeName)){
            errorInfo = "仓库名称不能为空！";
        }else if(storeType == null){
            errorInfo = "请选择仓库类型！";
        }else if(storeLevel == null){
            errorInfo = "请选择仓库级别！";
        }else if(utilService.checkColumnDataExist("m_store", condArr)){
            errorInfo = "仓库名称重复！";
        }
        if(!"".equals(errorInfo)){
            return failure(errorInfo);
        }

        int result = mStoreService.executeUpdate(getUpdateData(mStore));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam String uuid) {
        MStore mStore = mStoreService.queryObject(uuid);
        String storeNo = mStore.getStoreNo();

        if(mInService.getCountByStoreNo(storeNo) > 0){
            return failure("仓库信息已经使用过，不能删除！");
        }

        int result = mStoreService.executeDelete(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    @ResponseBody
    @RequestMapping(value = "/getStoreList")
    public Object getStoreList() {
        String chkUser = request.getParameter("chkUser");
        String upDown = request.getParameter("upDown");
        String mngTeam = request.getParameter("mngTeam");
        String ifSafe = request.getParameter("ifSafe");
        String ifStock = request.getParameter("ifStock");
        String bigType = request.getParameter("bigType");
        String storeType = request.getParameter("storeType");
        String storeLevel = request.getParameter("storeLevel");
        return mStoreService.getStoreList(chkUser!=null ? LoginInfo.getUuid() : null, upDown, "0".equals(mngTeam) ? LoginInfo.getOrgId()+"" : mngTeam, ifSafe, ifStock, bigType, storeType, storeLevel);
    }

    @ResponseBody
    @RequestMapping(value = "/getStoreList1")
    public Object getStoreList1(){
        return mStoreService.getStoreList1();
    }

    @ResponseBody
    @RequestMapping(value = "/getOutStoreList")
    public Object getOutStoreList(){
        String startDate = XDate.dateTo8(request.getParameter("startDate"));
        String endDate = XDate.dateTo8(request.getParameter("endDate"));
        return mStoreService.getOutStoreList(startDate, endDate);
    }

    @ResponseBody
    @RequestMapping(value = "/getPowerStore")
    public Object getPowerStore(){
        try{
            boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2655);
            String chkUserStore = request.getParameter("chkUserStore");
            return mStoreService.getPowerStore(ifPower, chkUserStore==null ? null : LoginInfo.getUuid());
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getAllStockStore")
    public Object getAllStockStore(){
        try{
            boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2730);
            String level = request.getParameter("level");
            return mStoreService.getAllStockQryStore("".equals(level) ? "r.storeLevel.1,r.storeLevel.2" : level, ifPower);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getStockSumStore")
    public Object getStockSumStore(){
        try{
            boolean ifPower = utilService.checkRight(LoginInfo.getRoleId(), 2732);
            return mStoreService.getAllStockQryStore("r.storeLevel.1", ifPower);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
