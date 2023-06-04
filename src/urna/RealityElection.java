package urna;
//#if RealityShow
//@
//@import java.text.DecimalFormat;
//@import java.util.ArrayList;
//@import java.util.Map;
//@import java.util.stream.Collectors;
//@
//@public class RealityElection extends AbstractElection {
//@
//@    public RealityElection(String password){
//@        super(password);
//@        this.status = false;
//@        this.protestVotes = 0;
//@        this.nullVotes = 0;
//@    }
//@
//@    @Override
//@    public void addCandidate(Candidate candidate, String password) {
//@        String id = Integer.toString(candidate.number);
//@        
//@        RealityCandidate aux = (RealityCandidate) candidate;
//@        id = aux.nationality + id;
//@        
//@        if (candidates.get(id) != null) 
//@            throw new StopTrap("Candidato já cadastrado");
//@
//@        if (!isValid(password))
//@            throw new Warning("Senha inválida");
//@
//@        candidates.put(id, candidate);
//@    }
//@
//@    @Override
//@    public void removeCandidate(Candidate candidate, String password) {
//@        String id = Integer.toString(candidate.number);
//@        
//@        RealityCandidate aux = (RealityCandidate) candidate;
//@        id = aux.nationality + id;
//@        
//@        if (candidates.get(id) == null)
//@            throw new StopTrap("Candidato não cadastrado");
//@
//@        if (!isValid(password))
//@            throw new Warning("Senha inválida");
//@
//@        candidates.remove(id);
//@    }
//@    
//@    @Override
//@    public String getResults(String password) {
//@        if (!isValid(password))
//@            throw new Warning("Senha inválida");
//@
//@        if (this.status)
//@            throw new StopTrap("Eleição ainda não finalizou, não é possível gerar o resultado");
//@
//@        var decimalFormater = new DecimalFormat("0.00");
//@        var candidateRank = new ArrayList<Candidate>();
//@
//@        var builder = new StringBuilder();
//@
//@        builder.append("Resultado da eleicao:\n");
//@
//@        int totalVotes = 0;
//@        for (Map.Entry<String, Candidate> candidateEntry : this.candidates.entrySet()) {
//@            Candidate candidate = candidateEntry.getValue();
//@            totalVotes += candidate.numVotes;
//@            candidateRank.add(candidate);
//@        }
//@
//@        var sortedCandidateRank = candidateRank.stream()
//@            .sorted((o1, o2) -> o1.numVotes == o2.numVotes ? 0 : o1.numVotes < o2.numVotes ? 1 : -1)
//@            .collect(Collectors.toList());
//@        
//@        RealityCandidate mostVoted = (RealityCandidate) sortedCandidateRank.get(0);
//@        builder.append("  Votos participantes do reality show:\n");
//@        builder.append("  Total: " + totalVotes + "\n");
//@            
        // #if ExibirDerrotados
//@        builder.append("\tNumero - Programa - Nome - Nacionalidade - Votos  - % dos votos totais\n");
//@        for (Candidate candidate : sortedCandidateRank) {
//@            RealityCandidate realityParticipant = (RealityCandidate) candidate;
//@            builder.append("\t" + realityParticipant.number + " - " + realityParticipant.party + " - " + realityParticipant.name + " - " + realityParticipant.nationality +" - "+
//@                + realityParticipant.numVotes + " - "
//@                + decimalFormater.format((double) realityParticipant.numVotes / (double) totalVotes * 100)
//@                + "%\n");
//@        }
        // #endif
//@
//@        builder.append("\n\n  Participante de reality show vencedor:\n");
//@        builder.append("  " + mostVoted.name + " do programa " + mostVoted.party + " e do país" + mostVoted.nationality + " com "
//@            + decimalFormater.format((double) mostVoted.numVotes / (double) totalVotes * 100) + "% dos votos\n");
//@            
//@        builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
//@        return builder.toString();
//@    }
//@}
// #endif
