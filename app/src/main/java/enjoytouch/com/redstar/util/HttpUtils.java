package enjoytouch.com.redstar.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import enjoytouch.com.redstar.activity.LoginActivity;

public class HttpUtils {
	public static final int METHOD_GET = 1;
	public static final int METHOD_POST = 2;
	private static Dialog dialog;
	private static HttpClient client=new DefaultHttpClient();
	/**
	 * 向指定的资源路径发送请求获取响应实体对象并返回
	 * 
	 * @param uri
	 *            资源路径
	 * @param params
	 *            向服务端发送请求时的实体数据
	 * @param method
	 *            请求方法
	 * @return
	 * @throws IOException
	 */
	public static HttpEntity getEntity(String uri, List<NameValuePair> params,
			int method) throws IOException {
		HttpEntity entity = null;
		// 创建客户端对象
		client = new DefaultHttpClient();
		client.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
		client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT
				, 20000);
		
		// 创建请求对象
		HttpUriRequest request = null;
		switch (method) {
		case METHOD_GET:
			StringBuilder sb = new StringBuilder(uri);
			if (params != null && !params.isEmpty()) {
				sb.append('?');
				for (NameValuePair pair : params) {
					sb.append(pair.getName()).append('=')
							.append(pair.getValue()).append('&');
				}
				sb.deleteCharAt(sb.length() - 1);
			}
			Log.i("GET请求", sb.toString());
			request = new HttpGet(sb.toString());
			break;
		case METHOD_POST:
			request = new HttpPost(uri);
			if (params != null && !params.isEmpty()) {
				Log.i("HTTP URI", ""+uri);
				Log.i("HTTP参数", ""+params.toString());
				// 创建请求实体对象
				
				UrlEncodedFormEntity reqEntity = new UrlEncodedFormEntity(
						params,HTTP.UTF_8);
				// 设置请求实体
				((HttpPost) request).setEntity(reqEntity);
				
			}
			break;
		}
		// 执行请求获取相应对象
		try{
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				entity = response.getEntity();
			}
			return entity;
		}catch (IllegalStateException e){
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 获取实体对象的内容长度并返回
	 * 
	 * @param entity
	 * @return
	 */
	public static long getEntity(HttpEntity entity) {
		long len = 0;
		if (entity != null) {
			len = entity.getContentLength();
		}
		return len;
	}

	/**
	 * 获取指定的响应实体对象的网络输入流
	 * 
	 * @param entity
	 * @return
	 * @throws IOException
	 * @throws IllegalStateException
	 */

	public static InputStream getStream(HttpEntity entity)
			throws IllegalStateException, IOException {
		InputStream in = null;//输入流是空
		if (entity != null) {//如空果实体不是空
			in = entity.getContent();//将实体的内容赋给输入流
		}
		return in;				//返回输入流的值
	}
	/**
	 * 上传头像
	 */
	public static String uploadImage(String url, File file,String param,
			Context context) {
		Log.i("lzz", "上传头像file:" + file + "值：" + file.exists());
	    if (!file.exists()) {
	       Log.w("上传图片","图片不存在");
	        return null;
	    }

	    HttpClient client = new DefaultHttpClient();
	    HttpPost post = new HttpPost(url);
		FileBody fileBody = new FileBody(file, "image/jpeg");
	    MultipartEntity entity = new MultipartEntity();
	    // image 是服务端读取文件的 key
	    entity.addPart(param, fileBody);

	    post.setEntity(entity);

	    try {
	        HttpResponse response = client.execute(post);
	        int statusCode = response.getStatusLine().getStatusCode();
	        String result = EntityUtils.toString(response.getEntity(), "utf-8");
	        
	        if (statusCode == HttpStatus.SC_OK) {
	        	Log.i("Upload", "成功");
	        	Log.i("ImageUrl",result);
	        	return result;
	        }

	        
	    } catch (Exception e) {
        	Toast.makeText(context, "头像上传失败", Toast.LENGTH_LONG).show();
	        e.printStackTrace();
	    }
	    return null;
	}

	public static void getResultToHandler(final Context context, final String catalog, final String item, final List<NameValuePair>params, final Handler handler, final int what){
		new Thread(){//新建工作线程
			@Override
			public void run() {
				InputStream in;//输入流
				try {
					in = HttpUtils.getStream(HttpUtils.getEntity(GlobalConsts.URI+catalog+"/"+item+"/", params, HttpUtils.METHOD_GET));//得到输入流的值
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));//新建缓冲输入流，接字符输入流（字符流接字节流）
					char[] buffer = new char[8192];//新建字符数组，每个数组8个字符
					int n = 0;
					StringBuffer s = new StringBuffer();//可以改变的字符串对象
					while ((n=reader.read(buffer))>-1){//当缓冲流读取的数组大于-1，说明未读完
						s.append(buffer, 0, n);			//向字符串对象里添加字符
					}
					Log.i("Get JSON", s.toString());
					JSONObject jo = new JSONObject(s.toString());//新建json对象
					Message msg = new Message();				//新建消息对象
					msg.what = what;
					msg.obj = jo;
					handler.sendMessage(msg);					//handle发送消息
					if (!jo.getString("status").toLowerCase().equals("ok")){//json对象中的status值如果不是ok(先将status值转换成小写)
						String error = jo.getJSONObject("error").getString("code");//从json对象里取出error的值，并得到code字符串值
						if (error.equals("1012")){					//如果code值是1012
							((Activity)context).runOnUiThread(new Runnable() {//新建工作线程
								@Override
								public void run() {
									DialogUtil.show(context, "用户Token已过期，请重新登录！", false).show();//显示对话框
									Intent intent = new Intent(context, LoginActivity.class);//跳转到登录界面
									intent.putExtra("from", true);
									context.startActivity(intent);
								}
							});
						}
					}else{
						//DialogUtil.show(context, jo.getJSONArray("error").getString(Integer.parseInt("message")), false).show();
					}
				}catch (Exception e){
					e.printStackTrace();
				}
			}
		}.start();
	}

	public static void cancel(){
		client.getConnectionManager().shutdown();
	}

}
