package urna;

//#if RealityShow
//@import java.text.DecimalFormat;
//@import java.util.Map;
//@import java.util.stream.Stream;
//@
//@public class RealityStatisticsObserver extends StatisticsObserver {
//@
//@	@Override
//@	public void update(Candidate candidate, String voteType) {
//@		this.updateCounts(candidate, voteType);
//@		
//@		//Print the votes
//@	    this.printHeader();
//@	    
//@	    System.out.println("\tNumero - Programa - Nome - Nacionalidade - Votos - % dos votos totais");
//@		var decimalFormater = new DecimalFormat("0.00");
//@		Stream<Map.Entry<Candidate,Integer>> sortedVotes = this.voteCount.entrySet().stream().sorted(Map.Entry.comparingByValue());
//@		sortedVotes.forEach(entry -> {
//@		    RealityCandidate voted = (RealityCandidate) entry.getKey();
//@		    Integer count = entry.getValue();
//@		    System.out.println("\t" + voted.number + " - " + voted.party + " - " + voted.name + " - " + voted.nationality + " - "
//@	          + count + " - " + decimalFormater.format((double) count / (double) this.totalVotes * 100) + "%\n");
//@		});
//@
//@	}
//@
//@}
//#endif
