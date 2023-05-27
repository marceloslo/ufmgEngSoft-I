import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.*;
import org.junit.Test;
import urna.Election;

public class ElectionTest {
  @Test
  public void isValidTest(){
    String electionPassword = "password";

    Election currentElection = Election.getInstance(electionPassword);

    assertTrue("A senha da eleição deveria ter sido validada corretamente.", currentElection.isValid(electionPassword));
    assertFalse("O candidato a presidente não foi adicionado corretamente.", currentElection.isValid("wrong"));
  }
}
