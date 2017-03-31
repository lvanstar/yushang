package enjoytouch.com.redstar.bean;

/**
 * Created by lizhaozhao on 16/6/28.
 */
public class OrderInfoManager {

    public static final int TITLE = 0;
    public static final int VALUE = 1;
    public static final int WULIU = 2;
    public int type;
    public Object object;
    public int size;

    public OrderInfoManager(Object object) {
        this.object = object;
        if (object instanceof TitleBean) {
            type = TITLE;
        } else if (object instanceof SkuBean) {
            type = VALUE;
        } else if(object instanceof LogisticsBean){

            type=WULIU;
        }
    }

}
