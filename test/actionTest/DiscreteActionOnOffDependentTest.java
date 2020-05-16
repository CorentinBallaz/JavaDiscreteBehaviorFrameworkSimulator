package actionTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import action.DiscreteAction;
import action.DiscreteActionOnOffDependent;
import timer.OneShotTimer;

class DiscreteActionOnOffDependentTest {

	@Test
	void testConstructorWithTwoActions() {
		ClasseTest test = new ClasseTest();
		String methodeOn = "methodeTest";
		OneShotTimer timerOn = new OneShotTimer(5);
		
		String methodeOff = "methodeTest2";
		OneShotTimer timerOff = new OneShotTimer(3);
		
		DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(test,methodeOn,timerOn,methodeOff,timerOff);
		
		assertEquals(methodeOff,action.getMethod().getName());
		assertEquals(test,action.getObject());
	}
	
	@Test
	void testConstructorWithIntegerTreeSetFirstOnLowerThanFirstOff() {
		TreeSet<Integer> datesOn = new TreeSet<Integer>();
		TreeSet<Integer> datesOff = new TreeSet<Integer>();
		datesOn.add(1);
		datesOn.add(3);
		datesOff.add(2);
		datesOff.add(4);
		
		ClasseTest test = new ClasseTest();
		String methodeOn = "methodeTest";
		OneShotTimer timerOn = new OneShotTimer(5);
		
		String methodeOff = "methodeTest2";
		OneShotTimer timerOff = new OneShotTimer(3);
		
		DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(test,methodeOn,datesOn,methodeOff,datesOff);
		
		assertEquals(methodeOn,action.getMethod().getName());
		assertEquals(test,action.getObject());
		
	}
	
	@Test
	void testConstructorWithIntegerTreeSetFirstOnHigherThanFirstOff() {
		TreeSet<Integer> datesOn = new TreeSet<Integer>();
		TreeSet<Integer> datesOff = new TreeSet<Integer>();
		datesOn.add(2);
		datesOn.add(4);
		datesOff.add(1);
		datesOff.add(3);
		
		ClasseTest test = new ClasseTest();
		String methodeOn = "methodeTest";
		OneShotTimer timerOn = new OneShotTimer(5);
		
		String methodeOff = "methodeTest2";
		OneShotTimer timerOff = new OneShotTimer(3);
		
		DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(test,methodeOn,datesOn,methodeOff,datesOff);
		
		assertEquals(methodeOff,action.getMethod().getName());
		assertEquals(test,action.getObject());
		
	}
	
	@Test
	void testNextOnAction() {
		ClasseTest test = new ClasseTest();
		String methodeOn = "methodeTest";
		OneShotTimer timerOn = new OneShotTimer(5);
		
		String methodeOff = "methodeTest2";
		OneShotTimer timerOff = new OneShotTimer(3);
		
		DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(test,methodeOn,timerOn,methodeOff,timerOff);
		action.next();
		assertEquals(methodeOn,action.getMethod().getName());
		assertEquals(test,action.getObject());
	}
	
	@Test
	void testNextOffAction() {
		ClasseTest test = new ClasseTest();
		String methodeOn = "methodeTest";
		OneShotTimer timerOn = new OneShotTimer(5);
		
		String methodeOff = "methodeTest2";
		OneShotTimer timerOff = new OneShotTimer(3);
		
		DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(test,methodeOn,timerOn,methodeOff,timerOff);
		action.next();
		action.next();
		assertEquals(methodeOff,action.getMethod().getName());
		assertEquals(test,action.getObject());
	}
	
	@Test
	void testSpendTime() {
		ClasseTest test = new ClasseTest();
		String methodeOn = "methodeTest";
		OneShotTimer timerOn = new OneShotTimer(5);
		
		String methodeOff = "methodeTest2";
		OneShotTimer timerOff = new OneShotTimer(3);
		
		DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(test,methodeOn,timerOn,methodeOff,timerOff);
		action.next();
		action.spendTime(3);

		assertEquals(2,action.getCurrentLapsTime());
		
	}
	
	@Test
	void testCompareTo() {
		
		//CompareTo ayant déjà été testé dans DiscreteAction, on estime ce test suffisant
		
		ClasseTest test = new ClasseTest();
		String methodeOn = "methodeTest";
		OneShotTimer timerOn = new OneShotTimer(5);
		
		String methodeOff = "methodeTest2";
		OneShotTimer timerOff = new OneShotTimer(3);
		
		DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(test,methodeOn,timerOn,methodeOff,timerOff);
		action.next();
		
		String methodeActionSeule = "methodeTest3";
		OneShotTimer timerActionSeule = new OneShotTimer(10);
		DiscreteAction actionSeule = new DiscreteAction(test,methodeActionSeule,timerActionSeule);
		//actionSeule lapsTime is null
		assertEquals(-1,action.compareTo(actionSeule));
		
		//set actionSeule lapsTime 
		actionSeule.next();
		assertEquals(-1,action.compareTo(actionSeule));
		
	}
	
	@Test
	void testHasNextWithNoAndOneAndTwoNext() {
		ClasseTest test = new ClasseTest();
		String methodeOn = "methodeTest";
		OneShotTimer timerOn = new OneShotTimer(5);
		
		String methodeOff = "methodeTest2";
		OneShotTimer timerOff = new OneShotTimer(3);
		
		DiscreteActionOnOffDependent action = new DiscreteActionOnOffDependent(test,methodeOn,timerOn,methodeOff,timerOff);

		assertEquals(true,action.hasNext());
		
		action.next();

		assertEquals(true,action.hasNext());
		
		action.next();

		assertEquals(false,action.hasNext());
	}
	
}
