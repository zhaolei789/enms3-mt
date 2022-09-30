package cn.ewsd.repository.controller;

import cn.ewsd.base.utils.XDate;
import cn.ewsd.base.utils.XException;
import cn.ewsd.base.utils.jwt.LoginInfo;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;
import cn.ewsd.material.model.MMaterial;
import cn.ewsd.repository.model.MMove;
import cn.ewsd.repository.service.MMoveService;
import cn.ewsd.repository.service.MOutService;
import cn.ewsd.system.service.TCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @Author fengjw
 * @Email 11128474@qq.cn
 * @Date 2021-04-25 17:06:36
 */
@Controller
@RequestMapping("/repository/moveCheck")
public class MoveCheckController extends RepositoryBaseController {
    @Autowired
    private MMoveService mMoveService;
    @Autowired
    private MOutService mOutService;
    @Autowired
    private TCheckService tCheckService;

    @RequestMapping("/index")
    public String index(@RequestParam Map<String, Object> params) {
        return "repository/moveCheck/index";
    }

    @ResponseBody
    @RequestMapping(value = "/getMoveCheckList", method = RequestMethod.POST)
    public Object getMoveCheckList(PageParam pageParam) {
        try{
            PageSet<MMove> pageSet  = mMoveService.getMoveCheckList(pageParam, LoginInfo.getUuid());
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping(value = "/getMoveList", method = RequestMethod.POST)
    public Object getMoveList(PageParam pageParam) {
        try{
            PageSet<MMove> pageSet  = mMoveService.getCheckList(pageParam, request.getParameter("transNo"));
            return pageSet;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @ResponseBody
    @RequestMapping("/check")
    public Object save() {
        String transNo = request.getParameter("transNo");
        String[] listNos = request.getParameterValues("listNo");
        if(transNo==null && "".equals(transNo)){
            return failure("请选择移库指令！");
        }
        if(listNos==null || listNos.length<1){
            return failure("请选择要收货的物料！");
        }

        try {
            mMoveService.dealMove(transNo, listNos);
            return success("收货成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("收货失败");
        }
    }

    @ResponseBody
    @RequestMapping("/back")
    public Object delete() {
        String transNo = request.getParameter("transNo");
        String[] listNos = request.getParameterValues("listNo");
        if(transNo==null && "".equals(transNo)){
            return failure("请选择移库指令！");
        }
        if(listNos==null || listNos.length<1){
            return failure("请选择要退回的物料！");
        }

        try {
            mMoveService.backMove(transNo, listNos);
            return success("退回成功");
        }catch (XException e){
            return failure(e.getInfo());
        }catch (Exception e){
            e.printStackTrace();
            return failure("退回失败");
        }
    }
}
