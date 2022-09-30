package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.IParameter;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.repository.model.MSafeStore;
import cn.ewsd.repository.model.MStore;
import cn.ewsd.repository.service.MSafeStoreService;
import cn.ewsd.repository.service.MStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/mStoreSafe")
public class MStoreSafeController extends RepositoryBaseController {

    @Autowired
    private MStoreService mStoreService;
    @Autowired
    private MSafeStoreService mSafeStoreService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        List<MStore> storeList = (List<MStore>)getStoreForSelect();
        if(storeList != null && storeList.size()>0){
            request.setAttribute("storeNoQry", storeList.get(0).getStoreNo());
        }
        return "repository/mStoreSafe/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getStoreForSelect", method = RequestMethod.POST)
    public Object getStoreForSelect() {
        return mStoreService.getStoreList(null, null, null, "1", null, null, null, null);
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    public Object getPageSet(PageParam pageParam) {
        String storeNo = request.getParameter("storeNoQry");
        String matQry = request.getParameter("matQry");
        PageSet<MSafeStore> pageSet = mSafeStoreService.getPageSet(pageParam, storeNo, matQry);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/save")
    public Object save() {
        IParameter iParameter = new IParameter(request);
        String[] matNos = iParameter.getParameters("matNo");
        HashMap<String, Double[]> map = new HashMap<String, Double[]>();

        for(int i=0; i<matNos.length; i++){
            String matNo = matNos[i];
            Double max = iParameter.getDoubleParameter(matNo+"_max", 0);
            Double yellow = iParameter.getDoubleParameter(matNo+"_yellow", 0);
            Double red = iParameter.getDoubleParameter(matNo+"_red", 0);
            if(max<0 || yellow<0 || red<0){
                return failure("安全库存必须是大于等于0的数字！");
            }
            map.put(matNo, new Double[]{max, yellow, red});
        }

        try {
            mSafeStoreService.saveData(map);
        }catch (Exception e){
            e.printStackTrace();
            return failure("保存数据失败！");
        }

        return success("保存数据成功");
    }

    @ResponseBody
    @RequestMapping(value = "/calc")
    public Object calcSafeStore() {
        String storeNo = request.getParameter("storeNoQry");
        try {
            mSafeStoreService.calcSafeStore(storeNo);
        }catch (Exception e){
            return failure("计算安全库存失败！");
        }
        return success("计算安全库存成功！");
    }
}
