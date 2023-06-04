package urna;
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

public class UniversityElection extends AbstractElection {

    int abstentionVotes;
    String votedPosition;
    
    public UniversityElection(String votedPosition, String password){
        super(password);
        this.status = false;
        this.protestVotes = 0;
        this.abstentionVotes = 0;
        this.votedPosition = votedPosition;
    }
    
    
    @Override
    public void computeVote(Candidate candidate, Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        candidate.numVotes+= voter.getVoteWeight();
        voters.put(voter, 1);
        // #if EstatisticasDinamicas
         this.dynamicStatistics.notify("Default", "Valid", candidate);
        // #endif
    }

    @Override
    public void computeNullVote(Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        voters.put(voter, 1);
        this.abstentionVotes+= voter.getVoteWeight();
        // #if EstatisticasDinamicas
         this.dynamicStatistics.notify("Default", "Null");
        // #endif
    }

    @Override
    public void computeProtestVote(Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        voters.put(voter, 1);
        this.protestVotes+= voter.getVoteWeight();
        // #if EstatisticasDinamicas
         this.dynamicStatistics.notify("Default", "Protest");
        // #endif
    }

    public Candidate getCandidateByNumber(String number) {
        return candidates.get(number);
    }

    @Override
    public void addCandidate(Candidate candidate, String password) {
        String id = Integer.toString(candidate.number);
        
        if (candidates.get(id) != null) 
            throw new StopTrap("Candidato já cadastrado");

        if (!isValid(password))
            throw new Warning("Senha inválida");

        candidates.put(id, candidate);
    }
    
    @Override
    public void removeCandidate(Candidate candidate, String password) {
        String id = Integer.toString(candidate.number);
        
        if (candidates.get(id) == null)
            throw new StopTrap("Candidato não cadastrado");

        if (!isValid(password))
            throw new Warning("Senha inválida");

        candidates.remove(id);
    }
    
    @Override
    public String getResults(String password) {
        if (!isValid(password))
            throw new Warning("Senha inválida");

        if (this.status)
            throw new StopTrap("Eleição ainda não finalizou, não é possível gerar o resultado");

        var decimalFormater = new DecimalFormat("0.00");
        var candidateRank = new ArrayList<Candidate>();

        var builder = new StringBuilder();

        builder.append("Resultado da eleicao:\n");

        int totalVotes = nullVotes + protestVotes;
        for (Map.Entry<String, Candidate> candidateEntry : this.candidates.entrySet()) {
            Candidate candidate = candidateEntry.getValue();
            totalVotes += candidate.numVotes;
            candidateRank.add(candidate);
        }

        var sortedCandidateRank = candidateRank.stream()
            .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
            .collect(Collectors.toList());
        
        UniversityCandidate mostVoted = (UniversityCandidate) sortedCandidateRank.get(0);
        
        builder.append("  Votos " + votedPosition + ":\n");

        builder.append("  Total: " + totalVotes + "\n");
        builder.append("  Votos brancos: " + protestVotes + " ("
            + decimalFormater.format((double) protestVotes / (double) totalVotes * 100) + "%)\n");
        builder.append("  Abstenções: " + abstentionVotes + " ("
            + decimalFormater.format((double) abstentionVotes / (double) totalVotes * 100) + "%)\n");
        
        // #if ExibirDerrotados
//@        builder.append("\tNumero - Departamento - Nome  - Votos  - % dos votos totais\n");
//@        for (Candidate candidate : sortedCandidateRank) {
//@            UniversityCandidate uniCandidate = (UniversityCandidate) candidate;
//@           builder.append("\t" + uniCandidate.number + " - " + uniCandidate.party + " - " + uniCandidate.name + " - "
//@                + uniCandidate.numVotes + " - "
//@                + decimalFormater.format((double) uniCandidate.numVotes / (double) totalVotes * 100)
//@                + "%\n");
//@        }
        // #endif

        UniversityCandidate electUniCandidate = (UniversityCandidate) mostVoted;
        builder.append("\n\n  " + votedPosition + " eleito:\n");
        builder.append("  " + electUniCandidate.name + " do " + electUniCandidate.party + " com "
            + decimalFormater.format((double) electUniCandidate.numVotes / (double) totalVotes * 100) + "% dos votos\n");
    
        builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        return builder.toString();
  }

}
