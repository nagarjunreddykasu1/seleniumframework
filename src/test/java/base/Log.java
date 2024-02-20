package base;

import org.apache.log4j.Logger;

public class Log {
public static Logger log=Logger.getLogger(Log.class.getName());
	
	public static void info(String message){
		log.info(message);
	}
	
	public static void error(String message){
		log.error(message);
	}
	
	public static void getExceptionMessage(Exception e){
		log.error(e);
	}

}
