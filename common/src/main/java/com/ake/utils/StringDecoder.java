package com.ake.utils;

import java.util.Iterator;
import java.util.Map.Entry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.LastHttpContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringDecoder extends ChannelInboundHandlerAdapter{

	private static Logger logger = LoggerFactory.getLogger(StringDecoder.class);
	private ByteBufToBytes reader;
	private static int requestCount = 0;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		logger.info("StringDecoder.channelRead");
		
		if (msg instanceof HttpRequest) {
			logger.info("msg is HttpRequest, requestCount=" + requestCount++);
			//这边进行处理的是 url字符串
			HttpRequest request = (HttpRequest)msg;
			System.out.println(request.uri()+"\n"+request.method()+"\n"+request.protocolVersion()+"\n---------headers----------");
			Iterator<Entry<String, String>> headerItor = request.headers().iteratorAsString();
			while(headerItor.hasNext()){
				Entry<String, String> header = headerItor.next();
				System.out.println(header.getKey()+":"+header.getValue());
			}
		}
		
		if (msg instanceof HttpContent) {
			logger.info("msg is httpContent, requestCount=" + requestCount++);
			//这边处理的是body
			HttpContent httpContent = (HttpContent) msg;
			String content = ByteBufUtils.getString(httpContent.content());
			System.out.println("httpContent: "+content);
		}
		
		if (msg instanceof LastHttpContent) {
			logger.info("here is the last http content, requestCount=" + requestCount++);
			//传递给下一个handler 处理
			ctx.fireChannelRead(msg);
		}
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	}
}
