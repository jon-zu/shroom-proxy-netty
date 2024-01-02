package org.example;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.example.proxy.ProxyClientCodec;
import org.example.proxy.ProxyHandshake;
import org.example.proxy.ProxyServerCodec;

import javax.net.ssl.SSLException;
import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.cert.CertificateException;

public class Main {

    private static final int PORT = 8484;

    public static void main(String[] args) throws CertificateException, SSLException, UnknownHostException {
        var certFile = new File("/home/jonas/projects/shroom/shroom_proxy/keys/cert.pem");
        var keyFile = new File("/home/jonas/projects/shroom/shroom_proxy/keys/key.pem");


        var sslContext = SslContextBuilder.forServer(certFile, keyFile).build();


        EventLoopGroup bossGroup = new NioEventLoopGroup(4);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        var handshake = new ProxyHandshake(
                1,
                (short)1,
                InetAddress.getByName("127.0.0.1")
        );

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws UnknownHostException {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(sslContext.newHandler(ch.alloc()));
                            pipeline.addLast(new ProxyServerCodec());
                            pipeline.addLast(new EchoHandler());
                        }
                    });

            // Start the server
            ChannelFuture f = b.bind(PORT).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}