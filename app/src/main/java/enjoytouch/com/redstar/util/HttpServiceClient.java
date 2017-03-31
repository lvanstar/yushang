package enjoytouch.com.redstar.util;

import android.content.Context;

import enjoytouch.com.redstar.callback.InterfaceService;


/**
 * Created by lizhaozhao on 16/4/20.
 *
 * 网络请求初始化
 */
public class HttpServiceClient {

    private static InterfaceService interfaceService;
    private Context context;

    /**
     * 获取实例
     * @return
     */
    public static InterfaceService getInstance(){
//        System.setProperty("http.keepAlive", "false");
//        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//        httpClient.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request original = chain.request();
//
//                // Customize the request
//                Request request = original.newBuilder()
//                        .header("Accept", "application/json")
//                        .header("Authorization", "auth-token")
//                        .method(original.method(), original.body())
//                        .build();
//
//                Response response = chain.proceed(request);
//
//                // Customize or return the response
//                return response;
//            }
//        });
//
//        OkHttpClient client = httpClient.build();
//        if(interfaceService==null){
//            Retrofit retrofit=new Retrofit.Builder()
//                    .baseUrl(GlobalConsts.URL)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client)
//                    .build();
//
//            interfaceService=retrofit.create(InterfaceService.class);
//
//        }

        RetrofitUtils.setUrl_ROOT(GlobalConsts.URI);
        interfaceService=RetrofitUtils.createApiForGson(InterfaceService.class);

        return  interfaceService;
    }
   /* class  a implements ErrorHandler{

       @Override
       public void warning(SAXParseException exception) throws SAXException {

       }

       @Override
       public void error(SAXParseException exception) throws SAXException {
           if (exception.equals("1012")){
               Intent intent = new Intent(context, LoginActivity.class);
               MyApplication.sf.edit().putBoolean(GlobalConsts.ISLOGIN,false).commit();
               MyApplication.isLogin=false;
               intent.putExtra("from", true);
               context.startActivity(intent);
           }
       }

       @Override
       public void fatalError(SAXParseException exception) throws SAXException {

       }
   }*/
}
