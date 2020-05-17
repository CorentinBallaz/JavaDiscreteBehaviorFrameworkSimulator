package timerTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import timer.OneShotTimer;

class OneShotTimerTest {

	@Test
	void hasNextTest() {
		OneShotTimer ost1 = new OneShotTimer(10);
		boolean actual = ost1.hasNext();
		assertTrue(actual);
	}
	
	@Test
	void hasNextTestNegative() {
	 
	  Assertions.assertThrows(IllegalArgumentException.class, () -> {
		  OneShotTimer ost1 = new OneShotTimer(-10);
			boolean actual = ost1.hasNext();
			assertTrue(actual);
	  });
	 
	}
	
	@Test
	void hasNextTestNext() {
		OneShotTimer ost1 = new OneShotTimer(10);
		ost1.next();
		boolean actual = ost1.hasNext();
		assertFalse(actual);
	}
	
	
	@Test
	void NextTest() {
		Integer atparam = 10;
		OneShotTimer ost1 = new OneShotTimer(atparam);
		Integer resNext = ost1.next();
		Integer atNext = ost1.getAt();
		boolean actual = ost1.hasNext();
		assertEquals(atparam, resNext);
		assertNull(atNext);
		assertFalse(actual);
	}
	
	@Test
	void NextTestNegative() {
	 
	  Assertions.assertThrows(IllegalArgumentException.class, () -> {
		  Integer atparam = -10;
			OneShotTimer ost1 = new OneShotTimer(atparam);
			Integer resNext = ost1.next();
			Integer atNext = ost1.getAt();
			boolean actual = ost1.hasNext();
			assertEquals(atparam, resNext);
			assertNull(atNext);
			assertFalse(actual);
	  });

	}
}
