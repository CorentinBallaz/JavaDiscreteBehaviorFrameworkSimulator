package action;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.TreeSet;

import timer.Timer;

/**
 * DiscreteActionDependant is the class that allows the creation and the management of a serie (TreeSet) of discrete actions who are dependent
 * The dependence is on the order of insertion in the TreeSet
 * This class is characterized by the following informations : 
 * <ul> 
 * <li> baseAction</li>
 * <li>depentActions is a tree of actions</li>
 * <li>it is an iterator</li>
 * <li>currentAction</li>
 * </ul>
 * @author flver
 *
 */
public class DiscreteActionDependent implements DiscreteActionInterface {
	
	protected DiscreteActionInterface baseAction;
	protected TreeSet<DiscreteActionInterface> depedentActions;
	private Iterator<DiscreteActionInterface> it;
	protected DiscreteActionInterface currentAction;
	
	
	/**
	 * Construct a series of dependent actions, first action is baseAction. Other actions are stored in depedentActions, a TreeSet. Add action in it with addDependence
	 * 
	 * @param o
	 * @param baseMethodName
	 * @param timerBase
	 */	
	public DiscreteActionDependent(Object o, String baseMethodName, Timer timerBase){
		this.baseAction = new DiscreteAction(o, baseMethodName, timerBase);
		this.currentAction = this.baseAction;
		this.depedentActions = new TreeSet<DiscreteActionInterface>();
		this.depedentActions.add(baseAction);
		this.it = this.depedentActions.iterator();
		
	}
	/**
	 * Add a discret action in the tree dependentActions
	 * Iterator is reloaded to take the add into account
	 * @param o 
	 * @param depentMethodName 
	 * @param timerDependence
	 */
	public void addDependence(Object o, String depentMethodName, Timer timerDependence) {
		this.depedentActions.add(new DiscreteAction(o, depentMethodName, timerDependence));
		this.it = this.depedentActions.iterator();
	}
	
	//Reinitialization of the tree depedentActions (back to begining), the iterator is reloaded.
	private void reInit() { 
		this.it = this.depedentActions.iterator();		
	}
	
	/**
	 * Decrease the lapsTime of the current action by t.
	 * @param t
	 */
	public void spendTime(int t) {
		this.currentAction.spendTime(t);
	}

	/**
	 * @return currentAction method
	 */
	public Method getMethod() {
		return this.currentAction.getMethod();
	}
	/**
	 * @return  currentAction lapstime 
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
	 * @param c
	 * @return,1,-1 or 0
	 */
	public int compareTo(DiscreteActionInterface c) {
		return this.currentAction.compareTo(c);
	}
    /**
     * Check if the current depedentActions iterator has no more actions or if there's no more element in baseAction timmer
     * @return boolean 
     */
	public Boolean isEmpty() {
		return !this.hasNext();
	}
	
	/**
	 * Take the next action in depedentActions and next lapsTime in the action timmer
	 * If the currentAction is the last action of depedentActions, then reInit() method is call
	 * @return this
	 */
	public DiscreteActionInterface next() {
		if(this.currentAction == this.depedentActions.last()) {
			this.reInit();
		}
		this.currentAction = this.it.next();
		this.currentAction = this.currentAction.next();
		
		return this;
	}
	/**
	 * Check if the current depedentActions iterator has more actions or if there's still element in baseAction timmer
	 * @return boolean
	 */
	public boolean hasNext() {
		return this.baseAction.hasNext() || this.it.hasNext();		
	}

}
