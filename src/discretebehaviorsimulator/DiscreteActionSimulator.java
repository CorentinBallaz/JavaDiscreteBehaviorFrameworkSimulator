
package discretebehaviorsimulator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import action.DiscreteAction;
import action.DiscreteActionInterface;


/**
 * Allows to launch the simulator in the form of a Thread
 * @author Tiphaine Bulou (2016)
 * @author Flavien Vernier
 *
 */
public class DiscreteActionSimulator implements Runnable {


	private Thread t;
	private boolean running = false;
	
	private Clock globalTime;
	
	private ArrayList<DiscreteActionInterface> actionsList = new ArrayList<>();
	
	private int nbLoop;
	private int step;
	
	private Logger logger;
	private FileHandler logFile; 
	private ConsoleHandler logConsole; 


	/**
	 * Constructor
	 */
	public DiscreteActionSimulator(){
		
		// Start logger
		this.logger = Logger.getLogger("DAS");

		this.logger.setLevel(Level.ALL);
		this.logger.setUseParentHandlers(true);
		try{
			this.logFile = new FileHandler(this.getClass().getName() + ".log");

			this.logFile.setFormatter(new LogFormatter());
			this.logConsole = new ConsoleHandler();
		}catch(Exception e){
			this.logger.log(Level.WARNING,"Log file error", e);
		
		}
		this.logger.addHandler(logFile);
		this.logger.addHandler(logConsole);
		

		this.globalTime = Clock.getInstance();
		
		this.t = new Thread(this);
		this.t.setName("Discrete Action Simulator");
	}
	
	/**
	 * Defines the number of loops for the simulation
	 * @param nbLoop the number of loops for the simulation, the simulation is infinite if nbLoop is negative or 0.
	 */
	public void setNbLoop(int nbLoop){
		if(nbLoop>0){
			this.nbLoop = nbLoop;
			this.step = 1;
		}else{ // running infinitely
			this.nbLoop = 0;
			this.step = -1;
		}
	}


	/**
	 * Adds an action to the action list
	 * @param c an action, @see DiscreteActionInterface
	 * @return
	 */
	public void addAction(DiscreteActionInterface c){

		if(c.hasNext()) {
			// add to list of actions, next is call to the action exist at the first time
			this.actionsList.add(c.next());

			// sort the list for ordered execution 
			Collections.sort(this.actionsList);
		}
	}
	


	/**
	 * @return the laps time before the next action
	 */
	private int nextLapsTime() {
		DiscreteActionInterface currentAction = this.actionsList.get(0);
		return currentAction.getCurrentLapsTime();
	}
	/**
	 * @return laps time of the running action
	 * @throws Exception if the calling thread cannot run on the current processor
	 */
	private int runAction(){
		// Run the first action
		int sleepTime = 0;



		DiscreteActionInterface currentAction = this.actionsList.get(0);
		Object o = currentAction.getObject();
		Method m = currentAction.getMethod();
		sleepTime = currentAction.getCurrentLapsTime();
		
		try {
			//Thread.sleep(sleepTime); // Real time can be very slow
			Thread.yield();
			//Thread.sleep(1000); // Wait message bus sends
			if(this.globalTime!=null) {
				this.globalTime.increase(sleepTime);
			}
			m.invoke(o);
			this.logRunAction(m, o, sleepTime);
			
		}catch (Exception e) {
			this.logger.log(Level.WARNING,"Thread isn't yielded",e);
		}

		return sleepTime;
	}
	
	/***
	 * This method is called in the method runAction method, useful to write statement to the log file
	 * @param m
	 * @param o
	 * @param sleepTime
	 */
	private void logRunAction(Method m, Object o, int sleepTime) {
		String tologger = "[DAS] run action " + m.getName() + " on " + o.getClass().getName() + ":"+o.hashCode();
		if (this.globalTime!=null){
			tologger += " at " + this.globalTime.getTime() ;
		
		}
		tologger+=" after " + sleepTime + " time units\n";
		
		this.logger.log(Level.FINE, tologger);
	}
	


	/**
	 * Allows you to update all the time lapses of each action according to the execution time of the current action. 
	 * Also allows, in case the action executes several timers, to execute the next one. 
	 * @param runningTimeOf1stCapsul duration of the first encapsulation
	 * @return
	 */
	private void updateTimes(int runningTimeOf1stCapsul){
		
		// update time laps off all actions
		for(int i=1 ; i < this.actionsList.size(); i++){
			this.actionsList.get(i).spendTime(runningTimeOf1stCapsul);
		}

	

		DiscreteActionInterface a = this.actionsList.remove(0);
		if(a.hasNext()) {
			a = a.next();
			this.actionsList.add(a);
			this.loggedUpdatesTimes(a);
			
			Collections.sort(this.actionsList);
		}
	}
	
	/**
	 * This method is called in the method updateTimes method, useful to write statement to the log file
	 * @param a
	 */
	public void loggedUpdatesTimes(DiscreteActionInterface a) {
		String toLogger = "[DAS] reset action " + a.getMethod().getName() + " on " + a.getObject().getClass().getName() + ":" + a.getObject().hashCode();
		if (this.globalTime!=null) {
			toLogger +=" at " + this.globalTime.getTime();
			
		}
		toLogger +=" to " + a.getCurrentLapsTime() + " time units\n";
		
		this.logger.log(Level.FINE,toLogger);
			
	}

	/**
	 * Allows you to start simulator execution
	 * @throws Exception if we can't lift the thread
	 */
	public void run() {
		int count = this.nbLoop;
		boolean finished = false;
		this.logger.log(Level.ALL,"LANCEMENT DU THREAD " + t.getName() + " \n");
		while(running && !finished){

			if(!this.actionsList.isEmpty()){
				this.globalTime.setNextJump(this.nextLapsTime());
				
				this.globalTime.lockWriteAccess();
				int runningTime = this.runAction();
				this.updateTimes(runningTime);
				this.globalTime.unlockWriteAccess();
				try {
					Thread.sleep(100);
				}catch(Exception e) {
				
					this.logger.log(Level.SEVERE,"Thread can't sleep",e);
				}
			}else{
				this.logger.log(Level.ALL, "NOTHING TO DO\n");
				this.stop();
			}

			count -= this.step;
			if(count<=0) {
				finished = true;
			}
		}
		this.running = false;
		if(this.step>0) {
			this.logger.log(Level.ALL,"DAS: " + (this.nbLoop - count) + " actions done for " + this.nbLoop + " turns asked.");
		
		}else {
			this.logger.log(Level.ALL,"DAS: {} actions done!",(count));			
		}
	}

	/**
	 * class that starts the simulation process by calling the run method
	 */
	public void start(){
		this.running = true;
		this.t.start();
	}

	/**
	 * class that stops the simulation process
	 */
	public void stop(){
		
		
		this.logger.log(Level.ALL,String.format("STOP THREAD %s obj %s", t.getName(),this));
		this.running = false;
	}
	
	/**
	 * @return String containing a list of all actions
	 */
	public String toString(){
		StringBuilder toS = new StringBuilder("------------------\nTestAuto :" + this.actionsList.size());
		for(DiscreteActionInterface c : this.actionsList){
			toS.append(c.toString() + "\n");
		}
		toS.append("---------------------\n");
		return toS.toString();
	}

	/**
	 * 
	 * @return boolean which gives an indication of the execution status.
	 */
	public boolean getRunning() {
		return this.running;
	}

}
