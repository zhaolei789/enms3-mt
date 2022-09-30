package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.PoiUtils;
import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.repository.model.KeyAnaly;
import cn.ewsd.repository.service.MOutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/keyAnaly")
public class KeyAnalyController extends RepositoryBaseController {
    @Autowired
    MOutService mOutService;
    //分页页面
    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        request.setAttribute("date1Qry", XDate.dateTo10(XDate.getYear()+"0101"));
        request.setAttribute("date2Qry", XDate.dateTo10(XDate.getDate()));

        return "repository/keyAnaly/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Object getList() {
        try{
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));

            return mOutService.getKeyAnalyList(date1Qry, date2Qry);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/download")
    public void downloadPlan(HttpServletResponse response) {
        try{
            String date1Qry = XDate.dateTo8(request.getParameter("date1Qry"));
            String date2Qry = XDate.dateTo8(request.getParameter("date2Qry"));

            List<KeyAnaly> list = mOutService.getKeyAnalyList(date1Qry, date2Qry);

            String textName = "物料组,物料编码,物料描述,单位,金额TOP20,物料组,物料编码,物料描述,单位,数量TOP20,物料组,物料编码,物料描述,单位,价值TOP20";
            String fieldName = "balaTypeName,balaMatCode,balaMatName,balaMatUnit,balaBala,amountTypeName,amountMatCode,amountMatName,amountMatUnit,amountBala,priceTypeName,priceMatCode,priceMatName,priceMatUnit,priceBala";
            PoiUtils.exportExcelOld(response, "重点物资分析", textName, fieldName, list);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
