/******************************************************************
ϵͳ����: J2EEͨ�õײ�
ģ������: ����
�����Ȩ: krwz
����˵��: �쳣��
ϵͳ�汾: 1.0
������Ա: stone
����ʱ��: 2020-11-25
�����Ա:
����ĵ�:
�޸ļ�¼: �޸����� �޸���Ա �޸�˵��
******************************************************************/
package cn.ewsd.base.utils;

import java.lang.Exception;
import java.util.HashMap;

@SuppressWarnings("serial")
public class XException extends Exception {
    public static final String ERR_DEFAULT    = "ERR_DEFAULT";
    public static final String ERR_LOGIN      = "ERR_LOGIN";
    public static final String ERR_TIMEOUT    = "ERR_TIMEOUT";
    public static final String ERR_ERP        = "ERR_ERP";
    public static final String ERR_BC_LOGIN   = "ERR_BC_LOGIN";
	public static final String ERR_OPERATE 	  = "ERR_OPERATE";
	public static final String ERR_DOWNLOAD   = "ERR_DOWNLOAD";
    
	protected static HashMap<String,String> msgMap = new HashMap<String,String>();;
	static{
	    msgMap.put(ERR_DEFAULT,      "未知错误");
	    msgMap.put(ERR_LOGIN,        "登录失败");
	    msgMap.put(ERR_TIMEOUT,      "超时或未登录");
	    msgMap.put(ERR_ERP,          "ERP内错误");
	    msgMap.put(ERR_BC_LOGIN,     "终端登录失败");
		msgMap.put(ERR_OPERATE, 	 "操作失败");
		msgMap.put(ERR_DOWNLOAD, 	 "下载失败");
	}
	
    protected String code;
    protected String info;

    public XException(String code) {
        super(getMsg(code));
        this.code = code;
        this.info = getMsg(code);
        if (this instanceof Exception) {
        	//Logger.getInstance().error(this);
        }
    }

    public XException(String code, String info) {
        super(info);
        this.code = code;
        this.info = info;
        
        if (this instanceof XException) {
        	if(ERR_TIMEOUT.equals(code)){
        		//Logger.getInstance().error("��ʱ��δ��¼");
        	}else{
        		//Logger.getInstance().error(this);
        	}
        }
    }

	public static String getMsg(String code) {
		String msg = (String)msgMap.get(code);
		if (msg == null) {
		    return "δ֪����";
		}
		return msg;
	}
    
    public XException(Throwable e, String code, String info) {
        super(code+" "+info);
        this.code = code;
        this.info = info;
        if (this.getClass().getName().endsWith(".XException")) {
        	//Logger.getInstance().error(this.toString(),e);
        }
    }
    
    public String getCode() {
        return code;
    }
    
    public String getInfo() {
        return info;
    }

    public String toString() {
        return super.toString() + "\nCODE:" + code; 
    }
    
    public static String getInfo(Exception e,String defInfo) {
    	return e instanceof XException ? ((XException)e).getInfo() : defInfo;
    }

}
