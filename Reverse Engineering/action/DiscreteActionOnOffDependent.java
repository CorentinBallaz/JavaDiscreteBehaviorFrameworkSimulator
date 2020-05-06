package action;

import java.lang.reflect.Method;
import java.util.TreeSet;
import java.util.Vector;

import timer.DateTimer;
import timer.Timer;

/**
 * @author flver
 *
 */

/**
 * DiscreteActionDependantOnOff is the class that allows the creation and the management of a serie (TreeSet) of discrete actions with an On Off dependence :
 * first action (method) called is On, then method nextAction() is called to select the next action. Alternate between On and Off action.
 * 
 * @param o
 * @param on
 * @param timerOn
 * @param off
 * @param timerOff
 */

public class DiscreteActionOnOffDependent implements DiscreteActionInterface {
	
	protected DiscreteActionInterface onAction;
	protected DiscreteActionInterface offAction;
	protected DiscreteActionInterface currentAction;
	
	private Integer currentLapsTime;
	private Integer lastOffDelay=0;
	
	/**
	 * DiscreteActionDependantOnOff is the class that allows the creation and the management of a serie (TreeSet) of discrete actions with an On Off dependence :
	 * first action (method) called is On, then method nextAction() is called to select the next action. Alternate between On and Off action.
	 * 
	 * @param o
	 * @param on
	 * @param timerOn
	 * @param off
	 * @param timerOff
	 */
	/*public DiscreteActionOnOffDependent(Wo o, Method on, Timer timerOn, Method off, Timer timerOff){
		this.onAction = new DiscreteAction(o, on, timerOn);
		this.offAction = new DiscreteAction(o, off, timerOff);
		
		this.currentAction = this.onAction;
	}*/
	
	public DiscreteActionOnOffDependent(Object o, String on, Timer timerOn, String off, Timer timerOff){
		this.onAction = new DiscreteAction(o, on, timerOn);
		this.offAction = new DiscreteAction(o, off, timerOff);
		
		this.currentAction = this.offAction;
		this.currentLapsTime = 0;
	}

	private void dates2Timalapse(TreeSet<Integer> datesOn, TreeSet<Integer> datesOff, Vector<Integer> timeLapseOn, Vector<Integer> timeLapseOff) {
		Vector<Integer> currentTimeLapse;
		TreeSet<Integer> currentDates;
		Integer date=0;
		if(datesOn.first()<datesOff.first()) {
			currentTimeLapse = timeLapseOn;
			currentDates = datesOn;
		}else {
			currentTimeLapse = timeLapseOff;	
			currentDates = datesOff;		
		}
		Integer nextDate;
		
		while(datesOn.size()>0 || datesOff.size()>0) {
			nextDate = currentDates.first();
		
			currentTimeLapse.add(nextDate - date);
			currentDates.remove(nextDate);
		
			date = nextDate;
			
			if(currentDates == datesOn) {
				currentDates = datesOff;
				currentTimeLapse = timeLapseOff;
			}else {
				currentDates = datesOn;
				currentTimeLapse = timeLapseOn;			
			}
		}
		
	}
	/**
	 * 
	 * @param o
	 * @param on
	 * @param datesOn
	 * @param off
	 * @param datesOff
	 */
	public DiscreteActionOnOffDependent(Object o, String on, TreeSet<Integer> datesOn, String off, TreeSet<Integer> datesOff){
		/*Vector<Integer> timeLapseOn = new Vector<Integer>();
		Vector<Integer> timeLapseOff = new Vector<Integer>();
		
		dates2Timalapse((TreeSet<Integer>)datesOn.clone(), (TreeSet<Integer>)datesOff.clone(), timeLapseOn, timeLapseOff);
		*/
		this.onAction = new DiscreteAction(o, on, new DateTimer(datesOn));
		this.offAction = new DiscreteAction(o, off, new DateTimer(datesOff));
		
		
		
		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.onAction;
		}else{
			this.currentAction = this.offAction;
		}
	}
	/**
	 * Select the next action, if current action is On then select Off action, if current is Off then select On
	 */
	public void nextAction(){
		if (this.currentAction == this.onAction){
			this.currentAction = this.offAction;
			this.currentAction = this.currentAction.next();
			this.lastOffDelay = this.currentAction.getCurrentLapsTime();
		}else{
			this.currentAction = this.onAction;
			this.currentAction = this.currentAction.next();
			this.currentAction.spendTime(this.lastOffDelay);
		}
	}
	/**
	 * Decrease the time of the discrete action
	 * @param t
	 */
	public	void spendTime(int t) {
		this.currentAction.spendTime(t);
	}
	/**
	 * @return the method of the current action
	 */
	public Method getMethod() {
		return this.currentAction.getMethod();
	}
	/**
	 * @return the last laps time of the current action without update
	 */
	public Integer getCurrentLapsTime() {
		return this.currentAction.getCurrentLapsTime();
	}
	/**
	 * @return the object of the current action
	 */
	public Object getObject() {
		return this.currentAction.getObject();
	}
	/**
	 * @return 1,-1 or 0
	 */
	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}
	/**
	 * Return DiscreteActionInterface for the next Action
	 * @return DiscreteActionInterface
	 */
	public DiscreteActionInterface next() {
		this.nextAction();
		return this;
	}
	/**
	 * Returns true if the iteration has more elements
	 * @return boolean
	 */
	public boolean hasNext() {
		return this.onAction.hasNext() || this.offAction.hasNext();		
	}

	

}
