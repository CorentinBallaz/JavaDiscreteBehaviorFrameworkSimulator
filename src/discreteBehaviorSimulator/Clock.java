package discreteBehaviorSimulator;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Allows to instantiate a general clock, allowing to manage the execution of the different "Action" or "ClockObserver" elements. 
 */
public class Clock {
	private static Clock instance = null;
	
	private int time;
	private int nextJump;
	private ReentrantReadWriteLock lock;
	private boolean virtual;
	
	
	private Set<ClockObserver> observers;
	
	/**
	 * Constructor 
	 */
	private Clock() {
		this.time = 0;
		this.nextJump=0;
		this.lock = new ReentrantReadWriteLock();
		this.virtual = true;
		this.observers = new HashSet<ClockObserver>();
	}
	
	/**
	 * 
	 * @return Clock an instance of its class
	 */
	public static Clock getInstance() {
		if (Clock.instance == null) {
			Clock.instance = new Clock();
		}
		return Clock.instance;
	}
	
	/**
	 * Adds an observer to the observer list
	 * @param o which is an observer, @see ClockObserver
	 */
	public void addObserver(ClockObserver o) {
		this.observers.add(o);
	}

	/**
	 * Removes an observer to the observer list
	 * @param o which is an observer, @see ClockObserver
	 */
	public void removeObserver(ClockObserver o) {
		this.observers.remove(o);
	}
	
	/**
	 * 
	 * @param virtual 
	 */
	public void setVirtual(boolean virtual) {
		this.virtual = virtual;
	}

	/**
	 * 
	 * @return this.virtual 
	 */
	public boolean isVirtual() {
		return this.virtual;
	}
	

	/**
	 * 
	 * @param nextJump the next time jump for all observers
	 */
	public void setNextJump(int nextJump) {
		this.nextJump = nextJump;
		for(ClockObserver o:this.observers) {
			o.nextClockChange(this.nextJump);
		}
	}
	/*public void setTime(int time) throws IllegalAccessException {
		this.lock.lock();
		if (this.time < time) {
			this.time = time;
			for(ClockObserver o:this.observers) {
				o.ClockChange();
			}
		}else{
			this.lock.unlock();
			throw new IllegalAccessException("Set time error, cannot go back to the past !!!");
		}
		this.lock.unlock();
	}*/

	/**
	 * Increments the time if the parameter and the time of the next jump match.
	 * @param time 
	 * @throws Exception if the simulation time does not correspond to the next defined jump time
	 */
	public void increase(int time) throws Exception {

		this.lockWriteAccess();

		if(time != this.nextJump) {
			throw new Exception("Unexpected time change");
		}
		this.time += time;
		for(ClockObserver o:this.observers) {
			o.clockChange(this.time);
		}
		this.unlockWriteAccess();
	}

	/**
	 * 
	 * @return time of the simulation
	 */
	public long getTime() {
		if(this.virtual) {
			return this.time;
		}else {
			return new Date().getTime();
		}
	}
	
	/**
	 * Blocks read access rights
	 */
	public void lockReadAccess() {
		this.lock.readLock().lock();
	}
	
	/**
	 * Unblocks read access rights
	 */
	public void unlockReadAccess() {
		this.lock.readLock().unlock();
	}
	
	/**
	 * Blocks write access rights
	 */
	public void lockWriteAccess() {
		this.lock.writeLock().lock();
	}

	/**
	 * Unblocks write access rights
	 */
	public void unlockWriteAccess() {
		this.lock.writeLock().unlock();		
	}
	
	/**
	 * @return character string containing the simulation time
	 */
	public String toString() {
		return ""+this.time;
	}
}
