package timerTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import timer.DateTimer;
import timer.MergedTimer;
import timer.OneShotTimer;
import timer.PeriodicTimer;

class MergedTimerTest {


	@Test
	void hasNextTestTrue() {
		PeriodicTimer pt1 = new PeriodicTimer(0);
		PeriodicTimer pt2 = new PeriodicTimer(0);
		
		MergedTimer mt = new MergedTimer(pt1, pt2);
		
		boolean actual = mt.hasNext();
		
		assertTrue(actual);
	}
	
	@Test
	void hasNextTestFalse1() {
		PeriodicTimer pt1 = new PeriodicTimer(0);
		OneShotTimer ost1 = new OneShotTimer(0);
		ost1.next();
		
		MergedTimer mt = new MergedTimer(pt1, ost1);
		
		boolean actual = mt.hasNext();
		
		assertFalse(actual);
	}
	
	@Test
	void hasNextTestFalse2() {
		OneShotTimer ost1 = new OneShotTimer(0);
		OneShotTimer ost2 = new OneShotTimer(0);
		ost1.next();
		ost2.next();
		
		MergedTimer mt = new MergedTimer(ost1, ost2);
		
		boolean actual = mt.hasNext();
		
		assertFalse(actual);
	}
	
	@Test
	void nextTestnull() {
		OneShotTimer ost1 = new OneShotTimer(0);
		OneShotTimer ost2 = new OneShotTimer(0);
		ost1.next();
		ost2.next();
		
		MergedTimer mt = new MergedTimer(ost1, ost2);
		
		Integer actual = mt.next();
		
		assertNull(actual);
	}
	
	@Test
	void nextTestnull2() {
		OneShotTimer ost1 = new OneShotTimer(0);
		PeriodicTimer pt1 = new PeriodicTimer(0);
		ost1.next();

		
		MergedTimer mt = new MergedTimer(ost1, pt1);
		
		Integer actual = mt.next();
		
		assertNull(actual);
	}
	
	@Test
	void nextTest() {
		OneShotTimer ost1 = new OneShotTimer(0);
		PeriodicTimer pt1 = new PeriodicTimer(0);
		
		
		MergedTimer mt = new MergedTimer(ost1, pt1);
		
		Integer actual = mt.next();
		
		Integer expected = 0;
		
		assertEquals(expected, actual);
	}

}
