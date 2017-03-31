package enjoytouch.com.redstar.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.umeng.analytics.MobclickAgent;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.util.ContentUtil;


/**
 * Created by Administrator on 2015/7/28.
 */
public class MapActivity extends Activity implements AMap.CancelableCallback{

    private View back;
    private MapView mapView;
    private LatLng latto;
    private AMap aMap = null;
    private String latitude="";
    private String longitude="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        latitude=getIntent().getStringExtra("lat");
        longitude=getIntent().getStringExtra("lng");

        setViews();
        setListeners();
    }

    private void setViews() {
        back = findViewById(R.id.back);
        if(latitude!=null&&longitude!=null){
            latto = new LatLng(Double.valueOf(latitude),
                    Double.valueOf(longitude));
        }else{
            return ;
        }
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(
                latto, 18, 30, 0)),this);

    }

    private void setListeners() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MapActivity.this.finish();
            }
        });
    }

    private void setUpMap() {
        aMap.addMarker(new MarkerOptions()
                .perspective(true)
                .position(latto)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));

    }

    /**
     * 根据动画按钮状态，调用函数animateCamera或moveCamera来改变可视区域
     */
    private void changeCamera(CameraUpdate update, AMap.CancelableCallback callback) {
        aMap.moveCamera(update);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onFinish() {

    }

    @Override
    public void onCancel() {

    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
