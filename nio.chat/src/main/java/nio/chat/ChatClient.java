package nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;


public class ChatClient {
	private static final String STOP = "STOP", SERVERNAME = "localhost";
	private static final int PORT = 9091, BUFFER_SIZE = 256;
	
	protected Scanner scanner;
	protected Selector selector;
	protected SocketChannel clientSocket;
	protected ByteBuffer buffer;
	
	public ChatClient() {
		try {
			scanner = new Scanner(System.in);
			selector = Selector.open();
			clientSocket = SocketChannel.open();
			clientSocket.connect(new InetSocketAddress(SERVERNAME, PORT));
			clientSocket.configureBlocking(false);
			
			buffer = ByteBuffer.allocate(BUFFER_SIZE);

		} catch (IOException e) {
			System.out.println("Can't initialize client socket");
			e.printStackTrace();
		}
	}
	
	public void start() throws IOException {
		
		clientSocket.register(selector, SelectionKey.OP_WRITE);
		
		mainLoop:
		while (true) {
			selector.selectNow();
			Set<SelectionKey> selectedKeys = selector.selectedKeys();
			Iterator<SelectionKey> iter = selectedKeys.iterator();
			
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				
				if (key.isWritable()) {
					System.out.println("Enter text:");
					String message = scanner.nextLine();
					
					if (message.equals(STOP))
						break mainLoop;
					
					write(message, key);
				}
				else if (key.isReadable()) {
					read(key);
				}
				
				iter.remove();
			}
		} // mainloop
		
		scanner.close();
		clientSocket.close();
		selector.close();
		
	} // <void start>
	
	protected void write(String message, SelectionKey key) throws IOException {
		//System.out.println("client writing buffer");
		SocketChannel client = (SocketChannel) key.channel();
		buffer.clear();
		buffer.put(message.getBytes());
		buffer.flip();
		client.write(buffer);
		buffer.clear();
		
		client.register(key.selector(), SelectionKey.OP_READ);
	}
	
	protected void read(SelectionKey key) throws IOException {
		//System.out.println("client reading buffer");
		SocketChannel client = (SocketChannel) key.channel();
		client.read(buffer);
		buffer.flip();
		String message = "";
		
		while (buffer.hasRemaining())
			message += (char)buffer.get();
		
		buffer.clear();
		System.out.println("Message> " + message.trim());
		
		client.register(key.selector(), SelectionKey.OP_WRITE);
	}
}
