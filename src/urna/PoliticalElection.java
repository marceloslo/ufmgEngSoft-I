package urna;

// #if Federal || Municipal
import java.util.ArrayList;
import java.util.Map;
import java.util.stream.Collectors;
import java.text.DecimalFormat;

public class PoliticalElection extends AbstractElection {

    public PoliticalElection(String password){
        super(password);
        this.status = false;
        this.protestVotes = 0;
        this.nullVotes = 0;
    }

    @Override
    public void addCandidate(Candidate candidate, String password) {
        String id = Integer.toString(candidate.number);

        if(candidate instanceof FederalDeputy) {
            FederalDeputy aux = (FederalDeputy) candidate;
            id = aux.state + id;
        }
        else if(candidate instanceof StateDeputy) {
            StateDeputy aux = (StateDeputy) candidate;
            id = aux.state + id;
        }
        else if(candidate instanceof Senator) {
            Senator aux = (Senator) candidate;
            id = aux.state + id;
        }
        else if(candidate instanceof Governor) {
            Governor aux = (Governor) candidate;
            id = aux.state + id;
        }
        else if(candidate instanceof Mayor) {
            Mayor aux = (Mayor) candidate;
            id = aux.district + id;
        }
        else if(candidate instanceof CityCouncilor) {
            CityCouncilor aux = (CityCouncilor) candidate;
            id = aux.district + id;
        }
        
        if (candidates.get(id) != null) 
            throw new StopTrap("Candidato já cadastrado");

        if (!isValid(password))
            throw new Warning("Senha inválida");

        candidates.put(id, candidate);
    }
    
    @Override
    public void removeCandidate(Candidate candidate, String password) {
        String id = Integer.toString(candidate.number);
        
        if(candidate instanceof FederalDeputy) {
            FederalDeputy aux = (FederalDeputy) candidate;
            id = aux.state + id;
        }
        else if(candidate instanceof StateDeputy) {
            StateDeputy aux = (StateDeputy) candidate;
            id = aux.state + id;
        }
        else if(candidate instanceof Senator) {
            Senator aux = (Senator) candidate;
            id = aux.state + id;
        }
        else if(candidate instanceof Governor) {
            Governor aux = (Governor) candidate;
            id = aux.state + id;
        }
        else if(candidate instanceof Mayor) {
            Mayor aux = (Mayor) candidate;
            id = aux.district + id;
        }
        else if(candidate instanceof CityCouncilor) {
            CityCouncilor aux = (CityCouncilor) candidate;
            id = aux.district + id;
        }
        
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
        
        Candidate mostVoted = sortedCandidateRank.get(0);
        if(mostVoted instanceof President){
        	
            builder.append("  Votos presidente:\n");
            builder.append("  Total: " + totalVotes + "\n");
            builder.append("  Votos nulos: " + nullVotes + " ("
                + decimalFormater.format((double) nullVotes / (double) totalVotes * 100) + "%)\n");
            builder.append("  Votos brancos: " + protestVotes + " ("
                + decimalFormater.format((double) protestVotes / (double) totalVotes * 100) + "%)\n");
            
            // #if ExibirDerrotados
            builder.append("\tNumero - Partido - Nome  - Votos  - % dos votos totais\n");
            for (Candidate candidate : sortedCandidateRank) {
                President president = (President) candidate;
                builder.append("\t" + president.number + " - " + president.party + " - " + president.name + " - "
                    + president.numVotes + " - "
                    + decimalFormater.format((double) president.numVotes / (double) totalVotes * 100)
                    + "%\n");
            }
            // #endif

            President electPresident = (President) mostVoted;
            builder.append("\n\n  Presidente eleito:\n");
            builder.append("  " + electPresident.name + " do " + electPresident.party + " com "
                + decimalFormater.format((double) electPresident.numVotes / (double) totalVotes * 100) + "% dos votos\n");
        }
        else if(mostVoted instanceof Governor){
            builder.append("\n\n  Votos para Governador:\n");
            builder.append("  Votos nulos: " + nullVotes + " ("
                + decimalFormater.format((double) nullVotes / (double) totalVotes * 100) + "%)\n");
            builder.append("  Votos brancos: " + protestVotes + " ("
                + decimalFormater.format((double) protestVotes / (double) totalVotes * 100) + "%)\n");
            builder.append("  Total: " + totalVotes + "\n");
            // #if ExibirDerrotados
            builder.append("\tNumero - Partido - Nome - Estado - Votos - % dos votos totais\n");
            for (Candidate candidate : sortedCandidateRank) {
                FederalLegislativeCandidate fd = (FederalLegislativeCandidate) candidate;
                builder.append(
                "\t" + fd.number + " - " + fd.party + " - " + fd.state + " - " + fd.name + " - "
                    + fd.numVotes + " - "
                    + decimalFormater.format((double) fd.numVotes / (double) totalVotes * 100)
                    + "%\n");
            }
            // #endif
            Governor electGovernor = (Governor) mostVoted;
            builder.append("\n\n  Governador eleito:\n");
            builder.append(" " + electGovernor.name + " do " + electGovernor.party + " com "
                + decimalFormater.format((double) electGovernor.numVotes / (double) totalVotes * 100) + "% dos votos\n");
        }
        else if(mostVoted instanceof FederalLegislativeCandidate){
            builder.append("\n\n  Votos para "+ mostVoted.getClass().getSimpleName().replaceAll("(.)([A-Z])", "$1 $2") + ":\n");
            builder.append("  Votos nulos: " + nullVotes + " ("
                + decimalFormater.format((double) nullVotes / (double) totalVotes * 100) + "%)\n");
            builder.append("  Votos brancos: " + protestVotes + " ("
                + decimalFormater.format((double) protestVotes / (double) totalVotes * 100) + "%)\n");
            builder.append("  Total: " + totalVotes + "\n");
            // #if ExibirDerrotados
            builder.append("\tNumero - Partido - Nome - Estado - Votos - % dos votos totais\n");
            for (Candidate candidate : sortedCandidateRank) {
                FederalLegislativeCandidate fl = (FederalLegislativeCandidate) candidate;
                builder.append(
                "\t" + fl.number + " - " + fl.party + " - " + fl.state + " - " + fl.name + " - "
                    + fl.numVotes + " - "
                    + decimalFormater.format((double) fl.numVotes / (double) totalVotes * 100)
                    + "%\n");
            }
            // #endif
            FederalLegislativeCandidate firstLegislator = (FederalLegislativeCandidate) mostVoted;
            FederalLegislativeCandidate secondLegislator = (FederalLegislativeCandidate) sortedCandidateRank.get(1);
            builder.append("\n\n"+ mostVoted.getClass().getSimpleName().replaceAll("(.)([A-Z])", "$1 $2") + " eleitos:\n");
            builder.append("  1º " + firstLegislator.name + " do " + firstLegislator.party + " com "
                + decimalFormater.format((double) firstLegislator.numVotes / (double) totalVotes * 100) + "% dos votos\n");
            builder.append("  2º " + secondLegislator.name + " do " + secondLegislator.party + " com "
                + decimalFormater.format((double) secondLegislator.numVotes / (double) totalVotes * 100) + "% dos votos\n");
        }
        else if(mostVoted instanceof Mayor){
            builder.append("\n\n  Votos para Prefeito:\n");
            builder.append("  Votos nulos: " + nullVotes + " ("
                + decimalFormater.format((double) nullVotes / (double) totalVotes * 100) + "%)\n");
            builder.append("  Votos brancos: " + protestVotes + " ("
                + decimalFormater.format((double) protestVotes / (double) totalVotes * 100) + "%)\n");
            builder.append("  Total: " + totalVotes + "\n");
            // #if ExibirDerrotados
            builder.append("\tNumero - Partido - Nome - Distrito - Votos - % dos votos totais\n");
            for (Candidate candidate : sortedCandidateRank) {
                Mayor fd = (Mayor) candidate;
                builder.append(
                "\t" + fd.number + " - " + fd.party + " - " + fd.district + " - " + fd.name + " - "
                    + fd.numVotes + " - "
                    + decimalFormater.format((double) fd.numVotes / (double) totalVotes * 100)
                    + "%\n");
            }
            // #endif
            Mayor electMayor = (Mayor) mostVoted;
            builder.append("\n\n  Prefeito eleito:\n");
            builder.append(" " + electMayor.name + " do " + electMayor.party + " com "
                + decimalFormater.format((double) electMayor.numVotes / (double) totalVotes * 100) + "% dos votos\n");
        }
        else if(mostVoted instanceof CityCouncilor){
            builder.append("\n\n  Votos para Vereador:\n");
            builder.append("  Votos nulos: " + nullVotes + " ("
                + decimalFormater.format((double) nullVotes / (double) totalVotes * 100) + "%)\n");
            builder.append("  Votos brancos: " + protestVotes + " ("
                + decimalFormater.format((double) protestVotes / (double) totalVotes * 100) + "%)\n");
            builder.append("  Total: " + totalVotes + "\n");
            // #if ExibirDerrotados
            builder.append("\tNumero - Partido - Nome - Distrito - Votos - % dos votos totais\n");
            for (Candidate candidate : sortedCandidateRank) {
                CityCouncilor fd = (CityCouncilor) candidate;
                builder.append(
                "\t" + fd.number + " - " + fd.party + " - " + fd.district + " - " + fd.name + " - "
                    + fd.numVotes + " - "
                    + decimalFormater.format((double) fd.numVotes / (double) totalVotes * 100)
                    + "%\n");
            }
            // #endif
            CityCouncilor electCouncilor = (CityCouncilor) mostVoted;
            builder.append("\n\n  Vereador eleito:\n");
            builder.append(" " + electCouncilor.name + " do " + electCouncilor.party + " com "
                + decimalFormater.format((double) electCouncilor.numVotes / (double) totalVotes * 100) + "% dos votos\n");
        }
        builder.append("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
        return builder.toString();
  }

    @Override
    protected boolean checkSecondRoundConditions() {
    	Map.Entry<String,Candidate> entry = candidates.entrySet().iterator().next();
    	if(entry.getValue() instanceof President || entry.getValue() instanceof Mayor || entry.getValue() instanceof Governor) {
    		return true;
    	}
    	return false;
    }
}

// #endif
