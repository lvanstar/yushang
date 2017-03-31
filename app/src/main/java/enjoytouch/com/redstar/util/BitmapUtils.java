package enjoytouch.com.redstar.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BitmapUtils {
	/**
	 * 加载本地的图片文件 到内存
	 * 
	 * @param path
	 *            图片文件的路径
	 * @return
	 */
	public static Bitmap loadBitmap(String path) {
		Bitmap bm = null;
		bm = BitmapFactory.decodeFile(path);
		return bm;
	}

	/**
	 * 按指定宽高 加载字节数组中的图片到内存
	 * 
	 * @param data
	 * @return
	 */
	public static Bitmap loadBitmap(byte[] data) {
		Bitmap bm = null;
		// 加载图片的边界信息
//		Options opts = new Options();
//		opts.inJustDecodeBounds = true;
//		BitmapFactory.decodeByteArray(data, 0, data.length, opts);
//		// 计算收缩比例
//		int xSacle = opts.outWidth / width;
//		int yScale = opts.outHeight / height;
//
		// 设置收缩比例
//		opts.inSampleSize = xSacle > yScale ? xSacle : yScale;
		// 加载图片
//		opts.inJustDecodeBounds = false;
		bm = BitmapFactory.decodeByteArray(data, 0, data.length);
		return bm;
	}

	/**
	 * 保存内存中的图片到指定文件
	 * 
	 * @param bm
	 * @param file
	 * @throws IOException
	 */
	public static void save(Bitmap bm, File file) throws IOException {
		if (bm != null && file != null) {
			File f = file.getAbsoluteFile().getParentFile();
			if (f == null || !f.exists()){
				f.mkdirs();
			}
			
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream stream = new FileOutputStream(file);
			bm.compress(CompressFormat.JPEG, 100, stream);
		}
	}

	public static Bitmap decodeFile(File f) {
		Bitmap bm=BitmapFactory.decodeFile(f.toString());
		return bm;
	}

	public static Bitmap getCroppedBitmap(Bitmap map, int width, int height){
		if (map == null) {
			return null;
		}
		int w = map.getWidth();
		int h = map.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / (float) w);
		float scaleHeight = ((float) height / (float) h);
		if (scaleWidth > scaleHeight) scaleHeight = scaleWidth;
		else scaleWidth = scaleHeight;
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(map, 0, 0, w, h, matrix, true);
		newbmp = Bitmap.createBitmap(newbmp, 0, 0, width, height);
		return newbmp;
	}


	public static Bitmap getHttpBitmap(String url) {
		//Log.i("yc","getHttpBitmap的url==="+url);
		Bitmap bitmap = null;
		try {
			URL pictureUrl = new URL(url);
			HttpURLConnection con = (HttpURLConnection) pictureUrl.openConnection();
			InputStream in = con.getInputStream();
			bitmap = BitmapFactory.decodeStream(in);
			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
}
