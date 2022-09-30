/******************************************************************
*ϵͳ����: J2EE�ײ�
*ģ������: 
 �����Ȩ: krwz
 ����˵��:
 ϵͳ�汾: 1.0
 ������Ա: stone
 ����ʱ��: 2020-11-25
*�����Ա:
*����ĵ�:
*�޸ļ�¼: �޸����� �޸���Ա �޸�˵��
******************************************************************/
package cn.ewsd.base.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


/**
 * Used to indicate the location of an axis on a 2D plot, prior to knowing the 
 * orientation of the plot.
 */
@SuppressWarnings("unchecked")
public class Data {
    
	public static final String PLUS = "PLUS";
	public static final String NOT_NEGATIVE = "NOT_NEGATIVE";
	
    public static final String[] CINT = {"һ","��","��","��","��","��","��","��","��","ʮ",
                                           "ʮһ","ʮ��","ʮ��","ʮ��","ʮ��","ʮ��","ʮ��","ʮ��","ʮ��","��ʮ",
                                           "��һ","����","����","����","����","����","����","����","����","��ʮ",
    									   "��һ","����","����","����","����","����","����","����","����","��ʮ",
                                           "��һ","�Ķ�","����","����","����","����","����","�İ�","�ľ�","��ʮ",
                                           "��һ","���","����","����","����","����","����","���","���","��ʮ",
                                           "��һ","����","����","����","����","����","����","����","����","��ʮ",
    									   "��һ","�߶�","����","����","����","����","����","�߰�","�߾�","��ʮ",
    									   "��һ","�˶�","����","����","����","����","����","�˰�","�˾�","��ʮ",
    									   "��һ","�Ŷ�","����","����","����","����","����","�Ű�","�ž�"};
    
    
    //��������֮��������
	public static long random(int from,int to){
		return Math.round(Math.random()*(999999-100000))+100000;
	}
    
    // �ж��Ƿ��� double
    public static boolean isDouble(String value) {
    	try {
    		Double.parseDouble(value.trim());
    	} catch(Exception e) {
    		return false;
    	}
    	return true;
    }
    
    // �Ƿ�������
    public static boolean isInteger(String value) {
    	if(value==null || "".equals(value.trim())) return false;
    	Pattern pattern = Pattern.compile("[0-9]*");  
        return pattern.matcher(value.trim()).matches();     
    }
    
    //�Ƿ�������
    public static boolean isEmail(String email) {
    	if(email==null || "".equals(email.trim())) return false;
    	final String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        final Pattern pattern = Pattern.compile(str);
        final Matcher mat = pattern.matcher(email.trim());
        if(!mat.find()) return false;
        return true;
    }
    
    //�Ƿ����ֻ�
    public static boolean isHandset(String handset) {
    	if(handset==null || "".equals(handset.trim())) return false;
        String str = "^1[3|4|5|8][0-9]\\d{8}$";
        final Pattern pattern = Pattern.compile(str);
        final Matcher mat = pattern.matcher(handset.trim());
        if(!mat.find()) return false;
        return true;
    }
    
    //תΪDouble
    public static double toDouble(String str){
    	if(str!=null && !str.equals("")){
    		try{
				return Double.parseDouble(str);
			}catch (Exception e) {
			}
		}
    	return 0;
    }
    
    public static double double2double(double data, int len) {
    	String str = Data.trimDouble(data, len);
    	return Double.parseDouble(str);
    }
    
    //תΪDouble
    public static int toInt(String str){
    	if(str!=null && !str.equals("")){
    		try{
				return Integer.parseInt(str);
			}catch (Exception e) {
			}
		}
    	return 0;
    }

    //תΪMB�ֽ�
    public static double toMB(long size, int len) {
    	return toDouble(trimDouble(1.0*size/1024/1024, len));
    }
    
    /**
     * �õ�16����String
     * @param byteData
     * @return
     */
    public static String getHexNum(byte byteData) {  
        return Integer.toHexString(byteData);
    }

    /**
     * ���ڼ�����ת������ͨ�����͡�
     * <br>���� 132,000,123.00 ת����Ϊ 132000123.00
     * @param data - Ҫת���������ַ���
     * @return String - ����ת����������ַ���
     */
    public static String finalToNormal(String data) {
        String newData = data;
        int index = newData.indexOf(',');
        while(index != -1){
            newData = newData.substring(0, index) + newData.substring(index+1);
            index = newData.indexOf(',');
        }
        
        return newData;
    }
    
    /**
     * ��ͨ������ת���ɽ��ڼ�������
     * <br>���� 132000123.00 ת����Ϊ 132,000,123.00
     * @param data - Ҫת���������ַ���
     * @return String - ����ת����������ַ���
     */
    public static String normalToFinal(String data) {
        if("".equals(data)) {
            return "";
        }
        
        int pos = data.lastIndexOf('.');
        int len = 0; //С��λ��
        if (pos != -1) {
            len = data.length() - 1 - pos;
        }
        
        try {
            double d = Double.parseDouble(data); 
            NumberFormat form = NumberFormat.getInstance();
            String mask = "#,##0";
            if (len > 0) {
                mask = "#,##0.";
                for (int i = 0; i < len; i++) {
                    mask = mask + "0";
                }
            }
            
            ((DecimalFormat) form).applyPattern(mask);
            return form.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String normalToFinal(double data, int len) {
    	String str = formatDouble(data,len);
    	return normalToFinal(str);
    }
    
    public static String normalToFinal(double data) {
    	return normalToFinal(data,2);
    }


    /**
     * ��ʽ������ֵΪ�ַ�����, ָ��С��λ�����ȡ�
     * <br>���� 132000123.000000 ת������λС�����Ϊ "132000123.00"
     * @param data - Ҫת���ĸ�����
     * @param len - ����С��λ��
     * @return String - ����ת����������ַ���
     */
    public static String formatDouble(double data, int len) {
        String ret = null;
        try {			
            NumberFormat form = NumberFormat.getInstance();
            String mask = "###0";
            if (len > 0) {
                mask = "###0.";
                for (int i = 0; i < len; i++) {
                    mask = mask + "0";
                }
            }
            ((DecimalFormat) form).applyPattern(mask);
            ret = form.format(data);
        } catch (Exception e) {
            e.printStackTrace();
            ret = null;
        }
        return ret;
    }
    
    public static String formatDouble(String data, int len) {
    	return formatDouble(toDouble(data),len);
    }

    public static String formatDouble(String data) {
    	return formatDouble(toDouble(data),2);
    }

    public static String formatDoubleNo0(double data, int len) {
        String str = formatDouble(data, len);
        if ("0".equals(str)) return "";
        return str;
    }

    public static String formatDoubleNo0(String data, int len) {
        String str = formatDouble(data, len);
        if ("0".equals(str)) return "";
        return str;
    }

    /**
     * ��ʽ������ֵΪ�ַ�����, ָ��С��λ�����ȣ�ȥ��ĩβ��0��
     * <br>���� 132000123.000000 ת������λС�����Ϊ "132000123"
     * @param data - ������
     * @param len - ��ȷ�� 
     * @return String - ����ת����������ַ���
     */
    public static String trimDouble(double data, int len) {
    	String str = formatDouble(data, len);
    	if (str == null) return str;
    	
    	while (str.indexOf(".") >= 0) {
    		if (!str.endsWith("0") && !str.endsWith(".")) {
    			break;
    		}
    		str = str.substring(0, str.length()-1);
    	}
    	return str;
    }
    
    public static String trimDouble(String data, int len) {
    	return trimDouble(toDouble(data),len);
    }

    public static String trimDoubleNo0(double data, int len) {
        String str = trimDouble(data, len);
        if ("0".equals(str)) return "";
        return str;
    }

    public static String trimDoubleNo0(String data, int len) {
        String str = trimDouble(data, len);
        if ("0".equals(str)) return "";
        return str;
    }

    /**
     * ��Сд������ת��Ϊ��д�Ľ��:����һ
     * @param data
     * @return
     */
    public static String getCDouble(double data, int len) {
    	String d = trimDouble(data, len);
    	
    	String d1 = d;
    	String d2 = "";
    	
    	int idx = d.indexOf(".");
    	if (idx >= 0) {
	    	d1 = d.substring(0, d.indexOf("."));
	    	d2 = d.substring(d.indexOf(".")+1, d.length());
    	}

    	String ret = "";
    	for (int i=0; i<d1.length(); i++) {
        	String str = d1.substring(i,i+1);
    		ret += getCData(str) + getCUnit(i) ;
    	}
    	
    	for (int i=0; i<d2.length(); i++) {
    		if (i==0) ret += "��";
    		
    		String str = d2.substring(i,i+1);
    		ret = ret + getCData(str);
    	}
	    	
    	return ret;
    }
    
    private static String getCUnit(int idx) {
    	if (idx == 4) {
    		return "��";
    	} else if (idx == 8) {
    		return "��";
    	}	
    	return "";
    }
    
    private static String getCData(String data) {
    	if ("0".equals(data)) {
    		return "��";
    	} else if ("1".equals(data)) {
    		return "Ҽ";
    	} else if ("2".equals(data)) {
    		return "��";
    	} else if ("3".equals(data)) {
    		return "��";
    	} else if ("4".equals(data)) {
    		return "��";
    	} else if ("5".equals(data)) {
    		return "��";
    	} else if ("6".equals(data)) {
    		return "½";
    	} else if ("7".equals(data)) {
    		return "��";
    	} else if ("8".equals(data)) {
    		return "��";
    	} else if ("9".equals(data)) {
    		return "��";
    	}
    	return "";
    }
    
    /**
     * ��Сд������ת��Ϊ��д�Ľ��:������
     * @param data
     * @return
     */
    public static String lowToUpOfMoney(double data){ 
        // ת������ַ���
        String money=""; 
        String num="��Ҽ��������½��ƾ�"; 
        String[] unit={"Ԫ","ʰ","��","Ǫ","��","ʰ��","����","Ǫ��","��","ʰ��","����","Ǫ��"}; 
        String s=String.valueOf(data);//�����ת��Ϊ�ַ��� 
        int a=s.indexOf("+");//�ж�s�Ƿ����'+',��1.67E+4 
        int e=s.indexOf("E");//�ж�s�Ƿ����'E',��1.67E+4 
        // �������'E'(�ý�����Կ�ѧ��������ʾ,��ת������ͨ��ʾ��) 
        if(e!=-1){ 
            int index=0;//ָ��ֵ 
            if(a==-1) index=Integer.parseInt(s.substring(e+1));//ȡ��ָ��ֵ 
            else index=Integer.parseInt(s.substring(a+1));//ȡ��ָ��ֵ 
            String sub1=s.substring(0,e);//ȡ��β��ֵ 
            int dot=sub1.indexOf(".");//β����С����λ�� 
            // ���������С����,���ں��油index��'0' 
            if(dot==-1){ 
                for(int i=1;i<=index;i++){ 
                    s=sub1+"0"; 
                } 
            }else{//�������С����,������ƶ�С����indexλ 
                String sub11=sub1.substring(0,dot);//С����ǰ����ִ� 
                String sub12=sub1.substring(dot+1);//С���������ִ� 
                if(index>=sub12.length()){ 
                    int j=index-sub12.length(); 
                    for(int i=1;i<=j;i++){ 
                        sub12=sub12+"0"; 
                    } 
                }else{ 
                    sub12=sub12.substring(0,index)+"."+sub12.substring(index); 
                } 
                s=sub11+sub12; 
            } 
        } 
        int sdot=s.indexOf(".");//s��С�����λ�� 
        String beforeDot="";//С����ǰ����ִ� 
        String afterDot="";//С���������ִ�
        
        // �������С���� 
        if(sdot!=-1){ 
            beforeDot=s.substring(0,sdot); 
            afterDot=s.substring(sdot+1); 
        }else{//������С���� 
            beforeDot=s; 
        } 
        int bl=beforeDot.length(); 
        boolean zero=false;//�����Ƿ�Ϊ�� 
        int z=0;//'0'�ĸ���
        
        // ��λȡ���� 
        for(int j=0,i=bl-1;j<=bl-1;j++,i--){ 
            int number=Integer.parseInt(String.valueOf(beforeDot.charAt(j))); 
            if(number == 0) { 
                zero=true; 
                z++; 
            } else { 
                zero=false; 
                z=0; 
            } 
            if(zero && z==1) { 
                money+="��"; 
            }else if(zero){ 
            }else if(!zero){ 
                money+=num.substring(number,number+1)+unit[i]; 
            } 
        } 
        
        // ɾȥ�����'��'��'��' 
        for(int i=1;i<=2;i++){ 
            String ss=""; 
            if(i==1) ss="��"; 
            else ss="��"; 
            int last=money.lastIndexOf(ss); 
            if(last!=-1){ 
                String moneySub1=money.substring(0,last); 
                String moneySub2=money.substring(last,money.length()); 
                int last2=moneySub1.indexOf(ss); 
                while(last2!=-1){ 
                    moneySub1=moneySub1.substring(0,last2)+moneySub1.substring(last2+1,moneySub1.length()); 
                    last2=moneySub1.indexOf(ss); 
                } 
                money=moneySub1+moneySub2; 
            } 
        } 
        
        // money���Ƿ����'Ԫ' 
        int yuan=money.indexOf("Ԫ"); 
        
        // ���������'Ԫ' 
        if(yuan==-1){ 
            while(money.lastIndexOf("��")==money.length()-1 && money.length()>0) {
                money=money.substring(0,money.length()-1);//��money������'Ԫ'
            }
            if(!"".equals(money)) {
                money += "Ԫ";
            } else {
                money += "��Ԫ";
            }
        } 
        
        
        // ���С���������ִ���Ϊ��,����'��','��' 
        if(!"".equals(afterDot)){ 
            int al=afterDot.length(); 
            if(al>2){//����ִ����ȴ���2,��ض� 
                afterDot=afterDot.substring(0,2); 
                al=afterDot.length(); 
            } 
            
            // ����ַ�����Ϊ'0'��'00',����,���򲻽��д��� 
            if(!afterDot.equals("0") && !afterDot.equals("00")){ 
                
                // ��λȡ���ַ�
                for(int i=0;i<afterDot.length(); i++) { 
                    int number=Integer.parseInt(String.valueOf(afterDot.charAt(i))); 
                    if(number!=0 && i==0){ 
                        money+=num.substring(number,number+1)+"��"; 
                    }else if(number!=0 && i==1){ 
                        money+=num.substring(number,number+1)+"��"; 
                    }else if(number==0 && i==0){ 
                        money+="��"; 
                    } 
                } 
            } 
        }
        // ���������'��','��'����������'��'�� 
        if(money.indexOf("��")==-1 && money.indexOf("��")==-1) money+="��"; 
        
        return money; 
    } 
    
    
    /**************************�ַ�������***********************************/

    //�ֽڳ���
    public static int getStrLength(String str) {
    	if (str==null) return 0;
    	
    	try {
    		return str.getBytes("GBK").length;
    	} catch(Exception e) {
    	}
    	return 0;
    }
    
    /**
     * ��ȡָ�����ȵ������ַ�
     * cnSubstr("�����й���",4,"") ���Ϊ "����"
     * cnSubstr("�����й���",7,"") ���Ϊ "������","��"����Ϊռ�����ֽ�,����ʡ��
     * cnSubstr("�����й���",7,"...") ���Ϊ "������...",ʡ��"��"�ֲ����Ϻ�׺
     * @param str �ַ���
     * @param length ָ������,���ֽ���Ϊ��λ
     * @param tail ���ַ������ȳ���ָ������ʱ���ϵĺ�׺
     * @return
     */
    public static String cnSubstr(String str, int length, String tail) {
        String result = null;
        int pos = 0;
        int end = 0;
        char[] strChars = str.toCharArray();
        int strLength = strChars.length;
        for (int i = 0; i < strLength; i++, end++) {
            int ascii = strChars[i];
            if (ascii > 255)
                pos += 2;
            else
                pos++;
            if (pos > length)
                break;
        }
        result = (end < strLength) ? (str.substring(0, end) + tail) : (str);
        return result;
    }
    
    //���ַ�
    public static String fixStr(String str, int len, char fix, boolean before) throws Exception {
    	if(str==null) str="";
    	int fixN = len - Data.getStrLength(str);
    	if(fixN <= 0) return str;
    	for(int i=0; i<fixN; i++){
    		if(before){
    			str = fix + str;
    		}else{
    			str = str + fix;
    		}
    	}
    	return str;
    }
    
    //ǰ����
    public static String fix0BeforeStr(String str, int len) throws Exception {
    	return fixStr(str,len,'0',true);
    }
    
    //����
    public static String fix0AfterStr(String str, int len) throws Exception {
    	return fixStr(str,len,'0',false);
    }

    //ǰ����
    public static String fixSpaceBeforeStr(String str, int len) throws Exception {
    	return fixStr(str,len,' ',true);
    }
    
    //�󲹿�
    public static String fixSpaceAfterStr(String str, int len) throws Exception {
    	return fixStr(str,len,' ',false);
    }
    
    /**
     * ��ҳ�ϵ��ַ����������ݿ�ǰ��Ԥ����
     * 
     * @param src Դ�ַ���
     * @return Ŀ���ַ���
     */
    public static String Html2DB(String src) {
        if (src == null || src.length() == 0)
            return src;
        
        String dest = "";
        for (int i = 0; i < src.length(); i++) {
            if (src.charAt(i) == '<') { 
                dest += "��";
            } else if (src.charAt(i) == '>') { 
                dest += "��";
            } else if (src.charAt(i) == '\'') { 
                dest += "\"";
            } else {
                dest += src.charAt(i);
            }
        }
        
        return dest;
    }
    
    /**
     * ���ݿ�ȡ�����ַ�����ʾ����ҳ��Ԥ����
     * 
     * @param src Դ�ַ���
     * @return Ŀ���ַ���
     */
    public static String DB2Html(String src) {
        if (src == null || src.length() == 0) 
            return src;
        
        String dest = "";
        for (int i = 0; i < src.length(); i++) {
            if (src.charAt(i) == '\n') { 			//�س�����
                dest += "<br>";
            } else if (src.charAt(i) == ' ') { 		//�ո�
                dest += "&nbsp;";
            } else {
                dest += src.charAt(i);
            }
        }
        
        return dest;
    }
    
    //��127.0.0.1��ʽ��IP��ַת����ʮ��������������û�н����κδ�����   
    public static long ipToLong(String strIp) {  
    	try{
	        long[] ip = new long[4];   
	        //���ҵ�IP��ַ�ַ�����.��λ��   
	        int position1 = strIp.indexOf(".");   
	        int position2 = strIp.indexOf(".", position1 + 1);   
	        int position3 = strIp.indexOf(".", position2 + 1);   
	        //��ÿ��.֮����ַ���ת��������   
	        ip[0] = Long.parseLong(strIp.substring(0, position1));   
	        ip[1] = Long.parseLong(strIp.substring(position1+1, position2));   
	        ip[2] = Long.parseLong(strIp.substring(position2+1, position3));   
	        ip[3] = Long.parseLong(strIp.substring(position3+1));   
	        return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];   
    	}catch(Exception e){
    		return 0;
    	}
    }
        
    //��ʮ����������ʽת����127.0.0.1��ʽ��ip��ַ   
    public static String longToIP(long longIp) {   
        StringBuffer sb = new StringBuffer("");   
        //ֱ������24λ   
        sb.append(String.valueOf((longIp >>> 24)));   
        sb.append(".");   
        //����8λ��0��Ȼ������16λ   
        sb.append(String.valueOf((longIp & 0x00FFFFFF) >>> 16));   
        sb.append(".");   
        //����16λ��0��Ȼ������8λ   
        sb.append(String.valueOf((longIp & 0x0000FFFF) >>> 8));   
        sb.append(".");   
        //����24λ��0   
        sb.append(String.valueOf((longIp & 0x000000FF)));   
        return sb.toString();   
    }   
    
	public static HashMap cloneMap(HashMap map){
    	HashMap retMap = new HashMap();
    	if(map==null) return retMap;

    	Iterator it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			String value = (String)map.get(key);
			retMap.put(key, value);
		}
		return retMap;
    }

	public static LinkedHashMap cloneMap(LinkedHashMap map){
		LinkedHashMap retMap = new LinkedHashMap();
    	if(map==null) return retMap;

    	Iterator it = map.keySet().iterator();
		while(it.hasNext()) {
			String key = (String)it.next();
			String value = (String)map.get(key);
			retMap.put(key, value);
		}
		return retMap;
    }
	
	//������ת����Ӣ�Ĵ�д
    private static final String[] majorNames   =   {
        " ",
        " THOUSAND ",
        " MILLION ",
        " BILLION ",
        " TRILLION ",
        " QUADRILLION ",
        " QUINTILLION "
    };

    private static final String[] tensNames   =   {
        " ",
        " TEN ",
        " TWENTY ",
        " THIRTY ",
        " FOURTY ",
        " FIFTY ",
        " SIXTY ",
        " SENVENTY ",
        " EIGHTY ",
        " NINETY "
    };

    private static final String[] numNames   =   {
        " ",
        " ONE ",
        " TWO ",
        " THREE ",
        " FOUR ",
        " FIVE ",
        " SIX ",
        " SEVEN ",
        " EIGHT ",
        " NINE ",
        " TEN ",
        " ELEVEN ",
        " TWELVE ",
        " THIRTEEN ",
        " FOURTEEN ",
        " FIFTEEN ",
        " SIXTEEN ",
        " SEVEVTEEN ",
        " EIGHTEEN ",
        " NINETEEN "
    };

    private static String convertLessThanOneThousand(int   number)   {
        String soFar;

        if (number % 100 < 20){
                soFar = numNames[number % 100];
                number /= 100;
        }else {
                soFar = numNames[number % 10];
                number /= 10;

                soFar = tensNames[number % 10] + soFar;
                number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " HUNDRED " + soFar;
	}

	public  static String convert(int   number)   {
        /*   special   case   */
        if (number == 0)   {  
        	return  "ZERO ";   
        }

        String prefix   =   " ";

        if (number < 0)   {
                number   =   -number;
                prefix   =   "NEGATIVE ";
        }

        String soFar = " ";
        int place = 0;

        do {
            int n = number % 1000;
            if(n != 0){
                  String s = convertLessThanOneThousand(n);
                  soFar = s + majorNames[place] + soFar;
            }
            place++;
            number /= 1000;
            }   while (number > 0);

        return (prefix + soFar).trim();
	}
}
