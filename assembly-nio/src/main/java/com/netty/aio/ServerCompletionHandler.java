package com.netty.aio;


import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;

/**
 * Created by Admin on 2019/4/9.
 */
public class ServerCompletionHandler implements CompletionHandler<AsynchronousSocketChannel,Server> {
    @Override
    public void completed(AsynchronousSocketChannel result, Server attachment) {
            attachment.asynchronousServerSocketChannel.accept(attachment,this);
            read(result);
    }

    private void read(AsynchronousSocketChannel asc) {
        //读取数据
        		ByteBuffer buf = ByteBuffer.allocate(1024);
        		asc.read(buf, buf, new CompletionHandler<Integer, ByteBuffer>() {
        			@Override
        			public void completed(Integer resultSize, ByteBuffer attachment) {
        				//进行读取之后,重置标识位
        				attachment.flip();
        				//获得读取的字节数
        				System.out.println("Server -> " + "收到客户端的数据长度为:" + resultSize);
        				//获取读取的数据
        				String resultData = new String(attachment.array()).trim();
        				System.out.println("Server -> " + "收到客户端的数据信息为:" + resultData);
        				String response = "服务器响应, 收到了客户端发来的数据: " + resultData;
        				write(asc, response);
        			}
        			@Override
        			public void failed(Throwable exc, ByteBuffer attachment) {
        				exc.printStackTrace();
        			}
        		});
    }

    private void write(AsynchronousSocketChannel ack,String response){
        byte[] bytes = response.getBytes();
        ByteBuffer b = ByteBuffer.allocate(bytes.length);
        b.put(bytes);
        b.flip();
        try {
            ack.write(b).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void failed(Throwable exc, Server attachment) {
        exc.printStackTrace();
    }
}
