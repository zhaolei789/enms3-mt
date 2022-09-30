package cn.ewsd.material.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MAskCode;
import cn.ewsd.material.model.MPlan;
import cn.ewsd.material.service.MAskCodeService;
import cn.ewsd.repository.model.MIn;
import cn.ewsd.repository.service.MAddrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-21 16:55:52
 */
@Controller
@RequestMapping("/material/makeBack")
public class MakeBackController extends MaterialBaseController {

    @Autowired
    private MAskCodeService mAskCodeService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("day1Qry", XDate.dateTo10(XDate.getYear()+"0101"));
        request.setAttribute("startDateQry", XDate.dateTo10(XDate.getMonth()+"01"));
        request.setAttribute("day2Qry", XDate.dateTo10(XDate.addDate(XDate.getDate(), 60)));
        request.setAttribute("stepQry", "7300F");

        return "material/makeBack/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
        String addrQry = request.getParameter("addrQry");
        String matQry = request.getParameter("matQry");
        String teamQry = request.getParameter("teamQry");
        String flagQry = request.getParameter("flagQry");
        String stepQry = request.getParameter("stepQry");
        String day1Qry = XDate.dateTo8(request.getParameter("day1Qry"));
        String day2Qry = XDate.dateTo8(request.getParameter("day2Qry"));
        String needTypeQry = request.getParameter("needTypeQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MAskCode> pageSet = mAskCodeService.getMakeBackPageSet(pageParam, filterSort, date1Qry, addrQry, matQry, teamQry, "1".equals(flagQry), XDate.getDate(), stepQry, day1Qry, day2Qry, needTypeQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadPlan(HttpServletResponse response) {
        try{
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String addrQry = request.getParameter("addrQry");
            String matQry = request.getParameter("matQry");
            String teamQry = request.getParameter("teamQry");
            String flagQry = request.getParameter("flagQry");
            String stepQry = request.getParameter("stepQry");
            String day1Qry = XDate.dateTo8(request.getParameter("day1Qry"));
            String day2Qry = XDate.dateTo8(request.getParameter("day2Qry"));
            String needTypeQry = request.getParameter("needTypeQry");

            List<MAskCode> list = mAskCodeService.getMakeBackList(date1Qry, addrQry, matQry, teamQry, "1".equals(flagQry), XDate.getDate(), stepQry, day1Qry, day2Qry, needTypeQry);
            for(int i=0; i<list.size(); i++){
                MAskCode mAskCode = list.get(i);
                mAskCode.setAskName(mAskCode.getAskName()+" "+mAskCode.getAskModel());
                mAskCode.setDoDate(XDate.dateTo10(mAskCode.getDoDate()));
                mAskCode.setNeedDate(XDate.dateTo10(mAskCode.getNeedDate()));
            }

            String textName = "使用区队,物料信息,单位,单价,申请日期,回收预期,预计回收单位,使用地点,计划备注,计划数,已回收";
            String fieldName = "askTeamName,askName,askUnitName,matFee,doDate,needDate,preTeamName,useAddr,remark,checkAmount,backAmount";
            PoiUtils.exportExcelOld(response, "加工回收", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getDetailPageSet", method = RequestMethod.POST)
    public Object getDetailPageSet(PageParam pageParam) {
        String startDateQry = XDate.dateTo8(request.getParameter("startDateQry"));
        String endDateQry = XDate.dateTo8(request.getParameter("endDateQry"));
        String matNameQry = request.getParameter("matNameQry");

        String filterSort = "";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<MIn> pageSet = mAskCodeService.getMakeBackDetailPageSet(pageParam, filterSort, startDateQry, endDateQry, matNameQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/submit")
    public Object submitCheck() {
        try {
            mAskCodeService.submitMakeBack(request, LoginInfo.get());
            return success("回收成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("回收失败！");
        }
    }

    //跳转编辑页
    @RequestMapping("/edit")
    public String edit() {
        return "material/makeBack/edit";
    }

    //编辑页数据
    @ResponseBody
    @RequestMapping(value = "/getAsk")
    public Object getDetailByMatNo(String askNo) {
        MAskCode mAskCode = mAskCodeService.getAskByNo(askNo);
        return mAskCode;
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Object updateAsk() {
        try {
            mAskCodeService.updateAsk(request, LoginInfo.get());
            return success("调整成功！");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("调整失败！");
        }
    }
}
