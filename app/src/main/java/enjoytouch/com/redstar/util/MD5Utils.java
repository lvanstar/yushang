package enjoytouch.com.redstar.util;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class MD5Utils {
	 public final static String MD5(String s) {
	        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};       
	        try {
	            byte[] btInput = s.getBytes();
	            // 获得MD5摘要算法的 MessageDigest 对象
	            MessageDigest mdInst = MessageDigest.getInstance("MD5");
	            // 使用指定的字节更新摘要
	            mdInst.update(btInput);
	            // 获得密文
	            byte[] md = mdInst.digest();
	            // 把密文转换成十六进制的字符串形式
	            int j = md.length;
	            char str[] = new char[j * 2];
	            int k = 0;
	            for (int i = 0; i < j; i++) {
	                byte byte0 = md[i];
	                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
	                str[k++] = hexDigits[byte0 & 0xf];
	            }
	            return new String(str);
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	    
	
	public static List<NameValuePair> getMD5uri(List<NameValuePair> params){
		String name;
		String value;
		ArrayList<String> list=new ArrayList<String>();
		
		for(NameValuePair param:params){
			name=param.getName();
			value=param.getValue();
			list.add(name+value);
		}
		 String[] array = list.toArray(new String[list.size()]);
		Arrays.sort(array, String.CASE_INSENSITIVE_ORDER);
		ContentUtil.makeLog("排序集合", ""+ContentUtil.toString(array));
		String MD5=MD5(ContentUtil.toString(array));
		ContentUtil.makeLog("md5", ""+MD5);
		params.add(new BasicNameValuePair("ver", "2.0"));
		params.add(new BasicNameValuePair("sign", MD5));
		return params;
	}

	
}
