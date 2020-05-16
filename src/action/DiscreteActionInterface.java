package action;

import java.lang.reflect.Method;
import java.util.Iterator;

public interface DiscreteActionInterface extends Comparable<DiscreteActionInterface>, Iterator<DiscreteActionInterface>{

	/**
	 * decrease the time of the discrete action 
	 */
	public	void spendTime(int t);
	
	/**
	 * return the method of the discrete action
	 */
	public Method getMethod();
	
	/**
	 * return the lapsTime of the discrete action
	 */
	public Integer getCurrentLapsTime();
	
	/**
	 * return the object of the disrecte actiion
	 */
	public Object getObject();

	/**
	 * compare two discrete actions on their lapsTimes
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(DiscreteActionInterface c);
	
	/*
	 * @see java.util.Iterator#next()
	 */
	public DiscreteActionInterface next();
	/*
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext();
}
