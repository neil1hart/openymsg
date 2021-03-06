package org.openymsg.testing;

import org.openymsg.network.direct.YMSG9InputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;

/**
 * Load an export from Wireshark into a stream used by OpenYmsg. To use select the "Yahoo YMSG Messenger Protocol..."
 * line and right click the menu option "Export Selected Packet Bytes..."
 * @author neilhart
 */
public class FileReader {
	public FileReader(String filename) throws UnsupportedEncodingException, IOException {
		URL url = FileReader.class.getClassLoader().getResource(filename);
		String fullyQualifiedFilename = url.getFile();
		File file = new File(fullyQualifiedFilename);
		FileInputStream stream = new FileInputStream(file);
		YMSG9InputStream ymsg = new YMSG9InputStream(stream);
		System.err.println(ymsg.readPacket());
		ymsg.close();
	}

	public static final void main(String[] args) throws UnsupportedEncodingException, IOException {
		new FileReader("AddBuddyDeclineIn");
	}
}
