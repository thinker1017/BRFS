package com.bonree.brfs.disknode.server.netty;

import com.bonree.brfs.disknode.DiskWriterManager;
import com.bonree.brfs.disknode.server.DiskOperation;
import com.bonree.brfs.disknode.server.handler.impl.CloseMessageHandler;
import com.bonree.brfs.disknode.server.handler.impl.DeleteMessageHandler;
import com.bonree.brfs.disknode.server.handler.impl.DiskNettyHttpRequestHandler;
import com.bonree.brfs.disknode.server.handler.impl.OpenMessageHandler;
import com.bonree.brfs.disknode.server.handler.impl.ReadMessageHandler;
import com.bonree.brfs.disknode.server.handler.impl.WriteMessageHandler;

public class Test {

	public static void main(String[] args) throws Exception {
		int port = 8899;
		if(args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		
		NettyHttpServer s = new NettyHttpServer(port);
		
		DiskWriterManager nodeManager = new DiskWriterManager();
		nodeManager.start();
		DiskNettyHttpRequestHandler diskHttpRequestHandler = new DiskNettyHttpRequestHandler();
		diskHttpRequestHandler.put(DiskOperation.OP_OPEN, new OpenMessageHandler(nodeManager));
		diskHttpRequestHandler.put(DiskOperation.OP_WRITE, new WriteMessageHandler(nodeManager));
		diskHttpRequestHandler.put(DiskOperation.OP_READ, new ReadMessageHandler());
		diskHttpRequestHandler.put(DiskOperation.OP_CLOSE, new CloseMessageHandler(nodeManager));
		diskHttpRequestHandler.put(DiskOperation.OP_DELETE, new DeleteMessageHandler(nodeManager));
		s.addContextHandler(new NettyHttpContextHandler("/disk", diskHttpRequestHandler));
		
		s.start();
	}
}
