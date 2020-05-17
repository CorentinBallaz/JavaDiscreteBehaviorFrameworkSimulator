package timer;

public class PeriodicTimer implements Timer {

	private int period;
	private int next;
	private RandomTimer moreOrLess = null;
	
	/**
	 * Constructor class PeriodicTimer
	 * @param at
	 */
	public PeriodicTimer(int at) {
		this.period = at;
		this.next = at;
	}
	
	
	/**
	 * Constructor class PeriodicTimer
	 * @param period
	 * @param at
	 */
	public PeriodicTimer(int period, int at) {
		this.period = period;
		this.next = at;
	}
	
	/**
	 *@return period
	 */
	public int getPeriod() {
		return this.period;
	}
	
	
	/**
	 *@return Next value
	 */
	@Override
	public Integer next() {
		
		int next =  this.next;
		
		this.next += this.period;
		
		return next;
	}
	

	/**
	 *@return true
	 */
	@Override
	public boolean hasNext() {
		return true;
	}

}
