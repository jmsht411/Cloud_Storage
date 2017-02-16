package com.example.fcy.myapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by fcy on 17-2-14.
 */

public class SendCommand implements Runnable {
    private String m_command;
    private BufferedReader in = null;
    private PrintWriter out = null;

    public SendCommand(String m_command) {
        this.m_command = m_command;
    }

    @Override
    public void run() {

        try {
            //向服务器端发送请求，服务器IP地址和服务器监听的端口号
            Socket client = new Socket("192.168.31.78", 4242);

            in = new BufferedReader(new InputStreamReader(client
                    .getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
                    client.getOutputStream())), true);

            if(client.isConnected()) {
                if(!client.isOutputShutdown()) {
                    out.println(m_command);
                }
            }



        }catch (Exception e){
            e.printStackTrace();
        }

//
//        //通过printWriter 来向服务器发送消息
//        PrintWriter printWriter = new PrintWriter(client.getOutputStream());
//        System.out.println("连接已建立...");
//
//        //发送消息
//        printWriter.println("hello Server");
//
//        printWriter.flush();
//
//        //InputStreamReader是低层和高层串流之间的桥梁
//        //client.getInputStream()从Socket取得输入串流
//        InputStreamReader streamReader = new InputStreamReader(client.getInputStream());
//
//        //链接数据串流，建立BufferedReader来读取，将BufferReader链接到InputStreamReder
//        BufferedReader reader = new BufferedReader(streamReader);
//        String advice =reader.readLine();
//
//
//        System.out.println("接收到服务器的消息 ："+advice);
//        reader.close();

    }
}
