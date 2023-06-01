package urna;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UrnaElection {


  	private static UrnaPrintInterface printInterface = new UrnaPrintInterface();


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
//@		//TODO
		// #endif
	    urnaEletronica.startMenu();
	}


	private static int readInput(){
		BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
		
		String type_of_voting_machine = "";
		
		try {
       		type_of_voting_machine = scanner.readLine();
		} catch (Exception e) {
			printInterface.printErrorInReadingEntry();
		}
		return Integer.parseInt(type_of_voting_machine);

	}

}
