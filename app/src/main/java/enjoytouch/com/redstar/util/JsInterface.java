package enjoytouch.com.redstar.util;

import android.content.Context;
import android.content.Intent;

import enjoytouch.com.redstar.activity.FoundDetailActivity;
import enjoytouch.com.redstar.activity.QuChuDetailActivity;

/**
 * Created by lizhaozhao on 16/8/16.
 */
public class JsInterface {

    Context context;
    public JsInterface(Context context){
        this.context=context;
    }


    public String jumpValFeedback(final String value){
        ContentUtil.makeLog("lzz","value:"+value);

        if(value!=null){

            if(value.contains("qc")){
                //趣处
                String id=value.substring(value.indexOf(":"),value.length());
                ContentUtil.makeLog("lzz", "id:" + id);
                Intent intent=new Intent(context, QuChuDetailActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,id);
                context.startActivity(intent);
//            }else if(value.contains("sp")){
//                //商品
//                String id=value.substring(value.indexOf("="),value.length());
//                ContentUtil.makeLog("lzz","id:"+id);
//                Intent intent=new Intent(context, ShopDetailsActivity.class);
//                intent.putExtra(GlobalConsts.INTENT_DATA,id);
//                context.startActivity(intent);
//            }else if(value.contains("pp")){
//                //品牌
//                String id=value.substring(value.indexOf("="),value.length());
//                ContentUtil.makeLog("lzz","id:"+id);
//                Intent intent=new Intent(context, BrandDetailActivity.class);
//                intent.putExtra("id",id);
//                context.startActivity(intent);
//            }else if(value.contains("dd")){
//                //单店
//                String id=value.substring(value.indexOf("="),value.length());
//                ContentUtil.makeLog("lzz","id:"+id);
//                Intent intent=new Intent(context, QuChuDetailActivity.class);
//                intent.putExtra(GlobalConsts.INTENT_DATA,id);
//                context.startActivity(intent);
            }else if(value.contains("fx")){
                //为你发现
                String id=value.substring(value.indexOf(":"),value.length());
                ContentUtil.makeLog("lzz","id:"+id);
                Intent intent=new Intent(context, FoundDetailActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);
            }
        }

        return value;
    }
}
