import static org.junit.Assert.*;

import org.junit.Test;
import urna.MultipleElections;

public class ElectionTest {
  @Test
  public void singletonTest() {
    MultipleElections e1 = MultipleElections.getInstance("password");
    MultipleElections e2 = MultipleElections.getInstance("password");
    assertEquals(e1,e2);
  }
}
