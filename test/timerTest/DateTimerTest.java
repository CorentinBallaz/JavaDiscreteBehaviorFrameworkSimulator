package timerTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeSet;
import java.util.Vector;

import org.junit.jupiter.api.Test;

import timer.DateTimer;

class DateTimerTest {
	
	
	@Test
	void DateTimerTest() {
		Vector<Integer> lapsTimes = new Vector<>();
		DateTimer dtExpected = new DateTimer(lapsTimes);
		
		TreeSet<Integer> dates = new TreeSet<>();
		DateTimer dtActual = new DateTimer(dates);
		
		assertEquals(dtExpected, dtActual);
	}

	
	@Test
	void hasNextTestPositive() {
		
		Vector<Integer> lapsTimes = new Vector<>();
		lapsTimes.add(1);
		DateTimer dtt = new DateTimer(lapsTimes);

		
		boolean actual = dtt.hasNext();

		assertTrue(actual);
	}
	
	
	@Test
	void hasNextTestNegative() {
		
		Vector<Integer> lapsTimes = new Vector<>();
		DateTimer dtt = new DateTimer(lapsTimes);

		
		boolean actual = dtt.hasNext();

		assertTrue(actual);
	}
	
	
	@Test
	void nextTestPositive() {
		
		Vector<Integer> lapsTimes = new Vector<>();
		lapsTimes.add(1);
		DateTimer dtt = new DateTimer(lapsTimes);

		
		int actual = dtt.it.next();
		int expected = 1; 

		assertEquals(actual, expected);
	}
	
	
	@Test
	void nextTestNegative() {
		
		Vector<Integer> lapsTimes = new Vector<>();
		lapsTimes.add(1);
		DateTimer dtt = new DateTimer(lapsTimes);

		
		int actual = dtt.it.next();
		int expected = 2; 

		assertEquals(actual, expected);
	}
}