package urna;
// #if Municipal
//@import java.util.HashMap;
//@import java.util.List;
//@import java.util.Map;
//@
//@public class UrnaDistrital extends Urna{
//@
//@    private static int protestVote = 1;
//@
//@    private Map<String, String> districtalDictionary = new HashMap<>();
//@
//@    public UrnaDistrital(String password){
//@        super(password);
//@
//@        districtalDictionary.put("Prefeito", "Mayor");
//@        districtalDictionary.put("Vereador", "CityCouncilor");
//@    }
//@
//@    protected void voterMenu() {
//@    	// adequa o menu às condições de segundo turno
    	// #if SegundoTurno
//@    	List<String> noSecondRound = urnaModel.electionsWithNoSecondRound();
//@    	for(String role : noSecondRound) {
//@    		districtalDictionary.values().remove(role);
//@    	}
    	// #endif
//@        try {
//@          printInterface.printSeparator();
//@          if (!urnaModel.getElectionStatus()) {
//@            printInterface.print("A eleição ainda não foi inicializada, verifique com um funcionário do TSE");
//@            return;
//@          }
//@    
//@          Voter voter = getVoter();
//@          if (voter == null)
//@            return;
//@          printInterface.printSeparator();
//@    
//@          printInterface.print("Vamos começar!\n");
//@          printInterface.print(
//@              "OBS:\n- A partir de agora caso você queira votar nulo você deve usar o numero 00 \n- A partir de agora caso você queira votar branco você deve escrever br\n");
//@          printInterface.printSeparator();
//@          
//@          for (Map.Entry<String, String> entry : districtalDictionary.entrySet()) {
//@            String key = entry.getKey();
//@            String candidateType = entry.getValue();
//@            while (!voteForCandidate(voter, key, candidateType));
//@        }
//@    
//@        } catch (Warning e) {
//@          printInterface.print(e.getMessage());
//@        } catch (StopTrap e) {
//@          printInterface.print(e.getMessage());
//@        } catch (Exception e) {
//@          printInterface.print(e.getMessage());
//@        }
//@      }
//@    
//@      private boolean checkForProtestVote(String vote, String candidateType){
//@        if (vote.equals("ext")){
//@          throw new StopTrap("Saindo da votação");
//@        
//@        }else{
//@            printInterface.confirmationMessage("branco");
//@    
//@            int confirm = readInt();
//@            if (confirm == protestVote){
//@                urnaModel.protestVote(candidateType);
//@                return true;
//@            }else{
//@                return false;
//@            }
//@        }
//@      }
//@    
//@      private boolean checkForNullVote(String vote, String candidateType){
//@        printInterface.confirmationMessage("nulo");
//@    
//@        int confirm = readInt();
//@        if (confirm == protestVote) {
//@            urnaModel.NullVote(candidateType);
//@            return true;
//@        } else{
//@            return false;
//@        }
//@      }
//@    
//@      private Candidate getCandidate(Voter voter, String key, Integer voteNumber){
//@        Candidate candidate = null;
//@        
//@        if (key == "Prefeito"){
//@            Mayor media_candidate = urnaModel.getMayorByNumber(voter.district, voteNumber); 
//@            candidate = media_candidate;
//@    
//@        }
//@        else if (key =="Vereador"){
//@            CityCouncilor media_candidate = urnaModel.getCityCouncilorByNumber(voter.district, voteNumber); 
//@            candidate = media_candidate;
//@        }
//@    
//@        if(candidate == null){
//@            throw new Warning("Candidato não encontrado");
//@        }
//@
//@        return candidate;
//@      }
//@    
//@      protected boolean voteForCandidate(Voter voter, String key, String candidateType){
//@        try{
//@          printInterface.askForCandidateNumber(key);
//@          String vote = readString();
//@      
//@          if (vote.equals("ext") || vote.equals("br"))
//@            return checkForProtestVote(vote, candidateType);
//@          
//@          int voteNumber = Integer.parseInt(vote);
//@          
//@          if (voteNumber == 0){
//@              return checkForNullVote(vote, candidateType);
//@          }
//@      
//@          Candidate candidate = getCandidate(voter, key, voteNumber);
//@      
//@          printInterface.confirmationCandidate(candidate.name, candidate.party);
//@          int confirm = readInt();
//@          if(confirm == 1){
//@              urnaModel.vote(Integer.toString(voteNumber), candidateType);
//@              return true;
//@          }else{
//@              return false;
//@          }
//@        }catch(Warning e){
//@          printInterface.print(e.getMessage());
//@          return false;
//@        }
//@      }
//@    
//@      protected void addCandidate(TSEEmployee tseProfessional) {
//@      	  printInterface.askForPassword();
//@      	  String password = readString();
//@      	  
//@      	  printInterface.print("1 - Adicionar Prefeito(a)");
//@      	  printInterface.print("2 - Adicionar Vereador(a)");
//@      	  int opt = readInt();
//@      	  
//@          printInterface.print("Digite o nome do candidato: ");
//@          String name = readString();
//@          printInterface.print("Digite o número do candidato: ");
//@          int number = readInt();
//@          printInterface.print("Digite o partido do candidato: ");
//@          String party = readString();
//@          printInterface.print("Digite o distrito do candidato: ");
//@          String district = readString();
//@          
//@          switch (opt) {
//@          	case 1: 
//@          		Mayor mayorCandidate = new Mayor.Builder().name(name).number(number).party(party).district(district).build();
//@          		urnaModel.addCandidate(tseProfessional, mayorCandidate, password);
//@          	case 2:
//@          		CityCouncilor cityCouncilorCandidate = new CityCouncilor.Builder().name(name).number(number).party(party).district(district).build();
//@          		urnaModel.addCandidate(tseProfessional, cityCouncilorCandidate, password);
//@          	default:
//@          		printInterface.invalidCommand();
//@          }
//@      }
//@
//@      protected void removeCandidate(TSEEmployee tseProfessional) {
//@    	  printInterface.askForPassword();
//@    	  String password = readString();
//@    	  
//@      	  printInterface.print("1 - Remover Prefeito(a)");
//@      	  printInterface.print("2 - Remover Vereador(a)");
//@      	  int opt = readInt();
//@    	  
//@    	  printInterface.print("Digite o distrito do candidato: ");
//@    	  String district = readString();
//@          printInterface.print("Digite o número do candidato: ");
//@          int number = readInt();
//@          
//@          switch (opt) {
//@        	case 1: 
//@        		Mayor mayorCandidate = urnaModel.getMayorByNumber(district, number);
//@        		urnaModel.removeCandidate(tseProfessional, mayorCandidate, password);
//@        	case 2:
//@        		CityCouncilor cityCouncilorCandidate = urnaModel.getCityCouncilorByNumber(district, number);
//@        		urnaModel.removeCandidate(tseProfessional, cityCouncilorCandidate, password);
//@        	default:
//@        		printInterface.invalidCommand();
//@          }
//@      }
//@    
//@}
//@
// #endif
