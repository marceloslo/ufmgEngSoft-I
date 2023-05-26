package urna;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

public class Election {
  private final String password;

  private boolean status;

  private int nullPresidentVotes;

  private int nullFederalDeputyVotes;

  private int presidentProtestVotes;

  private int federalDeputyProtestVotes;

  // Na prática guardaria uma hash do eleitor
  private Map<Voter, Integer> votersPresident = new HashMap<Voter, Integer>();

  // Na prática guardaria uma hash do eleitor
  private Map<Voter, Integer> votersFederalDeputy = new HashMap<Voter, Integer>();

  private Map<Integer, President> presidentCandidates = new HashMap<Integer, President>();

  private Map<String, FederalDeputy> federalDeputyCandidates = new HashMap<String, FederalDeputy>();

  private Map<Voter, FederalDeputy> tempFDVote = new HashMap<Voter, FederalDeputy>();
  
  // Segundo turno afeta a função finish, cria a função setupSecondRound e afeta a função voterMenu da classe Urna
  // #if SegundoTurno
//@  protected boolean segundoTurno = false;
  // #endif
  
  // Estatisticas dinamicas afeta o construtor, as 3 funcoes que computam voto e o setup de segundo turno
  // #if EstatisticasDinamicas
  private StatisticsManager dynamicStatistics = new StatisticsManager();
  // #endif

  public static class Builder {
    protected String password;

    public Builder password(String password) {
      this.password = password;
      return this;
    }

    public Election build() {
      if (password == null)
        throw new IllegalArgumentException("password mustn't be null");

      if (password.isEmpty())
        throw new IllegalArgumentException("password mustn't be empty");

      return new Election(this.password);
    }
  }

  protected Election(
      String password) {
    this.password = password;
    this.status = false;
    this.nullFederalDeputyVotes = 0;
    this.nullPresidentVotes = 0;
    this.presidentProtestVotes = 0;
    this.federalDeputyProtestVotes = 0;
    
    // #if EstatisticasDinamicas
    dynamicStatistics.add("President",new VotesStatisticsObserver());
    dynamicStatistics.add("FederalDeputy",new VotesStatisticsObserver());
    // #endif
  }

  private Boolean isValid(String password) {
    return this.password.equals(password);
  }

  public void computeVote(Candidate candidate, Voter voter) {
    if (candidate instanceof President) {
      if (votersPresident.get(voter) != null && votersPresident.get(voter) >= 1)
        throw new StopTrap("Você não pode votar mais de uma vez para presidente");

      candidate.numVotes++;
      votersPresident.put(voter, 1);
    } else if (candidate instanceof FederalDeputy) {
      if (votersFederalDeputy.get(voter) != null && votersFederalDeputy.get(voter) >= 2)
        throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");

      if (tempFDVote.get(voter) != null && tempFDVote.get(voter).equals(candidate))
        throw new Warning("Você não pode votar mais de uma vez em um mesmo candidato");

      candidate.numVotes++;
      if (votersFederalDeputy.get(voter) == null) {
        votersFederalDeputy.put(voter, 1);
        tempFDVote.put(voter, (FederalDeputy) candidate);
      } else {
        votersFederalDeputy.put(voter, this.votersFederalDeputy.get(voter) + 1);
        tempFDVote.remove(voter);
      }
    }
    // #if EstatisticasDinamicas
    this.dynamicStatistics.notify(candidate, "Valid");
    // #endif
  };

  public void computeNullVote(String type, Voter voter) {
    if (type.equals("President")) {
      if (this.votersPresident.get(voter) != null && votersPresident.get(voter) >= 1)
        throw new StopTrap("Você não pode votar mais de uma vez para presidente");

      this.nullPresidentVotes++;
      votersPresident.put(voter, 1);
    } else if (type.equals("FederalDeputy")) {
      if (this.votersFederalDeputy.get(voter) != null && this.votersFederalDeputy.get(voter) >= 2)
        throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");

      this.nullFederalDeputyVotes++;
      if (this.votersFederalDeputy.get(voter) == null)
        votersFederalDeputy.put(voter, 1);
      else
        votersFederalDeputy.put(voter, this.votersFederalDeputy.get(voter) + 1);
    }
    // #if EstatisticasDinamicas
    this.dynamicStatistics.notify(type,"Null");
    // #endif
  }

  public void computeProtestVote(String type, Voter voter) {
    if (type.equals("President")) {
      if (this.votersPresident.get(voter) != null && votersPresident.get(voter) >= 1)
        throw new StopTrap("Você não pode votar mais de uma vez para presidente");

      this.presidentProtestVotes++;
      votersPresident.put(voter, 1);
    } else if (type.equals("FederalDeputy")) {
      if (this.votersFederalDeputy.get(voter) != null && this.votersFederalDeputy.get(voter) >= 2)
        throw new StopTrap("Você não pode votar mais de uma vez para deputado federal");

      this.federalDeputyProtestVotes++;
      if (this.votersFederalDeputy.get(voter) == null)
        votersFederalDeputy.put(voter, 1);
      else
        votersFederalDeputy.put(voter, this.votersFederalDeputy.get(voter) + 1);
    }
    // #if EstatisticasDinamicas
    this.dynamicStatistics.notify(type,"Blank");
    // #endif
  }

  public boolean getStatus() {
    return this.status;
  }

  public void start(String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");

    this.status = true;
  }

  public void finish(String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");
    
    // #if SegundoTurno
//@    // calculate conditions for second round
//@    int totalVotesP = 0;
//@    int maxVotes=0;
//@    for (Map.Entry<Integer, President> candidateEntry : presidentCandidates.entrySet()) {
//@      President candidate = candidateEntry.getValue();
//@      totalVotesP += candidate.numVotes;
//@      if(candidate.numVotes >= maxVotes) {
//@    	  maxVotes=candidate.numVotes;
//@      }
//@    }
//@    
//@    // check if a candidate had more than 50% of votes
//@    if (!this.segundoTurno && ((double)maxVotes/(double)totalVotesP) <= 0.5) {
//@    	this.setupSecondRound();
//@    	return;
//@    }
    // #endif

    this.status = false;
  }
  
  // #if SegundoTurno
//@  private void setupSecondRound() {
//@	    
//@	  President p1 = null;
//@	  President p2 = null;
//@	  for (Map.Entry<Integer, President> candidateEntry : presidentCandidates.entrySet()) {
//@	       President candidate = candidateEntry.getValue();
//@	       if(p1==null || candidate.numVotes > p1.numVotes) {
//@	    	   p2 = p1;
//@	    	   p1 = candidate;
//@	       } else if (p2==null || candidate.numVotes > p2.numVotes) {
//@	    	   p2=candidate;
//@	       }
//@	  }
//@	  //reset attributes
//@	  this.segundoTurno=true;
//@	  this.nullPresidentVotes = 0;
//@	  this.presidentProtestVotes = 0;
//@	  this.presidentCandidates = new HashMap<Integer, President>();
//@	  this.votersPresident = new HashMap<Voter, Integer>();
//@	  p1 = new President.Builder().name(p1.name).number(p1.number).party(p1.party).build();
//@	  p2 = new President.Builder().name(p2.name).number(p2.number).party(p2.party).build();
//@	  
	  //#if EstatisticasDinamicas
//@	  dynamicStatistics.remove("President");
//@	  dynamicStatistics.add("President",new VotesStatisticsObserver());
	  //#endif
//@	  
//@	  //add presidential candidates for second round
//@	  this.presidentCandidates.put(p1.number,p1);
//@	  this.presidentCandidates.put(p2.number,p2);
//@	  
//@	  
//@	  System.out.println("Nenhum candidato conseguiu mais de 50% dos votos validos, comecando segundo turno, favor iniciar nova sessão.");
//@	  System.out.println("Candidatos:");
//@	  System.out.println("\t"+p1.name+" - Numero: "+String.valueOf(p1.number)+" - Partido: "+p1.party);
//@	  System.out.println("\t"+p2.name+" - Numero: "+String.valueOf(p2.number)+" - Partido: "+p2.party);
//@	  
//@  }
  // #endif
  

  public President getPresidentByNumber(int number) {
    return this.presidentCandidates.get(number);
  }

  public void addPresidentCandidate(President candidate, String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");

    if (this.presidentCandidates.get(candidate.number) != null)
      throw new Warning("Numero de candidato indisponível");

    this.presidentCandidates.put(candidate.number, candidate);

  }

  public void removePresidentCandidate(President candidate, String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");

    this.presidentCandidates.remove(candidate.number);
  }

  public FederalDeputy getFederalDeputyByNumber(String state, int number) {
    return this.federalDeputyCandidates.get(state + number);
  }

  public void addFederalDeputyCandidate(FederalDeputy candidate, String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");

    if (this.federalDeputyCandidates.get(candidate.state + candidate.number) != null)
      throw new Warning("Numero de candidato indisponível");

    this.federalDeputyCandidates.put(candidate.state + candidate.number, candidate);
  }

  public void removeFederalDeputyCandidate(FederalDeputy candidate, String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");

    this.federalDeputyCandidates.remove(candidate.state + candidate.number);
  }

  public String getResults(String password) {
    if (!isValid(password))
      throw new Warning("Senha inválida");

    if (this.status)
      throw new StopTrap("Eleição ainda não finalizou, não é possível gerar o resultado");

    var decimalFormater = new DecimalFormat("0.00");
    var presidentRank = new ArrayList<President>();
    var federalDeputyRank = new ArrayList<FederalDeputy>();

    var builder = new StringBuilder();

    builder.append("Resultado da eleicao:\n");

    int totalVotesP = presidentProtestVotes + nullPresidentVotes;
    for (Map.Entry<Integer, President> candidateEntry : presidentCandidates.entrySet()) {
      President candidate = candidateEntry.getValue();
      totalVotesP += candidate.numVotes;
      presidentRank.add(candidate);
    }

    int totalVotesFD = federalDeputyProtestVotes + nullFederalDeputyVotes;
    for (Map.Entry<String, FederalDeputy> candidateEntry : federalDeputyCandidates.entrySet()) {
      FederalDeputy candidate = candidateEntry.getValue();
      totalVotesFD += candidate.numVotes;
      federalDeputyRank.add(candidate);
    }

    var sortedFederalDeputyRank = federalDeputyRank.stream()
        .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
        .collect(Collectors.toList());

    var sortedPresidentRank = presidentRank.stream()
        .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
        .collect(Collectors.toList());

    builder.append("  Votos presidente:\n");
    builder.append("  Total: " + totalVotesP + "\n");
    builder.append("  Votos nulos: " + nullPresidentVotes + " ("
        + decimalFormater.format((double) nullPresidentVotes / (double) totalVotesFD * 100) + "%)\n");
    builder.append("  Votos brancos: " + presidentProtestVotes + " ("
        + decimalFormater.format((double) presidentProtestVotes / (double) totalVotesFD * 100) + "%)\n");
    builder.append("\tNumero - Partido - Nome  - Votos  - % dos votos totais\n");
    for (President candidate : sortedPresidentRank) {
      builder.append("\t" + candidate.number + " - " + candidate.party + " - " + candidate.name + " - "
          + candidate.numVotes + " - "
          + decimalFormater.format((double) candidate.numVotes / (double) totalVotesP * 100)
          + "%\n");
    }

    President electPresident = sortedPresidentRank.get(0);
    builder.append("\n\n  Presidente eleito:\n");
    builder.append("  " + electPresident.name + " do " + electPresident.party + " com "
        + decimalFormater.format((double) electPresident.numVotes / (double) totalVotesP * 100) + "% dos votos\n");
    builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");

    builder.append("\n\n  Votos deputado federal:\n");
    builder.append("  Votos nulos: " + nullFederalDeputyVotes + " ("
        + decimalFormater.format((double) nullFederalDeputyVotes / (double) totalVotesFD * 100) + "%)\n");
    builder.append("  Votos brancos: " + federalDeputyProtestVotes + " ("
        + decimalFormater.format((double) federalDeputyProtestVotes / (double) totalVotesFD * 100) + "%)\n");
    builder.append("  Total: " + totalVotesFD + "\n");
    builder.append("\tNumero - Partido - Nome - Estado - Votos - % dos votos totais\n");
    for (FederalDeputy candidate : sortedFederalDeputyRank) {
      builder.append(
          "\t" + candidate.number + " - " + candidate.party + " - " + candidate.state + " - " + candidate.name + " - "
              + candidate.numVotes + " - "
              + decimalFormater.format((double) candidate.numVotes / (double) totalVotesFD * 100)
              + "%\n");
    }

    FederalDeputy firstDeputy = sortedFederalDeputyRank.get(0);
    FederalDeputy secondDeputy = sortedFederalDeputyRank.get(1);
    builder.append("\n\n  Deputados eleitos:\n");
    builder.append("  1º " + firstDeputy.name + " do " + firstDeputy.party + " com "
        + decimalFormater.format((double) firstDeputy.numVotes / (double) totalVotesFD * 100) + "% dos votos\n");
    builder.append("  2º " + secondDeputy.name + " do " + secondDeputy.party + " com "
        + decimalFormater.format((double) secondDeputy.numVotes / (double) totalVotesFD * 100) + "% dos votos\n");

    return builder.toString();
  }

}
