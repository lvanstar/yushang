package enjoytouch.com.redstar.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Abner on 15/9/22.
 * QQ 230877476
 * Email nimengbo@gmail.com
 * github https://github.com/nimengbo
 */
public class FileUtil {

    private static FileUtil instance = null;

    private static Context mContext;

    private static final String APP_DIR = "Abner";

    private static final String TEMP_DIR = "Abner/.TEMP";

    public static FileUtil getInstance(Context context) {
        if (instance == null) {
            synchronized (FileUtil.class) {
                if (instance == null) {
                    mContext = context.getApplicationContext();
                    instance = new FileUtil();
                }
            }
        }
        return instance;
    }

    /**
     * 保存图像到本地
     *
     * @param bm
     * @return
     */
    public static String saveBitmapToLocal(Bitmap bm, Context context) {
        String path = null;
        try {
            File file = FileUtil.getInstance(context).createTempFile("IMG_", ".jpg");
            FileOutputStream fos = new FileOutputStream(file);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            path = file.getAbsolutePath();
        } catch (FileNotFoundException e) {
            ContentUtil.makeLog("lzz",e.toString());
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            ContentUtil.makeLog("lzz",e.toString());
            e.printStackTrace();
            return null;
        }

        return path;
    }

    /**
     * @param prefix
     * @param extension
     * @return
     * @throws IOException
     */
    public File createTempFile(String prefix, String extension)
            throws IOException {
        File file = new File(getAppDirPath() + ".TEMP/" + prefix
                + System.currentTimeMillis() + extension);
        file.createNewFile();
        return file;
    }

    /**
     * 得到当前应用程序内容目录,外部存储空间不可用时返回null
     *
     * @return
     */
    public String getAppDirPath() {
        String path = null;
        if (getLocalPath() != null) {
            path = getLocalPath() + APP_DIR + "/";
        }
        ContentUtil.makeLog("lzz", "path:" + path);
        return path;
    }

    /**
     * 得到当前app的目录
     *
     * @return
     */
    public  String getLocalPath() {
        String sdPath = null;
        sdPath = mContext.getFilesDir().getAbsolutePath() + "/";
        return sdPath;
    }

    /**
     * 检查sd卡是否就绪并且可读写
     *
     * @return
     */
    public boolean isSDCanWrite() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)
                && Environment.getExternalStorageDirectory().canWrite()
                && Environment.getExternalStorageDirectory().canRead()) {
            return true;
        } else {
            return false;
        }
    }

    private FileUtil() {
        // 创建应用内容目录
        if (isSDCanWrite()) {
            creatSDDir(APP_DIR);
            creatSDDir(TEMP_DIR);
        }
    }

    /**
     * 在SD卡根目录上创建目录
     *
     * @param dirName
     */
    public File creatSDDir(String dirName) {
        File dir = new File(getLocalPath() + dirName);
        dir.mkdirs();
        return dir;
    }


    public void copyBigDataToSD(String strOutFileName,String assets) throws IOException
    {
        InputStream myInput;
        OutputStream myOutput = new FileOutputStream(strOutFileName);
        myInput = mContext.getAssets().open(assets);
        byte[] buffer = new byte[1024];
        int length = myInput.read(buffer);
        while(length > 0)
        {
            myOutput.write(buffer, 0, length);
            length = myInput.read(buffer);
        }

        myOutput.flush();
        myInput.close();
        myOutput.close();
    }


    public static void savePicture(Bitmap bitmap) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
//                File sdcardDir = Environment
//                        .getExternalStorageDirectory();
//                String filename = sdcardDir.getCanonicalPath() + System.currentTimeMillis() + ".jpg";
                File files=new File("/storage/emulated/legacy/RedStar");
                if(!files.exists()){
                    files.mkdirs();
                }
                File file=new File(files,System.currentTimeMillis()+".jpg");
                FileOutputStream out = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
               // Log.i("yc", "FileNotFoundException保存失败");
            } catch (IOException e) {
               // Log.i("yc", "IOException保存失败");
                e.printStackTrace();
            }
        }
    }
}
