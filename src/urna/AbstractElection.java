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

    // #if SegundoTurno
    protected boolean segundoTurno;
    // #endif

    // #if EstatisticasDinamicas
//@    protected StatisticsManager dynamicStatistics = new StatisticsManager();
    // #endif


    protected AbstractElection(String password){
        if (password == null)
            throw new IllegalArgumentException("password mustn't be null");
    
          if (password.isEmpty())
            throw new IllegalArgumentException("password mustn't be empty");
        
        this.password = password;
        // #if EstatisticasDinamicas && (!RealityShow && !Universidade)
//@         dynamicStatistics.add("Default",new VotesStatisticsObserver());
        // #endif
        
        // #if EstatisticasDinamicas && RealityShow
//@        dynamicStatistics.add("Default",new RealityStatisticsObserver());
        // #endif
        
        //#if EstatisticasDinamicas && Universidade
//@        dynamicStatistics.add("Default",new UniversityStatisticsObserver());
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
//@         this.dynamicStatistics.notify("Default", "Valid", candidate);
        // #endif
    }

    public void computeNullVote(Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        voters.put(voter, 1);
        this.nullVotes++;
        // #if EstatisticasDinamicas
//@         this.dynamicStatistics.notify("Default", "Null");
        // #endif
    }

    public void computeProtestVote(Voter voter) {
        if (voters.get(voter) != null && voters.get(voter) >= 1)
            throw new StopTrap("Você não pode votar mais de uma vez para esse cargo");

        voters.put(voter, 1);
        this.protestVotes++;
        // #if EstatisticasDinamicas
//@         this.dynamicStatistics.notify("Default", "Protest");
        // #endif
    }
    public Candidate getCandidateByNumber(String number) {
        return candidates.get(number);
    }


    public void addCandidate(Candidate candidate, String password) {
        String id = Integer.toString(candidate.number);
        
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
        
        // #if SegundoTurno
        this.segundoTurno = checkSecondRoundConditions();
        // #endif
    
        this.status = true;
    }
    
    //return if finished successfully
    public boolean finish(String password) {
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
        //check if a candidate had more than 50% of votes
        if (this.segundoTurno && ((double)maxVotes/(double)totalVotes) <= 0.5) {
            this.setupSecondRound();
            return false;
        }
        // #endif
        this.status = false;
        return true;
    }

    public String getResults(String password) {
    	return "";
    }
    
    // #if SegundoTurno
    private void setupSecondRound() {
    	Candidate c1 = null;
    	Candidate c2 = null;
    	for (Map.Entry<String, Candidate> candidateEntry : candidates.entrySet()) {
    		Candidate candidate = candidateEntry.getValue();
    		if(c1==null || candidate.numVotes > c1.numVotes) {
    		    c2 = c1;
    		    c1 = candidate;
    		} else if (c2==null || candidate.numVotes > c2.numVotes) {
    			c2=candidate;
    		}
    	}
    	//reset attributes
    	this.segundoTurno=false;
    	this.nullVotes = 0;
    	this.protestVotes = 0;
    	this.candidates = new HashMap<String, Candidate>();
    	this.voters = new HashMap<Voter, Integer>();
    	
    	c1.numVotes = 0;
    	c2.numVotes = 0;
    		  
    	// #if EstatisticasDinamicas
//@    	dynamicStatistics.remove("Default");
//@    	dynamicStatistics.add("Default",new VotesStatisticsObserver());
    	// #endif
    	String id1 = Integer.toString(c1.number);
    	String id2 = Integer.toString(c2.number);
    	
    	// #if Municipal
//@    	if(c1 instanceof Mayor) {
//@    		id1 = ((Mayor) c1).district + id1;
//@    		id2 = ((Mayor) c2).district + id2;
//@    	}
    	// #endif
    	// #if Federal
    	if (c1 instanceof Governor) {
    		id1 = ((Governor) c1).state + id1;
    		id2 = ((Governor) c2).state + id2;
    	}
    	// #endif
    	
    	//add candidates for second round
    	this.candidates.put(id1,c1);
    	this.candidates.put(id2,c2);
    		  
    		  
    	System.out.println("Nenhum candidato conseguiu mais de 50% dos votos validos, comecando segundo turno.");
    	System.out.println("Candidatos:");
    	System.out.println("\t"+c1.name+" - Numero: "+String.valueOf(c1.number)+" - Partido: "+c1.party);
    	System.out.println("\t"+c2.name+" - Numero: "+String.valueOf(c2.number)+" - Partido: "+c2.party);  		  
    } 
    
    protected boolean checkSecondRoundConditions() {
    	return true;
    }
    // #endif
}
