package timer;

import java.util.NoSuchElementException;
import java.util.Random;

/**
 * @author Flavien Vernier
 *
 */



public class RandomTimer implements Timer {
	
	public enum randomDistribution {
		POISSON, EXP, POSIBILIST, GAUSSIAN;
	}
	
	
	private Random r = new Random();
	private randomDistribution distribution;
	private double rate;
	private double mean;
	private double lolim;
	private double hilim; 
	
	
	/**
	 * To chose the distribution with a string input whether it is poisson, exp, posibilist, gaussian (it will be put in uppercase to match the list of possible distribution)
	 * @param distributionName 
	 * @return
	 */
	public static randomDistribution string2Distribution(String distributionName){
		return RandomTimer.randomDistribution.valueOf(RandomTimer.randomDistribution.class, distributionName.toUpperCase());
	}
	
	/**
	 * Gives the distribution's name
	 * @param distribution 
	 * @return
	 */
	public static String distribution2String(randomDistribution distribution){
		return distribution.name();
	}
	

	/**
	 * Constructor 
	 * @param distribution
	 * @param param
	 * @throws Exception if the distribution neither is EXP nor POISSON
	 */
	public RandomTimer(randomDistribution distribution, double param){
		if(distribution == randomDistribution.EXP ){
			this.distribution = distribution;
			this.rate = param;
			this.mean = 1/param;
			this.lolim = 0;
			this.hilim = Double.POSITIVE_INFINITY;
		}else if(distribution == randomDistribution.POISSON){
			this.distribution = distribution;
			this.rate = Double.NaN;
			this.mean = param;
			this.lolim = 0;
			this.hilim = Double.POSITIVE_INFINITY;
		}
	}
	
	

	/**
	 * Constructor
	 * @param distribution
	 * @param lolim
	 * @param hilim
	 * @throws Exception if the distribution neither is POSIBILIST nor GAUSSIAN
	 */
	public RandomTimer(randomDistribution distribution, int lolim, int hilim){
		if(distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN){
			this.distribution = distribution;
			this.mean = lolim + ((double)hilim - (double)lolim)/2;
			this.rate = Double.NaN;
			this.lolim = lolim;
			this.hilim = hilim;
		}
	}
	
	/**
	 * 
	 * @return returns the distribution's name
	 */
	public String getDistribution(){
		return this.distribution.name();
	}
	
	/**
	 * @return returns the parameter depending on the distribution
	 */
	public String getDistributionParam() {
		if(distribution == randomDistribution.EXP ){
			return "rate: " + this.rate;
		}else if(distribution == randomDistribution.POISSON){
			return "mean: " + this.mean;
		}else if(distribution == randomDistribution.POSIBILIST || distribution == randomDistribution.GAUSSIAN){
			return "lolim: " + this.lolim + " hilim: " + this.hilim;
		}
		
		return "null";
	}
	
	/**
	 * @return returns the mean
	 */
	public double getMean(){
		return this.mean;
	}
	
	/**
	 *ToString override
	 *@return returns a summary string about all the attribute corresponding to the distribution 
	 */
	public String toString(){
		String s = this.getDistribution();
		switch (this.distribution){
		case POSIBILIST :
			s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
			break;
		case POISSON :
			s += " mean:" + this.mean;
			break;
		case EXP :
			s += " rate:" + this.rate;
			break;
		case GAUSSIAN :
			s += " LoLim:" + this.lolim + " HiLim:" + this.hilim;
			break;
		}
		
		return s;
	}
	

	/* (non-Javadoc)
	 * @see methodInvocator.Timer#next()
	 */
	/**
	 *Depending on the distribution it will return
	 *
	 *
	 *@return POSIBILIST: {@link RandomTimer#nextTimePosibilist}, POISSON:{@link RandomTimer#nextTimePoisson}, EXP :{@link #nextTimeExp}, GAUSSIAN:{@link #nextTimeGaussian}
	 *
	 */
	@Override
	public Integer next() {
		if (this.distribution == null) {
		    throw new NoSuchElementException();
		}
		switch (this.distribution){
		case POSIBILIST :
			return this.nextTimePosibilist();
		case POISSON :
			return this.nextTimePoisson();
		case EXP :
			return this.nextTimeExp();
		case GAUSSIAN :
			return this.nextTimeGaussian();
		}
		return -1 ;		
	}
	
	
	/**
	 * 
	 * @return returns the next value following the posibilist distribution
	 */
	private int nextTimePosibilist(){
	    return (int)this.lolim + (int)(this.r.nextInt() * (this.hilim - this.lolim));
	}
	
	/**
	 * 
	 * @return returns the next value following the Exp distribution
	 */
	private int nextTimeExp(){
	    return (int)(-Math.log(1.0 - this.r.nextInt()) / this.rate);
	}
	
	
	/**
	 * 
	 * @return returns the next value following the Poisson distribution
	 */
	private int nextTimePoisson() {
	    
	    double l = Math.exp(-this.mean);
	    int k = 0;
	    double p = 1.0;
	    do {
	        p = p * this.r.nextDouble();
	        k++;
	    } while (p > l);
	    return k - 1;
	}   		
	    
	
	/**
	 * return the next value following the Gaussian distribution
	 * @return
	 */
	private int nextTimeGaussian(){
		return (int)this.lolim + (int)((this.r.nextGaussian() + 1.0)/2.0 * (this.hilim - this.lolim));
	}
	
	
	/**
	 *@return true
	 */
	@Override
	public boolean hasNext() {
		return true;
	}
}
