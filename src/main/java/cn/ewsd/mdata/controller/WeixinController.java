//package cn.ewsd.mdata.controller;
//
//import cn.ewsd.base.utils.StringUtils;
//import cn.ewsd.base.utils.weixin.*;
//import cn.ewsd.common.bean.Audience;
//import cn.ewsd.common.controller.BaseController;
//import cn.ewsd.common.utils.JwtUtil;
//import cn.ewsd.common.utils.SnUtils;
//import cn.ewsd.mdata.mapper.UserMapper;
//import cn.ewsd.mdata.model.User;
//import cn.ewsd.mdata.service.UserService;
//
//import cn.hutool.json.JSONException;
//import cn.hutool.json.JSONObject;
//import com.alibaba.fastjson.JSON;
//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import javax.annotation.Resource;
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.URI;
//import java.util.Map;
//import java.util.SortedMap;
//import java.util.TreeMap;
//
//@Controller("weixinController")
//@RequestMapping("/mdata/weixin")
//public class WeixinController extends BaseController {
//    @Resource
//    private WeixinProperties weixinProperties;
//
//    @Autowired
//    UserService userService;
//
//    @Autowired
//    UserMapper userMapper;
//
//    @Autowired
//    private Audience audience;
//
//    /**
//     * 获取用户信息
//     *
//     * @param authResult
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping("getUserInfo")
//    @ResponseBody
//    public R getUserInfo(@RequestBody String authResult) throws Exception {
//
//        return getAppWeiXinUserInfo(authResult);
//    }
//
//
//    @RequestMapping("getUserByOpenid")
//    @ResponseBody
//    public User getUserByOpenid(String openid) throws Exception {
//        return userService.getUserByOpenid(openid);
//    }
//
//    //根据openid去查询用户信息
//    private R getAppWeiXinUserInfo(String authResult) {
//        com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(authResult);
//        Object authResult1 = jsonObject.get("authResult");
//        com.alibaba.fastjson.JSONObject jsonObject1 = com.alibaba.fastjson.JSONObject.parseObject(authResult1.toString());
//        AccessToken accessToken = JSON.toJavaObject(jsonObject1, AccessToken.class);
//        String uri = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken.getAccessToken() + "&openid=" + accessToken.getOpenid();
//        org.apache.http.client.HttpClient client = new DefaultHttpClient();
//        HttpGet get = new HttpGet(URI.create(uri));
//        try {
//            HttpResponse response = client.execute(get);
//            if (response.getStatusLine().getStatusCode() == 200) {
//                BufferedReader reader = new BufferedReader(
//                        new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
//                StringBuilder builder = new StringBuilder();
//                for (String temp = reader.readLine(); temp != null; temp = reader.readLine()) {
//                    builder.append(temp);
//                }
//                JSONObject object = new JSONObject(builder.toString().trim());
//                return insertOrUpdateUser(object);//insert or update sys_user表
//            }
//        } catch (ClientProtocolException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return R.ok();
//    }
//
//    private R insertOrUpdateUser(JSONObject object) {
//        String country = object.get("country").toString();
//        String nickName = object.get("nickname").toString();
//        String unionid = object.get("unionid").toString();
//        String province = object.get("province").toString();
//        String city = object.get("city").toString();
//        String openid = object.get("openid").toString();
//        String sex = object.get("sex").toString();
//        String headimgurl = object.get("headimgurl").toString();
//        // String language = object.get("language").toString();
//        User user = userService.getUserByOpenid(openid);// 根据 openid 判断是 update 还是 insert
//        if (null == user) {
//            user = new User();
//        }
//        user.setNickName(nickName);
//        user.setCountry(country);
//        user.setProvince(province);
//        user.setCity(city);
//        user.setSex(sex);
//        user.setAvatar(headimgurl);
//
//        String jwtToken = JwtUtil.createJWT(
//                user.getUuid(),
//                user.getUserNameId(),
//                user.getUserName(),
//                user.getOrgId(),
//                user.getUserGroup(),
//                audience.getClientId(),
//                audience.getName(),
//                audience.getExpiresSecond() * 1000,
//                audience.getBase64Secret()
//        );
//        Integer integer = 0;
//        if (!StringUtils.isNullOrEmpty(user.getUuid())) {
//            integer = userService.updateByPrimaryKeySelective(getUpdateData(user));
//        } else {
//            user.setUserNameId(openid);
//            user.setOpenid(openid);
//            user.setUnionid(unionid);
//            user.setIsDel(0);
//            integer = userService.insertSelective(getSaveData(user));
//        }
//        if (integer == 1) {
//            String judgeCellphone=StringUtils.isNullOrEmpty(user.getCellphone())?"no":"yes";
//            return R.ok().put("userInfo", user).put("openid", openid).put("token", jwtToken).put("judgeCellphone",judgeCellphone);
//        } else {
//            return R.error();
//        }
//    }
//
//
//    @RequestMapping("/play")
//    @ResponseBody
//    public SortedMap<String,Object> play(String total) {//@RequestBody@RequestBody String orderInfo
//        try {
//              SortedMap<String, Object> signParams = new TreeMap<String, Object>();
//            signParams.put("appid", weixinProperties.getAppId());//app_id
//            signParams.put("body","佐佑商城");//商品参数信息
//            signParams.put("mch_id", weixinProperties.getMchId());//微信商户账号
//            signParams.put("nonce_str", PayCommonUtil2.CreateNoncestr());//32位不重复的编号
//            signParams.put("notify_url", "http://192.168.1.104:9794/weixin/pla");//回调页面
//            signParams.put("out_trade_no", SnUtils.getRandomNo(10000,99999));//订单编号
//            signParams.put("spbill_create_ip",request.getRemoteAddr() );//请求的实际ip地址
//            signParams.put("total_fee",PayCommonUtil2.getMoney(total));//支付金额 单位为分
//            signParams.put("trade_type", "APP");//付款类型为APP
//            String sign = PayCommonUtil2.createSign("UTF-8", signParams,weixinProperties.getApiKey());//生成签名
//            signParams.put("sign", sign);
//            signParams.remove("key");//调用统一下单无需key（商户应用密钥）
//            String requestXml = PayCommonUtil2.getRequestXml2(signParams);//生成Xml格式的字符串
//            // 调用统一下单接口
//            String result = PayCommonUtil2.httpsRequest(weixinProperties.getUnifiedorderUrl(), "POST",
//                    requestXml);
//            // 返回的result成功结果取出prepay_id：
//            Map map = XMLUtil.doXMLParse(result);
//            String return_code=(String) map.get("return_code");
//            String prepay_id =null;
//            if (return_code.contains("SUCCESS")){
//                prepay_id=(String) map.get("prepay_id");//获取到prepay_id
//            }
//            long currentTimeMillis = System.currentTimeMillis();//生成时间戳
//            long second = currentTimeMillis / 1000L;
//            String seconds = String.valueOf(second).substring(0, 10);
//            SortedMap<String, Object> signParam = new TreeMap<String, Object>();
//            signParam.put("appid", weixinProperties.getAppId());//app_id
//            signParam.put("noncestr", PayCommonUtil2.CreateNoncestr());//自定义不重复的长度不长于32位
//            signParam.put("package", "Sign=WXPay");//默认sign=WXPay
//            signParam.put("partnerid", weixinProperties.getMchId());//微信商户账号
//            signParam.put("prepayid", prepay_id);//预付订单id
//            signParam.put("timestamp",seconds);//北京时间时间戳
//            String signAgain = PayCommonUtil2.createSign("UTF-8", signParam,weixinProperties.getApiKey());//再次生成签名
//            signParam.put("sign", signAgain);
//            return signParam;
//        } catch (Exception e) {
//            e.printStackTrace();
//            SortedMap<String, Object> errMap =new TreeMap<String, Object>();
//            errMap.put("errMsg", e.getMessage());
//            return errMap;
//        }
//    }
//}
