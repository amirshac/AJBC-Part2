package ajbc.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class AsyncFileDemo {

	public static void run() throws IOException {
		Path path = Paths.get("data", "shakespeare.txt");
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate((int)fileChannel.size());
		
		fileChannel.read(buffer, 0);

		fileChannel.close();

	}
}
