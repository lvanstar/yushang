package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.control.LoginControl;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by Administrator on 2016/5/31.
 */
public class EditNikenName extends Activity {
    EditNikenName INSTANCE;
    @InjectView(R.id.cancel)
    TextView cancel;
    @InjectView(R.id.finish)
    TextView finish;
    @InjectView(R.id.et_nikeName)
    EditText etNikeName;
    private EditText editText;
    private String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_edit_nikename);
        ButterKnife.inject(this);
        value=getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        editText= (EditText) findViewById(R.id.et_nikeName);
        INSTANCE=this;
        setViews();
    }

    private void setViews() {
        etNikeName.setText(value);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                value = etNikeName.getText().toString();
                if (value.length() != 0) {
                    new LoginControl(INSTANCE).uPData("2",value);
                    ContentUtil.makeToast(EditNikenName.this, "修改成功");
                }else{
                    ContentUtil.makeToast(EditNikenName.this,"昵称不能为空");
                }
            }
        });
        findViewById(R.id.iv_remove).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }
}
