import static org.junit.Assert.*;

import org.junit.Test;
import urna.Election;

public class ElectionTest {
  @Test
  public void singletonTest() {
	Election.reset();
    Election e1 = Election.getInstance("password");
    Election e2 = Election.getInstance(null);
    assertEquals(e1,e2);
  }
	
  @Test
  public void isValidTest(){
	Election.reset();
    String electionPassword = "password";

    Election currentElection = Election.getInstance(electionPassword);
    System.out.println(currentElection.isValid(electionPassword));
    assertTrue("A senha da eleição deveria ter sido validada corretamente.", currentElection.isValid(electionPassword));
    assertFalse("O candidato a presidente não foi adicionado corretamente.", currentElection.isValid("wrong"));
  }
}
