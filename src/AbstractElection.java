package urna;
// import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
// import java.util.stream.Collectors;
// import java.text.DecimalFormat;

public abstract class AbstractElection{
    protected final String password;
    protected boolean status;

    protected Map<Voter, Integer> voters = new HashMap<Voter, Integer>();
    protected Map<String, Candidate> candidates = new HashMap<String, Candidate>();

    protected int nullVotes;
    protected int protestVotes;

    protected boolean segundoTurno = false;

    // #if EstatisticasDinamicas
    protected StatisticsManager dynamicStatistics = new StatisticsManager();
    // #endif


    protected AbstractElection(String password){
        if (password == null)
            throw new IllegalArgumentException("password mustn't be null");
    
          if (password.isEmpty())
            throw new IllegalArgumentException("password mustn't be empty");
        
        this.password = password;
        // #if EstatisticasDinamicas
         dynamicStatistics.add("Default",new VotesStatisticsObserver());
        // #endif
    }

    public Boolean isValid(String password) {
        return this.password.equals(password);
    }

    
    public void computeVote(Candidate candidate, Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        candidate.numVotes++;
        voters.put(voter, 1);
        // #if EstatisticasDinamicas
         this.dynamicStatistics.notify("Default", "Valid", candidate);
        // #endif
    }

    public void computeNullVote(Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        voters.put(voter, 1);
        this.nullVotes++;
        // #if EstatisticasDinamicas
         this.dynamicStatistics.notify("Default", "Null");
        // #endif
    }

    public void computeProtestVote(Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        voters.put(voter, 1);
        this.protestVotes++;
        // #if EstatisticasDinamicas
         this.dynamicStatistics.notify("Default", "Protest");
        // #endif
    }
    public Candidate getCandidateByNumber(String number) {
        return candidates.get(number);
    }


    public void addCandidate(Candidate candidate, String password) {
        String id = Integer.toString(candidate.number);

        if(candidate instanceof FederalDeputy) {
            FederalDeputy aux = (FederalDeputy) candidate;
            id = aux.state + id;
        }
        if(candidate instanceof StateDeputy) {
            StateDeputy aux = (StateDeputy) candidate;
            id = aux.state + id;
        }
        if(candidate instanceof Senator) {
            Senator aux = (Senator) candidate;
            id = aux.state + id;
        }
        if(candidate instanceof Governor) {
            Governor aux = (Governor) candidate;
            id = aux.state + id;
        }
        if(candidate instanceof Mayor) {
            Mayor aux = (Mayor) candidate;
            id = aux.district + id;
        }
        if(candidate instanceof CityCouncilor) {
            CityCouncilor aux = (CityCouncilor) candidate;
            id = aux.district + id;
        }
        if(candidate instanceof RealityCandidate) {
            RealityCandidate aux = (RealityCandidate) candidate;
            id = aux.nationality + id;
        }
        if (candidates.get(id) != null) 
            throw new StopTrap("Candidato já cadastrado");

        if (!isValid(password))
            throw new Warning("Senha inválida");

        candidates.put(id, candidate);
    }


    public void removeCandidate(Candidate candidate, String password) {
        String id = Integer.toString(candidate.number);
        if (candidates.get(id) == null)
            throw new StopTrap("Candidato não cadastrado");

        if (!isValid(password))
            throw new Warning("Senha inválida");

        candidates.remove(id);
        
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
//@        // calculate conditions for second round
//@        int totalVotes = 0;
//@        int maxVotes = 0;
//@        for (Candidate candidate : candidates.values()) {
//@            totalVotes += candidate.numVotes;
//@            if (candidate.numVotes > maxVotes) {
//@                maxVotes = candidate.numVotes;
//@            }
//@        }
//@        // check if a candidate had more than 50% of votes
//@        // if (!this.segundoTurno && ((double)maxVotes/(double)totalVotes) <= 0.5) {
//@        //     this.setupSecondRound();
//@        //     return;
//@        // }
        // #endif
        this.status = false;
    }

    public String getResults(String password) {
    	return "";
    }
}
