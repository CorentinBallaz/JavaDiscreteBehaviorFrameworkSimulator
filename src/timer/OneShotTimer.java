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

	/**
	 *@return bool hasNext
	 */
	@Override
	public boolean hasNext() {
		return this.hasNext;
	}

	/**
	 *Returns the value of the at attribute of the Object OneShotTimer, and disables it
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
