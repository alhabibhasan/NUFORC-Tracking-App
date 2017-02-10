package requirement0;
import api.ripley.*;
public class Contributors {

	public static void main(String[] args) {
		System.out.println("Muhammed Hasan");
		System.out.println("Aflal Asker");
		System.out.println("Baha Aboukoura");
		System.out.println("Jaman Salique");
		
		
		//Ripley(String privateKey, String publicKey)
		Ripley test = new Ripley("10tLI3CRs9qyVD6ql2OMtA==", "tBgm4pRv9wrVqL46EnH7ew==");
		
		System.out.println(test.getLastUpdated().toString());
		
				
	}
}
