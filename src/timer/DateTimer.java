package timer;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;

/**
 * @author acochet
 *
 */
public class DateTimer  implements Timer {
	
	Vector<Integer> lapsTimes;
	Iterator<Integer> it;
	
	/**
	 * Constructor of the DateTimer class
	 * @param dates
	 */
	public DateTimer(TreeSet<Integer> dates) {
		this.lapsTimes = new Vector<Integer>();
		Integer last;
		Integer current=0;
		
		Iterator<Integer> itr = dates.iterator();
		while (itr.hasNext()) {
			last = current;
			current = itr.next();
			this.lapsTimes.add(current-last);
		}
		this.it = this.lapsTimes.iterator();

	}
	
	/**
	 * Constructor of the DateTimer class
	 * @param lapsTimes
	 */
	public DateTimer(Vector<Integer> lapsTimes) {
		this.lapsTimes = new Vector<Integer>(lapsTimes);
		this.it = this.lapsTimes.iterator();
	}

	/**
	 *@return returned values from the Integer<Iterator>'s hasNext()
	 */
	@Override
	public boolean hasNext() {
		return it.hasNext();
	}

	/**
	 * @return returned values from the Integer<Iterator>'s next()
	 */
	@Override
	public Integer next() {
		return it.next();
	}

}
