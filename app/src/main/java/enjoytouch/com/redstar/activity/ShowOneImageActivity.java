package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import enjoytouch.com.redstar.R;

public class ShowOneImageActivity extends Activity {
   public static String IMG_URI="IMG_URI";
    public static String IS_SAVE="IS_SAVE";
    private String uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_one_image);
        uri=getIntent().getStringExtra(IMG_URI)==null?"":getIntent().getStringExtra(IMG_URI);
        setView();
        setlistener();
    }

    private void setView() {
        ImageView iv_img= (ImageView) findViewById(R.id.iv_img);
        Glide.with(ShowOneImageActivity.this.getApplicationContext())
                .load(uri)
                .placeholder(R.drawable.placeholder)
                .fitCenter()
                .into(iv_img);
    }

    private void setlistener() {
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(33, ShowOneImageActivity.this.getIntent()
                        .putExtra(IS_SAVE,false));
                finish();
            }
        });
        findViewById(R.id.sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(33, ShowOneImageActivity.this.getIntent()
                        .putExtra(IS_SAVE,true));
                finish();
            }
        });
    }
}
