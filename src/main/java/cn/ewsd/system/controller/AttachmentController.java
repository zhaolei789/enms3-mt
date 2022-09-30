package cn.ewsd.system.controller;

import cn.ewsd.base.config.UeditorConfig;
import cn.ewsd.common.utils.BaseUtils;
import cn.ewsd.common.utils.JsonUtils;
import cn.ewsd.common.utils.StringUtils;
import cn.ewsd.common.utils.easyui.PageParam;
import cn.ewsd.common.utils.easyui.PageSet;

import cn.ewsd.system.model.Attachment;
import cn.ewsd.system.service.AttachmentService;
//import com.aliyun.oss.OSSClient;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 附件
 */
@Controller
@RequestMapping("/system/attachment")
public class AttachmentController extends SystemBaseController {

    @Resource
    private AttachmentService attachmentService;

    @Value("${my.upload-dir}")
    private String uploadDir;

    public static String accessKeyId = "4WEX9WabROAtS62m";
    public static String accessKeySecret = "0rjT6OsMLF1c7Sa6LGgQKoQWgkKO55";
    public static String bucketName = "ewsd";

    @RequestMapping("/index")
    public String index(PageParam pageParam) throws Exception {
//        String hql = "FROM Attachment" + Common.searchInfo(request);
//        PageSet pageSet = attachmentService.getPageSetByHql(request, hql);
//        request.setAttribute("pageSet", pageSet);
//        System.out.println(request.getSession().getId());
//        return display();

        String filterSort = BaseUtils.filter(request);
        PageSet pageSet = attachmentService.getPageSet(pageParam, filterSort);
        request.setAttribute("pageSet", pageSet);
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "/getPageSetData")
    public Object getPageSetData(PageParam pageParam) throws Exception {
        String filterStr = "";
        /*if(request.getParameter("codeItemId") != null) {
            filterStr = "AND orgId = '"+ request.getParameter("codeItemId") +"'";
		} else {
			filterStr = "";
		}*/

        filterStr = BaseUtils.filterSort(request);
        PageSet<Attachment> pageSet = attachmentService.getPageSet(pageParam, filterStr);
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getListByPuuid")
    public Object getListByPuuid(PageParam pageParam, String puuid) throws Exception {
        PageSet<Attachment> pageSet = new PageSet<>();
        if (!StringUtils.isNullOrEmpty(puuid)) {
            String filterSort = BaseUtils.filterSort(request, " and puuid = '" + puuid + "'");
            pageSet = attachmentService.getPageSet(pageParam, filterSort);
        }
        return pageSet;
    }

    @ResponseBody
    @RequestMapping(value = "/getListByPuuid2")
    public Object getListByPuuid2(PageParam pageParam,String puuid) throws Exception {
        PageSet<Attachment> pageSet = new PageSet<>();
        String filterSort = "";
        filterSort += " and puuid = '"+puuid+"'";
        filterSort = BaseUtils.filterSort(request, filterSort + getAuthFilter());
        pageSet = attachmentService.getPageSet(pageParam, filterSort);
        return pageSet;
    }

   /* @ResponseBody
    @RequestMapping(value = "/getListByPuuidFlow")
    public Object getListByPuuidFlow(PageParam pageParam, String puuid) throws Exception {
        PageSet<Attachment> pageSet = new PageSet<>();
        if (!StringUtils.isNullOrEmpty(puuid)) {
            String filterSort = BaseUtils.filterSort(request, " and puuid = '" + puuid + "'");
            pageSet = attachmentService.getPageSet(pageParam, filterSort);
        }
        for (int i = 0; i < pageSet.getRows().size(); i++) {
            Attachment attachment = pageSet.getRows().get(i);
            String path = attachment.getFilePath();
            String fileNameOrg = attachment == null?"image" :attachment.getFileName();
            if(path.indexOf(uploadDir) == -1){
                path = uploadDir + path;
            }
            File file = new File(path);
            HttpHeaders headers = new HttpHeaders();
            String fileName = new String(fileNameOrg.getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
            headers.setContentDispositionFormData("attachment", fileName);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            pageSet.getRows().get(i).setFileflow(new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED));
        }
        return pageSet;
    }
*/
    @ResponseBody
    @RequestMapping(value = "/getDetailByUuid")
    public Object getDetailByUuid() throws Exception {
        String uuid = request.getParameter("uuid");
        Attachment info = attachmentService.selectByPrimaryKey(uuid);
        return info;
    }

    public String getUploadDir(String filtType) {
        //String ctxPath = request.getSession().getServletContext().getRealPath("/") + "uploads";
        //String uploadDir2 = System.getProperty("ewsdEMIS.root") + "uploads/" + filtType;
        String uploadTypeDir = uploadDir + "/uploads/" + filtType;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + File.separator + "MM" + File.separator + "dd");
        Date date = new Date();
        String ymd = sdf.format(date);
        uploadTypeDir += File.separator + ymd + File.separator;
        return uploadTypeDir;
    }

    @ResponseBody
    @RequestMapping(value = "/md5Validate")
    public Object md5Validate() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("isHave", false);
        return map;
    }

    /**
     * 单个附件上传
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/upload")
    public Object upload(HttpServletRequest request) throws Exception {

        request.setCharacterEncoding("UTF-8");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        //定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,pdf,mp4");

        Map<String, Object> returnMap = new HashMap<String, Object>();

        String fileName;
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            // 上传文件
            MultipartFile mf = entity.getValue();
            long fileSize = mf.getSize();
            fileName = mf.getOriginalFilename();
            //fileName = URLDecoder.decode(fileName, "UTF-8");

            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            // 获得文件类型，放入相应的文件夹
            String fileType = "";
            Boolean allowUpload = false;
            for (Map.Entry<String, String> vo : extMap.entrySet()) {
                if (vo.getValue().indexOf(fileExt) > -1) {
                    fileType = vo.getKey();
                    allowUpload = true;
                    break;
                }
            }

            if (!allowUpload) {
                returnMap.put("statusCode", 300);
                returnMap.put("title", "操作提示");
                returnMap.put("message", "不允许上传该类文件！");
                return returnMap;
            }

            fileType = "".equals(fileType) ? "file" : fileType;
            String uploadDir = getUploadDir(fileType);
            // 创建文件夹
            File file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            // 生成保存到服务器的文件名
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + "_" + BaseUtils.getFixLengthNum(10000, 99999) + "." + fileExt;
            File uploadFile = new File(uploadDir + "/" + newFileName);
            uploadDir = uploadDir.replace("\\", "/");
            try {
                FileCopyUtils.copy(mf.getBytes(), uploadFile);
                save("uplodify", request.getParameter("module"), request.getParameter("category"), request.getParameter("puuid"), fileExt, fileSize, fileName, newFileName, uploadDir.substring(uploadDir.indexOf("uploads") - 1) + newFileName);
                String filepath = "/" + uploadDir.substring(uploadDir.indexOf("uploads")) + newFileName;
                returnMap.put("statusCode", 200);
                returnMap.put("title", "操作提示");
                returnMap.put("message", "上传成功");
                returnMap.put("filePath", filepath);
            } catch (IOException e) {
                e.printStackTrace();
                returnMap.put("statusCode", 300);
                returnMap.put("title", "操作提示");
                returnMap.put("message", "上传失败");
            }
        }
        return returnMap;
    }

    /**
     * 多个附件上传，同时接收多个附件，如ajaxFileUploader上传工具
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/multiUpload")
    public Object multiUpload(HttpServletRequest request) throws Exception {
        request.setCharacterEncoding("UTF-8");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

        Map<String, String[]> params = multipartRequest.getParameterMap();

        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        List<MultipartFile> multiFileMap = multipartRequest.getMultiFileMap().get("files");

        //定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,pdf");

        Map<String, Object> returnMap = new HashMap<String, Object>();

        String fileName;
        //for (Map.Entry<String, List<MultipartFile>> entity : multiFileMap.entrySet()) {
        for (int i = 0; i < multiFileMap.size(); i++) {
            // 上传文件
            MultipartFile mf = multiFileMap.get(i);
            long fileSize = mf.getSize();
            fileName = mf.getOriginalFilename();
            //fileName = URLDecoder.decode(fileName, "UTF-8");

            String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();

            // 获得文件类型，放入相应的文件夹
            String fileType = "";
            for (Map.Entry<String, String> vo : extMap.entrySet()) {
                if (vo.getValue().indexOf(fileExt) > -1) {
                    fileType = vo.getKey();
                    break;
                }
            }
            fileType = "".equals(fileType) ? "file" : fileType;
            String uploadDir = getUploadDir(fileType);
            // 创建文件夹
            File file = new File(uploadDir);
            if (!file.exists()) {
                file.mkdirs();
            }
            // 生成保存到服务器的文件名
            String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + "_" + BaseUtils.getFixLengthNum(10000, 99999) + "." + fileExt;
            File uploadFile = new File(uploadDir + "/" + newFileName);
            try {
                FileCopyUtils.copy(mf.getBytes(), uploadFile);
                //save("uplodify", request.getParameter("module"), request.getParameter("category"), request.getParameter("puuid"), fileExt, fileSize, fileName, newFileName, uploadDir.substring(uploadDir.indexOf("uploads") - 1) + newFileName);
                returnMap.put("success", true);
            } catch (IOException e) {
                e.printStackTrace();
                returnMap.put("success", false);
            }
        }
        return returnMap;
    }

    /**
     * 如果调整路径，请注意MyMultipartResolver类的修改
     *
     * @return
     * @throws IOException
     * @throws FileUploadException
     */
//    @RequestMapping("kindeditorUpload")
//    @ResponseBody
//    public Object ii(HttpServletResponse response, HttpServletRequest request) throws IOException, FileUploadException {
//        /**
//         * KindEditor JSP
//         *
//         * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
//         * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
//         *
//         */
//
//        //上传文件的类型，local：本地文档，cloud：云端文档
//        String type = request.getParameter("type");
//
//        //文件保存目录路径
//        String savePath = request.getSession().getServletContext().getRealPath("/") + "uploads/";
//        //String savePath = "/usr/local/tomcat/webapps/uploads/";
//
//        //文件保存目录URL
//        //String saveUrl  = request.getContextPath() + "/uploads/";
//        String saveUrl = "/uploads/";
//
//        //定义允许上传的文件扩展名
//        HashMap<String, String> extMap = new HashMap<String, String>();
//        extMap.put("image", "gif,jpg,jpeg,png,bmp");
//        extMap.put("flash", "swf,flv");
//        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
//        extMap.put("file", "doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,pdf");
//        extMap.put("temp", "doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,pdf,gif,jpg,jpeg,png,bmp");
//
//        //最大文件大小
//        long maxSize = 20971520;
//
//        response.setContentType("text/html; charset=UTF-8");
//
//        if (!ServletFileUpload.isMultipartContent(request)) {
//            return getError("请选择文件。");
//        }
//        //检查目录
//        File uploadDir = new File(savePath);
//        if (!uploadDir.isDirectory()) {
//            uploadDir.mkdirs();
//            return getError("上传目录 " + savePath + " 不存在，系统已自动创建，请重新上传！");
//        }
//        //检查目录写权限
//        if (!uploadDir.canWrite()) {
//            return getError("上传目录没有写权限。");
//        }
//
//        String dirName = request.getParameter("dir");
//        if (dirName == null) {
//            dirName = "file";
//        }
//        if (!extMap.containsKey(dirName)) {
//            return getError("目录名不正确。");
//        }
//        //创建文件夹
//       /* savePath += dirName + "/";
//        saveUrl += dirName + "/";
//        File saveDirFile = new File(savePath);
//        if (!saveDirFile.exists()) {
//            saveDirFile.mkdirs();
//        }*/
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        String ymd = sdf.format(new Date());
//        savePath += dirName + "/" + ymd + "/";
//        saveUrl += dirName + "/" + ymd + "/";
//        File dirFile = new File(savePath);
//        if (!dirFile.exists()) {
//            dirFile.mkdirs();
//        }
//
//        FileItemFactory factory = new DiskFileItemFactory();
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        upload.setHeaderEncoding("UTF-8");
//        List items = upload.parseRequest(request);
//        Iterator itr = items.iterator();
//        while (itr.hasNext()) {
//            FileItem item = (FileItem) itr.next();
//            String fileName = item.getName();
//            long fileSize = item.getSize();
//            if (!item.isFormField()) {
//                String module = request.getParameter("module");
//                // 判断是否设置了module
//                if (module == "" || module == null) {
//                    return getError("上传失败：未设置module值");
//                }
//                String puuid = request.getParameter("puuid");
//                // 判断是否存在puuid
//                if (puuid == "" || puuid == null) {
//                    return getError("上传失败：请先保存数据后再上传");
//                }
//                //检查文件大小
//                if (item.getSize() > maxSize) {
//                    return getError("上传文件大小超过限制。");
//                }
//                //检查扩展名
//                String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
//                if (!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)) {
//                    return getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。");
//                }
//
//                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
//                String newFileName = df.format(new Date()) + "_" + BaseUtils.getFixLengthNum(10000, 99999) + "." + fileExt;
//                try {
//                    //上传附件到本地
//                    File uploadedFile = new File(savePath, newFileName);
//                    item.write(uploadedFile);
//                    save("kindeditor", request.getParameter("module"), request.getParameter("category"), puuid, fileExt, fileSize, fileName, newFileName, savePath.substring(savePath.indexOf("uploads") - 1) + newFileName);
//                } catch (Exception e) {
//                    return getError("上传文件失败。");
//                }
//
//                Map<String, Object> msg = new HashMap<String, Object>();
//                msg.put("error", 0);
//                msg.put("url", saveUrl + newFileName);
//                msg.put("fileName", fileName);
//                return msg;
//            }
//        }
//        return null;
//    }
    private Map<String, Object> getError(String message) {
        Map<String, Object> msg = new HashMap<String, Object>();
        msg.put("error", 1);
        msg.put("message", message);
        return msg;
    }

    @RequestMapping(value = "/cloudUpload", method = RequestMethod.POST)
    public void cloudUpload() {
        String filePath = request.getParameter("filePath");
        String fileExt = filePath.substring(filePath.lastIndexOf("."));

        //文件保存路径
        String savePath = "uploads/";

        //定义允许上传的文件扩展名
        HashMap<String, String> extMap = new HashMap<String, String>();
        extMap.put("image", "gif,jpg,jpeg,png,bmp");
        extMap.put("flash", "swf,flv");
        extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
        extMap.put("file", "doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,pdf");
        extMap.put("temp", "doc,docx,xls,xlsx,ppt,pptx,htm,html,txt,zip,rar,gz,bz2,pdf,gif,jpg,jpeg,png,bmp");

        String dirName = request.getParameter("dir");
        if (dirName == null) {
            dirName = "file";
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String ymd = sdf.format(new Date());
        savePath += dirName + "/" + ymd + "/";

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
        String newFileName = df.format(new Date()) + "_" + BaseUtils.getFixLengthNum(10000, 99999) + fileExt;

        // endpoint以杭州为例，其它region请按实际情况填写
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        // 创建OSSClient实例
//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//        // 上传文件
//        ossClient.putObject(bucketName, savePath + newFileName, new File(filePath));
//        // 关闭client
//        ossClient.shutdown();

    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> download() throws Exception {

        String uuid = request.getParameter("uuid");
        Attachment attach = attachmentService.selectByPrimaryKey(uuid);

        //String realPath = request.getSession().getServletContext().getRealPath("/");
        //String path = realPath + attach.getFilePath();
        String path = uploadDir + attach.getFilePath();
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String(attach.getFileName().getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
    /**
     * @MethodName pdfStreamHandeler
     * @Description 返回pdf流文件
     * @Param request
     * @Param response
     * @Return void
     * @Author 宋景民<songjingmin@zuoyoutech.com>
     * @Date 2019/1/21 11:31
     */
    @RequestMapping(value = "/pdfStreamHandeler")
    public void pdfStreamHandeler(HttpServletRequest request, HttpServletResponse response) {
        String uuid = request.getParameter("uuid");
        Attachment attach = attachmentService.selectByPrimaryKey(uuid);

        //String realPath = request.getSession().getServletContext().getRealPath("/");
        //String path = realPath + attach.getFilePath();
        String path = uploadDir + attach.getFilePath();
        File file = new File(path);
        byte[] data = null;
        try {
            // 解决请求头跨域问题（IE兼容性  也可使用该方法）
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setContentType("application/pdf");
            FileInputStream input = new FileInputStream(file);
            data = new byte[input.available()];
            input.read(data);
            response.getOutputStream().write(data);
            input.close();
        } catch (Exception e) {
        }
    }

    public void save(String source, String module, String category, String puuid, String fileType, Long fileSize, String fileName, String newFileName, String filePath) {

        //Article article = articleService.get(Article.class, puuid);

        Attachment attachment = new Attachment();
        //attachment.setArticle(article);
        attachment.setSource(source);
        attachment.setModule(module);
        attachment.setCategory(category);
        attachment.setPuuid(puuid);
        attachment.setFileType(fileType);
        attachment.setFileSize(fileSize);
        attachment.setFileName(fileName);
        attachment.setFilePath(filePath);
        attachmentService.insertSelective(getSaveData(attachment));
    }

    /**
     * 批量保存附件信息
     *
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping("/batchSave")
    public Integer batchSave(String puuid, String file1, String file2, String file3, String file4, String file5) throws Exception {
        Attachment attachment = new Attachment();
        attachment.setPuuid(puuid);

        Integer result = 0;
        if (!StringUtils.isNullOrEmpty(file1)) {
            attachment.setFileName(file1);
            attachmentService.insertSelective(getSaveData(attachment));
            result++;
        }
        if (!StringUtils.isNullOrEmpty(file2)) {
            attachment.setFileName(file2);
            attachmentService.insertSelective(getSaveData(attachment));
            result++;
        }
        if (!StringUtils.isNullOrEmpty(file3)) {
            attachment.setFileName(file3);
            attachmentService.insertSelective(getSaveData(attachment));
            result++;
        }
        if (!StringUtils.isNullOrEmpty(file4)) {
            attachment.setFileName(file4);
            attachmentService.insertSelective(getSaveData(attachment));
            result++;
        }
        if (!StringUtils.isNullOrEmpty(file5)) {
            attachment.setFileName(file5);
            attachmentService.insertSelective(getSaveData(attachment));
            result++;
        }
        return result;
    }

    @RequestMapping("/add")
    public String add() throws Exception {
        return display();
    }

    @RequestMapping("/cloudAdd")
    public String cloudAdd() throws Exception {
        return display();
    }

    @RequestMapping("/edit")
    public String edit() throws Exception {
        return display();
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object update(String uuid, String fileName, String fileVersion, String fileStatus) throws Exception {
        //"UPDATE Attachment SET fileName = '" + fileName + "', fileVersion = '" + fileVersion + "', fileStatus = '" + fileStatus + "' WHERE uuid = '" + uuid + "'"
        int result = attachmentService.updateFileNameAndFileVersionAndFileStatusAndUuid(fileName, fileVersion, fileStatus, uuid);
        if (result == 1) {
            return JsonUtils.messageJson(200, "温馨提示", "数据更新成功");
        } else {
            return JsonUtils.messageJson(300, "温馨提示", "数据更新失败");
        }
    }

    @ResponseBody
    @RequestMapping("/delete")
    public Object delete(String[] uuid) throws Exception {
        //  String hql = "DELETE FROM Attachment WHERE uuid IN (" + uuid + ")";
        Integer resule = 0;
        for (int i = 0; i < uuid.length; i++) {
            attachmentService.deleteByPrimaryKey(uuid[i]);
            resule++;
        }
        if (resule > 0)
            return JsonUtils.messageJson(200, "温馨提示", "删除成功");
        else
            return JsonUtils.messageJson(300, "温馨提示", "删除失败");
    }

    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response) {
        List<Attachment> attachments = attachmentService.selectAll();

        //PoiUtils.exportExcel(response, request.getParameter("excelTitle"), request.getParameter("colName"), request.getParameter("fieldName"), attachments);
    }

    @ResponseBody
    @RequestMapping(value = "/config")
    public Object config(HttpServletRequest request ,String action) throws Exception {
        if("config".equals(action)) {
            return UeditorConfig.UEDITOR_CONFIG;
        }else if("uploadimage".equals(action)||"uploadscrawl".equals(action)){
            return imgUpload(request);
        }else if("uploadfile".equals(action)){
            return fileUpload(request);
        }
        return null;
    }

    /**
     * @MethodName imgUpload
     * @Description 富文本上传
     * @Param request
     * @Return java.util.Map<java.lang.String,java.lang.Object>
     * @Author 宋景民<songjingmin@zuoyoutech.com>
     * @Date 2019/7/3 14:12
     */
    @RequestMapping(value = "/imgUpload")
    @ResponseBody
    public Map<String, Object> imgUpload(HttpServletRequest request) {
        Map<String, Object> rs = new HashMap<String, Object>();
        MultipartHttpServletRequest mReq = null;
        MultipartFile file = null;
        String fileName = "";
        String originalFileName = "";

        try {
            mReq = (MultipartHttpServletRequest) request;
            // 从config.json中取得上传文件的ID "imageFieldName": "upfile", /* 提交的图片表单名称 */
            file = mReq.getFile("upfile");

            if (!file.isEmpty()) {
                // 取得文件的原始文件名称
                originalFileName = file.getOriginalFilename();
                fileName = originalFileName;

                // 设置上传目录
                String ext = (FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();
                String storePath = "";
                if ("jpg".equals(ext) || "png".equals(ext) || "jpeg".equals(ext) || "bmp".equals(ext)) {
                    storePath = "upload/image";
                } else {
                    storePath = "upload/video";
                }
                String uploadPath = getUploadDir(storePath);
                //
                ////获取服务器根目录 D:\wwwroot\java\ewsdSC\system\src\main\resources\static
                //File path = new File(ResourceUtils.getURL("classpath:").getPath().replace("target/classes","src/main/resources/static"));
                //// 编辑上传目录，使其生效
                //File uploadPath = new File(path.getAbsolutePath(),storePath);
                //System.out.println("upload url:"+uploadPath.getAbsolutePath());
                // 获取新的文件名
                String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + "_" + BaseUtils.getFixLengthNum(10000, 99999) + "." + ext;


                //获取上传的图片具体路径
                File targetFile = new File(uploadPath, newFileName);

                //获取父目录
                File pfile = new File(uploadPath);

                //判断如果目录不存在，先创建
                if(!pfile.exists())pfile.mkdirs();

                // 文件上传
                file.transferTo(targetFile);

                // 文件访问路径
                String httpImgPath = uploadPath + "/" + fileName;
                String filepath = "/" + uploadPath.substring(uploadPath.indexOf("uploads")) + newFileName;

                rs.put("state", "SUCCESS"); // UEDITOR的规则:不为SUCCESS则显示state的内容
                rs.put("url", "/system/attachment/showPic?path="+URLEncoder.encode(filepath,"UTF-8")); //能访问到你现在图片的路径
                rs.put("title", originalFileName);
                rs.put("original", originalFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("state", "文件上传失败!"); //在此处写上错误提示信息，这样当错误的时候就会显示此信息
            rs.put("url", "");
            rs.put("title", "");
            rs.put("original", "");
        }
        return rs;

    }

    @RequestMapping(value = "/fileUpload")
    @ResponseBody
    public Map<String, Object> fileUpload(HttpServletRequest request) {
        Map<String, Object> rs = new HashMap<String, Object>();
        MultipartHttpServletRequest mReq = null;
        MultipartFile file = null;
        String fileName = "";
        String originalFileName = "";


        try {
            mReq = (MultipartHttpServletRequest) request;
            // 从config.json中取得上传文件的ID "imageFieldName": "upfile", /* 提交的图片表单名称 */
            file = mReq.getFile("upfile");

          if (!file.isEmpty()) {
                // 取得文件的原始文件名称
                originalFileName = file.getOriginalFilename();
                fileName = originalFileName;

                // 设置上传目录
                String ext = (FilenameUtils.getExtension(file.getOriginalFilename())).toLowerCase();
                String uploadPath = getUploadDir(ext);

              // 获取新的文件名
              String newFileName = new SimpleDateFormat("yyyyMMddHHmmssSSSS").format(new Date()) + "_" + BaseUtils.getFixLengthNum(10000, 99999) + "." + ext;

              //获取上传的图片具体路径
              File targetFile = new File(uploadPath, newFileName);

              //获取父目录
              File pfile = new File(uploadPath);

              //判断如果目录不存在，先创建
              if(!pfile.exists())pfile.mkdirs();

              // 文件上传
              file.transferTo(targetFile);

              // 文件访问路径
              String httpImgPath = uploadPath + "/" + fileName;
              String filepath = "/" + uploadPath.substring(uploadPath.indexOf("uploads")) + newFileName;


              save("ueditor", "fileUpload", request.getParameter("category"), request.getParameter("puuid"), ext, file.getSize(), fileName, file.getName(), filepath);

              rs.put("state", "SUCCESS"); // UEDITOR的规则:不为SUCCESS则显示state的内容
              rs.put("url", "/system/attachment/showPic?path="+URLEncoder.encode(filepath,"UTF-8")); //能访问到你现在图片的路径
              rs.put("title", originalFileName);
              rs.put("original", originalFileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
            rs.put("state", "文件上传失败!"); //在此处写上错误提示信息，这样当错误的时候就会显示此信息
            rs.put("url", "");
            rs.put("title", "");
            rs.put("original", "");
        }
        return rs;

    }

    @RequestMapping("/showPic")
    public ResponseEntity<byte[]> showPic(HttpServletRequest request) throws Exception {
        String path = request.getParameter("path");
        Attachment attachment = attachmentService.getAttachmentByPath(path);
        String fileNameOrg = attachment == null?"image" :attachment.getFileName();
        if(path.indexOf(uploadDir) == -1){
            path = uploadDir + path;
        }
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String(fileNameOrg.getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }

    @RequestMapping("/showPicByPidOne")
    public ResponseEntity<byte[]> showPicByPidOne(HttpServletRequest request) throws Exception {
        String puuid = request.getParameter("puuid");
        Attachment attachment = attachmentService.getByPuuidOne(puuid);
        String path = attachment.getFilePath();
        String fileNameOrg = attachment == null?"image" :attachment.getFileName();
        if(path.indexOf(uploadDir) == -1){
            path = uploadDir + path;
        }
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        String fileName = new String(fileNameOrg.getBytes("UTF-8"), "iso-8859-1");//为了解决中文名称乱码问题
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
}
