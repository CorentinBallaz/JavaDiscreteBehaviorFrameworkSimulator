package action;

import java.lang.reflect.Method;

import java.util.logging.Level;
import java.util.logging.Logger;

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

public class DiscreteAction implements DiscreteActionInterface {

	private Object object; // object

	private Method method; // object method

	private Timer timmer; // timmer

	private Integer lapsTime; // lapsTime, taken from timmer

	private Logger logger; // logger


	private DiscreteAction() {
			this.logger = Logger.getLogger("DAS");
			this.logger.setLevel(Level.ALL);
			this.logger.setUseParentHandlers(true);
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
			this.method = o.getClass().getDeclaredMethod(m);
		}
		catch(Exception e){
			this.logger.log(Level.SEVERE, "Uncaught exception", e);
		}
		this.timmer = timmer;
	}
	
	/**
	 * @param t 
	 * decrease the action lapsTime by t (consumed time) if lapsTime isn't null
	 */
	public void spendTime(int t) {
		Integer old = this.lapsTime;
		if(this.lapsTime != null) {
			this.lapsTime -= t;
		}
		this.logger.log(Level.FINE, "[DA] operate spendTime on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
	}

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

	/**
	 * Compare two discrete actions lapsTime
	 * @param c , an object from a class implementing DiscreteActionInterface
	 * @return 1, -1 or 0
	 */
	public int compareTo(DiscreteActionInterface c) {
		if (this.lapsTime == null) {
			return 1;
		}
		if (c.getCurrentLapsTime() == null) {
			return -1;
		}
    	if(this.lapsTime > c.getCurrentLapsTime()){
    		return 1;
    	}
    	if(this.lapsTime < c.getCurrentLapsTime()){
    		return -1;
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
	 * Check if timmer isn't null
	 * @return this (the action)
	 */
	public DiscreteActionInterface next() {
		Integer old = this.lapsTime;
		if (this.timmer != null) {
			this.lapsTime = this.timmer.next();
		}
		this.logger.log(Level.FINE, "[DA] operate next on  " + this.getObject().getClass().getName() + ":" + this.getObject().hashCode() + ": old time " + old + " new time " + this.getCurrentLapsTime());
		return this;
	}
	/**
	 * Return true if the action timmer still has elements
	 * @return boolean
	 */
	public boolean hasNext() {
		Boolean more=false;
		if (this.timmer != null && this.timmer.hasNext()) {
			more = true;
		}
		return more;		
	}
	

}
