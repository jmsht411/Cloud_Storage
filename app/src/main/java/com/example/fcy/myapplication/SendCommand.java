package com.example.fcy.myapplication;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.example.fcy.myapplication.database.DirectoryDAO;
import com.example.fcy.myapplication.database.DirectoryDAOImp;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


/**
 * Created by fcy on 17-2-14.
 */

public class SendCommand implements Runnable {
    private int m_command;
    private Handler m_handler;
    private BufferedReader in = null;
    private PrintWriter out = null;
    private HttpURLConnection urlConnection = null;
    InputStream inputStream = null;

    public static final String TAG = "MainActivity";
    public static final MediaType JSON= MediaType.parse("application/json; charset=utf-8");

    public SendCommand(int m_command, Handler handler) {
        this.m_command = m_command;
        this.m_handler = handler;
    }

    @Override
    public void run() {


        switch (m_command) {
            case 0: {
                try {
                    String apkPackageUrl = "http://192.168.31.78:5000/refresh";
                    OkHttpClient client = new OkHttpClient();
                    final Request request = new Request.Builder().url(apkPackageUrl).build();
                    client.newCall(request).enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println("获取apk列表失败");
                            Log.d("GetApkPackage", e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String result = response.body().string();
                            System.out.println(result);

                            Gson gson = new Gson();
                            List<DirectoryInfo> directoryInfos =gson.fromJson(result,new TypeToken<List<DirectoryInfo>>(){}.getType());

                            DirectoryInfoBean directoryInfoBean = new DirectoryInfoBean(directoryInfos);


                            System.out.println(directoryInfos.get(0).getM_fileModifyTime().toString());

                            Message msg = new Message();
                            msg.what = 0x1233;
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("refresh",directoryInfoBean);
                            msg.setData(bundle);

                            m_handler.sendMessage(msg);

                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (urlConnection != null) {
                        urlConnection.disconnect();
                    }
                }

                break;
            }
            case 1: {
                DirectoryInfo newDocument = new DirectoryInfo(1,"bbb",0,0,0,"2017-2-18","http://www.google.com","djhfkdjfo");
                Gson gson = new Gson();
                String result = gson.toJson(newDocument);
                System.out.println(result);

                m_handler.sendEmptyMessage(0x1232);

                //申明给服务端传递一个json串
                //创建一个OkHttpClient对象
                OkHttpClient okHttpClient = new OkHttpClient();
                //创建一个RequestBody(参数1：数据类型 参数2传递的json串)
                RequestBody requestBody = RequestBody.create(JSON, result);
                //创建一个请求对象
                Request request = new Request.Builder()
                        .url("http://192.168.31.78:5000/newdocument")
                        .post(requestBody)
                        .build();
                //发送请求获取响应
                try {
                    Response response=okHttpClient.newCall(request).execute();
                    //判断请求是否成功
                    if(response.isSuccessful()){
                        //打印服务端返回结果
                        Log.i(TAG,response.body().string());

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            }
            default: {

            }
        }



    }
}
