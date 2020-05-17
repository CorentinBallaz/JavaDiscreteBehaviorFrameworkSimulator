package timer;

public class OneShotTimer  implements Timer {
	
	private Integer at;
	private boolean hasNext;
	
	/**
	 * Constructor class OneShotTimer
	 * @param at
	 */
	public OneShotTimer(int at) {
		this.at = at;
		this.hasNext = true;
	}
	public Integer getAt() {
		return this.at;
	}

	/**
	 *@return true if method {@link #next()} hasn't been called, else false
	 */
	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	/**
	 *Returns the value of the "at" attribute of the Object OneShotTimer, then set the at attribute to null and hasNext to false
	 *@return next
	 */
	@Override
	public Integer next() {
		Integer next=this.at;
		this.at=null;
		this.hasNext = false;
		return next;
	}

}
