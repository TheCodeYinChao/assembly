package com.netty.bio;

import lombok.extern.slf4j.Slf4j;

import javax.sound.sampled.Line;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Admin on 2019/4/7.
 */
@Slf4j
public class TimeClient {
    public static void main(String[] args)throws Exception {
        Socket socket = null;
        PrintWriter pw = null;
        BufferedReader br = null;
        try {
            socket = new Socket("127.0.0.1",80);

            pw = new PrintWriter(socket.getOutputStream(),true);
            pw.println("select time?");

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            StringBuffer sb = new StringBuffer();
            String str;
            while ((str = br.readLine())!=null){
                sb.append(str);
                log.info("recevice server  msg:{}",sb.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
            pw.close();
            br.close();
        }
    }
 }
