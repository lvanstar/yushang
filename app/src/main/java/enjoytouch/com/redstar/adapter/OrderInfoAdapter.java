package enjoytouch.com.redstar.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.LogisticsOtherActivity;
import enjoytouch.com.redstar.activity.ShopDetailsActivity;
import enjoytouch.com.redstar.activity.logisticsActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.LogisticsBean;
import enjoytouch.com.redstar.bean.OrderInfoManager;
import enjoytouch.com.redstar.bean.SkuBean;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.bean.TitleBean;
import enjoytouch.com.redstar.selfview.MiddleDialog;
import enjoytouch.com.redstar.selfview.MiddleDialog2;
import enjoytouch.com.redstar.selfview.RefundDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lizhaozhao on 16/6/28.
 */
public class OrderInfoAdapter extends BaseAdapter{

    private Context context;
    private List<OrderInfoManager> managers;
    private String id;
    private String status;
    public OrderInfoAdapter(Context context,List<OrderInfoManager>managers,String id,String status){
        this.context=context;
        this.managers=managers;
        this.id=id;
        this.status=status;
    }
    @Override
    public int getCount() {
        return managers.size();
    }

    @Override
    public Object getItem(int position) {
        return managers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return managers.get(position).type;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        switch (getItemViewType(position)){
            case OrderInfoManager.TITLE:
                convertView = View.inflate(context, R.layout.item_make_order_title, null);
                TextView title = (TextView) convertView.findViewById(R.id.item_make_order_title);
                TitleBean titleBean = (TitleBean) managers.get(position).object;
                title.setText("商家:"+titleBean.getTitle());
                break;
            case OrderInfoManager.VALUE:
                final SkuBean skuBean= (SkuBean) managers.get(position).object;
                convertView=View.inflate(context,R.layout.item_order_info,null);
                SimpleDraweeView iv = (SimpleDraweeView)convertView.findViewById(R.id.iv_orderimg);
                TextView name = (TextView) convertView.findViewById(R.id.item_make_order_name);
                TextView price = (TextView) convertView.findViewById(R.id.text_price);
                TextView number = (TextView) convertView.findViewById(R.id.text_number);
                RelativeLayout make= (RelativeLayout) convertView.findViewById(R.id.make);
                View line=convertView.findViewById(R.id.item_order_info_line);
                final TextView fail_Tv= (TextView) convertView.findViewById(R.id.order_info_fail_tv);
                final RelativeLayout tuikuan_Rl= (RelativeLayout) convertView.findViewById(R.id.order_info_repay_rl);
                final RelativeLayout fail_Rl= (RelativeLayout) convertView.findViewById(R.id.order_info_status_rl);
                final TextView fail_time= (TextView) convertView.findViewById(R.id.order_info_value);
                final TextView status_Tv= (TextView) convertView.findViewById(R.id.order_info_status);
                iv.setImageURI(Uri.parse(skuBean.getCover_img()));
                name.setText(skuBean.getName());
                price.setText("¥"+skuBean.getSale_price());
                number.setText("数量:"+skuBean.getAmount());
                make.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ShopDetailsActivity.class);
                        intent.putExtra(GlobalConsts.INTENT_DATA, skuBean.getId());
                        context.startActivity(intent);
                    }
                });
                ContentUtil.makeLog("lzz","size:"+managers.get(position).size+","+position);
//                if(){
//
//                }

                // 1为可退 2为不可退
                if("1".equals(skuBean.getRefund_enable())&&("2".equals(status)||"7".equals(status)||"8".equals(status)||"9".equals(status))){

                    fail_time.setText(skuBean.getReturn_time());
                    fail_Tv.setText("失败原因:"+skuBean.getReturn_reason());
                    switch (skuBean.getRefund_status()){
//                        case "0":
//                            tuikuan_Rl.setVisibility(View.VISIBLE);
//                            fail_Tv.setVisibility(View.GONE);
//                            fail_Rl.setVisibility(View.GONE);
//                            break;
                        case "1":
                            tuikuan_Rl.setVisibility(View.GONE);
                            fail_Tv.setVisibility(View.GONE);
                            fail_Rl.setVisibility(View.VISIBLE);
                            status_Tv.setText("退款申请中");
                            break;
                        case "2":
                            tuikuan_Rl.setVisibility(View.GONE);
                            fail_Tv.setVisibility(View.GONE);
                            fail_Rl.setVisibility(View.VISIBLE);
                            status_Tv.setText("退款处理中");
                            break;
                        case "3":
                            fail_Tv.setVisibility(View.VISIBLE);
                            tuikuan_Rl.setVisibility(View.GONE);
                            fail_Rl.setVisibility(View.VISIBLE);
                            status_Tv.setText("退款成功");
                            break;
                        case "4":
                            fail_Tv.setVisibility(View.VISIBLE);
                            tuikuan_Rl.setVisibility(View.GONE);
                            fail_Rl.setVisibility(View.VISIBLE);
                            status_Tv.setText("退款失败");
                            break;
                    }

                }else {
                    tuikuan_Rl.setVisibility(View.GONE);
                    fail_Rl.setVisibility(View.GONE);
                    fail_Tv.setVisibility(View.GONE);
                }


                tuikuan_Rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        RefundDialog refundDialog=new RefundDialog(context, new RefundDialog.onButtonCLickListener() {
                            @Override
                            public void onActivieButtonClick() {
                                final Dialog dialog= DialogUtil.createLoadingDialog(context,context.getString(R.string.loading));
                                dialog.show();
                                Call<StatusBean> call= HttpServiceClient.getInstance().order_refund(MyApplication.token,id, skuBean.getId(),skuBean.getSku_id());
                                call.enqueue(new Callback<StatusBean>() {
                                    @Override
                                    public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                                        dialog.dismiss();
                                        if (response.isSuccessful()) {
                                            if ("ok".equals(response.body().getStatus())) {
                                                tuikuan_Rl.setVisibility(View.GONE);
                                                fail_Rl.setVisibility(View.VISIBLE);
                                                status_Tv.setText("退款受理中");
                                                new MiddleDialog2(context, "提示", context.getResources().getString(R.string.refund_value), new MiddleDialog2.onBottonListener() {
                                                    @Override
                                                    public void onOk() {

                                                    }
                                                },R.style.registDialog).show();

//                                                fail_time.setText(skuBean.getReturn_time());
                                            } else {

                                                ContentUtil.makeToast(context, response.body().getError().getMessage());
                                            }
                                        } else {

                                            try {
                                                ContentUtil.makeToast(context, response.errorBody().string());
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<StatusBean> call, Throwable t) {

                                        dialog.dismiss();
                                    }
                                });
                            }
                        },R.style.registDialog);
                        refundDialog.show();
                    }
                });
                break;
            case OrderInfoManager.WULIU:
                convertView = View.inflate(context, R.layout.item_logistics, null);
                TextView name_Tv= (TextView) convertView.findViewById(R.id.logistics_name);
                TextView code_Tv= (TextView) convertView.findViewById(R.id.logistics_code);
                final LogisticsBean bean= (LogisticsBean) managers.get(position).object;
                if(bean.getLogistics_name()==null||bean.getLogistics_name().equals("")){
                    name_Tv.setVisibility(View.GONE);
                }
                if(bean.getLogistics_no()==null||bean.getLogistics_no().equals("")){
                    code_Tv.setVisibility(View.GONE);
                }
                name_Tv.setText("物流公司:"+bean.getLogistics_name());
                code_Tv.setText("物流单号:"+bean.getLogistics_no());

                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if("1".equals(bean.getType())){
                            Intent intent=new Intent(context, logisticsActivity.class);
                            intent.putExtra(GlobalConsts.INTENT_DATA,new String[]{bean.getLogistics_no(),bean.getLogistics_name()});
                            context.startActivity(intent);
                        }else {
                            Intent intent=new Intent(context, LogisticsOtherActivity.class);
                            intent.putExtra(GlobalConsts.INTENT_DATA,new String[]{bean.getLogistics_no(),bean.getLogistics_name(),bean.getWebsite()});
                            context.startActivity(intent);
                        }

                    }
                });
                break;
        }
        return convertView;
    }
}
