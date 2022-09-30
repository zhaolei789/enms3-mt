package cn.ewsd.system.controller;

import cn.ewsd.common.utils.JacobUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system/jacob")
public class JacobController extends SystemBaseController {

    @RequestMapping("/index")
    public void Index() {
//        ActiveXComponent word = null;
        try {
//            word = new ActiveXComponent("Word.Application");
//            System.out.println("jacob当前版本" + word.getBuildVersion());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("convertToPdf")
    public void convertToPdf() {
        String docfile = "C:\\Users\\Zhaoxiace\\Desktop\\系统管理员个人简历.doc";
        String htmlfile = "C:\\Users\\Zhaoxiace\\Desktop\\陈瑞杰：开题报告2017425.html";
        String pdffile = "C:\\Users\\Zhaoxiace\\Desktop\\系统管理员个人简历.pdf";

        String excelfile = "C:\\Users\\Zhaoxiace\\Desktop\\移动APP开发平台功能列表.xlsx";
        String excelpdffile = "C:\\Users\\Zhaoxiace\\Desktop\\移动APP开发平台功能列表.pdf";
        //JacobUtils.word2Html(docfile, htmlfile);
        JacobUtils.word2PDF(docfile, pdffile);
        //JacobUtils.excel2PDF(excelfile, excelpdffile);
    }

}