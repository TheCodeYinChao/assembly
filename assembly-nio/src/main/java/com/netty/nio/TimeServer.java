package com.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.server.ServerCloneException;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Admin on 2019/4/8.
 */
public class TimeServer {

    public static void main(String[] args) {
        new Thread(new MultiplexerTimeServer()).start();
    }

    @Slf4j
    static class MultiplexerTimeServer implements Runnable{
        private Selector selector;
        private ServerSocketChannel serverSocketChannel;
        private volatile boolean flag = false;

        public MultiplexerTimeServer() {
            try {
                serverSocketChannel = ServerSocketChannel.open();
                selector = Selector.open();
                serverSocketChannel.configureBlocking(false);
                /*1024 最大的连接channel数量*/
                serverSocketChannel.socket().bind(new InetSocketAddress(81),1024);
                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                log.info("nio init start...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (!flag){
                try {
                    selector.select(1000);
                    Set<SelectionKey> keys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = keys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey next = iterator.next();
                        iterator.remove();
                        try {
                            handlerKey(next);
                        } catch (Exception e) {
                            e.printStackTrace();
                            if(next != null){
                                next.cancel();
                                next.channel().close();
                            }
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    log.info(e.getMessage(),e);
                }
            }

            if(selector != null){
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        /**
         * 处理轮训出来的key
         * @param next
         * @throws Exception
         */
        private void handlerKey(SelectionKey next) throws Exception{
            if(next.isValid()){
                if(next.isAcceptable()){
                    ServerSocketChannel ss = (ServerSocketChannel) next.channel();
                    SocketChannel accept = ss.accept();
                    accept.configureBlocking(false);
                    accept.register(selector,SelectionKey.OP_READ);
                }
                if(next.isReadable()){
                    SocketChannel sc = (SocketChannel)next.channel();
                    ByteBuffer b = ByteBuffer.allocate(1024);
                    int read = sc.read(b);
                    if(read > 0){
                        b.flip();
                        byte[] bytes = new byte[b.remaining()];
                        b.get(bytes);
                        String body = new String(bytes,"UTF-8");
                        log.info("recevice msg :{}",body);
                        doWrite(sc);
                        log.info("send over！");


                    }
                    else if(read < 0){
                        next.cancel();
                        next.channel().close();
                    }
                }
            }else{
                throw  new Exception("轮训就绪的key无效");
            }
        }

        /**
         * 组织响应数据
         * @param sc
         * @throws Exception
         */
        private void doWrite(SocketChannel sc) throws Exception{
            String resp = "current time : " + System.currentTimeMillis();

            byte[] bytes = resp.getBytes();
            ByteBuffer b = ByteBuffer.allocate(bytes.length);
            b.put(bytes);
            b.flip();
            sc.write(b);
        }
    }
}
