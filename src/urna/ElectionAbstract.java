package urna;
// import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
// import java.util.stream.Collectors;
// import java.text.DecimalFormat;

public class ElectionCrime {
    protected final String password;
    protected boolean status;

    protected Map<Voter, Integer> voters = new HashMap<Voter, Integer>();
    protected Map<String, Candidate> candidates = new HashMap<String, Candidate>();

    protected int nullVotes;
    protected int protestVotes;

    protected boolean segundoTurno = false;

    //Singleton
    protected static ElectionCrime instance;


    private static class Builder {
        protected String password;
    
        public Builder password(String password) {
          this.password = password;
          return this;
        }
    
        public ElectionCrime build() {
          if (password == null)
            throw new IllegalArgumentException("password mustn't be null");
    
          if (password.isEmpty())
            throw new IllegalArgumentException("password mustn't be empty");
    
          return new ElectionCrime(this);
        }
    }

    protected ElectionCrime(Builder builder) {
        this.password = builder.password;
        this.status = false;

        this.nullVotes = 0;
        this.protestVotes = 0;
    }


    public static ElectionCrime getInstance(String electionPassword) {
        if(instance==null) instance = new ElectionCrime.Builder().password(electionPassword).build();
        return instance;
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
    //@    this.dynamicStatistics.notify(candidate, "Valid");
        // #endif
    };

    public void computeNullVote(Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        voters.put(voter, 1);
        this.nullVotes++;
        // #if EstatisticasDinamicas
    //@    this.dynamicStatistics.notify(null, "Valid");
        // #endif
    };

    public void computeProtestVote(Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        voters.put(voter, 1);
        this.protestVotes++;
        // #if EstatisticasDinamicas
    //@    this.dynamicStatistics.notify(null, "Protest");
        // #endif
    }; 


    public void start(String password) {
        if (!isValid(password))
            throw new Warning("Senha inválida");
    
        this.status = true;
    }
    
    public void finish(String password) {
        if (!isValid(password))
            throw new Warning("Senha inválida");
        
        // #if SegundoTurno
        // calculate conditions for second round
        int totalVotes = 0;
        int maxVotes = 0;
        for (Candidate candidate : candidates.values()) {
            totalVotes += candidate.numVotes;
            if (candidate.numVotes > maxVotes) {
                maxVotes = candidate.numVotes;
            }
        }
        // check if a candidate had more than 50% of votes
        if (!this.segundoTurno && ((double)maxVotes/(double)totalVotes) <= 0.5) {
            this.setupSecondRound();
            return;
        }
        // #endif

        this.status = false;
    }

    // #if SegundoTurno
    private void setupSecondRound() {
        
    }
}
