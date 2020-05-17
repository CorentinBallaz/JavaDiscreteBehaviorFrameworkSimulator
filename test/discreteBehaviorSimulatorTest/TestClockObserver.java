package discreteBehaviorSimulatorTest;
import discretebehaviorsimulator.ClockObserver;
public class TestClockObserver implements ClockObserver {
	
	 private int currentTime;
	 private int nextTime;
	 public TestClockObserver() {
		 this.currentTime=0;
		 this.nextTime= 0 ;
	 }
	
	@Override
	public void clockChange(int time) {
		// TODO Auto-generated method stub
		//System.out.println("clock change " + time);
		this.currentTime=time;
	}

	@Override
	public void nextClockChange(int nextJump) {
		// TODO Auto-generated method stub
		this.nextTime+=nextJump;
		
		//System.out.println("next clock change " + nextJump);
		
	}
	
	public int getCurrentTime() {
		return this.currentTime;
	}
	
	public int getNextTime() {
		return this.nextTime;
	}

}
