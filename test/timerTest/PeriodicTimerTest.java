package timerTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import timer.DateTimer;
import timer.MergedTimer;
import timer.OneShotTimer;
import timer.PeriodicTimer;

class PeriodicTimerTest {
	
	private static PeriodicTimer pt1;
	private static PeriodicTimer pt2;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pt1 = new PeriodicTimer(10);
		pt2 = new PeriodicTimer(10, 5);
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}
	
	@Test
	void getPeriodTest() {
		int expected = 10;
		int actual = pt1.getPeriod();
		
		assertEquals(expected, actual);
		
		
		actual = pt2.getPeriod();
		
		assertEquals(expected, actual);
	}

	@Test
	void hasNextTest() {
		
		boolean actual = pt1.hasNext();
		
		assertTrue(actual);
	}
	
	@Test
	void nextWithOneParamTest() {
		
		Integer actual = pt1.next();
		Integer expected = 10;
		
		assertEquals(expected, actual);
		
		actual = pt1.next();
		expected = 20;
		
		assertEquals(expected, actual);
		
	}
	
	@Test
	void nextWithTwoParamTest() {
		
		Integer actual1 = pt2.next();
		Integer expected1 = 5;
		
		Integer actual2 = pt2.next();
		Integer expected2 = 15;
		
		Integer actual3 = pt2.next();
		Integer expected3 = 25;
		
		assertEquals(expected1, actual1);
		assertEquals(expected2, actual2);
		assertEquals(expected3, actual3);
		
		
	}

}
