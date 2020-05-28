package timer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

/**
 * @author Flavien Vernier
 *
 */
public class DateTimer  implements Timer {
	
	ArrayList<Integer> lapsTimes;
	Iterator<Integer> it;
	
	/**
	 * Constructor of the DateTimer class
	 * @param dates
	 */
	public DateTimer(SortedSet<Integer> dates) {
		this.lapsTimes = new ArrayList<>();
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
	public DateTimer(List<Integer> lapsTimes) {
		this.lapsTimes = new ArrayList<>(lapsTimes);
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
