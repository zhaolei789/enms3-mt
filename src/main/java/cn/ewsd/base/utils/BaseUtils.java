package cn.ewsd.base.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * 公用方法
 * ============================================================================
 *
 * @author 易网时代<service@ewsd.cn>
 * @version 0.1
 * ============================================================================
 * @date 2015-06-01
 */

@SuppressWarnings({"unused", "restriction"})
public class BaseUtils {

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    // 时间戳转换为日期格式
    public static String TimeStampToDate(String timestampString, String formats) {
        Long timestamp = Long.parseLong(timestampString) * 1000;
        String date = new SimpleDateFormat(formats).format(new Date(timestamp));
        return date;
    }

    public static int getFixLengthNum(int min, int max) {
        return new Random().nextInt(max) % (max - min + 1) + min;
    }

    /**
     * 将某个日期以固定格式转化成字符串
     *
     * @param date
     * @return String
     */
    public static String dateToStr(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = sdf.format(date);
        return str;
    }

    /**
     * 判断任意一个整数是否素数
     *
     * @param n
     * @return boolean
     */
    public static boolean isPrimes(int n) {
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 获得任意一个整数的阶乘，递归
     *
     * @param n
     * @return n!
     */
    public static int factorial(int n) {
        if (n == 1) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    /**
     * 将指定byte数组以16进制的形式打印到控制台
     *
     * @param hint String
     * @param b    byte[]
     * @return void
     */
    public static void printHexString(String hint, byte[] b) {
        System.out.print(hint);
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            System.out.print(hex.toUpperCase() + " ");
        }
        System.out.println("");
    }

    /**
     * 分割字符串
     *
     * @param str       String 原始字符串
     * @param splitsign String 分隔符
     * @return String[] 分割后的字符串数组
     */
    public static String[] split(String str, String splitsign) {
        int index;
        if (str == null || splitsign == null)
            return null;
        ArrayList al = new ArrayList();
        while ((index = str.indexOf(splitsign)) != -1) {
            al.add(str.substring(0, index));
            str = str.substring(index + splitsign.length());
        }
        al.add(str);
        return (String[]) al.toArray(new String[0]);
    }

    /**
     * 替换字符串
     *
     * @param from   String 原始字符串
     * @param to     String 目标字符串
     * @param source String 母字符串
     * @return String 替换后的字符串
     */
    public static String replace(String from, String to, String source) {
        if (source == null || from == null || to == null)
            return null;
        StringBuffer bf = new StringBuffer("");
        int index = -1;
        while ((index = source.indexOf(from)) != -1) {
            bf.append(source.substring(0, index) + to);
            source = source.substring(index + from.length());
            index = source.indexOf(from);
        }
        bf.append(source);
        return bf.toString();
    }

    /**
     * 替换字符串，能能够在HTML页面上直接显示(替换双引号和小于号)
     *
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlencode(String str) {
        if (str == null) {
            return null;
        }

        return replace("\"", "&quot;", replace("<", "&lt;", str));
    }

    /**
     * 替换字符串，将被编码的转换成原始码（替换成双引号和小于号）
     *
     * @param str String
     * @return String
     */
    public static String htmldecode(String str) {
        if (str == null) {
            return null;
        }

        return replace("&quot;", "\"", replace("&lt;", "<", str));
    }

    private static final String _BR = "<br/>";

    /**
     * 在页面上直接显示文本内容，替换小于号，空格，回车，TAB
     *
     * @param str String 原始字符串
     * @return String 替换后的字符串
     */
    public static String htmlshow(String str) {
        if (str == null) {
            return null;
        }

        str = replace("<", "&lt;", str);
        str = replace(" ", "&nbsp;", str);
        str = replace("\r\n", _BR, str);
        str = replace("\n", _BR, str);
        str = replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;", str);
        return str;
    }

    /**
     * 返回指定字节长度的字符串
     *
     * @param str    String 字符串
     * @param length int 指定长度
     * @return String 返回的字符串
     */
    public static String toLength(String str, int length) {
        if (str == null) {
            return null;
        }
        if (length <= 0) {
            return "";
        }
        try {
            if (str.getBytes("GBK").length <= length) {
                return str;
            }
        } catch (Exception ex) {
        }
        StringBuffer buff = new StringBuffer();

        int index = 0;
        char c;
        length -= 3;
        while (length > 0) {
            c = str.charAt(index);
            if (c < 128) {
                length--;
            } else {
                length--;
                length--;
            }
            buff.append(c);
            index++;
        }
        buff.append("");
        return buff.toString();
    }

    /**
     * 判断是否为整数
     *
     * @param str 传入的字符串
     * @return 是整数返回true, 否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断是否为浮点数，包括double和float
     *
     * @param str 传入的字符串
     * @return 是浮点数返回true, 否则返回false
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断输入的字符串是否符合Email样式.
     *
     * @param str 传入的字符串
     * @return 是Email样式返回true, 否则返回false
     */
    public static boolean isEmail(String str) {
        Pattern pattern = Pattern
                .compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断输入的字符串是否为纯汉字
     *
     * @param str 传入的字符窜
     * @return 如果是纯汉字返回true, 否则返回false
     */
    public static boolean isChinese(String str) {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(str).matches();
    }

    /**
     * 是否为空白,包括null和""
     *
     * @param str
     * @return
     */
    public static boolean isBlank(String str) {
        return str == null || str.trim().length() == 0;
    }

    /**
     * 判断是否为质数
     *
     * @param x
     * @return
     */
    public static boolean isPrime(int x) {
        if (x <= 7) {
            if (x == 2 || x == 3 || x == 5 || x == 7)
                return true;
        }
        int c = 7;
        if (x % 2 == 0)
            return false;
        if (x % 3 == 0)
            return false;
        if (x % 5 == 0)
            return false;
        int end = (int) Math.sqrt(x);
        while (c <= end) {
            if (x % c == 0) {
                return false;
            }
            c += 4;
            if (x % c == 0) {
                return false;
            }
            c += 2;
            if (x % c == 0) {
                return false;
            }
            c += 4;
            if (x % c == 0) {
                return false;
            }
            c += 2;
            if (x % c == 0) {
                return false;
            }
            c += 4;
            if (x % c == 0) {
                return false;
            }
            c += 6;
            if (x % c == 0) {
                return false;
            }
            c += 2;
            if (x % c == 0) {
                return false;
            }
            c += 6;
        }
        return true;
    }

    /**
     * 全角字符转半角字符
     *
     * @param QJStr
     * @return String
     */
    public static final String QJToBJChange(String QJStr) {
        char[] chr = QJStr.toCharArray();
        String str = "";
        for (int i = 0; i < chr.length; i++) {
            chr[i] = (char) ((int) chr[i] - 65248);
            str += chr[i];
        }
        return str;
    }

    /**
     * 去掉字符串中重复的子字符串
     *
     * @param str
     * @return String
     */
    private static String removeSameString(String str) {
        Set<String> mLinkedSet = new LinkedHashSet<String>();
        String[] strArray = str.split(" ");
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < strArray.length; i++) {
            if (!mLinkedSet.contains(strArray[i])) {
                mLinkedSet.add(strArray[i]);
                sb.append(strArray[i] + " ");
            }
        }
        System.out.println(mLinkedSet);
        return sb.toString().substring(0, sb.toString().length() - 1);
    }

    // 过滤特殊字符
    public static String encoding(String src) {
        if (src == null)
            return "";
        StringBuilder result = new StringBuilder();
        if (src != null) {
            src = src.trim();
            for (int pos = 0; pos < src.length(); pos++) {
                switch (src.charAt(pos)) {
                    case '\"':
                        result.append("&quot;");
                        break;
                    case '<':
                        result.append("&lt;");
                        break;
                    case '>':
                        result.append("&gt;");
                        break;
                    case '\'':
                        result.append("&apos;");
                        break;
                    case '&':
                        result.append("&amp;");
                        break;
                    case '%':
                        result.append("&pc;");
                        break;
                    case '_':
                        result.append("&ul;");
                        break;
                    case '#':
                        result.append("&shap;");
                        break;
                    case '?':
                        result.append("&ques;");
                        break;
                    default:
                        result.append(src.charAt(pos));
                        break;
                }
            }
        }
        return result.toString();
    }

    // 反过滤特殊字符
    public static String decoding(String src) {
        if (src == null)
            return "";
        String result = src;
        result = result.replace("&quot;", "\"").replace("&apos;", "\'");
        result = result.replace("&lt;", "<").replace("&gt;", ">");
        result = result.replace("&amp;", "&");
        result = result.replace("&pc;", "%").replace("&ul", "_");
        result = result.replace("&shap;", "#").replace("&ques", "?");
        return result;
    }

    /**
     * 人民币转成大写
     *
     * @param value
     * @return String
     */
    public static String hangeToBig(double value) {
        char[] hunit = {'拾', '佰', '仟'}; // 段内位置表示
        char[] vunit = {'万', '亿'}; // 段名表示
        char[] digit = {'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'}; // 数字表示
        long midVal = (long) (value * 100); // 转化成整形
        String valStr = String.valueOf(midVal); // 转化成字符串

        String head = valStr.substring(0, valStr.length() - 2); // 取整数部分
        String rail = valStr.substring(valStr.length() - 2); // 取小数部分

        String prefix = ""; // 整数部分转化的结果
        String suffix = ""; // 小数部分转化的结果
        // 处理小数点后面的数
        if (rail.equals("00")) { // 如果小数部分为0
            suffix = "整";
        } else {
            suffix = digit[rail.charAt(0) - '0'] + "角"
                    + digit[rail.charAt(1) - '0'] + "分"; // 否则把角分转化出来
        }
        // 处理小数点前面的数
        char[] chDig = head.toCharArray(); // 把整数部分转化成字符数组
        char zero = '0'; // 标志'0'表示出现过0
        byte zeroSerNum = 0; // 连续出现0的次数
        for (int i = 0; i < chDig.length; i++) { // 循环处理每个数字
            int idx = (chDig.length - i - 1) % 4; // 取段内位置
            int vidx = (chDig.length - i - 1) / 4; // 取段位置
            if (chDig[i] == '0') { // 如果当前字符是0
                zeroSerNum++; // 连续0次数递增
                if (zero == '0') { // 标志
                    zero = digit[0];
                } else if (idx == 0 && vidx > 0 && zeroSerNum < 4) {
                    prefix += vunit[vidx - 1];
                    zero = '0';
                }
                continue;
            }
            zeroSerNum = 0; // 连续0次数清零
            if (zero != '0') { // 如果标志不为0,则加上,例如万,亿什么的
                prefix += zero;
                zero = '0';
            }
            prefix += digit[chDig[i] - '0']; // 转化该数字表示
            if (idx > 0)
                prefix += hunit[idx - 1];
            if (idx == 0 && vidx > 0) {
                prefix += vunit[vidx - 1]; // 段结束位置应该加上段名如万,亿
            }
        }

        if (prefix.length() > 0)
            prefix += '圆'; // 如果整数部分存在,则有圆的字样
        return prefix + suffix; // 返回正确表示

    }

    /**
     * 利用BASE64进行加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException     没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public static String encodeByBase64(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        byte[] b = null;
        String s = null;
        try {
            b = str.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * 利用BASE64进行解密
     *
     * @param encodeStr 待解密的字符串
     * @return 解密后的字符串
     * @throws NoSuchAlgorithmException 没有这种产生消息摘要的算法
     * @throws IOException
     */
    public static String decodeByBase64(String encodeStr) throws NoSuchAlgorithmException, IOException {
        byte[] b = null;
        String result = null;
        if (encodeStr != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(encodeStr);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 利用MD5进行加密
     *
     * @param str 待加密的字符串
     * @return 加密后的字符串
     * @throws NoSuchAlgorithmException     没有这种产生消息摘要的算法
     * @throws UnsupportedEncodingException
     */
    public static String encodeByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //确定计算方法
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64en = new BASE64Encoder();
        //加密后的字符串
        String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        return newstr;
    }

    /**
     * 判断用户密码是否正确
     *
     * @param newStr 用户输入的密码
     * @param oldStr 数据库中存储的密码－－用户密码的摘要
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public boolean checkEncodeByMd5(String newStr, String oldStr) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        if (encodeByMd5(newStr).equals(oldStr))
            return true;
        else
            return false;
    }

    public BaseUtils() {
        try {
            /*boolean isAllow = false;
            String frameSessionId = null;
            if (Constants.FRAME_SESSION_ID == null || Constants.FRAME_SESSION_ID == "") {
                frameSessionId = UUIDGenerator();
            } else {
                frameSessionId = Constants.FRAME_SESSION_ID;
            }

            List<String> protocolList;
            protocolList = getLocalIPList();
            String[] frameSessionIdArr = BaseUtils.decodeByBase64(frameSessionId).split(",");
            for (String frameSessionId2 : frameSessionIdArr)
                for (String protocol : protocolList) if (protocol.contains(frameSessionId2)) isAllow = true;

            if (!isAllow) {
                System.out.println(protocolList);
                System.exit(0);
            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成uuid序列号
     *
     * @return
     */
    public static String UUIDGenerator() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "").toUpperCase();
    }

    public static List<String> getLocalIPList() {
        List<String> ipList = new ArrayList<String>();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface
                    .getNetworkInterfaces();
            NetworkInterface networkInterface;
            Enumeration<InetAddress> inetAddresses;
            InetAddress inetAddress;
            String ip;
            while (networkInterfaces.hasMoreElements()) {
                networkInterface = networkInterfaces.nextElement();
                inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) {
                        // IPV4
                        ip = inetAddress.getHostAddress();
                        ipList.add(ip);
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return ipList;
    }

    public Map<String, Object> jsonReturn(int statusCode) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功");
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "操作失败，请重试");
        }
        jsonObj.put("closeCurrent", true);
        return jsonObj;
    }

    public Map<String, Object> jsonReturn(int statusCode, boolean closeCurrent) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功");
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "操作失败，请重试");
        }
        jsonObj.put("closeCurrent", closeCurrent);
        return jsonObj;
    }

    public Map<String, Object> jsonReturn(int statusCode, String msg) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功 " + msg);
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "操作失败:" + msg);
        }
        jsonObj.put("closeCurrent", true);
        return jsonObj;
    }

    public Map<String, Object> jsonReturn(int statusCode, String msg,
                                          boolean closeCurrent) {
        Map<String, Object> jsonObj = new HashMap<String, Object>();
        if (statusCode == 200) {
            jsonObj.put("statusCode", "200");
            jsonObj.put("message", "操作成功 " + msg);
        } else if (statusCode == 300) {
            jsonObj.put("statusCode", "300");
            jsonObj.put("message", "操作失败:" + msg);
        }
        jsonObj.put("closeCurrent", closeCurrent);
        return jsonObj;
    }

    public static String filter(HttpServletRequest request, String filterStr) {
        return "".equals(filterStr) ? " 1 = 1 " + filter(request) : filter(request);
    }

    /**
     * 组合过滤查询语句
     *
     * @param request
     * @return
     */
    public static String filter(HttpServletRequest request) {
        //String searchHql = "";
        String searchHql = " AND 1 = 1 ";
        String simpleFilter = request.getParameter("filterRules");
        String advanceFilter = request.getParameter("advanceFilter");
        String filterRules = simpleFilter + advanceFilter;
        if (filterRules.equals("nullnull")) {
            filterRules = null;
        } else {
            filterRules = filterRules.replace("null[", "[").replace("]null", "]").replace("][", "");
        }
        String fieldName = null;
        String op = null;
        String fieldValue = null;
        String joinValue = null;
        String conditionAndValue = null;
        String join = null;

        if (filterRules != null) {
            JSONArray jsonArray = JSONArray.parseArray(filterRules);
            for (int k = 0; k < jsonArray.size(); k++) {
                JSONObject jsonObject = (JSONObject) jsonArray.get(k);

                fieldName = jsonObject.getString("field");

                if (!fieldName.equals("")) {
                    op = jsonObject.getString("op").toString();
                    fieldValue = jsonObject.getString("value");

                    if (op.equals("contains")) {
                        conditionAndValue = " LIKE '%" + fieldValue + "%'";
                    } else if (op.equals("equal")) {
                        conditionAndValue = " = '" + fieldValue + "'";
                    } else if (op.equals("notequal")) {
                        conditionAndValue = " <> '" + fieldValue + "'";
                    } else if (op.equals("beginwith")) {
                        conditionAndValue = " LIKE '" + fieldValue + "%'";
                    } else if (op.equals("endwith")) {
                        conditionAndValue = " LIKE '%" + fieldValue + "'";
                    } else if (op.equals("less")) {
                        conditionAndValue = " < '" + fieldValue + "'";
                    } else if (op.equals("lessorequal")) {
                        conditionAndValue = " <= '" + fieldValue + "'";
                    } else if (op.equals("greater")) {
                        conditionAndValue = " > '" + fieldValue + "'";
                    } else if (op.equals("greaterorequal")) {
                        conditionAndValue = " >= '" + fieldValue + "'";
                    }

                    if (k == 0) {
                        join = " AND ";
                    } else {
                        JSONObject joinObject = (JSONObject) jsonArray.get(k);
                        joinValue = joinObject.getString("join");
                        if (joinValue == null) {
                            joinValue = "AND";
                        }

                        if (joinValue.equals("or")) {
                            join = " OR ";
                        } else {
                            join = " AND ";
                        }
                    }
                    String lb = jsonObject.getString("lb") == null ? "" : jsonObject.getString("lb");
                    String rb = jsonObject.getString("rb") == null ? "" : jsonObject.getString("rb");

                    searchHql += join + lb + fieldName + conditionAndValue + rb;
                    //System.out.println(myobj.getString("field"));
                    //System.out.print(jsonArray.get(k) + "\t");
                }
            }
        }
        return searchHql;
    }

    /**
     * 获得排序信息，默认以创建时间排序
     *
     * @param request
     * @return 返回带排序信息的字符串
     */
    public static String sort(HttpServletRequest request) {
        String sortHql = "";
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String aa = "";
        if (sort != null) {
            String[] sortArr = sort.split(",");
            String[] orderArr = order.split(",");
            for (int i = 0; i < sortArr.length; i++) {
                aa += "," + sortArr[i] + " " + orderArr[i];
            }
            sortHql += " ORDER BY " + aa.substring(1);
        } else {
            sortHql = " ORDER BY createTime DESC";
        }
        return sortHql;
    }

    public static Example mybatisFilter(HttpServletRequest request, Example example) {
        String searchHql = "";
        String simpleFilter = request.getParameter("filterRules");
        String advanceFilter = request.getParameter("advanceFilter");
        String filterRules = simpleFilter + advanceFilter;
        if (filterRules.equals("nullnull")) {
            filterRules = null;
        } else {
            filterRules = filterRules.replace("null[", "[").replace("]null", "]").replace("][", "");
        }
        String fieldName = null;
        String op = null;
        String fieldValue = null;
        String joinValue = null;
        String conditionAndValue = null;
        String join = null;

        //Example example = new Example(clazz);

        Example.Criteria criteria = example.createCriteria();

        if (filterRules != null) {
            JSONArray jsonArray = JSONArray.parseArray(filterRules);
            for (int k = 0; k < jsonArray.size(); k++) {

                JSONObject joinObject = (JSONObject) jsonArray.get(k);
                joinValue = joinObject.getString("join");

                JSONObject jsonObject = (JSONObject) jsonArray.get(k);
                fieldName = jsonObject.getString("field");

                if (joinValue == null) {
                    //example.or();
                }
                if (joinValue.equals("or")) {
                    //example.or(criteria);
                    if (!fieldName.equals("")) {
                        op = jsonObject.getString("op").toString();
                        fieldValue = jsonObject.getString("value");

                        if (op.equals("contains")) {
                            example.or().andLike(fieldName, "%" + fieldValue + "%");
                        } else if (op.equals("equal")) {
                            example.or().andEqualTo(fieldName, fieldValue);
                        } else if (op.equals("notequal")) {
                            example.or().andNotEqualTo(fieldName, fieldValue);
                        } else if (op.equals("beginwith")) {
                            example.or().andLike(fieldName, fieldValue + "%");
                        } else if (op.equals("endwith")) {
                            example.or().andLike(fieldName, "%" + fieldValue);
                        } else if (op.equals("less")) {
                            example.or().andLessThan(fieldName, fieldValue);
                        } else if (op.equals("lessorequal")) {
                            example.or().andLessThanOrEqualTo(fieldName, fieldValue);
                        } else if (op.equals("greater")) {
                            example.or().andGreaterThan(fieldName, fieldValue);
                        } else if (op.equals("greaterorequal")) {
                            example.or().andGreaterThanOrEqualTo(fieldName, fieldValue);
                        }

                        //System.out.println(myobj.getString("field"));
                        //System.out.print(jsonArray.get(k) + "\t");
                    }
                } else {
                    if (!fieldName.equals("")) {
                        op = jsonObject.getString("op").toString();
                        fieldValue = jsonObject.getString("value");

                        if (op.equals("contains")) {
                            criteria.andLike(fieldName, "%" + fieldValue + "%");
                        } else if (op.equals("equal")) {
                            criteria.andEqualTo(fieldName, fieldValue);
                        } else if (op.equals("notequal")) {
                            criteria.andNotEqualTo(fieldName, fieldValue);
                        } else if (op.equals("beginwith")) {
                            criteria.andLike(fieldName, fieldValue + "%");
                        } else if (op.equals("endwith")) {
                            criteria.andLike(fieldName, "%" + fieldValue);
                        } else if (op.equals("less")) {
                            criteria.andLessThan(fieldName, fieldValue);
                        } else if (op.equals("lessorequal")) {
                            criteria.andLessThanOrEqualTo(fieldName, fieldValue);
                        } else if (op.equals("greater")) {
                            criteria.andGreaterThan(fieldName, fieldValue);
                        } else if (op.equals("greaterorequal")) {
                            criteria.andGreaterThanOrEqualTo(fieldName, fieldValue);
                        }

                        //System.out.println(myobj.getString("field"));
                        //System.out.print(jsonArray.get(k) + "\t");
                    }
                    //example.or(criteria);
                    //example.createCriteria();
                }

            }
        }
        return example;
    }

    public static Example mybatisSort(HttpServletRequest request, Example example) {
        //Example example = new Example(clazz);

        String sortHql = "";
        String sort = request.getParameter("sort");
        String order = request.getParameter("order");
        String aa = "";
        if (sort != null) {
            String[] sortArr = sort.split(",");
            String[] orderArr = order.split(",");
            for (int i = 0; i < sortArr.length; i++) {
                if ("asc".equals(orderArr[i])) {
                    example.orderBy(sortArr[i]).asc();
                } else {
                    example.orderBy(sortArr[i]).desc();
                }
            }
        } else {
            example.setOrderByClause("createTime DESC");
        }
        return example;
    }

    /**
     * 过滤排序器
     *
     * @param request
     * @return 返回带isDel <> '1'+排序信息的字符串
     */
    public static String filterSort(HttpServletRequest request) {
        return filter(request) + sort(request);
    }

    public static String filterSort(HttpServletRequest request, String filterStr) {
        return filter(request, filterStr) + sort(request);
    }

    /**
     * hql过滤排序器，传入自定义过滤条件filterStr
     *
     * @param request
     * @param clazz
     * @param filterStr 例：String filterStr = "userNameId = 'admin' AND "
     * @return 返回带自定义过滤条件filterStr + 1 = 1 + 排序信息的hql语句
     */
    public static String filterSort(HttpServletRequest request, Class clazz, String filterStr) {
        return "FROM " + clazz.getName() + " WHERE 1 = 1 " + filterStr + filterSort(request);
    }

    /**
     * hql过滤排序器，不传入自定义过滤条件
     *
     * @param request
     * @param clazz
     * @return 返回带1 = 1 + 排序信息的hql语句
     */
    public static String filterSort(HttpServletRequest request, Class clazz) {
        return filterSort(request, clazz, "");
    }

}
