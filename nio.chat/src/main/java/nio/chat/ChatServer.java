package nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServer {
	private static final int PORT = 9091, BUFFER_SIZE = 256;
	
	protected Selector selector;
	protected ServerSocketChannel serverChannel;
	protected ByteBuffer buffer;
	
	protected List<SocketChannel> connectedClients;
	
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
		connectedClients = new ArrayList<SocketChannel>();
	}
	
	public void start() throws IOException {
		System.out.println("Chat server listening on port " + PORT);
		
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
				
			iter.remove();
		}
	}
	
	protected void readMessage(SelectionKey key) throws IOException {
		SocketChannel client = (SocketChannel) key.channel();
		client.read(buffer);
		System.out.println("client sent> " + buffer);
		sendMessage(key);
		
	}
	
	protected void sendMessage(SelectionKey key) throws IOException {
		System.out.println("server sending buffer");
		SocketChannel client = (SocketChannel) key.channel();
		buffer.flip();
		
		connectedClients.forEach(c->{
			try {
				c.write(buffer);
				buffer.rewind();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		});
	//	client.write(buffer);
	//	buffer.rewind();
	//	client.write(buffer);
		buffer.clear();
	}
	/**
	 * Registers clientChannel for reading and writing
	 * @throws IOException
	 */
	protected void registerServer() throws IOException {
		SocketChannel client = serverChannel.accept();
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_READ);
		
		connectedClients.add(client);
		System.out.println("connected clients:" + connectedClients.size());
	}

}
