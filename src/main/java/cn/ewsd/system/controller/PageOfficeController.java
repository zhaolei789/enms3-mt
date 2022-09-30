//package cn.ewsd.system.controller;
//
//import com.zhuozhengsoft.pageoffice.FileSaver;
//import com.zhuozhengsoft.pageoffice.OpenModeType;
//import com.zhuozhengsoft.pageoffice.PageOfficeCtrl;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Map;
//
///**
// * 首页
// * @author Administrator
// */
//@RestController
//@RequestMapping("/system/pageOffice")
//public class PageOfficeController {
//
//    @Value("${my.upload-dir}")
//    private String uploadDir;
//
//    @RequestMapping("/hello")
//    public String test() {
//        System.out.println("hello run");
//        return "Hello";
//    }
//
//    @RequestMapping(value = "/index", method = RequestMethod.GET)
//    public ModelAndView showIndex() {
//        ModelAndView mv = new ModelAndView("system/pageOffice/Index");
//        return mv;
//    }
//
//    @RequestMapping(value = "/word", method = RequestMethod.GET)
//    public ModelAndView showWord(HttpServletRequest request, Map<String, Object> map, String path) {
//
//        PageOfficeCtrl poCtrl = new PageOfficeCtrl(request);
//        poCtrl.setServerPage("/poserver.zz");//设置服务页面
//        poCtrl.setSaveFilePage("/system/pageOffice/save");//设置处理文件保存的请求方法
//
//        poCtrl.addCustomToolButton("保存文档", "Save", 1);//添加自定义保存按钮
//        poCtrl.addCustomToolButton("保存关闭", "SaveAndClose", 8);//添加自定义保存按钮
//        poCtrl.addCustomToolButton("-", "", 0);
//        poCtrl.addCustomToolButton("加盖印章", "AddSeal()", 9);//添加自定义盖章按钮
//        poCtrl.addCustomToolButton("手写签批", "AddHandSign()", 10);
//        poCtrl.addCustomToolButton("验证印章", "VerifySeal()", 5);
//        poCtrl.addCustomToolButton("-", "", 0);
//        poCtrl.addCustomToolButton("打印文档", "ShowPrintDlg", 6);
//        poCtrl.addCustomToolButton("全屏切换", "SwitchFullScreen", 4);
//        poCtrl.addCustomToolButton("关闭窗口", "CloseWindow", 21);
//
//        //打开word
//        //poCtrl.webOpen("D:\\test.doc", OpenModeType.docAdmin, "张三");
//        poCtrl.webOpen(uploadDir + path, OpenModeType.docAdmin, "张三");
//        //poCtrl.webOpen("192.168.1.170/group1/M00/00/00/wKgBqlv6mVyAC62qAACYAEf5OgQ940.doc", OpenModeType.docAdmin, "张三");
//        map.put("pageoffice", poCtrl.getHtmlCode("PageOfficeCtrl1"));
//
//        ModelAndView mv = new ModelAndView("system/pageOffice/Word");
//        return mv;
//    }
//
//    @RequestMapping("/save")
//    public void saveFile(HttpServletRequest request, HttpServletResponse response) {
//        FileSaver fs = new FileSaver(request, response);
//        String filePath = "D:\\" + fs.getFileName();
//        //fs.saveToFile("D:\\" + fs.getFileName());
//        fs.saveToFile(filePath);
//        fs.close();
//    }
//
//
//}
