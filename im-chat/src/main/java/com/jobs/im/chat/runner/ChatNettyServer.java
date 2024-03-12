package com.jobs.im.chat.runner;

import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.jobs.im.chat.handler.PermissionWebSocketHandler;
import com.jobs.im.chat.handler.WebSocketHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @program: im
 * @ClassName: ChatNettyServer
 * @description:
 * @author: Author
 * @create: 2024-02-26 21:30
 * @Version 1.0
 **/
@Order(1)
@Component
public class ChatNettyServer implements ApplicationRunner, DisposableBean {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    private static int cpuNums = Runtime.getRuntime().availableProcessors();

    private int bossCount = 2;

    private int workerCount = cpuNums * 4;

    @Value("${chat.port:28080}")
    private int port;

    @Autowired
    private PermissionWebSocketHandler permissionWebSocketHandler;

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("*********************ChatNettyServer start!*********************");
        ThreadFactory serverBoss = new ThreadFactoryBuilder().setNameFormat("server boss-%d").build();
        ThreadFactory serverWork = new ThreadFactoryBuilder().setNameFormat("server work-%d").build();
        EventLoopGroup bossGroup = new NioEventLoopGroup(bossCount, serverBoss);
        EventLoopGroup serverGroup = new NioEventLoopGroup(workerCount, serverWork);
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, serverGroup).channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    // 添加http编码解码器
                    pipeline.addLast(new HttpServerCodec())
                        // 支持大数据流
                        .addLast(new ChunkedWriteHandler())
                        // 对http消息做聚合操作.FullHttpRequest,FullHttpResponse
                        .addLast(new HttpObjectAggregator(1024 * 64)).addLast()
                        // websocket
                        .addLast(new WebSocketServerProtocolHandler("/"))
                        // 登录校验
                        .addLast(permissionWebSocketHandler)
                        // 自定义
                        .addLast(new WebSocketHandler());
                }
            });
        ChannelFuture future = serverBootstrap.bind(port);
    }
}
