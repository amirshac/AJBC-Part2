package ajbc.nio.channels;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.WritableByteChannel;

public class BasicChannel2 {
	
	private static final int BUFFER_SIZE = 30;

	public static void run() throws IOException {
		
		String fileName = "data/nio-data.txt";
		
		// access a file 
		RandomAccessFile file = new RandomAccessFile(fileName, "rw");
		
		// get file channel
		FileChannel fileChannel = file.getChannel();
		
		// create a buffer to hold and manipulate the file's data
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		
		// read text from file channel into buffer
		int bytesRead = 0;
		
		do {
			bytesRead = fileChannel.read(buffer);
			System.out.print("");
		}while(bytesRead!= -1);
	}
	
}
