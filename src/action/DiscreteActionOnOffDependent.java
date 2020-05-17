package action;

import java.lang.reflect.Method;
import java.util.TreeSet;

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
	
	private DiscreteActionInterface onAction;
	private DiscreteActionInterface offAction;
	private DiscreteActionInterface currentAction; //the current action is the on one or the off one
	private Integer lastOffLapsTime=0; //last off action lapsTime
	
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
	
	public DiscreteActionOnOffDependent(Object o, String on, Timer timerOn, String off, Timer timerOff){
		this.onAction = new DiscreteAction(o, on, timerOn);
		this.offAction = new DiscreteAction(o, off, timerOff);
		this.currentAction = this.offAction;
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
		this.onAction = new DiscreteAction(o, on, new DateTimer(datesOn));
		this.offAction = new DiscreteAction(o, off, new DateTimer(datesOff));
		
		if(datesOn.first() < datesOff.first()){
			this.currentAction = this.onAction;
		}else{
			this.currentAction = this.offAction;
		}
	}
	/**
	 * Decrease the lapsTime by t of the current action
	 * @param t
	 */
	public	void spendTime(int t) {
		this.currentAction.spendTime(t);
	}
	/**
	 * @return currentAction method
	 */
	public Method getMethod() {
		return this.currentAction.getMethod();
	}
	/**
	 * @return currentAction lapsTime
	 */
	public Integer getCurrentLapsTime() {
		return this.currentAction.getCurrentLapsTime();
	}
	/**
	 * @return currentAction object
	 */
	public Object getObject() {
		return this.currentAction.getObject();
	}
	/**
	 * Compare currentAction lapsTime to another DiscreteAction lapsTime
	 * @return 1,-1 or 0
	 */
	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}
	/**
	 * Select the next action, if current action is On then select Off action, if current is Off then select On.
	 * Also, set the new lapsTime of the next action (new currentAction). If the next action is a onAction, then her new
	 * lapsTime is descreased (spendTime) by the last offAction delay.
	 * @return DiscreteActionInterface
	 */
	public DiscreteActionInterface next() {
		if (this.currentAction == this.onAction){
			this.currentAction = this.offAction;
			this.currentAction = this.currentAction.next();
			this.lastOffLapsTime = this.currentAction.getCurrentLapsTime();
		}else{
			this.currentAction = this.onAction;
			this.currentAction = this.currentAction.next();
			this.currentAction.spendTime(this.lastOffLapsTime);
		}
		return this;
	}
	/**
	 * Returns true if the timmers of onAction or offAction still have some elements
	 * @return boolean
	 */
	public boolean hasNext() {
		return this.onAction.hasNext() || this.offAction.hasNext();		
	}

	

}
