package com.example.fcy.myapplication.upload;

/**
 * Created by fcy on 17-2-26.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.example.fcy.myapplication.R;
import com.example.fcy.myapplication.SendCommand;
import com.example.fcy.myapplication.coreprogress.helper.ProgressHelper;
import com.example.fcy.myapplication.coreprogress.listener.ProgressListener;
import com.example.fcy.myapplication.coreprogress.listener.impl.UIProgressListener;

public class UploadImpActivity extends AppCompatActivity {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            //设置超时，不设置可能会报异常
            .connectTimeout(1000, TimeUnit.MINUTES)
            .readTimeout(1000, TimeUnit.MINUTES)
            .writeTimeout(1000, TimeUnit.MINUTES)
            .build();
    private Button upload;
    private ProgressBar uploadProgress;
    private String path = null;
    private String documentId = "0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_imp_activity);
        Intent intent = getIntent();
        path = intent.getExtras().getString("path");
        documentId = intent.getExtras().getString("parentId");
        initView();
    }

    private void initView() {
        uploadProgress= (ProgressBar) findViewById(R.id.upload_progress);
        upload= (Button) findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upload();
            }
        });
    }

//    private void download() {
//        //这个是非ui线程回调，不可直接操作UI
//        final ProgressListener progressResponseListener = new ProgressListener() {
//            @Override
//            public void onProgress(long bytesRead, long contentLength, boolean done) {
//                Log.e("TAG", "bytesRead:" + bytesRead);
//                Log.e("TAG", "contentLength:" + contentLength);
//                Log.e("TAG", "done:" + done);
//                if (contentLength != -1) {
//                    //长度未知的情况下回返回-1
//                    Log.e("TAG", (100 * bytesRead) / contentLength + "% done");
//                }
//                Log.e("TAG", "================================");
//            }
//        };
//
//
//        //这个是ui线程回调，可直接操作UI
//        final UIProgressListener uiProgressResponseListener = new UIProgressListener() {
//            @Override
//            public void onUIProgress(long bytesRead, long contentLength, boolean done) {
//                Log.e("TAG", "bytesRead:" + bytesRead);
//                Log.e("TAG", "contentLength:" + contentLength);
//                Log.e("TAG", "done:" + done);
//                if (contentLength != -1) {
//                    //长度未知的情况下回返回-1
//                    Log.e("TAG", (100 * bytesRead) / contentLength + "% done");
//                }
//                Log.e("TAG", "================================");
//                //ui层回调
//                //downloadProgeress.setProgress((int) ((100 * bytesRead) / contentLength));
//                //Toast.makeText(getApplicationContext(), bytesRead + " " + contentLength + " " + done, Toast.LENGTH_LONG).show();
//            }
//
//            @Override
//            public void onUIStart(long bytesRead, long contentLength, boolean done) {
//                super.onUIStart(bytesRead, contentLength, done);
//                Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onUIFinish(long bytesRead, long contentLength, boolean done) {
//                super.onUIFinish(bytesRead, contentLength, done);
//                Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();
//            }
//        };
//
//        //构造请求
//        final Request request1 = new Request.Builder()
//                .url("http://192.168.31.78:8080/media/media/EP17.mp4")
//                .build();
//
//        Call call = ProgressHelper.addProgressResponseListener(client, uiProgressResponseListener).newCall(request1);
//
//        //包装Response使其支持进度回调
//        call.enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.e("TAG", "error ", e);
//            }
//
//            @Override
//            public void onResponse(Call call,Response response) throws IOException {
//                Log.e("TAG", response.body().string());
//            }
//        });
//    }

    private String getFileMD5(File file) {
        if (!file.isFile()) {
            return null;
        }
        MessageDigest digest = null;
        FileInputStream in = null;
        byte buffer[] = new byte[1024];
        int len;
        try {
            digest = MessageDigest.getInstance("MD5");
            in = new FileInputStream(file);
            while ((len = in.read(buffer, 0, 1024)) != -1) {
                digest.update(buffer, 0, len);
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        BigInteger bigInt = new BigInteger(1, digest.digest());
        return bigInt.toString(16);
    }

    private void upload() {
        File file = new File(path);
        //此文件必须在手机上存在，实际情况下请自行修改，这个目录下的文件只是在我手机中存在。

        String[] pathSplit = path.split("/");
        String m_fileName = pathSplit[pathSplit.length-1];
        String m_fileSize = String.valueOf(file.length());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String time = formatter.format(curDate);

        String md5 = getFileMD5(file);

        //这个是非ui线程回调，不可直接操作UI
        final ProgressListener progressListener = new ProgressListener() {
            @Override
            public void onProgress(long bytesWrite, long contentLength, boolean done) {
                Log.e("TAG", "bytesWrite:" + bytesWrite);
                Log.e("TAG", "contentLength" + contentLength);
                Log.e("TAG", (100 * bytesWrite) / contentLength + " % done ");
                Log.e("TAG", "done:" + done);
                Log.e("TAG", "================================");
            }
        };


        //这个是ui线程回调，可直接操作UI
        final UIProgressListener uiProgressRequestListener = new UIProgressListener() {
            @Override
            public void onUIProgress(long bytesWrite, long contentLength, boolean done) {
                Log.e("TAG", "bytesWrite:" + bytesWrite);
                Log.e("TAG", "contentLength" + contentLength);
                Log.e("TAG", (100 * bytesWrite) / contentLength + " % done ");
                Log.e("TAG", "done:" + done);
                Log.e("TAG", "================================");
                //ui层回调
                uploadProgress.setProgress((int) ((100 * bytesWrite) / contentLength));
                //Toast.makeText(getApplicationContext(), bytesWrite + " " + contentLength + " " + done, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUIStart(long bytesWrite, long contentLength, boolean done) {
                super.onUIStart(bytesWrite, contentLength, done);
                Toast.makeText(getApplicationContext(),"start",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onUIFinish(long bytesWrite, long contentLength, boolean done) {
                super.onUIFinish(bytesWrite, contentLength, done);
                Toast.makeText(getApplicationContext(),"end",Toast.LENGTH_SHORT).show();
            }
        };

        //构造上传请求，类似web表单

        RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("m_fileName", m_fileName)
                .addFormDataPart("m_parentFileId",documentId+"")
                .addFormDataPart("m_file_type","0")
                .addFormDataPart("m_fileSize",m_fileSize+"")
                .addFormDataPart("m_fileModifyTime",time)
                .addFormDataPart("m_fileUrl","http://192.168.31.78:8080/upload/"+md5)
                .addFormDataPart("m_fileMD5",md5)
                .addFormDataPart("photo", file.getName(), RequestBody.create(null, file))
                .addPart(Headers.of("Content-Disposition", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), file))
                .build();

        //进行包装，使其支持进度回调
        final Request request = new Request.Builder().url("http://192.168.31.78:5000/upload").post(ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener)).build();

        Call uploadCall = client.newCall(request);

        //开始请求
        uploadCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call uploadCall, IOException e) {
                Log.e("TAG", "error ", e);
            }

            @Override
            public void onResponse(Call uploadCall,Response response) throws IOException {
                Log.e("TAG", response.body().string());

            }
        });

    }
}
