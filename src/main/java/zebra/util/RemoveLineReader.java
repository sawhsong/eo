package zebra.util;

import java.io.FilterReader;
import java.io.IOException;
import java.io.Reader;

public class RemoveLineReader extends FilterReader {
	public RemoveLineReader(Reader in) {
		super(in);
	}

	public int read(char[] buf, int from, int len) throws IOException {
		int numchars = in.read(buf, from, len);

		if (numchars == -1) {return -1;}

		for (int i=from; i<from + len; i++) {
			if (buf[i] == 13 || buf[i] == 10)
				buf[i] = ' ';
		}
		return numchars;
	}

	public int read() throws IOException {
		int c = in.read();
		if (c == 13 || c == 10) {return ' ';}
		return c;
	}
}