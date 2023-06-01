package urna;
import java.util.HashMap;
import java.util.Map;

public class UrnaFederal extends Urna{


    private static int MAX_OPTIONS = 5; 
    private static int MIN_OPTIONS = 1;
    private static int protestVote = 1;

    private Map<Integer, String> federalDictionary = new HashMap<>();

    public UrnaFederal(String password){
        super(password);

        federalDictionary.put(1, "Presidente");
        federalDictionary.put(2, "Deputado Federal");
        federalDictionary.put(3, "Deputado Estadual");
        federalDictionary.put(4, "Senador");
        federalDictionary.put(5, "Governador");
    }

  protected void voterMenu() {
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
      
      for (Map.Entry<Integer, String> entry : federalDictionary.entrySet()) {
        Integer key = entry.getKey();
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

  private Candidate getCandidate(Voter voter, Integer key, Integer voteNumber){
    Candidate candidate = null;

    if (key == 1){
        President media_candidate = urnaModel.getPresidentByNumber(voteNumber);//currentElection.getPresidentByNumber(voteNumber);
        candidate = media_candidate;

    }
    else if (key == 2){
        FederalDeputy media_candidate = urnaModel.getFederalDeputyByNumber(voter.state, voteNumber);//currentElection.getFederalDeputyByNumber(voter.state, voteNumber);
        candidate = media_candidate;

    }
    else if (key == 3){
        StateDeputy media_candidate = urnaModel.getStateDeputyByNumber(voter.state, voteNumber);//currentElection.getStateDeputyByNumber(voter.state, voteNumber);
        candidate = media_candidate;
    }
    else if (key == 4){
        Senator media_candidate = urnaModel.getSenatorByNumber(voter.state, voteNumber);//currentElection.getSenatorByNumber(voter.state, voteNumber);
        candidate = media_candidate;   
    }
    else if (key == 5){
        Governor media_candidate = urnaModel.getGovernorByNumber(voter.state, voteNumber);//currentElection.getGovernorByNumber(voter.state, voteNumber);
        candidate = media_candidate;
    }

    if(candidate == null){
          throw new Warning("Candidato não encontrado");
    }

    return candidate;
  }

  protected boolean voteForCandidate(Voter voter, Integer key, String candidateType){
    try{
    printInterface.askForCandidateNumber(candidateType);
    String vote = readString();

    if (vote.equals("00")){
        return checkForNullVote(vote, candidateType);
    }
    if (vote.equals("ext") || vote.equals("br"))
       return checkForProtestVote(vote, candidateType);
    
    int voteNumber = Integer.parseInt(vote);

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
    // Implementar
    }




  protected void removeCandidate(TSEEmployee tseProfessional) {
    // Implementar
    }
}