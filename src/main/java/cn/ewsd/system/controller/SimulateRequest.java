package cn.ewsd.system.controller;

import cn.ewsd.common.utils.HttpRequestUtils;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static cn.ewsd.common.utils.JsonUtils.logger;

public class SimulateRequest {

    static String LOGIN_URL = "http://cas.ewsd.cn:8112/sso/login?service=http://portal.ewsd.cn/index.jsp";

    static Element loginForm;
    static String action;
    static String username = "ewsd019017";
    static String password = "357357";
    static String lt = "";
    static String _eventId = "";
    static String btn_submit = "";

    public static void main(String[] args) throws IOException {
        fetchNecessaryParam();
        mockLogin();
    }

    /*public static void main(String[] args) throws IOException {
        fetchNecessaryParam();
        Map<String, String> loginPageCookies = Jsoup.connect(LOGIN_URL)
                .method(Connection.Method.POST).execute().cookies();

        Map<String, String> map = new HashMap<String, String>();
        map.put("username", username);
        map.put("password", password);
        map.put("lt", lt);
        map.put("_eventId", _eventId);
        map.put("btn_submit", btn_submit);
        Connection.Response response = Jsoup.connect(action)
                .data(map)
                .method(Connection.Method.POST)
                .timeout(20000)
                .execute();

        Map<String, String> userCookies = response.cookies();

        Element content = Jsoup.connect("http://portal.ewsd.cn/sys/portal/page.jsp").cookies(userCookies).get().body();
        System.out.println(content);*//*
    }*/

    /**
     * 获取必要的登陆参数信息
     *
     * @throws IOException
     */
    public static void fetchNecessaryParam() throws IOException {
        // 查看CSDN登陆页面源码发现登陆时需要post5个参数
        // name、password，另外三个在页面的隐藏域中，a good start
        logger.info("获取必要的登陆信息。。。。。");
        // 这样登陆不行，因为第一次需要访问需要拿到上下文context
        // Document doc = Jsoup.connect(LOGIN_URL).get();
        String html = HttpRequestUtils.sendGet(LOGIN_URL, "");
        Document doc = Jsoup.parse(html);
        Element form = doc.select("#loginForm").get(0);
        //username = form.select("input[name=username]").get(0).val();
        //password = form.select("input[name=password]").get(0).val();
        loginForm = form.select("#loginForm").get(0);
        action = "http://cas.ewsd.cn:8112" + loginForm.attr("action");
        //action = "http://cas.ewsd.cn:8112/sso/login?service=http://portal.ewsd.cn/sys/person/sys_person_zone/sysPersonZone.do%3Fmethod=view";
        //action = "http://cas.ewsd.cn:8112/sso/login";
        lt = form.select("input[name=lt]").get(0).val();
        _eventId = form.select("input[name=_eventId]").get(0).val();
        btn_submit = form.select("#btn_submit").get(0).val();
        btn_submit = "提交";
        logger.info(lt);
        logger.info(_eventId);
        logger.info(btn_submit);
        logger.info("获取成功。。。。。");
    }

    private static boolean mockLogin() {
        logger.info("开始登陆。。。。。");
        boolean result = false;
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", username));
        nvps.add(new BasicNameValuePair("password", password));
        nvps.add(new BasicNameValuePair("lt", lt));
        nvps.add(new BasicNameValuePair("_eventId", _eventId));
        nvps.add(new BasicNameValuePair("btn_submit", btn_submit));
        logger.info(username);
        logger.info(password);
        logger.info(lt);
        logger.info(_eventId);
        //String ret = HttpRequestUtils.sendPost(action, nvps);
        String ret = HttpRequestUtils.sendPost(action, "username=" + username + "&password=" + password + "&lt=" + lt + "&_eventId=" + _eventId + "&btn_submit=" + btn_submit, false);
        if (ret.indexOf("redirect_back") > -1) {
            logger.info("登陆成功。。。。。");
            result = true;
        } else if (ret.indexOf("登录太频繁") > -1) {
            logger.info("登录太频繁，请稍后再试。。。。。");
        } else {
            logger.info("登陆失败。。。。。");
        }
        return result;
    }

}
