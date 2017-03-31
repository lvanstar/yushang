package enjoytouch.com.redstar.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.umeng.analytics.MobclickAgent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.MainActivity;
import enjoytouch.com.redstar.activity.MiddleDialog2;
import enjoytouch.com.redstar.activity.OrderDetilsActivity;
import enjoytouch.com.redstar.activity.PaymentDetailsActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.OrderBean;
import enjoytouch.com.redstar.bean.OrderListBean;
import enjoytouch.com.redstar.bean.SkuInfoBean;
import enjoytouch.com.redstar.bean.StatusBean;
import enjoytouch.com.redstar.selfview.HorizontalListView;
import enjoytouch.com.redstar.selfview.MiddleDialog;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.ExclusiveYeUtils;
import enjoytouch.com.redstar.util.GlobalConsts;
import enjoytouch.com.redstar.util.HttpServiceClient;
import enjoytouch.com.redstar.util.Utils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static enjoytouch.com.redstar.bean.OrderBean.*;

/**
 * Created by Administrator on 2015/12/15.
 */
public class MyTheOderAdapter extends RecyclerView.Adapter<MyTheOderAdapter.ViewHolder>{
    private Context context;
    private List<OrderListBean.DataEntity>list;
    public MyTheOderAdapter(Context c,List<OrderListBean.DataEntity>list){
        context=c;
        this.list=list;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.item_my_the_oder,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.lv.setFocusable(false);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(context, "lookMyOrderDetail");
                Intent intent=new Intent(context, OrderDetilsActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,list.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.order_all_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        /*holder.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int positions, long id) {
                ExclusiveYeUtils.onMyEvent(context, "lookMyOrderDetail");
                Intent intent=new Intent(context, OrderDetilsActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,list.get(position).getId());
                context.startActivity(intent);
            }
        });*/
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExclusiveYeUtils.onMyEvent(context, "lookMyOrderDetail");
                Intent intent=new Intent(context, OrderDetilsActivity.class);
                intent.putExtra(GlobalConsts.INTENT_DATA,list.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.bussiness.setText(list.get(position).getBusiness());
        holder.date.setText("(下单时间:"+list.get(position).getCreated()+")");
        holder.price.setText("￥"+list.get(position).getReal_payment());
        holder.time_Tv.setText("剩余时间:"+(list.get(position).getRemainder_time()/60)+"分钟");
        //判断商品数量显示布局
        if(list.get(position).getProduct().size()>0){
            if(list.get(position).getProduct().size()>1){
                holder.lv.setVisibility(View.VISIBLE);
                holder.product_rl.setVisibility(View.GONE);
                HorizontalApadter apadter=new HorizontalApadter(context,list.get(position).getProduct());
                holder.lv.setAdapter(apadter);
            }else if(list.get(position).getProduct().size()==1){
                holder.lv.setVisibility(View.GONE);
                holder.product_rl.setVisibility(View.VISIBLE);
                holder.iv_orderimg.setImageURI(Uri.parse(list.get(position).getProduct().get(0).getCover_img()));
                holder.order_name.setText(list.get(position).getProduct().get(0).getName());
                holder.number.setText("数量:"+list.get(position).getProduct().get(0).getAmount());
            }
        }


        //判断商品当前状态 1为 待付款 2为已付款 3为交易关闭 6交易成功 7已发货
        switch (list.get(position).getStatus()){
            case "1":
                holder.state.setText("待付款");
                holder.pay_rl.setVisibility(View.VISIBLE);
                holder.time_Tv.setVisibility(View.VISIBLE);
                holder.pay_ok.setVisibility(View.GONE);
                holder.tv_topay.setVisibility(View.VISIBLE);
//                holder.handler.sendEmptyMessageDelayed(1, 1000);
                //去付款
                holder.pay_rl.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ExclusiveYeUtils.onMyEvent(context, "toPayAction");
                        OrderBean bean=new OrderBean();
                        bean.setData(new OrderBean.DataEntity());
                        bean.getData().setOrder_id(list.get(position).getId());
                        bean.getData().setOrder_no(list.get(position).getOrder_no());
                        bean.getData().setBusiness(list.get(position).getBusiness());
                        bean.getData().setTitle(list.get(position).getTitle());
                        bean.getData().setReal_payment(list.get(position).getReal_payment());
                        Intent intent=new Intent(context,PaymentDetailsActivity.class);
                        intent.putExtra(GlobalConsts.INTENT_DATA,bean);
                        context.startActivity(intent);
                    }
                });
                break;
            case "2":
                holder.state.setText("已付款");
                holder.payTv.setText("实付款:");
                holder.state.setTextColor(context.getResources().getColor(R.color.text01));
                holder.pay_rl.setVisibility(View.GONE);
                break;
            case "3":
                holder.state.setText("交易关闭");
                holder.state.setTextColor(context.getResources().getColor(R.color.text01));
                holder.pay_rl.setVisibility(View.GONE);
                break;
            case "8":
                holder.state.setText("交易成功");
                holder.state.setTextColor(context.getResources().getColor(R.color.text01));
                holder.pay_rl.setVisibility(View.GONE);
                break;
            case "95":
                holder.state.setText("部分退款");
                holder.state.setTextColor(context.getResources().getColor(R.color.button3));
                holder.pay_rl.setVisibility(View.GONE);
//                holder.pay_rl.setVisibility(View.GONE);
//                holder.time_Tv.setVisibility(View.GONE);
//                holder.tv_topay.setVisibility(View.GONE);
//                holder.pay_ok.setVisibility(View.VISIBLE);
//                holder.pay_rl.setVisibility(View.VISIBLE);
                break;
            case "96":
                holder.state.setText("退款申请中");
                holder.state.setTextColor(context.getResources().getColor(R.color.button3));
                holder.pay_rl.setVisibility(View.GONE);
                break;
            case "97":
                holder.state.setText("退款受理中");
                holder.state.setTextColor(context.getResources().getColor(R.color.button3));
                holder.pay_rl.setVisibility(View.GONE);
                break;
            case "7":
                holder.state.setText("已发货");
                holder.state.setTextColor(context.getResources().getColor(R.color.text01));
                holder.pay_rl.setVisibility(View.GONE);
                holder.time_Tv.setVisibility(View.GONE);
                holder.tv_topay.setVisibility(View.GONE);
                holder.pay_ok.setVisibility(View.VISIBLE);
                holder.pay_rl.setVisibility(View.VISIBLE);

                break;
        }

        if("1".equals(list.get(position).getReceiving_show())){
            holder.pay_rl.setVisibility(View.GONE);
            holder.time_Tv.setVisibility(View.GONE);
            holder.tv_topay.setVisibility(View.GONE);
            holder.pay_ok.setVisibility(View.VISIBLE);
            holder.pay_rl.setVisibility(View.VISIBLE);

            //已发货接口
            holder.pay_rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final MiddleDialog dialogs = new MiddleDialog(context, "是否确认收货?\n", "", "否", "是", 0, new MiddleDialog.onButtonCLickListener() {
                        @Override
                        public void onActivieButtonClick(Object bean, int position) {

                            final Dialog dialog = DialogUtil.createLoadingDialog(context, context.getString(R.string.loading));
                            dialog.show();
                            Call<StatusBean> call = HttpServiceClient.getInstance().comfir_receipt(MyApplication.token, list.get(position).getId());
                            call.enqueue(new Callback<StatusBean>() {
                                @Override
                                public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                                    dialog.dismiss();
                                    if (response.isSuccessful()) {
                                        if ("ok".equals(response.body().getStatus())) {
                                            ExclusiveYeUtils.onMyEvent(context, "confirmReceipt");
                                            holder.state.setText("交易成功");
                                            holder.pay_rl.setVisibility(View.GONE);
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
                    }, R.style.registDialog);
                    dialogs.show();
                }
            });
        }




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView date;//下单时间
        private TextView state;//支付状态
        private HorizontalListView lv;
        private SimpleDraweeView iv_orderimg;
        private View product_rl,pay_rl; //等待付款时显示的布局
        private TextView time_Tv,tv_topay,order_name,number,price,pay_ok,payTv;   //剩余时间
        private TextView bussiness;
        private View view;
        private RelativeLayout order_all_rl;
        private RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);

            lv= (HorizontalListView) itemView.findViewById(R.id.order_lv);
            bussiness= (TextView) itemView.findViewById(R.id.my_the_order_bussines);
            date= (TextView) itemView.findViewById(R.id.my_the_order_date);
            state= (TextView) itemView.findViewById(R.id.my_the_order_state);
            product_rl= itemView.findViewById(R.id.my_order_product_ll);
            iv_orderimg= (SimpleDraweeView) itemView.findViewById(R.id.my_order_sv);
            order_name= (TextView) itemView.findViewById(R.id.my_the_order_name);
            number= (TextView) itemView.findViewById(R.id.my_the_order_number);
            price= (TextView) itemView.findViewById(R.id.my_the_order_price);
            time_Tv= (TextView) itemView.findViewById(R.id.my_the_order_time);
            tv_topay= (TextView) itemView.findViewById(R.id.tv_topay);
            pay_rl=itemView.findViewById(R.id.my_the_order_pay);
            pay_ok= (TextView) itemView.findViewById(R.id.my_the_order_ok);
            view=itemView.findViewById(R.id.order_view);
            order_all_rl= (RelativeLayout) itemView.findViewById(R.id.my_order_all_rl);
            rl=(RelativeLayout)itemView.findViewById(R.id.rl);
            payTv= (TextView) itemView.findViewById(R.id.tv2);
        }

//        private Handler handler = new Handler() {
//            public void handleMessage(android.os.Message msg) {
//
//                switch (msg.what){
//                    case 1:
//                       ContentUtil.makeLog("lzz",msg.arg1+"");
//                        time--;
//                        time_Tv.setText((time) / 60 + "分钟");
//                        if (time > 0) {
//                            if(time<=60){
//                                time_Tv.setText("1分钟");
//                            }
//                            handler.sendEmptyMessageDelayed(1,1000);
//                        } else {
//                            state.setText("交易关闭");
//
//                        }
//                        break;
//                }
//                super.handleMessage(msg);
//            }
//        };

    }

    private void shouhuo(final ViewHolder holder){
        //已发货接口
        holder.pay_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final MiddleDialog dialogs = new MiddleDialog(context, "是否确认收货?\n", "", "否","是",0, new MiddleDialog.onButtonCLickListener() {
                    @Override
                    public void onActivieButtonClick(Object bean, int position) {

                        final Dialog dialog=DialogUtil.createLoadingDialog(context,context.getString(R.string.loading));
                        dialog.show();
                        Call<StatusBean>call= HttpServiceClient.getInstance().comfir_receipt(MyApplication.token,list.get(position).getId());
                        call.enqueue(new Callback<StatusBean>() {
                            @Override
                            public void onResponse(Call<StatusBean> call, Response<StatusBean> response) {
                                dialog.dismiss();
                                if (response.isSuccessful()) {
                                    if ("ok".equals(response.body().getStatus())) {
                                        ExclusiveYeUtils.onMyEvent(context, "confirmReceipt");
                                        holder.state.setText("交易成功");
                                        holder.pay_rl.setVisibility(View.GONE);
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
                }, R.style.registDialog);
                dialogs.show();
            }
        });
    }

    public void updateData(List<OrderListBean.DataEntity>list){
        this.list=list;
        notifyDataSetChanged();
    }


    public class HorizontalApadter extends BaseAdapter{
        private Context context;
        private List<SkuInfoBean>list;
        public HorizontalApadter(Context context,List<SkuInfoBean>list){
            this.context=context;
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView=View.inflate(context,R.layout.item_my_order_sv,null);
            SimpleDraweeView sv= (SimpleDraweeView) convertView.findViewById(R.id.my_order_sv_simp);
            sv.setImageURI(Uri.parse(list.get(position).getCover_img()));
            return convertView;
        }
    }


}
