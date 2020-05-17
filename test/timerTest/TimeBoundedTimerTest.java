package timerTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import junit.framework.Assert;
import timer.OneShotTimer;
import timer.PeriodicTimer;
import timer.TimeBoundedTimer;

class TimeBoundedTimerTest {
	
	OneShotTimer ost = new OneShotTimer(10); 
	PeriodicTimer pt = new PeriodicTimer(10);
	@Test
	void initPtTest() {
		try {
			TimeBoundedTimer tbt = new TimeBoundedTimer(pt,20,30);
			boolean actual = tbt.hasNext();
			assertTrue(actual);
		}catch (Exception e) {
			fail("Exception " + e);
		}
	}
	
	@Test
	void initOstTest() {
		try {
			TimeBoundedTimer tbt = new TimeBoundedTimer(ost,20,30);
			boolean actual = tbt.hasNext();
			assertFalse(actual);
		}catch (Exception e) {
			fail("Exception " + e);
		}
	}
	
	
	@Test
	void inittPtTest_StopT_inf_StartT() {
		try {
			TimeBoundedTimer tbt = new TimeBoundedTimer(pt,30,20);
			boolean actual = tbt.hasNext();
			assertFalse(actual);
		}catch (Exception e) {
			fail("Exception " + e);
		}
	}
	
	@Test
	void nextPtTest_stopT_sup_startT_and_deltaT() {
		try {
			TimeBoundedTimer tbt = new TimeBoundedTimer(pt,20,30);
			tbt.next();
			boolean actual = tbt.hasNext();
			assertTrue(actual);
		}catch (Exception e) {
			fail("Exception " + e);
		}
	}
	
	@Test
	void nextPtTest_stopT_inf_startT_and_deltaT_2turns() {
		try {
			TimeBoundedTimer tbt = new TimeBoundedTimer(pt,20,29);
			tbt.next();
			tbt.next();
			boolean actual = tbt.hasNext();
			assertFalse(actual);
		}catch (Exception e) {
			fail("Exception " + e);
		}
	}
	
	
}
