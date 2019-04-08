package com.netty.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Admin on 2019/4/8.
 */
public class TimeClient {
    public static void main(String[] args) {
              new Thread(new TimeClinetHandle()).start();
    }

    @Slf4j
    static class TimeClinetHandle implements Runnable{
        private  String ip = "127.0.0.1";
        private  int port = 81;
        private SocketChannel socketChannel;
        private Selector selectorl;
        private volatile  boolean flag = false;

        public TimeClinetHandle() {
            try {
                socketChannel = SocketChannel.open();
                selectorl = Selector.open();
                socketChannel.configureBlocking(false);
                /*判断连接是否成功 不成功则注册连接 成功则注册read 并发送数据 */
                socketChannel.connect(new InetSocketAddress(ip,port));
                socketChannel.register(selectorl, SelectionKey.OP_CONNECT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 向channel 中写入数据
         * @param socketChannel
         * @throws Exception
         */
        private void doWrite(SocketChannel socketChannel) throws Exception{
            String  str = "what time is ?";
            byte[] bytes = str.getBytes();
            ByteBuffer rc = ByteBuffer.allocate(bytes.length);
            rc.put(bytes);
            rc.flip();
            socketChannel.write(rc);
            if(!rc.hasRemaining()){
                log.info("sent data over");
            }
        }

        @Override
        public void run() {
            while (!flag){
                try {
                    selectorl.select(1000);
                    Set<SelectionKey> selectionKeys = selectorl.selectedKeys();
                    Iterator<SelectionKey> iterator = selectionKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey next = iterator.next();
                        iterator.remove();
                        try {
                            handlerInput(next);
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
                }
            }
            if(selectorl != null){
                try {
                    selectorl.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        private void handlerInput(SelectionKey next) throws Exception{
            if(next.isValid()){
                if(next.isConnectable()){
                    SocketChannel sc = (SocketChannel)next.channel();
                    if(sc.finishConnect()){
                        sc.register(selectorl,SelectionKey.OP_READ);
                        doWrite(sc);
                    }
                }
                if(next.isReadable()){
                    SocketChannel sc = (SocketChannel)next.channel();
                    ByteBuffer b = ByteBuffer.allocate(1024);
                    int read = sc.read(b);
                    if(read >0 ){
                        b.flip();
                        byte[] bytes = new byte[b.remaining()];
                        b.get(bytes);
                        String str = new String(bytes,"UTF-8");
                        log.info("recevice msg :{}",str);
                        this.flag = true;
                    }else if(read < 0){
                        next.cancel();
                        next.channel().close();
                    }
                }
            }

        }
    }
}
