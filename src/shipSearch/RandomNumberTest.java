package shipSearch;

public class RandomNumberTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Clcg4 gen = new Clcg4();
		gen.initDefault();
		for (int i = 0 ; i < 1000; i ++){
			System.out.println(gen.nextValue(1));
		}
	}

}
