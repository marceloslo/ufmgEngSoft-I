package urna;
import java.util.HashMap;
import java.util.Map;

public class UrnaFederal extends Urna{


    private static int MAX_OPTIONS = 5; 
    private static int MIN_OPTIONS = 1;
    private static int protestVote = 0;

    private Map<Integer, String> federalDictionary = new HashMap<>();

    public UrnaFederal(String password){
        super(password);

        federalDictionary.put(1, "Presidente");
        // federalDictionary.put(2, "Governor");
        federalDictionary.put(2, "Deputado Federal");
        // federalDictionary.put(4, "StateDeputy");
        // federalDictionary.put(5, "Senator");
    }

  protected static Voter getVoter() {
    printInterface.print("Insira seu título de eleitor:");
    String electoralCard = readString();
    Voter voter = urnaModel.getVoter(electoralCard);//VoterMap.get(electoralCard);
    if (voter == null) {
      printInterface.print("Eleitor não encontrado, por favor confirme se a entrada está correta e tente novamente");
    } else {
      printInterface.print("Olá, você é " + voter.name + " de " + voter.state + "?\n");
      printInterface.displayYesOrNo();
      int command = readInt();
      if (command == 1)
        return voter;
      else if (command == 2)
        printInterface.print("Ok, você será redirecionado para o menu inicial");
      else {
        printInterface.print("Entrada inválida, tente novamente");
        return getVoter();
      }
    }
    return null;
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
          "OBS:\n- A partir de agora caso você queira votar nulo você deve usar um numero composto de 0 (00 e 0000)\n- A partir de agora caso você queira votar branco você deve escrever br\n");
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
        urnaModel.vote(Integer.toString(protestVote), "President");
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
    else{
        FederalDeputy media_candidate = urnaModel.getFederalDeputyByNumber(voter.state, voteNumber);//currentElection.getFederalDeputyByNumber(voter.state, voteNumber);
        candidate = media_candidate;

    }

    return candidate;
  }

  protected boolean voteForCandidate(Voter voter, Integer key, String candidateType){
    printInterface.askForCandidateNumber(candidateType);
    String vote = readString();
    
    if (vote.equals("ext") || vote.equals("br"))
       return checkForProtestVote(vote, candidateType);
    
    int voteNumber = Integer.parseInt(vote);
    if (voteNumber == 0)
        return checkForNullVote(vote, candidateType);

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
  }

  protected void addCandidate(TSEEmployee tseProfessional) {
    printInterface.federalElectionsOptionsToVote();

    int candidateType = readInt();

    if (candidateType > MIN_OPTIONS || candidateType <= MAX_OPTIONS) {
      printInterface.invalidCommand();
      addCandidate(tseProfessional);
    }

    printInterface.print("Qual o nome do candidato?");
    String name = readString();

    printInterface.print("Qual o numero do candidato?");
    int number = readInt();

    printInterface.print("Qual o partido do candidato?");
    String party = readString();

    Candidate candidate = null;
    if (candidateType >= 2) {
      printInterface.print("Qual o estado do candidato?");
      String state = readString();

      printInterface.print("\nCadastrar o candidato deputado federal " + name + " Nº " + number + " do " + party + "(" + state + ")?");
      candidate = new FederalDeputy.Builder()
          .name(name)
          .number(123)
          .party(party)
          .state(state)
          .build();
    } else if (candidateType == 1) {
      printInterface.print("\nCadastrar o candidato a presidente " + name + " Nº " + number + " do " + party + "?");
      candidate = new President.Builder()
          .name(name)
          .number(123)
          .party(party)
          .build();
    }

    printInterface.displayYesOrNo();
    int save = readInt();
    if (save == 1 && candidate != null) {
      printInterface.print("Insira a senha da urna");
      String pwd = readString();
      urnaModel.addCandidate(tseProfessional,candidate,pwd);//tseProfessional.addCandidate(candidate, currentElection, pwd);
      printInterface.print("Candidato cadastrado com sucesso");
    }
  }




  protected void removeCandidate(TSEEmployee tseProfessional) {
    printInterface.federalElectionsOptionsToVote();
    int candidateType = readInt();

    if (candidateType > MAX_OPTIONS || candidateType < MIN_OPTIONS) {
      printInterface.invalidCommand();
      removeCandidate(tseProfessional);
    }

    Candidate candidate = null;
    printInterface.print("Qual o numero do candidato?");
    int number = readInt();
    if (candidateType == 2) {
      printInterface.print("Qual o estado do candidato?");
      String state = readString();

      candidate = urnaModel.getFederalDeputyByNumber(state, number);//currentElection.getFederalDeputyByNumber(state, number);
      if (candidate == null) {
        printInterface.candidateNotFound();
        return;
      }
      printInterface.print("/Remover o candidato a deputado federal " + candidate.name + " Nº " + candidate.number + " do "
          + candidate.party + "("
          + ((FederalDeputy) candidate).state + ")?");
    } else if (candidateType == 1) {
      candidate = urnaModel.getPresidentByNumber(number);//currentElection.getPresidentByNumber(number);
      if (candidate == null) {
        printInterface.candidateNotFound();
        return;
      }
      printInterface.print("/Remover o candidato a presidente " + candidate.name + " Nº " + candidate.number + " do " + candidate.party
          + "?");
    }

      printInterface.displayYesOrNo();
    int remove = readInt();
    if (remove == 1) {
      printInterface.askForPassword();
      String pwd = readString();
      urnaModel.removeCandidate(tseProfessional,candidate,pwd);//tseProfessional.removeCandidate(candidate, currentElection, pwd);
      printInterface.print("Candidato removido com sucesso");
    }
  }
}