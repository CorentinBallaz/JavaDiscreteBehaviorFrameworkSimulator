package actionTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import action.DiscreteActionDependent;
import timer.OneShotTimer;
import timer.PeriodicTimer;

class DiscreteActionDependentTest {

	
	// En l'état, aucune méthode ne fonctionne comme il se doit si on regarde les tests
	// car la méthode next() n'est pas bonne, d'où la nécessité de refactorer DiscreteActionDependent comme 
	// indiqué en Todo.
	
	@Test
	void testAddDependence() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		
		action.addDependence(test2, m2, timer2);
		
		action.next(); //next() ne fonctionne pas correctement
		action.next();
		
		assertEquals(m2,action.getMethod().getName());
		assertEquals(test2,action.getObject());
		
		
	}
	
	//On ne test pas nextMethod car elle n'a pas lieu d'être étant donné l'implémentation de l'interface.
	
	
	@Test
	void testReInit() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		
		action.addDependence(test2, m2, timer2);
		
		action.next();
		action.next();
		action.next();
		
		assertEquals(m,action.getMethod().getName());
		assertEquals(test,action.getObject());
		
		action.next();
		assertEquals(test2,action.getObject());
	}
	
	@Test
	void testSpendTime() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		
		ClasseTest test3 = new ClasseTest();
		String m3 = "methodeTest3";
		OneShotTimer timer3 = new OneShotTimer(6);
		
		action.addDependence(test2, m2, timer2);
		action.addDependence(test3, m3, timer3);
		
		action.next();
		action.spendTime(2);
		assertEquals(3, action.getCurrentLapsTime());
		
		action.next();
		action.spendTime(2);
		assertEquals(1, action.getCurrentLapsTime());
		
		action.next();
		action.spendTime(2);
		assertEquals(4, action.getCurrentLapsTime());
		
		action.next();
		
	}
	
	@Test
	void testCompareTo() {
		
		//CompareTo ayant déjà été testé dans DiscreteAction, on estime ce test suffisant
		
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action1 = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		DiscreteActionDependent action2 = new DiscreteActionDependent(test2,m2,timer2);
		
		assertEquals(1,action1.compareTo(action2));
	}
	
	@Test 
	void testNext() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		
		action.addDependence(test2, m2, timer2);
		
		action.next();

		assertEquals(m,action.getMethod().getName());
		assertEquals(test,action.getObject());
		
		action.next();
		
		assertEquals(m2,action.getMethod().getName());
		assertEquals(test2,action.getObject());
	}
	
	@Test
	void testHasNextWithTwoActionsAndOneActionNext() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		
		action.addDependence(test2, m2, timer2);
		assertEquals(true,action.hasNext());
	}
	
	@Test
	void testHasNextWithTwoActionsAndNoNextAction() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		
		action.addDependence(test2, m2, timer2);

		action.next();
		action.next();
		assertEquals(false,action.hasNext());
	}
	
	@Test
	void testHasNextWithTwoActionsAndNoNextActionButStillValueInTimmer() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		PeriodicTimer timer = new PeriodicTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		PeriodicTimer timer2 = new PeriodicTimer(3);
		action.addDependence(test2, m2, timer2);
		
		action.next();
		action.next();
		
		assertEquals(true,action.hasNext());
	}
	
	@Test
	void testHasNextWithThreeActionsAndOneActionNextAndNoMoreValueInTimmer() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		action.addDependence(test2, m2, timer2);
		
		ClasseTest test3 = new ClasseTest();
		String m3 = "methodeTest3";
		OneShotTimer timer3 = new OneShotTimer(4);
		action.addDependence(test3, m3, timer3);
		
		action.next();
		action.next();
		
		assertEquals(true,action.hasNext());
	}
	
	
	@Test
	void testIsEmptyWithTwoActionsAndOneActionNext() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		
		action.addDependence(test2, m2, timer2);
		
		assertEquals(false,action.isEmpty());
	}
	
	@Test
	void testIsEmptyWithTwoActionsAndNoNextAction() {
		ClasseTest test = new ClasseTest();
		String m = "methodeTest";
		OneShotTimer timer = new OneShotTimer(5);
		DiscreteActionDependent action = new DiscreteActionDependent(test,m,timer);
		
		ClasseTest test2 = new ClasseTest();
		String m2 = "methodeTest2";
		OneShotTimer timer2 = new OneShotTimer(3);
		
		action.addDependence(test2, m2, timer2);
		action.next();
		action.next();
		assertEquals(true,action.isEmpty());
	}
	
}
