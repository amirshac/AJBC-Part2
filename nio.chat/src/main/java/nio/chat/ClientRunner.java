package nio.chat;

import java.io.IOException;

public class ClientRunner {

	public static void main(String[] args) throws IOException {
		ChatClient client = new ChatClient();
		client.start();
	}

}
