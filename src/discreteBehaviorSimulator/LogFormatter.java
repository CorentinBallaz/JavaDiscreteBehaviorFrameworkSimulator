package discreteBehaviorSimulator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Allows the creation of different logs (writing in a .log file, writing in the standard output...)
 * @author Flavien Vernier
 *	
 */
public class LogFormatter  extends Formatter {
	
	/**
	 * Neatly writes a log record
	 * @param rec which is a log record, @see LogRecord
	 * @return string formatting the parameter
	 */
	public String format(LogRecord rec) {
		StringBuffer buf = new StringBuffer();
		
		buf.append(calcDate(rec.getMillis()));
		buf.append(": ");
		buf.append(rec.getLevel());
		buf.append(System.getProperty("line.separator"));
		buf.append(formatMessage(rec));
		buf.append(System.getProperty("line.separator"));
		
		return buf.toString();
	}
	
	/**
	 * Calculates a date from an accumulation of milliseconds
	 * @param millisecs 
	 * @return
	 */
	private String calcDate(long millisecs) {
	    SimpleDateFormat date_format = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss.SS");
	    Date resultdate = new Date(millisecs);
	    return date_format.format(resultdate);
	  }


	  /**
	   * This method is called just after the handler using this formatter is created
	   */
	  public String getHead(Handler h) {
		  return "";
	  }
	  
	  /**
	   * This method is called just after the handler using this formatter is closed
	   */
	  public String getTail(Handler h) {
	    return "";
	  }

}
