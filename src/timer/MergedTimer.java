package timer;

public class MergedTimer implements Timer{
	
	private Timer timer1;
	private Timer timer2;

	/**
	 * Constructor of MergedTimer
	 * @param timer1
	 * @param timer2
	 */
	public MergedTimer(Timer timer1, Timer timer2) {
		this.timer1 = timer1;
		this.timer2 = timer2;
	}
	
	/**
	 *@return hasNext of each timer 1 & 2
	 */
	@Override
	public boolean hasNext() {
		return (this.timer1.hasNext() && this.timer2.hasNext());
	}

	/**
	 *@return addition of next method's results of timer 1 & 2
	 */
	@Override
	public Integer next() {
		if(this.hasNext()) {
			return this.timer1.next() + this.timer2.next();
		}
		return null;
	}

}
