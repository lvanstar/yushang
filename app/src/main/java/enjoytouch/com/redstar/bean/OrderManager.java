package enjoytouch.com.redstar.bean;

import enjoytouch.com.redstar.util.ContentUtil;

/**
 * Created by Administrator on 2016/1/6.
 */
public class OrderManager {

    public static final int TITLE = 0;
    public static final int VALUE = 1;
    public int type;
    public Object object;


    public OrderManager(Object object) {
        this.object = object;
        if (object instanceof TitleBean) {
            type = TITLE;
        } else if (object instanceof SkuInfoBean) {
            type = VALUE;
        }
    }

}
