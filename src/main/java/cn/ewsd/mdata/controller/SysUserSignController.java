package cn.ewsd.mdata.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.ewsd.base.utils.ImageUtils;
import cn.ewsd.base.utils.jwt.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

import cn.ewsd.common.annotation.ControllerLog;
import cn.ewsd.common.controller.BaseController;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.mdata.model.SysUserSign;
import cn.ewsd.mdata.service.SysUserSignService;

/**
 * 用户签名
 *
 * @Author zxrmine
 * @Email zxrmine@163.cn
 * @Date 2022-05-06 15:43:29
 */
@Controller
@RequestMapping("/mdata/sysUserSign")
public class SysUserSignController extends MdataBaseController {

    @Autowired
    private SysUserSignService sysUserSignService;

    @Value("${my.upload-dir}")
    private String uploadDir;

    @Value("${files.server-url-prefix}")
    private String urlPrefix;

    //分页页面
    @RequestMapping("/index")
    @ControllerLog(description = "打开SysUserSign模块管理页面")
    public String index(@RequestParam Map<String, Object> params) {
        return "mdata/sysUserSign/index";
    }

    //获取分页集
    @ResponseBody
    @RequestMapping(value = "/getPageSet", method = RequestMethod.POST)
    @ControllerLog(description = "获得SysUserSign分页集数据")
    public Object getPageSet(PageParam pageParam) {
        String filterSort = " and user_id = '"+ LoginInfo.getUuid()+"' and is_del != 1 ";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        PageSet<SysUserSign> pageSet = sysUserSignService.getPageSet(pageParam, filterSort.replace("ORDER BY create_time DESC","ORDER BY status DESC,create_time DESC"));
        return pageSet;
    }

    //获取详情
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    @ControllerLog(description = "获得SysUserSign模块详细数据")
    public Object getDetailByUuid(String uuid) {
        SysUserSign sysUserSign = sysUserSignService.selectByPrimaryKey(uuid);
        return sysUserSign;
    }

    @RequestMapping(value = "/goSignDetail")
    public Object goSignDetail(String uuid) {
        SysUserSign sysUserSign = sysUserSignService.selectByPrimaryKey(uuid);
        request.setAttribute("sysUserSign",sysUserSign);
        return "mdata/sysUserSign/signature_detail";
    }

    //新增页面
    @RequestMapping("/add")
    @ControllerLog(description = "打开SysUserSign模块新增页面")
    public String add() {
        return "mdata/sysUserSign/edit";
    }

    //保存数据
    @ResponseBody
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ControllerLog(description = "保存SysUserSign模块数据")
    public Object save(@ModelAttribute SysUserSign sysUserSign) {
        String signature = request.getParameter("signBase64");
        if(signature!=null&&signature.length()>10) {
            sysUserSignService.setAllStatus0(LoginInfo.getUuid());
            //base64转图片
            String filePath = ImageUtils.base64ToImg(signature,getUploadDir());
            String fileUrl = filePath.replace(uploadDir+"/uploads/",urlPrefix);
            sysUserSign.setUserId(LoginInfo.getUuid());
            sysUserSign.setUserNameId(LoginInfo.getUserNameId());
            sysUserSign.setUserName(LoginInfo.getUserName());
            sysUserSign.setSignPath(filePath.replaceAll("\\\\","/"));
            sysUserSign.setSignUrl(fileUrl.replaceAll("\\\\","/"));
            sysUserSign.setType(1);
            sysUserSign.setStatus(1);
            sysUserSign.setIsDel(0);
        }else {
            return failure("签名错误!");
        }
        int result = sysUserSignService.insertSelective(getSaveData(sysUserSign));
        return result > 0 ? success("保存成功！") : failure("保存失败！");
    }

    //编辑页面
    @RequestMapping("/edit")
    @ControllerLog(description = "打开SysUserSign模块编辑页面")
    public String edit() {
        return "mdata/sysUserSign/edit";
    }

    //更新数据
    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ControllerLog(description = "更新SysUserSign模块数据")
    public Object update(@ModelAttribute SysUserSign sysUserSign) {
        int result = sysUserSignService.updateByPrimaryKeySelective(getUpdateData(sysUserSign));
        return result > 0 ? success("更新成功！") : failure("更新失败！");
    }

    //删除数据
    @ResponseBody
    @RequestMapping(value = "/deleteBatch", method = RequestMethod.POST)
    @ControllerLog(description = "删除SysUserSign模块数据")
    public Object deleteBatch(@RequestParam String[] uuid) {
        int result = sysUserSignService.executeDeleteBatch(uuid);
        return result > 0 ? success("删除成功！") : failure("删除失败！");
    }

    public String getUploadDir() {
        String uploadTypeDir = uploadDir + "/uploads/sign";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd");
        Date date = new Date();
        String ymd = sdf.format(date);
        uploadTypeDir += File.separator + ymd + File.separator;
        File file = new File(uploadTypeDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        return uploadTypeDir;
    }

}
