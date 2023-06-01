import static org.junit.Assert.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import urna.TSEEmployee;
import urna.President;
import urna.MultipleElections;
import urna.PoliticalElection;
import urna.FederalDeputy;

public class TSEEmployeeTest {
  TSEEmployee tseEmployee;
  String user;
  String password;
  MultipleElections currentElection;
  
  @BeforeEach
  public void setUp() {
	user = "Alberto";
	password="123";
	tseEmployee = new TSEEmployee.Builder()
			.user(user)
		    .password(password)
		    .build();
	currentElection = MultipleElections.getInstance();
	currentElection.addElection("Presidente", new PoliticalElection(password));
  }
  
  @Test
  public void TSEEmployeeBuilderTest(){
    assertTrue("O nome do empregado do tse nao esta como definido pelo builder.", tseEmployee.getUser().equals(user));
	}

  @Test
  public void addCandidateTest(){
    String electionPassword = "password";

    Election currentElection = Election.getInstance(electionPassword);

    President presidentCandidate1 = new President.Builder().name("João").number(123).party("PDS1").build();
    FederalDeputy federalDeputyCandidate1 = new FederalDeputy.Builder().name("Carlos").number(12345).party("PDS1").state("MG").build();
    
    tseEmployee.addCandidate(presidentCandidate1, currentElection, electionPassword);
    tseEmployee.addCandidate(federalDeputyCandidate1, currentElection, electionPassword);
    assertTrue("O candidato a presidente não foi adicionado corretamente.", currentElection.getPresidentByNumber(123).getName().equals("João"));
    assertTrue("O candidato a deputado federal não foi adicionado corretamente.", currentElection.getFederalDeputyByNumber("MG",12345).getName().equals("Carlos"));
  }

  @Test
  public void removeCandidateTest(){
    String electionPassword = "password";

    Election currentElection = Election.getInstance(electionPassword);

    President presidentCandidate1 = new President.Builder().name("João").number(123).party("PDS1").build();
    FederalDeputy federalDeputyCandidate1 = new FederalDeputy.Builder().name("Carlos").number(12345).party("PDS1").state("MG").build();

    tseEmployee.removeCandidate(presidentCandidate1, currentElection, electionPassword);
    tseEmployee.removeCandidate(federalDeputyCandidate1, currentElection, electionPassword);
    
    assertTrue("O candidato a presidente não foi removido corretamente.", currentElection.getPresidentByNumber(123)==null);
    assertTrue("O candidato a deputado federal não foi removido corretamente.", currentElection.getFederalDeputyByNumber("MG",12345)==null);
  }
}