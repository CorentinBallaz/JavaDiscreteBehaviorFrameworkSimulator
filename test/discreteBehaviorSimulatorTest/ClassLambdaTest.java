package discreteBehaviorSimulatorTest;

public class ClassLambdaTest {

	private int count;
	private int numeroDeClasse;
	
	public ClassLambdaTest(int numeroDeClasse) {
		this.count=0;
		this.numeroDeClasse=numeroDeClasse;
	}
	
	public void test() {
		
		this.count+=1;
		
	}
	
	public String toString() {
		return "Classe Test n "+ numeroDeClasse;
	}
	
	


	public int getCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
