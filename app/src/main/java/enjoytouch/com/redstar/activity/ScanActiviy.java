package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.bean.OrderBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpUtils;
import enjoytouch.com.redstar.zxing_code.camera.CameraManager;
import enjoytouch.com.redstar.zxing_code.decoding.CaptureActivityHandler;
import enjoytouch.com.redstar.zxing_code.decoding.InactivityTimer;
import enjoytouch.com.redstar.zxing_code.view.ViewfinderView;

/**
 * Created by Administrator on 2015/12/15.
 * <p/>
 * 扫码页面
 */
public class ScanActiviy extends Activity implements SurfaceHolder.Callback {

    public static CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private boolean hasSurface;
    private static Vector<BarcodeFormat> decodeFormats;
    private static String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private static SurfaceView surfaceView;

    private static ScanActiviy INSTANCE;
    public static Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        INSTANCE = this;
        CameraManager.init(INSTANCE);
        setViews();
        setListeners();
    }

    private void setViews() {
        dialog=DialogUtil.createLoadingDialog(INSTANCE,getResources().getString(R.string.loading));
        viewfinderView = (ViewfinderView) findViewById(R.id.capture_viewfinder);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(INSTANCE);
    }

    private void setListeners() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScanActiviy.this.finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceView = (SurfaceView) findViewById(R.id.capture_preview);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        decodeFormats = null;
        characterSet = null;
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(INSTANCE);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        super.onDestroy();
    }


    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        String resultString = result.getText();
        if (resultString.startsWith("meiyu://")) {
            resultString = resultString.substring(8);
            getTicketInfo(resultString);
            initCamera(surfaceView.getHolder());
            if (handler != null) {
                handler.restartPreviewAndDecode();
            }

        } else {
            Toast.makeText(INSTANCE, "非美寓有效二维码！", Toast.LENGTH_LONG).show();
            SurfaceHolder surfaceHolder = surfaceView.getHolder();
            initCamera(surfaceHolder);
            if (handler != null)
                handler.restartPreviewAndDecode();
        }
    }

    private static void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(INSTANCE, decodeFormats,
                    characterSet);
        }
    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

//            AssetFileDescriptor file = getResources().openRawResourceFd(
//                    R.raw.beep);
//            try {
//                mediaPlayer.setDataSource(file.getFileDescriptor(),
//                        file.getStartOffset(), file.getLength());
//                file.close();
//                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
//                mediaPlayer.prepare();
//            } catch (IOException e) {
//                mediaPlayer = null;
//            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        ContentUtil.makeLog("holder==null?", holder + "");
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final MediaPlayer.OnCompletionListener beepListener = new MediaPlayer.OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };


    private enum State {
        PREVIEW,
        SUCCESS,
        DONE
    }

    private String serial;

    private void getTicketInfo(String serial) {
        this.serial = serial;

        dialog.show();
        HttpUtils.getResultToHandler(INSTANCE, "earnest", "order_confirm", params(serial), HANDLER, 0);
    }

    private static List<NameValuePair> params(String serial) {
        ArrayList<NameValuePair> params = new ArrayList<>();
        params.add(new BasicNameValuePair("code", serial));
        return params;
    }

    private static Handler HANDLER = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            JSONObject jo = (JSONObject) msg.obj;
            dialog.dismiss();
            try {
                if (jo.getString("status").toLowerCase().equals("ok")) {
                    Gson gson = new Gson();
                } else {
                    DialogUtil.show(INSTANCE, jo.getJSONObject("error").getString("message"), false).show();
                    initCamera(surfaceView.getHolder());
                    if (handler != null)
                        handler.restartPreviewAndDecode();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}
