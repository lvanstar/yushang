package enjoytouch.com.redstar.adapter;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import enjoytouch.com.redstar.R;
import enjoytouch.com.redstar.activity.ShoppingCartActivity;
import enjoytouch.com.redstar.application.MyApplication;
import enjoytouch.com.redstar.bean.ShopCarBean;
import enjoytouch.com.redstar.bean.ShoppingCartBean;
import enjoytouch.com.redstar.util.ContentUtil;
import enjoytouch.com.redstar.util.DialogUtil;
import enjoytouch.com.redstar.util.HttpServiceClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by duan on 2016/6/12.
 */
public class ShoppingCartAdapter extends BaseAdapter {
    private Context context;
    public List<ShoppingCartBean.DataEntity> data;
    private Dialog dialog;
    public static double price = 0.00;
    public boolean isOne=true;

    private ArrayList<String> amounts = new ArrayList<>();


    public ShoppingCartAdapter(Context context, List<ShoppingCartBean.DataEntity> data) {
        this.context = context;
        this.data = data;
        dialog = DialogUtil.createLoadingDialog(context, context.getString(R.string.loading));
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final MyHolder myHolder;
        if (view == null) {
            view = View.inflate(context, R.layout.item_shopcart, null);
            myHolder = new MyHolder(view);
            view.setTag(myHolder);
        } else {
            myHolder = (MyHolder) view.getTag();
        }
        //可购买时执行
        if(data.get(i)!=null){
            myHolder.sv.setImageURI(Uri.parse(data.get(i).getCover_img()));
            myHolder.title.setText(data.get(i).getName());
            myHolder.price.setText("￥" + data.get(i).getSale_price());
            myHolder.number.setText(data.get(i).getAmount() + "");
            myHolder.remain.setText(data.get(i).getStock_notice());
        }

        // type为1 正常 type为2 已下架
        if ("1".equals(data.get(i).getProduct_status())) {
            // type为1 已售罄 type为2 库存不足 type为3 正常
            if ("1".equals(data.get(i).getStock_status())) {
                myHolder.ll.setVisibility(View.GONE);
                myHolder.alpha.setVisibility(View.VISIBLE);
                myHolder.select.setVisibility(View.INVISIBLE);
                myHolder.alpha2.setVisibility(View.GONE);
                myHolder.state.setBackgroundResource(R.drawable.state_1);
            } else if ("3".equals(data.get(i).getStock_status())) {
                myHolder.ll.setVisibility(View.VISIBLE);
                myHolder.alpha.setVisibility(View.GONE);
                myHolder.select.setVisibility(View.VISIBLE);
                myHolder.alpha2.setVisibility(View.VISIBLE);
            } else if ("2".equals(data.get(i).getStock_status())) {
                myHolder.ll.setVisibility(View.VISIBLE);
                myHolder.alpha.setVisibility(View.GONE);
                myHolder.select.setVisibility(View.VISIBLE);
                myHolder.alpha2.setVisibility(View.VISIBLE);

            }
        } else {
            myHolder.alpha.setVisibility(View.VISIBLE);
            myHolder.select.setVisibility(View.INVISIBLE);
            myHolder.alpha2.setVisibility(View.GONE);
            myHolder.ll.setVisibility(View.GONE);
            myHolder.state.setBackgroundResource(R.drawable.state_2);
        }

      if(data.get(i).select){
          myHolder.select.setImageDrawable(context.getResources().getDrawable(R.drawable.select_ture));
      }else {
          myHolder.select.setImageDrawable(context.getResources().getDrawable(R.drawable.unselected));
      }
//        myHolder.shopcart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(context, ShopDetailsActivity.class);
//                intent.putExtra(GlobalConsts.INTENT_DATA,data.get(i).getId());
//                context.startActivity(intent);
//            }
//        });
        myHolder.jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!data.get(i).select) {
                    data.get(i).select = true;
                    price += (data.get(i).getAmount() * Double.parseDouble(data.get(i).getSale_price()));
                    myHolder.select.setImageDrawable(context.getResources().getDrawable(R.drawable.select_ture));
                    ShoppingCartActivity.setPrice(price);
                }

                editShopCar(data.get(i).getId(), "1", i);


//                if (data.get(i).select) {
//                    editShopCar(data.get(i).getId(), "1", i);
//                } else {
//                    ContentUtil.makeToast(context, "请先选中商品");
//                }
               // price += (data.get(i).getAmount() * Double.parseDouble(data.get(i).getSale_price()));


            }
        });
        myHolder.jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  if (data.get(i).select) {
                if (!data.get(i).select) {
                    data.get(i).select = true;
                    price += (data.get(i).getAmount() * Double.parseDouble(data.get(i).getSale_price()));
                    myHolder.select.setImageDrawable(context.getResources().getDrawable(R.drawable.select_ture));
                    ShoppingCartActivity.setPrice(price);
                }

                    if (data.get(i).getAmount() > 1) {
                        editShopCar(data.get(i).getId(), "2", i);
                       // price += (data.get(i).getAmount() * Double.parseDouble(data.get(i).getSale_price()));
                       // ShoppingCartActivity.setPrice(price);
                    } else {
                        Dialog dialog = DialogUtil.show(context,"最少有一件商品", false);
                        dialog.show();
//                        ContentUtil.makeToast(context, "最少有一件商品");
                    }
//                } else {
//                    ContentUtil.makeToast(context, "请先选中商品");
//                }

            }
        });
        myHolder.select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (data.get(i).select) {
                    data.get(i).select = false;
                    price -= (data.get(i).getAmount() * Double.parseDouble(data.get(i).getSale_price()));
                    myHolder.select.setImageDrawable(context.getResources().getDrawable(R.drawable.unselected));
                    ShoppingCartActivity.setPrice(price);
//                    if(products.contains(data.get(i).getId()))products.remove(products.indexOf(data.get(i).getId()));
//                    if(amounts.contains(String.valueOf(data.get(i).getAmount())))amounts.remove(amounts.indexOf(String.valueOf(data.get(i).getAmount())));
                } else {
                    data.get(i).select = true;
//                    products.add(data.get(i).getId());
//                    amounts.add(String.valueOf(data.get(i).getAmount()));
                    price += (data.get(i).getAmount() * Double.parseDouble(data.get(i).getSale_price()));
                    myHolder.select.setImageDrawable(context.getResources().getDrawable(R.drawable.select_ture));
                    ShoppingCartActivity.setPrice(price);
                }
            }
        });

//        if (isOne){
//            if ("1".equals(data.get(i).getProduct_status())&&!"1".equals(data.get(i).getStock_status())) {
//                data.get(i).select = true;
//                price += (data.get(i).getAmount() * Double.parseDouble(data.get(i).getSale_price()));
//                myHolder.select.setImageDrawable(context.getResources().getDrawable(R.drawable.select_ture));
//            }
//
//            if (i==(data.size()-1)) {
//                ShoppingCartActivity.setPrice(price);
//                isOne = false;
//            }
//        }

        return view;
    }

    class MyHolder {
        RelativeLayout jia;          //增加数量的按钮
        RelativeLayout jian;         //减少数量的按钮
        TextView number;        //数量
        ImageView state;        //阴影部分显示才有的状态图片
        TextView remain;        //剩余不足提醒
        SimpleDraweeView sv;
        TextView title;
        TextView price;

        RelativeLayout alpha;   //阴影部分              （这个显示状态时，下面两个会隐藏）
        ImageView select;       //不选中时的圆圈图片
        LinearLayout alpha2;  //显示选购数量的区域
        LinearLayout shopcart,ll;

        public MyHolder(View view) {
            remain = (TextView) view.findViewById(R.id.remain);
            select = (ImageView) view.findViewById(R.id.iv_select);
            jia = (RelativeLayout) view.findViewById(R.id.jia);
            jian = (RelativeLayout) view.findViewById(R.id.jian);
            number = (TextView) view.findViewById(R.id.item_number);
            alpha = (RelativeLayout) view.findViewById(R.id.alpha);
            alpha2 = (LinearLayout) view.findViewById(R.id.item_shop_cart_alpha_2);
            state = (ImageView) view.findViewById(R.id.sdv_state);
            sv = (SimpleDraweeView) view.findViewById(R.id.item_shop_cart_iv);
            title = (TextView) view.findViewById(R.id.item_shop_cart_title);
            price = (TextView) view.findViewById(R.id.item_shop_cart_money);
            shopcart = (LinearLayout) view.findViewById(R.id.shopcart);
            ll= (LinearLayout) view.findViewById(R.id.item_ll);
        }

    }


    public void setDataChanged(List<ShoppingCartBean.DataEntity> list) {
        this.data = list;
        notifyDataSetChanged();
    }

    public List<ShoppingCartBean.DataEntity> getProducts() {
        List<ShoppingCartBean.DataEntity> dataEntities = new ArrayList<>();
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).select) {
                dataEntities.add(data.get(i));
            }
        }
        return dataEntities;
    }


    private void editShopCar(String id, final String type, final int postion) {

        dialog.show();
        Call<ShopCarBean> call = HttpServiceClient.getInstance().edit_shopCar(id, type, MyApplication.token, MyApplication.cityId);
        call.enqueue(new Callback<ShopCarBean>() {
            @Override
            public void onResponse(Call<ShopCarBean> call, Response<ShopCarBean> response) {
                dialog.dismiss();
                if (response.isSuccessful()) {
                    if ("ok".equals(response.body().getStatus())) {
                        if ("1".equals(type)) {

                            ShoppingCartActivity.setPrice(price += Double.parseDouble(data.get(postion).getSale_price()));
                        } else {
                            ShoppingCartActivity.setPrice(price -= Double.parseDouble(data.get(postion).getSale_price()));
                        }
                        ShopCarBean.DataEntity shopCarBean = response.body().getData();
                        ShoppingCartBean.DataEntity bean = new ShoppingCartBean.DataEntity();
                        data.remove(postion);
                        bean.setId(shopCarBean.getId());
                        bean.setAmount(shopCarBean.getAmount());
                        bean.setCover_img(shopCarBean.getCover_img());
                        bean.setProduct_status(shopCarBean.getProduct_status());
                        bean.setStock_status(shopCarBean.getStock_statas());
                        bean.setName(shopCarBean.getName());
                        bean.setSale_price(shopCarBean.getSale_price());
                        bean.setStock_notice(shopCarBean.getStock_notice());
                        bean.select = true;
                        data.add(postion, bean);
                        setDataChanged(data);
                    } else {
                        Dialog dialog = DialogUtil.show(context, response.body().getError().getMessage(), false);
                        dialog.show();
//                        ContentUtil.makeToast(context, response.body().getError().getMessage());
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
            public void onFailure(Call<ShopCarBean> call, Throwable t) {
                dialog.dismiss();
            }
        });
    }

}
