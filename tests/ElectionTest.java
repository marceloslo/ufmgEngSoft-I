import static org.junit.Assert.*;

import org.junit.Test;
import urna.MultipleElections;

public class ElectionTest {
  @Test
  public void singletonTest() {
    MultipleElections e1 = MultipleElections.getInstance();
    MultipleElections e2 = MultipleElections.getInstance();
    assertEquals(e1,e2);
  }
}
