package librerie;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LibrerieVarie {
	
	public static String getData(String pattern) {
		
		String tmsStart = LocalDateTime.now().format(DateTimeFormatter.ofPattern(pattern));
		return tmsStart;
	}
}
