package enjoytouch.com.redstar.selfview.demo;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jeesoft.widget.pickerview.CharacterPickerView;
import cn.jeesoft.widget.pickerview.CharacterPickerWindow;
import enjoytouch.com.redstar.util.ContentUtil;

/**
 * Created by ye on 2016/1/4.
 */
public class MyOptionsWindowHelper {
    private static List<String> options1Items = null;
    private static List<List<String>> options2Items = null;
    private static List<List<List<String>>> options3Items = null;

    public static interface OnOptionsSelectListener {
        public void onOptionsSelect(String province, String city, String area);
    }

    private MyOptionsWindowHelper() {
    }

    public static CharacterPickerWindow builder(Activity activity,Map<String, Map<String, List<String>>> allCitys, final OnOptionsSelectListener listener) {
        //选项选择器
        CharacterPickerWindow mOptions = new CharacterPickerWindow(activity);
        //初始化选项数据
        setPickerData(mOptions.getPickerView(),allCitys);
        //设置默认选中的三级项目
        mOptions.setSelectOptions(17, 0, 0);
       // ContentUtil.makeLog("yc","城市列表"+allCitys.get(10).toString());
        //监听确定选择按钮
        mOptions.setOnoptionsSelectListener(new CharacterPickerWindow.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3) {
                if (listener != null) {
                    String province = options1Items.get(options1);
                    String city = options2Items.get(options1).get(option2);
                    String area = options3Items.get(options1).get(option2).get(options3);

                   // Log.i("aaa","city="+city+"  area="+area);

                    listener.onOptionsSelect(province, city, area);
                }
            }
        });
        return mOptions;
    }

    /**
     * 初始化选项数据
     */
    public static void setPickerData(CharacterPickerView view,Map<String,Map<String, List<String>>> all) {
        if (options1Items == null) {
            options1Items = new ArrayList<>();
            options2Items = new ArrayList<>();
            options3Items = new ArrayList<>();

            final Map<String, Map<String, List<String>>> allCitys = all;
            for (Map.Entry<String, Map<String, List<String>>> entry1 : allCitys.entrySet()) {
                String key1 = entry1.getKey();
                Map<String, List<String>> value1 = entry1.getValue();

                options1Items.add(key1);

                ArrayList options2Items_01 = new ArrayList();
                List<List<String>> options3Items_01 = new ArrayList<>();
                for (Map.Entry<String, List<String>> entry2 : value1.entrySet()) {
                    String key2 = entry2.getKey();
                    List<String> value2 = entry2.getValue();

                    options2Items_01.add(key2);

                    options3Items_01.add(new ArrayList(value2));
                   // Collections.sort(value2);
                }
               // Collections.sort(options2Items_01);
                options2Items.add(options2Items_01);
                options3Items.add(options3Items_01);
            }
           // Collections.sort(options1Items);
        }
        //三级联动效果
        view.setPicker(options1Items, options2Items, options3Items);

    }
}
