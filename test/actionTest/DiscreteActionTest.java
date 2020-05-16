package actionTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import action.DiscreteAction;
import timer.OneShotTimer;

class DiscreteActionTest {

	@Test
	void testSpendTimeNotNullPositiv() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteAction action = new DiscreteAction(test,m,timer);
		
		action.next();
		
		Integer newLapsTime = 3;
		action.spendTime(newLapsTime);

		assertEquals(2, action.getCurrentLapsTime());
		
	}
	
	@Test
	void testSpendTimeNotNullNegativ() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(-8);
		DiscreteAction action = new DiscreteAction(test,m,timer);
		
		action.next();
		
		Integer newLapsTime = 3;
		action.spendTime(newLapsTime);

		assertEquals(-11, action.getCurrentLapsTime());
		
	}
	
	@Test
	void testSpendTimeNull() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteAction action = new DiscreteAction(test,m,timer);
		
		Integer newLapsTime = 3;
		action.spendTime(newLapsTime);

		assertEquals(null, action.getCurrentLapsTime());
		
	}
	
	@Test
	void testCompareToNullNull() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteAction action1 = new DiscreteAction(test,m,timer);
		DiscreteAction action2 = new DiscreteAction(test,m,timer);
		assertEquals(1,action1.compareTo(action2));
	}
	
	@Test
	void testCompareToIntegerNull() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteAction action1 = new DiscreteAction(test,m,timer);
		action1.next();
		DiscreteAction action2 = new DiscreteAction(test,m,timer);
		assertEquals(-1,action1.compareTo(action2));
	}
	
	@Test
	void testCompareToThisLapsTimeHigherThanClapsTime() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer1 = new OneShotTimer(1);
		OneShotTimer timer2 = new OneShotTimer(0);
		DiscreteAction action1 = new DiscreteAction(test,m,timer1);
		action1.next();
		DiscreteAction action2 = new DiscreteAction(test,m,timer2);
		action2.next();
		assertEquals(1,action1.compareTo(action2));
	}
	
	@Test
	void testCompareToThisLapsTimeSmallerThanClapsTime() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer1 = new OneShotTimer(0);
		OneShotTimer timer2 = new OneShotTimer(1);
		DiscreteAction action1 = new DiscreteAction(test,m,timer1);
		action1.next();
		DiscreteAction action2 = new DiscreteAction(test,m,timer2);
		action2.next();
		assertEquals(-1,action1.compareTo(action2));
	}
	
	@Test
	void testCompareToThisLapsTimeEqualClapsTime() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer1 = new OneShotTimer(1);
		OneShotTimer timer2 = new OneShotTimer(1);
		DiscreteAction action1 = new DiscreteAction(test,m,timer1);
		action1.next();
		DiscreteAction action2 = new DiscreteAction(test,m,timer2);
		action2.next();
		assertEquals(0,action1.compareTo(action2));
	}
	
	@Test
	void testHasNextTimmerNull() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		DiscreteAction action = new DiscreteAction(test,m,null);
		assertEquals(false,action.hasNext());
	}
	
	@Test
	void testHasNextOneShotTimerFirstNext() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(1);
		DiscreteAction action = new DiscreteAction(test,m,timer);
		assertEquals(true,action.hasNext());
	}
	
	@Test
	void testHasNextOneShotTimerSecondNext() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(1);
		DiscreteAction action = new DiscreteAction(test,m,timer);
		action.next();
		assertEquals(false,action.hasNext());
	}
	
	@Test
	void testNextWithNullTimer() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		DiscreteAction action = new DiscreteAction(test,m,null);
		action.next();
	}
	
	@Test
	void testNextWithOneShotTimerFirstNext() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(1);
		DiscreteAction action = new DiscreteAction(test,m,timer);
		action.next();
		assertEquals(1,action.getCurrentLapsTime());
	}
	
	@Test
	void testNextWithOneShotTimerSecondNext() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(1);
		DiscreteAction action = new DiscreteAction(test,m,timer);
		action.next();
		action.next();
		assertEquals(null,action.getCurrentLapsTime());
	}
	
	@Test
	void testToString() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(1);
		DiscreteAction action = new DiscreteAction(test,m,timer);
		action.next();
		assertEquals("Object : " + "action.ClasseTest" + "\n Method : " + "methodeTest"+"\n Stat. : "+ timer + "\n delay: " + 1,action.toString());
	}
	
	@Test
	void testConstructorWithNullMethodCrash() {
		ClasseTest test = new ClasseTest();
		OneShotTimer timer = new OneShotTimer(1);
		DiscreteAction action = new DiscreteAction(test,null,timer);
	}
	
	@Test
	void testConstructorWithNullObjectCrash() {
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(1);
		DiscreteAction action = new DiscreteAction(null,m,timer);
	}
	
	@Test
	void testGetMethod() throws NoSuchMethodException, SecurityException {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(1);
		DiscreteAction action = new DiscreteAction(test,m,timer);
		assertEquals(test.getClass().getDeclaredMethod(m, new Class<?>[0]),action.getMethod());
	}
	
}
