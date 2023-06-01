package urna;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class UrnaElection {


  	private static UrnaPrintInterface printInterface = new UrnaPrintInterface();


	public static void main(String[] args) {
		Urna urnaEletronica = null;
		/*
		int type_of_voting_machine = 0;
		while(true){
			printInterface.printTypeOfVotingMachine();

			type_of_voting_machine = readInput();
			if(type_of_voting_machine > 0 && type_of_voting_machine < 5){
				break;
			}else{
				printInterface.printInvalidVotingMachine();
			}
		}

		if (type_of_voting_machine == 1){
			urnaEletronica = new UrnaFederal("password");
		}
		else if (type_of_voting_machine == 2){
			urnaEletronica = new UrnaDistrital("password");
		}
		else if(type_of_voting_machine == 3){
			urnaEletronica = new UrnaTvShow("password");
		}
		else if(type_of_voting_machine == 4){
			// to do 
		}
		 */
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
