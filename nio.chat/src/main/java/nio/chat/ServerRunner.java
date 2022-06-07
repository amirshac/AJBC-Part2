package nio.chat;

import java.io.IOException;

public class ServerRunner {
	public static void main(String[] args) throws IOException {
		ChatServer chatServer = new ChatServer();
		chatServer.start();
	}
}
