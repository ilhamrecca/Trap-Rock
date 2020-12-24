package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class Parser {
	
	public static String loadFileAsString(String path) throws IOException {
//		StringBuilder builder = new StringBuilder();
//		
//		try {
//			
//			BufferedReader br = new BufferedReader(new FileReader(path));
//			String line;
//			while((line = br.readLine()) != null) {
//				builder.append(line+"\n");
//			}
//			br.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		ClassLoader classLoader = ClassLoader.getSystemClassLoader();
	    try (InputStream is = classLoader.getResourceAsStream(path)) {
	        if (is == null) return null;
	        try (InputStreamReader isr = new InputStreamReader(is);
	             BufferedReader reader = new BufferedReader(isr)) {
	            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
	        }
	    }
//		return builder.toString();
	}
	
	public static int parseInt(String number) {
		
		try {
			
			return Integer.parseInt(number);
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
	
	
}
