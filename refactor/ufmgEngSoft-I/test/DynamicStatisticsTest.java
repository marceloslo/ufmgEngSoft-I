// #if EstatisticasDinamicas
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import urna.VotesStatisticsObserver;
import urna.Candidate;
import urna.President;
import urna.StopTrap;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class DynamicStatisticsTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	public VotesStatisticsObserver v;

	@BeforeEach
	public void setUp() {
		v = new VotesStatisticsObserver();
	    System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	public void tearDown() {
	    System.setOut(originalOut);
	}
	
	@Test
	void testValidVote() {
		Candidate c = new President.Builder().name("OdeioJava").number(66).party("Eclipse").build();
		v.update(c, "Valid");
		assertTrue(outContent.toString().contains("66 - Eclipse - OdeioJava - 1 - 100,00%"));
	}
	
	@Test
	void testNullVote() {
		v.update(null, "Null");
		assertTrue(outContent.toString().contains("Votos nulos: 1"));
	}
	
	@Test
	void testBlankVote() {
		v.update(null, "Protest");
		assertTrue(outContent.toString().contains("Votos brancos: 1"));
	}
	
	@Test
	void testInvalidVote() {
		assertThrows(StopTrap.class, () -> {
			v.update(null, "invalido");
		});
	}

}
// #endif