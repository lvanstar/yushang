package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.adapter.MyAddressAdapter;
import enjoytouch.com.redstar.bean.OneAddressBean;
//我的地址，（by chenye）
public class AddressActivity extends Activity {
   private ListView pay_unselected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        inint();
        //以下为假数据
//        List<OneAddressBean> list=new ArrayList<>();
//        list.add(new OneAddressBean("李益雄","13333334545","上海市普陀区长征路怒江北路598号4楼1号"));
//        list.add(new OneAddressBean("张益雄","13333334545","上海市普陀区长征路怒江北路598号4楼1号"));
//        list.add(new OneAddressBean("王益雄","13333334545","上海市普陀区长征路怒江北路598号4楼1号"));
//        list.add(new OneAddressBean("何益雄","13333334545","上海市普陀区长征路怒江北路598号4楼1号"));
//        list.add(new OneAddressBean("赵益雄","13333334545","上海市普陀区长征路怒江北路598号4楼1号"));
//        list.add(new OneAddressBean("周益雄","13333334545","上海市普陀区长征路怒江北路598号4楼1号"));
//        list.add(new OneAddressBean("吕益雄","13333334545","上海市普陀区长征路怒江北路598号4楼1号"));
//        MyAddressAdapter myAddressAdapter=new MyAddressAdapter(this,list);
//        pay_unselected.setAdapter(myAddressAdapter);


    }

  private void inint(){
      pay_unselected= (ListView) findViewById(R.id.lv_myaddress);
      //回退
      LinearLayout back_btn= (LinearLayout) findViewById(R.id.back_btn);
      back_btn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              AddressActivity.this.finish();
          }
      });
   }
}
