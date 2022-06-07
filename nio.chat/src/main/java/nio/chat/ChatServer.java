package nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

import java.util.Iterator;

public class ChatServer {
	private static final int PORT = 9091, BUFFER_SIZE = 256;
	
	protected Selector selector;
	protected ServerSocketChannel serverChannel;
	protected ByteBuffer buffer;
	
	public ChatServer() {
		try {
			selector = Selector.open();
			
			serverChannel = ServerSocketChannel.open();
			
			// binds channel socket to local address
			serverChannel.socket().bind(new InetSocketAddress(PORT));
			
			// blocking must be false before registering with selector
			serverChannel.configureBlocking(false);
		
			// register server with selector
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			
		} catch (IOException e) {
			System.out.println("Can't initilize server");
			e.printStackTrace();
		}
		
		buffer = ByteBuffer.allocate(BUFFER_SIZE);	
	}
	
	public void start() throws IOException {
		while (true) {
			selector.select();
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();
			
			SelectionKey key = iter.next();
			
			if(key.isAcceptable()) {
				registerServer();
			}
			else
				if(key.isReadable()) {
					readMessage(key);
				}
				else 
					if(key.isWritable()) {
						
					}			
				
			iter.remove();
		}
	}
	
	protected void readMessage(SelectionKey key) throws IOException {
		SocketChannel clientChannel = (SocketChannel) key.channel();
		clientChannel.read(buffer);
	}
	
	/**
	 * Registers clientChannel for reading and writing
	 * @throws IOException
	 */
	protected void registerServer() throws IOException {
		SocketChannel clientChannel = serverChannel.accept();
		clientChannel.configureBlocking(false);
		clientChannel.register(selector, SelectionKey.OP_READ);
		clientChannel.register(selector, SelectionKey.OP_WRITE);
	}

}
