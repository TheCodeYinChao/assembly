package com.netty.bio;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.TimerTask;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Admin on 2019/4/7.
 */
public class TimeServer {
    private static TimeServerHandlerExecutePool executePool = new TimeServerHandlerExecutePool(50, 10000);

    public static void main(String[] args)throws Exception {

        ServerSocket serverSocket = null;
        Socket socket = null;
        try {
            serverSocket = new ServerSocket(80);
            while (true){
                socket = serverSocket.accept();
                /*连接的数量由线程池的最大数量为准*/
                executePool.execute(new TimeServerHandler(socket));
            }
        } finally {
            if(serverSocket != null){
                serverSocket.close();
            }
            if(socket != null){
                socket.close();
            }
        }

    }


    @Slf4j
   static class TimeServerHandler implements Runnable{
       private Socket socket;

       public TimeServerHandler(Socket socket) {
           this.socket = socket;
       }

       @Override
       public void run() {

           BufferedReader in = null;
           PrintWriter pw = null;
           try {
               log.info("bulid connect success");
               in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
               StringBuffer sb = new StringBuffer();
               String str;
               while ((str = in.readLine()) != null){
                   sb.append(str);
                  log.info("recevice msg :{}",sb.toString());

                   pw = new PrintWriter(socket.getOutputStream(),true);
                   log.info("response time start ...");
                   pw.println("The Time is :"+ System.currentTimeMillis());
               }

           } catch (IOException e) {
               e.printStackTrace();
           } finally {
               if(in != null){
                   try {
                       in.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
               if(pw != null){
                   pw.close();
               }

               if(socket != null){
                   try {
                       socket.close();
                   } catch (IOException e) {
                       e.printStackTrace();
                   }
               }
           }

       }
   }


   static class TimeServerHandlerExecutePool{
        private ExecutorService executor;


       public TimeServerHandlerExecutePool(int maxPoolSize,int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),maxPoolSize,120L, TimeUnit.SECONDS,
                                            new ArrayBlockingQueue<Runnable>(queueSize));
       }

        public void execute(Runnable task){
           executor.execute(task);
        }

   }
}
