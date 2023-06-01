package urna;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UrnaDistrital extends Urna{


    private static int MAX_OPTIONS = 5; 
    private static int MIN_OPTIONS = 1;
    private static int protestVote = 1;

    private Map<Integer, String> districtalDictionary = new HashMap<>();

    public UrnaDistrital(String password){
        super(password);

        districtalDictionary.put(1, "Prefeito");
        districtalDictionary.put(2, "Vereador");
    }

    protected void voterMenu() {
    	// adequa o menu às condições de segundo turno
    	// #if SegundoTurno
//@    	List<String> noSecondRound = urnaModel.electionsWithNoSecondRound();
//@    	for(String role : noSecondRound) {
//@    		districtalDictionary.values().remove(role);
//@    	}
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
          
          for (Map.Entry<Integer, String> entry : districtalDictionary.entrySet()) {
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
            Mayor media_candidate = urnaModel.getMayorByNumber(voter.district, voteNumber); //currentElection.getMayorByNumber(voter.state, voteNumber);
            candidate = media_candidate;
    
        }
        else if (key == 2){
            CityCouncilor media_candidate = urnaModel.getCityCouncilorByNumber(voter.district, voteNumber); //currentElection.getFederalDeputyByNumber(voter.state, voteNumber);
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
      
          if (vote.equals("ext") || vote.equals("br"))
            return checkForProtestVote(vote, candidateType);
          
          int voteNumber = Integer.parseInt(vote);
          
          if (voteNumber == 0){
              return checkForNullVote(vote, candidateType);
          }
      
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
