package timer;

public class PeriodicTimer implements Timer {

	private int period;
	private int next;

	
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
		
		int ret =  this.next;
		
		this.next += this.period;
		
		return ret;
	}
	

	/**
	 *@return true
	 */
	@Override
	public boolean hasNext() {
		return true;
	}

}
