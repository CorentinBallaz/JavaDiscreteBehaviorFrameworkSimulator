package timer;

public class TimeBoundedTimer implements Timer {
	
	private Timer timer2bound;
	private Integer startTime;
	private Integer stopTime;
	
	private Integer next=0;
	private int time=0;
	private boolean hasNext;

	/**
	 * Constructor class TimeBoundedTimer
	 * @param timer2bound
	 * @param startTime
	 * @param stopTime
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime, int stopTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = stopTime;
		this.init();
	}

	/**
	 * Constructor class TimeBoundedTimer
	 * @param timer2bound
	 * @param startTime
	 */
	public TimeBoundedTimer(Timer timer2bound, int startTime) {
		this.timer2bound = timer2bound;
		this.startTime = startTime;
		this.stopTime = Integer.MAX_VALUE;
		this.init();
	}
	
	/**
	 * Increment next with {@link #next} until it reaches startTime
	 * Set hasNext to true if next hasn't reach stopTime
	 * Set hasNext to false if it has
	 */
	private void init() {
		this.next = this.timer2bound.next();
		while (this.next < this.startTime) {
			this.next += this.timer2bound.next();
		}
		if(this.next<this.stopTime) {
			this.hasNext = true;
		}else {
			this.hasNext = false;
		}
	}

	/**
	 *@return bool hasNext
	 */
	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	/**
	 *Increment time with next 
	 *Set next with timer2bound.next() if time hasn't reach stopTime 
	 *or null
	 *@return next
	 */
	@Override
	public Integer next() {
		Integer next = this.next;
		this.time+=this.next;
		if(this.time < this.stopTime) {
			this.next = this.timer2bound.next();
		}else {
			next = null;
			this.hasNext=false;
		}
		return next;
	}

}
