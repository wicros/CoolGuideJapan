package jp.co.jpmobile.coolguidejapan.utils;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.RequestQueue;
import com.yolanda.nohttp.rest.Response;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;

import jp.co.jpmobile.coolguidejapan.R;
import jp.co.jpmobile.coolguidejapan.base.BaseApplication;
import jp.co.jpmobile.coolguidejapan.conf.Urlconf;

public class HttpUtils {

    private static int number = -1;
    public final  static int NOURL = -2;
    private  static  String url = null;
    private  static StringBuffer stringBuffer =  new StringBuffer();

    public static Gson gson = new Gson();


    public interface OnNetResponseListner{

        public void onStart(int what) ;

        public void  onSucceed(int what, Object o);

        public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) ;

        public void onFinish(int what);

    }

    public static void sendGetRequet(String ex_url, Map<String,String> map, final OnNetResponseListner onNetResponseListner, final Class clazz,int number1){
        number = number1;
        sendGetRequet(ex_url,map,onNetResponseListner,clazz);
    }


    public static void sendGetRequet(String ex_url, Map<String,String> map, final OnNetResponseListner onNetResponseListner, final Class clazz){


        if (number == NOURL){
            url = ex_url;
        }else {
            Set<Map.Entry<String, String>> entries = map.entrySet();

            for (Map.Entry<String, String> entry : entries) {
                stringBuffer.append(entry.getKey()+"="+entry.getValue()+"&");
            }

            String substring= "";

            if (map.size() > 0){
                substring = stringBuffer.substring(0,stringBuffer.length() - 1);
            }
             url = Urlconf.HOME_URL + ex_url +"?"+ substring;
        }


        Log.e("test",url);

        Request<String> stringRequest = NoHttp.createStringRequest(url, RequestMethod.GET);

        BaseApplication.getRequestQueue().add( number , stringRequest,new OnResponseListener<String>(){
            @Override
            public void onStart(int what) {
                onNetResponseListner.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                Log.e("test","json:"+response.get());


                Object o = gson.fromJson(response.get(), clazz);
                if (o == null){
                   Toast.makeText(BaseApplication.getContext(),R.string.error_server,Toast.LENGTH_SHORT).show();
                    return;
                }
                onNetResponseListner.onSucceed(what,o);

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                        onNetResponseListner.onFailed(what,url,tag,exception,responseCode,networkMillis);
                Toast.makeText(BaseApplication.getContext(),BaseApplication.getContext().getString(R.string.network_error_msg),Toast.LENGTH_SHORT);
            }

            @Override
            public void onFinish(int what) {
                    onNetResponseListner.onFinish(what);
            }
        });

        stringBuffer.delete(0,stringBuffer.length());
        number = -1;
    }

    public void sendPostRequest (String ex_url, Map<String,String> map, final OnNetResponseListner onNetResponseListner, final Class clazz){


        Request<String> stringRequest = NoHttp.createStringRequest(Urlconf.HOME_URL+ex_url, RequestMethod.POST);
        stringRequest.add(map);

        BaseApplication.getRequestQueue().add( number , stringRequest,new OnResponseListener<String>(){
            @Override
            public void onStart(int what) {
                onNetResponseListner.onStart(what);
            }

            @Override
            public void onSucceed(int what, Response<String> response) {

                Log.e("test","json:"+response.get());

                Object o = gson.fromJson(response.get(), clazz);
                if (o == null){
                    Toast.makeText(BaseApplication.getContext(),BaseApplication.getContext().getString(R.string.network_error_msg),Toast.LENGTH_SHORT);
                    return;
                }
                onNetResponseListner.onSucceed(what,o);

            }

            @Override
            public void onFailed(int what, String url, Object tag, Exception exception, int responseCode, long networkMillis) {
                onNetResponseListner.onFailed(what,url,tag,exception,responseCode,networkMillis);
                Toast.makeText(BaseApplication.getContext(),BaseApplication.getContext().getString(R.string.network_error_msg),Toast.LENGTH_SHORT);
            }

            @Override
            public void onFinish(int what) {
                onNetResponseListner.onFinish(what);
            }
        });
    }


}
