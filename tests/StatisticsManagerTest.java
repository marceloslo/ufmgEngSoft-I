import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import urna.VotesStatisticsObserver;
import urna.StatisticsManager;
import urna.Candidate;

class StatisticsManagerTest {
	
	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final PrintStream originalOut = System.out;
	public StatisticsManager m;

	@BeforeEach
	public void setUp() {
		m = new StatisticsManager();
	    System.setOut(new PrintStream(outContent));
	}

	@AfterEach
	public void tearDown() {
	    System.setOut(originalOut);
	}

	@Test
	void testNotifyNull() {
		VotesStatisticsObserver v = new VotesStatisticsObserver();
		m.add("Election", v);
		m.notify("Election", "Null");
		assertTrue(outContent.toString().contains("Votos nulos: 1"));
	}
	
	@Test
	void testNotifyValid() {
		VotesStatisticsObserver v = new VotesStatisticsObserver();
		Candidate c = new Candidate("Osvaldo","pdt",10);
		m.add(c.getClass().getSimpleName(), v);
		
		m.notify("Candidate", "Null");
		assertTrue(outContent.toString().contains("Votos nulos: 1"));
		
		m.notify(c,"Valid");
		assertTrue(outContent.toString().contains("10 - pdt - Osvaldo - 1 - 50,00%"));
	}
	
	@Test
	void testRemove() {
		VotesStatisticsObserver v = new VotesStatisticsObserver();
		m.add("Remove", v);
		m.remove("Remove");
		
		assertThrows(Throwable.class,() -> {
			m.notify("Remove","Null");
		});
	}

}