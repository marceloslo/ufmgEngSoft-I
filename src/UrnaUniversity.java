package urna;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrnaUniversity extends Urna{

    private static int protestVote = 1;

    private Map<String, String> universityDictionary = new HashMap<>();

    public UrnaUniversity(String password){
        super(password);

        federalDictionary.put("Chefe de Departamento", "DepartmentHead");
    }

  protected void voterMenu() {
	// adequa o menu às condições de segundo turno
	// #if SegundoTurno
//@	List<String> noSecondRound = urnaModel.electionsWithNoSecondRound();
//@	for(String role : noSecondRound) {
//@		universityDictionary.values().remove(role);
//@	}
	// #endif
    try {
      printInterface.printSeparator();
      if (!urnaModel.getElectionStatus()) {
        printInterface.print("A eleição ainda não foi inicializada, verifique com um funcionário da Universidade.");
        return;
      }

      Voter voter = getVoter();
      if (voter == null)
        return;
      printInterface.printSeparator();

      printInterface.print("Vamos começar!\n");
      printInterface.print(
          "OBS:\n- A partir de agora caso você queira se abster você deve escrever abs\n");
      printInterface.printSeparator();
      for (Map.Entry<String, String> entry : universityDictionary.entrySet()) {
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
    printInterface.confirmationMessage("abstenção");

    int confirm = readInt();
    if (confirm == protestVote){
        urnaModel.protestVote(candidateType);
        return true;
    }else{
        return false;
    }
  }

  private Candidate getCandidate(Voter voter, String key, Integer voteNumber){

    DepartmentHead candidate = urnaModel.getDepartmentHeadByNumber(voteNumber);
    
    if(candidate == null){
          throw new Warning("Candidato não encontrado");
    }

    return candidate;
  }

  protected boolean voteForCandidate(Voter voter, String key, String candidateType){
    try{
    printInterface.askForCandidateNumber(key);
    String vote = readString();

    if (vote.equals("abs"))
       return checkForProtestVote(vote, candidateType);
    
    int voteNumber = Integer.parseInt(vote);

    Candidate candidate = getCandidate(voter, key, voteNumber);

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
