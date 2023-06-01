package urna;
import java.util.Map;
import java.text.DecimalFormat;
import java.util.HashMap;

abstract class StatisticsObserver {

	protected int totalVotes=0;
	protected int nullVotes=0;
	 
	protected int protestVotes=0;
	
	protected Map<Candidate,Integer> voteCount = new HashMap<>();
	
	public abstract void update(Candidate candidate, String voteType);
	
	protected void updateCounts(Candidate candidate,String voteType) {
		switch(voteType) {
		case "Null":
			this.nullVotes++;
			break;
		case "Protest":
			this.protestVotes++;
			break;
		case "Valid":
			System.out.println(candidate.number);
			if(!this.voteCount.containsKey(candidate)) {
				this.voteCount.put(candidate, 0);
			}
			this.voteCount.put(candidate, voteCount.get(candidate)+1);
			break;
		default:
			throw new StopTrap("Tipo de voto invalido");
		}
		this.totalVotes++;
	}
	
	protected void printHeader() {
		var decimalFormater = new DecimalFormat("0.00");
	    
		System.out.println("Resumo das Estatísticas:");
		System.out.println("  Votos totais: " + this.totalVotes);
		System.out.println("  Votos nulos: " + this.nullVotes + " ("
		        + decimalFormater.format((double) this.nullVotes / (double) this.totalVotes * 100) + "%)");
		System.out.println("  Votos brancos: " + this.protestVotes + " ("
		        + decimalFormater.format((double) this.protestVotes / (double) this.totalVotes * 100) + "%)");
		    
	    System.out.println("\tNumero - Partido - Nome - Votos - % dos votos totais");
	}
}
