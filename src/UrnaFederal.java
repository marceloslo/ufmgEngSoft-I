package urna;
// #if Federal

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrnaFederal extends Urna{

    private static int protestVote = 1;

    private Map<String, String> federalDictionary = new HashMap<>();

    public UrnaFederal(String password){
        super(password);

        federalDictionary.put("Presidente", "President");
        federalDictionary.put("Deputado Federal", "FederalDeputy");
        federalDictionary.put("Deputado Estadual", "StateDeputy");
        federalDictionary.put("Senador", "Senator");
        federalDictionary.put("Governador", "Governor");
    }

  protected void voterMenu() {
	// adequa o menu às condições de segundo turno
	// #if SegundoTurno
	List<String> noSecondRound = urnaModel.electionsWithNoSecondRound();
	for(String role : noSecondRound) {
		federalDictionary.values().remove(role);
	}
	// #endif
    try {
      printInterface.printSeparator();
      if (!urnaModel.getElectionStatus()) {
        printInterface.print("A eleição ainda não foi inicializada, verifique com um funcionário do TSE");
        return;
      }

      Voter voter = getVoter();
      if (voter == null)
        return;
      printInterface.printSeparator();

      printInterface.print("Vamos começar!\n");
      printInterface.print(
          "OBS:\n- A partir de agora caso você queira votar nulo você deve usar o numero 00 \n- A partir de agora caso você queira votar branco você deve escrever br\n");
      printInterface.printSeparator();
      for (Map.Entry<String, String> entry : federalDictionary.entrySet()) {
        String key = entry.getKey();
        String candidateType = entry.getValue();
        while (!voteForCandidate(voter, key, candidateType));
    }

    } catch (Warning e) {
      printInterface.print(e.getMessage());
    } catch (StopTrap e) {
      printInterface.print(e.getMessage());
    } catch (Exception e) {
      printInterface.print(e.getMessage());
    }
  }

  private boolean checkForProtestVote(String vote, String candidateType){
    if (vote.equals("ext")){
      throw new StopTrap("Saindo da votação");
    
    }else{
        printInterface.confirmationMessage("branco");

        int confirm = readInt();
        if (confirm == protestVote){
            urnaModel.protestVote(candidateType);
            return true;
        }else{
            return false;
        }
    }
  }

  private boolean checkForNullVote(String vote, String candidateType){
    printInterface.confirmationMessage("nulo");

    int confirm = readInt();
    if (confirm == protestVote) {
        urnaModel.NullVote(candidateType);
        return true;
    } else{
        return false;
    }
  }

  private Candidate getCandidate(Voter voter, String key, Integer voteNumber){
    Candidate candidate = null;

    if (key == "Presidente"){
        President media_candidate = urnaModel.getPresidentByNumber(voteNumber);//currentElection.getPresidentByNumber(voteNumber);
        candidate = media_candidate;

    }
    else if (key == "Deputado Federal"){
        FederalDeputy media_candidate = urnaModel.getFederalDeputyByNumber(voter.state, voteNumber);//currentElection.getFederalDeputyByNumber(voter.state, voteNumber);
        candidate = media_candidate;

    }
    else if (key == "Deputado Estadual"){
        StateDeputy media_candidate = urnaModel.getStateDeputyByNumber(voter.state, voteNumber);//currentElection.getStateDeputyByNumber(voter.state, voteNumber);
        candidate = media_candidate;
    }
    else if (key == "Senador"){
        Senator media_candidate = urnaModel.getSenatorByNumber(voter.state, voteNumber);//currentElection.getSenatorByNumber(voter.state, voteNumber);
        candidate = media_candidate;   
    }
    else if (key == "Governador"){
        Governor media_candidate = urnaModel.getGovernorByNumber(voter.state, voteNumber);//currentElection.getGovernorByNumber(voter.state, voteNumber);
        candidate = media_candidate;
    }

    if(candidate == null){
          throw new Warning("Candidato não encontrado");
    }

    return candidate;
  }

  protected boolean voteForCandidate(Voter voter, String key, String candidateType){
    try{
    printInterface.askForCandidateNumber(key);
    String vote = readString();

    if (vote.equals("ext") || vote.equals("br"))
       return checkForProtestVote(vote, candidateType);
    
    int voteNumber = Integer.parseInt(vote);
    
    if (voteNumber==0){
        return checkForNullVote(vote, candidateType);
    }

    Candidate candidate = getCandidate(voter, key, voteNumber);

    // EXTENDER!!!!
    printInterface.confirmationCandidate(candidate.name, candidate.party);
    int confirm = readInt();
    if(confirm == 1){
        urnaModel.vote(Integer.toString(voteNumber), candidateType);
        return true;
    }else{
        return false;
    }
  
    }catch(Warning e){
        printInterface.print(e.getMessage());
        return false;
    }
  }

  protected void addCandidate(TSEEmployee tseProfessional) {
  	  printInterface.askForPassword();
  	  String password = readString();
  	  
  	  printInterface.print("1 - Adicionar Presidente");
  	  printInterface.print("2 - Adicionar Deputado Federal");
  	  printInterface.print("3 - Adicionar Deputado Estadual");
  	  printInterface.print("4 - Adicionar Senador");
  	  printInterface.print("5 - Adicionar Governador");
  	  int opt = readInt();
  	  
      printInterface.print("Digite o nome do candidato: ");
      String name = readString();
      printInterface.print("Digite o número do candidato: ");
      int number = readInt();
      printInterface.print("Digite o partido do candidato: ");
      String party = readString();
      printInterface.print("Digite a estado do candidato: ");
      String state = readString();
      
      switch (opt) {
      	case 1: 
      		President presidentCandidate = new President.Builder().name(name).number(number).party(party).build();
      		urnaModel.addCandidate(tseProfessional, presidentCandidate, password);
      	case 2:
      		FederalDeputy federalDeputyCandidate = new FederalDeputy.Builder().name(name).number(number).party(party).state(state).build();
      		urnaModel.addCandidate(tseProfessional, federalDeputyCandidate, password);
      	case 3:
      		StateDeputy stateDeputyCandidate = new StateDeputy.Builder().name(name).number(number).party(party).state(state).build();
      		urnaModel.addCandidate(tseProfessional, stateDeputyCandidate, password);
      	case 4:
      		Senator senatorCandidate = new Senator.Builder().name(name).number(number).party(party).state(state).build();
      		urnaModel.addCandidate(tseProfessional, senatorCandidate, password);
      	case 5:
      		Governor governorCandidate = new Governor.Builder().name(name).number(number).party(party).state(state).build();
      		urnaModel.addCandidate(tseProfessional, governorCandidate, password);
      	default:
      		printInterface.invalidCommand();
      }
  }

  protected void removeCandidate(TSEEmployee tseProfessional) {
	  printInterface.askForPassword();
	  String password = readString();
	  
  	  printInterface.print("1 - Remover Presidente");
  	  printInterface.print("2 - Remover Deputado Federal");
  	  printInterface.print("3 - Remover Deputado Estadual");
  	  printInterface.print("4 - Remover Senador");
  	  printInterface.print("5 - Remover Governador");
  	  int opt = readInt();
	  
	  printInterface.print("Digite o estado do candidato: ");
	  String state = readString();
      printInterface.print("Digite o número do candidato: ");
      int number = readInt();
      
      switch (opt) {
    	case 1: 
    		President presidentCandidate = urnaModel.getPresidentByNumber(number);
    		urnaModel.removeCandidate(tseProfessional, presidentCandidate, password);
    	case 2:
    		FederalDeputy federalDeputyCandidate = urnaModel.getFederalDeputyByNumber(state, number);
    		urnaModel.removeCandidate(tseProfessional, federalDeputyCandidate, password);
    	case 3:
      		StateDeputy stateDeputyCandidate = urnaModel.getStateDeputyByNumber(state, number);
    		urnaModel.removeCandidate(tseProfessional, stateDeputyCandidate, password);
    	case 4:
    		Senator senatorCandidate = urnaModel.getSenatorByNumber(state, number);
    		urnaModel.removeCandidate(tseProfessional, senatorCandidate, password);
    	case 5:
    		Governor governorCandidate = urnaModel.getGovernorByNumber(state, number);
    		urnaModel.removeCandidate(tseProfessional, governorCandidate, password);
    	default:
    		printInterface.invalidCommand();
      }
  }
}

// #endif
