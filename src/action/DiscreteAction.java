package action;

import java.lang.reflect.Method;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import discreteBehaviorSimulator.LogFormatter;
import timer.Timer;

/**
 * DiscreteAction is the class that allows the creation and the management of a new discrete action
 * A DiscreteAction execute a method from an object each lapsTime given by a timmer
 * This class is characterized by the following informations : 
 * <ul> 
 * <li> object</li>
 * <li>method</li>
 * <li>timer</li>
 * <li>lapstime</li>
 * <li>logger</li>
 * </ul>
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 */

// TODO must inherit from Action
public class DiscreteAction implements DiscreteActionInterface {
	/**
	 * @see Object
	 */
	private Object object;
	/**
	 * @see Method
	 */
	private Method method;
	/**
	 * timer provides new lapstime
	 */
	
	private Timer timmer;				// timer provides new lapsTime
	//private TreeSet<Integer> dates;	// obsolete, managed in timer 
	//private Vector<Integer> lapsTimes;// obsolete, managed in timer
	/**
	 * lapstime is thewaiting time
	 */
	private Integer lapsTime; 			// waiting time (null if never used)
	/**
	 * @see Logger
	 */
	private Logger logger;

	// Constructor
	private DiscreteAction() {
		// Start logger
			this.logger = Logger.getLogger("DAS");
			//this.logger = Logger.getLogger("APP");
			this.logger.setLevel(Level.ALL);
			this.logger.setUseParentHandlers(true);
			
			/*FileHandler logFile; 
			ConsoleHandler logConsole; 
			try{
				this.logFile = new FileHandler(this.getClass().getName() + ".log");
				//this.logFile.setFormatter(new SimpleFormatter());
				this.logFile.setFormatter(new LogFormatter());
				this.logConsole = new ConsoleHandler();
			}catch(Exception e){
				e.printStackTrace();
			}
			this.logger.addHandler(logFile);
			this.logger.addHandler(logConsole);*/
	}
	/**
	 * Construct a discrete action 
	 * @param o
	 * @param m
	 * @param timmer
	 */
	public DiscreteAction(Object o, String m, Timer timmer){
		this();
		this.object = o;
		try{	
			this.method = o.getClass().getDeclaredMethod(m, new Class<?>[0]);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		this.timmer = timmer;
		//this.updateTimeLaps();
	}
	
	// ATTRIBUTION
	/**
	 * @param t 
	 * decrease the action lapsTime by t if it's not null
	 */
	public void spendTime(int t) {
		Integer old = this.lapsTime;
		if(this.lapsTime != null) {
			this.lapsTime -= t;
		}
		this.logger.log(Level.FINE, "[DA] operate spendTime on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
		//System.out.println(         "[DA] operate spendTime on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime() + "\n");
	}

	// RECUPERATION
	/**
	 * @return method
	 */
	public Method getMethod(){
		return method;
	}
	/**
	 * @return lapstime
	 */
	public Integer getCurrentLapsTime(){
		return lapsTime;
	}
	/**
	 * @return object
	 */
	public Object getObject(){
		return object;
	}



	// COMPARAISON
	/**
	 * Compare two discrete actions lapsTime
	 * @param c , the interface
	 * @return 1, -1 or 0
	 */
	public int compareTo(DiscreteActionInterface c) {
		if (this.lapsTime == null) { // no lapstime is equivalent to infinity 
			return 1;
		}
		if (c.getCurrentLapsTime() == null) {// no lapstime is equivalent to infinity 
			return -1;
		}
    	if(this.lapsTime > c.getCurrentLapsTime()){
    		return 1;
    	}
    	if(this.lapsTime < c.getCurrentLapsTime()){
    		return -1;
    	}
		if(this.lapsTime == c.getCurrentLapsTime()){
			return 0;
		}
		return 0;
	}
	/**
	 * Return a string with all the informations of the action
	 * @return string 
	 */
	public String toString(){
		return "Object : " + this.object.getClass().getName() + "\n Method : " + this.method.getName()+"\n Stat. : "+ this.timmer + "\n delay: " + this.lapsTime;

	}
	/**
	 * Set the new lapsTime of the action, taken from timmer
	 * @return the action
	 */
	public DiscreteActionInterface next() {
		Integer old = this.lapsTime;
		this.lapsTime = this.timmer.next();
		this.logger.log(Level.FINE, "[DA] operate next on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
		//System.out.println("[DA] operate 'next' on " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime() + "\n");
		return this;
	}
	/**
	 * Return true if the action timmer still has element (lapsTimes)
	 * @return boolean
	 */
	public boolean hasNext() {
		Boolean more=false;
		if (this.timmer != null && this.timmer.hasNext()) {
			more = true;
		}/*else if (this.dates != null) {
			more = !this.dates.isEmpty();
		}else if (this.lapsTimes != null) {
			more = !this.lapsTimes.isEmpty();
		}*/
		return more;		
	}
	

}
