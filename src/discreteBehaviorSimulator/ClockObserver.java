package discreteBehaviorSimulator;

/**
 * Interface for clock classes
 */
public interface ClockObserver {
	public void clockChange(int time);
	public void nextClockChange(int nextJump);
}
