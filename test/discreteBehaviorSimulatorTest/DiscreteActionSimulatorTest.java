package discreteBehaviorSimulatorTest;
//import discreteBehaviorSimulator;
import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import action.DiscreteAction;
import discreteBehaviorSimulator.ClockObserver;
import discreteBehaviorSimulator.DiscreteActionSimulator;
import timer.OneShotTimer;
import timer.PeriodicTimer;
import discreteBehaviorSimulator.Clock;

@TestMethodOrder(OrderAnnotation.class)
class DiscreteActionSimulatorTest {

	@Test
	@Order(1)
	void testRunPeriodicTimer() throws InterruptedException {
		
		//ok 1
		int nbLoop = 5;
		int frequencyForPeriodicTimer = 10;
		DiscreteActionSimulator sim = new DiscreteActionSimulator();
		
		
		PeriodicTimer timer  = new PeriodicTimer(frequencyForPeriodicTimer);
		ClassLambdaTest classTest = new ClassLambdaTest(1);
		DiscreteAction action = new DiscreteAction(classTest,"test",timer);
		//Interface clock Observer
		TestClockObserver clockObserverTest = new TestClockObserver();
		Clock horloge  = Clock.getInstance() ;
		horloge.addObserver(clockObserverTest);
		
		
		sim.setNbLoop(nbLoop);
		sim.addAction(action);
		//sim.addAction(action2);
		
		sim.start();
		
		
		while(sim.getRunning()) {
			Thread.sleep(10);
		}
		//test if the clock has correctly evolved
		Assertions.assertEquals(nbLoop*frequencyForPeriodicTimer, clockObserverTest.getCurrentTime());
		//test if 
		
	}
	
/*	@Test
	@Order(4)
void testRun2PeriodicTimer() throws InterruptedException {
		// oko 4 
		
		int nbLoop = 1;
		int frequencyForPeriodicTimer1 = 10;
		int frequencyForPeriodicTimer2 = 20;
		DiscreteActionSimulator sim = new DiscreteActionSimulator();
		
		
		PeriodicTimer timer1  = new PeriodicTimer(frequencyForPeriodicTimer1);
		PeriodicTimer timer2  = new PeriodicTimer(frequencyForPeriodicTimer2);

		ClassLambdaTest classTest1 = new ClassLambdaTest(1);
		ClassLambdaTest classTest2 = new ClassLambdaTest(2);
		DiscreteAction action1 = new DiscreteAction(classTest1,"test",timer1);
		DiscreteAction action2 = new DiscreteAction(classTest2,"test",timer2);
		//Interface clock Observer
		TestClockObserver clockObserverTest = new TestClockObserver();
		Clock horloge  = Clock.getInstance() ;
		
		long currentTime = horloge.getTime();
		horloge.addObserver(clockObserverTest);
		
		
		sim.setNbLoop(nbLoop);
		sim.addAction(action1);
		sim.addAction(action2);
		
		sim.start();
		
		
		while(sim.getRunning()) {
			Thread.sleep(10);
		}
		//test if the clock has correctly evolved
		Assertions.assertEquals((int)currentTime + 30 , clockObserverTest.getCurrentTime());
		//test if 
		
	}*/
	
	@Test
	@Order(2)
void testRunPeriodicTimerWithNegativeLoop() throws InterruptedException {
		//ok 2 
		
		int nbLoop = -5;
		int atTimmer= 10;
		DiscreteActionSimulator sim = new DiscreteActionSimulator();
		
		
		PeriodicTimer timer = new PeriodicTimer(atTimmer);
		ClassLambdaTest classTest = new ClassLambdaTest(1);
		DiscreteAction action = new DiscreteAction(classTest,"test",timer);
		//Interface clock Observer
		TestClockObserver clockObserverTest= new TestClockObserver();
		Clock horloge  = Clock.getInstance() ;
		horloge.addObserver(clockObserverTest);
		
		
		sim.setNbLoop(nbLoop);
		sim.addAction(action);
		//sim.addAction(action2);
		
		sim.start();
		

	/*	while(clockObserverTest.getCurrentTime()!=decisionWhenStop) {
			timeToStop+=1; 
		}*/
		
			Thread.sleep(100);
	
		//test if the clock has correctly evolved
		Assertions.assertEquals(true, sim.getRunning());
		Assertions.assertEquals(0, clockObserverTest.getCurrentTime()%atTimmer);
		//test if 
		
	}
	

	/*@Test 
	@Order(2)
	void testRunWithOneShot() throws InterruptedException{
		//Test goal : see if the simulator stop when he has nothing to do
		DiscreteActionSimulator sim = new DiscreteActionSimulator();
		OneShotTimer timer = new OneShotTimer(5);
		ClassLambdaTest classTest = new ClassLambdaTest(1);
		DiscreteAction action = new DiscreteAction(classTest,"test",timer);
		ClockObserver clockObesrverTest = new TestClockObserver();
		Clock horloge  = Clock.getInstance() ;
		horloge.addObserver(clockObesrverTest);
		
		
		sim.setNbLoop(10);
		sim.addAction(action);
		//sim.addAction(action2);
		
		sim.start();
		while(sim.getRunning()) {
			Thread.sleep(10);
		}
		Assertions.assertEquals(false, sim.getRunning());
	}*/
	
	@Test
	@Order(3)
	void testWith2OneShotAndNegativeNBLoop() throws InterruptedException {
		
		//ok 3 
		int nbLoop = -5;
		int atTimmer1= 5;
		int atTimmer2=10;
		DiscreteActionSimulator sim = new DiscreteActionSimulator();
		
		
		OneShotTimer timer1 = new OneShotTimer(atTimmer1);
		OneShotTimer timer2 = new OneShotTimer(atTimmer2);
		ClassLambdaTest classTest1 = new ClassLambdaTest(1);
		ClassLambdaTest classTest2 = new ClassLambdaTest(2);
		DiscreteAction action1 = new DiscreteAction(classTest1,"test",timer1);
		DiscreteAction action2 = new DiscreteAction(classTest2,"test",timer2);
		//Interface clock Observer
		TestClockObserver clockObserverTest= new TestClockObserver();
		Clock horloge  = Clock.getInstance() ;
		horloge.addObserver(clockObserverTest);
		
		
		sim.setNbLoop(nbLoop);
		sim.addAction(action1);
		sim.addAction(action2);
		
		sim.start();
		

	/*	while(clockObserverTest.getCurrentTime()!=decisionWhenStop) {
			timeToStop+=1; 
		}*/
		
			Thread.sleep(1000);
	
		//test if the clock has correctly evolved
		Assertions.assertEquals(false, sim.getRunning());
		Assertions.assertEquals(0, clockObserverTest.getCurrentTime()%atTimmer2);
		
	}
	
	/*@Test
	void testSetNBLoop() throws InterruptedException {
		
		int nbLoop =- 5;
		int frequencyForPeriodicTimer = 1;
		DiscreteActionSimulator sim = new DiscreteActionSimulator();
		PeriodicTimer timer  = new PeriodicTimer(frequencyForPeriodicTimer);
		ClassLambdaTest classTest = new ClassLambdaTest();
		DiscreteAction action = new DiscreteAction(classTest,"test",timer);
		//Interface clock Observer
		TestClockObserver clockObserverTest = new TestClockObserver();
		Clock horloge  = Clock.getInstance() ;
		horloge.addObserver(clockObserverTest);
		
		
		sim.setNbLoop(nbLoop);
		sim.addAction(action);
		//sim.addAction(action2);
	
		sim.start();
		Thread.sleep(1000);
		
		Assertions.assertEquals(true, sim.getRunning());
	}*/
	
	@Test
	@Order(5)
	void testWithEmptyActionList() throws InterruptedException {
		int nbLoop = 10;
		DiscreteActionSimulator sim = new DiscreteActionSimulator();
		sim.setNbLoop(nbLoop);
		sim.start();
		Assertions.assertEquals(true, sim.getRunning());
		Thread.sleep(1000);
		Assertions.assertEquals(false, sim.getRunning());
		
	}
	
	
	
	//TEST FOR THE CLOCK
	
	
	@Test
	@Order(6)
	void testSetNextJumpPositive() {
		Clock horloge  = Clock.getInstance() ;
		TestClockObserver clockObserverTest = new TestClockObserver();
		horloge.addObserver(clockObserverTest);
		
		horloge.setNextJump(10);
		
		Assertions.assertEquals(10,clockObserverTest.getNextTime());
	}
	
	@Test
	@Order(7)
	void testSetNextJumpNegative() {
		Clock horloge  = Clock.getInstance() ;
		TestClockObserver clockObserverTest = new TestClockObserver();
		horloge.addObserver(clockObserverTest);
		
		horloge.setNextJump(-10);
		
		Assertions.assertEquals(-10,clockObserverTest.getNextTime());
	}
	
	@Test
	@Order(8)
	void testIncrease1() throws Exception {
		Clock horloge  = Clock.getInstance() ;
		TestClockObserver clockObserverTest = new TestClockObserver();
		horloge.addObserver(clockObserverTest);
		
		horloge.setNextJump(50);
		horloge.increase(50);
		
		Assertions.assertEquals(horloge.getTime(),clockObserverTest.getCurrentTime());
		
	}
	@Test
	@Order(9)
	void testIncrease2() throws Exception {
		Clock horloge  = Clock.getInstance() ;
		TestClockObserver clockObserverTest = new TestClockObserver();
		horloge.addObserver(clockObserverTest);
		
		horloge.setNextJump(50);
		
		
		Assertions.assertThrows(Exception.class,()->{
			horloge.increase(20);});
		
	}
	
	@Test
	@Order(10)
	void testIncrease3() throws Exception {
		Clock horloge  = Clock.getInstance() ;
		TestClockObserver clockObserverTest = new TestClockObserver();
		horloge.addObserver(clockObserverTest);
		
		horloge.setNextJump(-50);
		
		Assertions.assertThrows(Exception.class,()->{
			horloge.increase(-50);});
		
	}
	
	
	
	
		

}
