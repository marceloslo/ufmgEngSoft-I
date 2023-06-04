import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

import urna.Candidate;
import urna.President;

public class CandidateTest {
  
  @Test
  public void testCandidateConstructor(){
    String name = "Alberto";
    String party = "PV";

    Candidate candidate = new President.Builder().name(name).number(123).party(party).build();

    assertTrue("O numero do candidato nao esta como definido pelo construtor.", candidate.numVotes == 0);
	}
}
