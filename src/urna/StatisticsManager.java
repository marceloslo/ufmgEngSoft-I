package urna;
import java.util.Map;
import java.util.HashMap;

public class StatisticsManager {
	private Map<String,StatisticsObserver> listeners = new HashMap<String,StatisticsObserver>();
	
	public void notify(String key,String voteType) {
		listeners.get(key).update(null, voteType);
	}
	public void notify(Candidate candidate,String voteType) {
		String key = candidate.getClass().getSimpleName();
		listeners.get(key).update(candidate, voteType);
	}
	public void add(String category,StatisticsObserver observer) {
		listeners.put(category,observer);
	}
	public void remove(String category) {
		listeners.remove(category);
	}
}
