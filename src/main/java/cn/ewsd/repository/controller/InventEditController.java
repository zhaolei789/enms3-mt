package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.Snow;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.mdata.service.UtilService;
import cn.ewsd.repository.model.MBulkList;
import cn.ewsd.repository.model.MCheck;
import cn.ewsd.repository.service.MBulkListService;
import cn.ewsd.repository.service.MCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/inventEdit")
public class InventEditController extends RepositoryBaseController {
    @Autowired
    private MCheckService mCheckService;
    @Autowired
    private UtilService utilService;
    @Autowired
    private MBulkListService mBulkListService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("currentYear", Integer.parseInt(XDate.getYear()));
        request.setAttribute("endYear", Integer.parseInt(XDate.getYear())+10);

        return "repository/inventEdit/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String yearQry = request.getParameter("yearQry");

        try{
            PageSet<MCheck> pageSet = mCheckService.getPageSet(pageParam, filterSort, yearQry, LoginInfo.getUuid());
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //跳转新增
    @RequestMapping("/edit")
    public String add() {
        return "repository/inventEdit/edit";
    }

    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object save(@ModelAttribute MCheck mCheck) {
        try{
            if(mCheck.getCheckEmp()==null || "".equals(mCheck.getCheckEmp())){
                return failure("请填写负责员工！");
            }
            if(mCheck.getStoreNo()==null || "".equals(mCheck.getStoreNo())){
                return failure("请选择盘点仓库！");
            }

            ArrayList<String[]> list = new ArrayList<>();
            list.add(new String[]{"store_no", "=", mCheck.getStoreNo()});
            list.add(new String[]{"check_step", "!=", "7213F"});
            if(utilService.checkColumnDataExist("m_check", list)){
                return failure("该仓库已有未完成的盘点！");
            }

            mCheck.setCheckNo(Snow.getUUID()+"");
            mCheck.setCheckStep("72132");
            mCheck.setCheckDate(XDate.getDate());

            int result = mCheckService.insertCheck(mCheck);
            return result > 0 ? success("保存成功！") : failure("保存失败！");
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete() {
        try{
            String checkNo = request.getParameter("checkNo");
            MCheck mCheck = mCheckService.getCheckByNo(checkNo);
            if(!"72132".equals(mCheck.getCheckStep())){
                return failure("盘点结果已经提交，不能删除！");
            }

            int result = mCheckService.deleteCheck(checkNo);
            return result > 0 ? success("删除成功！") : failure("删除失败！");
        }catch (Exception e){
            e.printStackTrace();
            return failure("删除失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getBulkPageSet", method = RequestMethod.POST)
    public Object getBulkPageSet(PageParam pageParam) {
        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());

        String checkNo = request.getParameter("checkNo");
        String siteNoQry = request.getParameter("siteNoQry");
        String matNameQry = request.getParameter("matNameQry");

        try{
            PageSet<MBulkList> pageSet = mBulkListService.getPageSet(pageParam, filterSort, checkNo, siteNoQry, matNameQry);
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveBulk")
    public Object saveBulk() {
        try {
            mBulkListService.updateBulk(request, LoginInfo.get());
            return success("保存成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存失败！");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/submitBulk")
    public Object submitBulk() {
        try {
            mBulkListService.submitBulk(request);
            return success("提交成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("提交失败！");
        }
    }

    @RequestMapping(value = "/download")
    public void export(HttpServletResponse response) {
        try{

            String checkNo = request.getParameter("checkNo");
            String siteNoQry = request.getParameter("siteNoQry");
            String matNameQry = request.getParameter("matNameQry");
            List<MBulkList> list = mBulkListService.getBulkList(checkNo, siteNoQry, matNameQry);

            String textName = "库存编码,物料编码,物料名称,单位,理论数,实际数,修改理由";
            String fieldName = "siteNo,matCode,matName,matUnit,theoryAmount,realAmount,reason";
            PoiUtils.exportExcelOld(response, "盘点信息", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
