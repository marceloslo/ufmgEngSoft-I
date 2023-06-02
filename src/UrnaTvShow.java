package urna;
import java.util.HashMap;
import java.util.Map;


public class UrnaTvShow extends Urna{

    private Map<String, String> tvDictionary = new HashMap<>();

    public UrnaTvShow(String password){
        super(password);

        tvDictionary.put("Participante Reality", "RealityCandidate");
    }
        
      protected void voterMenu() {
        try {
          printInterface.printSeparator();
          if (!urnaModel.getElectionStatus()) {
            printInterface.print("A eleição ainda não foi inicializada, verifique com um funcionário do canal de televisão");
            return;
          }
    
          Voter voter = getVoter();
          if (voter == null)
            return;
          printInterface.printSeparator();
          
          for (Map.Entry<String, String> entry : tvDictionary.entrySet()) {
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
    
      private Candidate getCandidate(Voter voter, String key, Integer voteNumber){
    
        RealityCandidate candidate = urnaModel.getRealityCandidateByNumber(voter.nationality, voteNumber);

        if(candidate == null){
            throw new Warning("Candidato não encontrado");
        }

        return candidate;
      }
    
      protected boolean voteForCandidate(Voter voter, String key, String candidateType){
        try{
          printInterface.askForCandidateNumber(key);
          
          int voteNumber = Integer.parseInt(readString());
      
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