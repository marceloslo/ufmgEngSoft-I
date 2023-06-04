package urna;

public class UrnaElection {

	public static void main(String[] args) {
		Urna urnaEletronica = null;

		// #if Federal
		urnaEletronica = new UrnaFederal("password");
		// #endif
		
		// #if Municipal
//@		urnaEletronica = new UrnaDistrital("password");
		// #endif
		
		// #if RealityShow
//@		urnaEletronica = new UrnaTvShow("password");
		// #endif
		
		// #if Universidade
//@		urnaEletronica = new UrnaUniversity("password");
		// #endif
	    urnaEletronica.startMenu();
	}

}
