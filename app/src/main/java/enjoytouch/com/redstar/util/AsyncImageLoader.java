package enjoytouch.com.redstar.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 实现批量的图片加载和缓存
 * 
 * @author pjy
 * 
 */
public class AsyncImageLoader {
	private ArrayList<ImageLoadTask> tasks;//图片集合
	private Thread workThread;			//工作线程
	private boolean isLoop;
	private Handler handler;
	private HashMap<String, SoftReference<Bitmap>> caches;//键值对集合（键，软引用图片）
	private Context context;

	public AsyncImageLoader(Context context, final Callback callback) {//构造方法（context,回调函数）异步图片加载
		this.context = context;
		this.handler = new Handler() {//新建Handle
			@Override
			public void handleMessage(Message msg) {//处理消息
                super.handleMessage(msg);
				// 获取加载完成的图片加载任务
				ImageLoadTask task = (ImageLoadTask) msg.obj;//取出消息中的obj对象
				// 回调接口中的框架进行图片加载（路径，图片）
				callback.imageLoaded(task.path, task.bitmap);
			}
		};
		this.tasks = new ArrayList<ImageLoadTask>();
		this.isLoop = true;
		this.caches = new HashMap<String, SoftReference<Bitmap>>();
		this.workThread = new Thread() {//新建工作线程
			@Override
			public void run() {
				Log.i("info", "工作线程开始运行");
				while (isLoop) {			//当isLoop时true时
					// 轮询任务集合
					while (isLoop == true && !tasks.isEmpty()) {//当isLoop是true并且tasks（任务）不是空
						// 获取并移除第一条任务
						ImageLoadTask task = tasks.remove(0);
						try {
							// 执行加载任务
							HttpEntity entity = HttpUtils.getEntity(//得到实体类
									task.path, null,
									HttpUtils.METHOD_GET);
							ContentUtil.makeLog("图片URL", task.path.toString());//log打印
							if(entity!=null){//如果实体不是空值
								byte[] data = EntityUtils.toByteArray(entity);//将实体转成字节数组
//								task.bitmap = BitmapUtils.loadBitmap(data);
//										(int)Myapplication.vpWidth, 
//										(int)Myapplication.vpHeight);
								task.bitmap=BitmapFactory.decodeByteArray(data, 0,data.length);//图片工厂编译字节数组得到图片
							}
							
//							InputStream is=new java.net.URL(task.path).openStream();
//							task.bitmap=BitmapFactory.decodeStream(is);
//							is.close();
							// 发送消息到转线程
							Message msg = Message.obtain(handler, 0, task);//获得任务
							msg.sendToTarget();//发送给目标
							// 缓存
							caches.put(task.path, new SoftReference<Bitmap>(//键值对存储集合添加任务
									task.bitmap));
							File file = getFileInfo(task.path);//得到文件
							BitmapUtils.save(task.bitmap, file);//保存图片到指定文件
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}

					if (isLoop == false)
						break;

					// 线程等待
					synchronized (this) {
						try {
							this.wait();
							ContentUtil.makeLog("lzz", "等待。。。。。");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				Log.i("info", "工作线程结束");
			}
		};
		this.workThread.start();
	}

	public void quit() {
		isLoop = false;
		synchronized (workThread) {
			try {
				workThread.notify();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public File getFileInfo(String path) {
		String imageName = MD5Utils.MD5(path);
		File file = new File(context.getExternalCacheDir(), imageName);
		return file;
	}

	/**
	 * 加载指定路径的图片
	 * 
	 * @param path
	 * @return
	 */
	public Bitmap loadBitmap(String path) {
		Bitmap bm = null;
		// 如果缓存集合中存在该路径的图片
		if (caches.containsKey(path)) {
			// 获取集合中缓存的图片
			bm = caches.get(path).get();
			if (bm != null) {
				// 如果图片未被释放
				return bm;
			} else {
				// 如果图片已被释放
				caches.remove(path);
			}
		}

		// 判断文件缓存
		bm = BitmapUtils.decodeFile(getFileInfo(path));
		if (bm != null) {
			// 存在文件缓存
			return bm;
		}
		// 创建图片加载任务，添加到任务集合并唤醒线程
		ImageLoadTask task = new ImageLoadTask();
		task.path = path;
		if (!tasks.contains(task)) {
			tasks.add(task);
			synchronized (workThread) {
				try {
					workThread.notify();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	private class ImageLoadTask {
		private String path;
		private Bitmap bitmap;

		@Override
		public boolean equals(Object o) {
			ImageLoadTask task = (ImageLoadTask) o;
			return path.equals(task.path);
		}
	}

	public interface Callback {
		void imageLoaded(String path, Bitmap bitmap);
	}


    /**
     * 是否存在sd卡
     * @return
     */
    public static boolean hasSdcard(){
        String state = Environment.getExternalStorageState();
        if(state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else{
            return false;
        }
    }
}
