package urna;
import java.util.Map;
import java.util.HashMap;

public class StatisticsManager {
	private Map<String,StatisticObserver> listeners = new HashMap<String,StatisticObserver>();
	
	public void notify(String key,String voteType) {
		listeners.get(key).update(null, voteType);
	}
	public void notify(Candidate candidate,String voteType) {
		String key = candidate.getClass().getSimpleName();
		listeners.get(key).update(candidate, voteType);
	}
	public void add(String category,StatisticObserver observer) {
		listeners.put(category,observer);
	}
	public void remove(String category) {
		listeners.remove(category);
	}
}
