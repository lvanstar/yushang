package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.umeng.analytics.MobclickAgent;

import butterknife.ButterKnife;
import butterknife.InjectView;
import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MyReceiptAddressAdapter;
import enjoytouch.com.redstar.selfview.MiddleDialog2;
import enjoytouch.com.redstar.util.GlobalConsts;

/**
 * Created by Administrator on 2016/1/5.
 * <p/>
 * 我的收货地址
 */


public class MyReceiptAddressActivity extends Activity {
    @InjectView(R.id.address_Lv)
    ListView lv;
    @InjectView(R.id.address_setting)
    RelativeLayout setting;

    private MyReceiptAddressActivity INSTANCE;
    MyReceiptAddressAdapter adapter;
    private boolean status = false;
    private MiddleDialog2 dialog2;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiptadress);
        ButterKnife.inject(this);
        INSTANCE = this;
        id = getIntent().getStringExtra(GlobalConsts.INTENT_DATA);
        setViews();
        setListeners();

    }

    private void setViews() {

//        adapter = new MyReceiptAddressAdapter(INSTANCE, list, id);
        lv.setAdapter(adapter);

        dialog2 = new MiddleDialog2(INSTANCE, getResources().getString(R.string.title), getResources().getString(R.string.value), new MiddleDialog2.onBottonListener() {
            @Override
            public void onOk() {

                finish();
            }
        },R.style.registDialog);
    }

    private void setListeners() {

        findViewById(R.id.back_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (INSTANCE.list.size() > 0) {
//                    if ("".equals(id)) {
//                        dialog2.show();
//                    } else {
//                        finish();
//                    }
//                } else {
////                    Intent intent = new Intent(INSTANCE, MakeOrderActivity.class);
////                    intent.putExtra(GlobalConsts.INTENT_DATA, new UserAdressBean());
////                    setResult(RESULT_OK, intent);
////                    finish();
//                }

            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                status = true;
//                INSTANCE.id = list.get(position).id;
//                adapter.updateData(list, list.get(position).id);
//                Intent intent = new Intent(INSTANCE, MakeOrderActivity.class);
//                intent.putExtra(GlobalConsts.INTENT_DATA, (Serializable) list.get(position));
//                setResult(RESULT_OK, intent);
//                finish();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(INSTANCE, MyAddressActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
