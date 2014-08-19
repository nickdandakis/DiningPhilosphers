import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class DiningRoom {
	// ArrayLists of Philosophers, Forks, Plates and Threads
	static ArrayList<Philosopher> philosophers = new ArrayList<Philosopher>();
	static ArrayList<Fork> forks = new ArrayList<Fork>();
	static ArrayList<Plate> plates = new ArrayList<Plate>();
	static ArrayList<Thread> threads = new ArrayList<Thread>();
	
	// Philosopher names to make output look better
	static String[] philosopherNames = { "Aristotle", "Plato", "Confucius",
		"Descartes", "Marx" };
	
	// Semaphore that conducts the fork taking. 
	// Permits allowed = 2 because only two philosophers can eat at a time
	// Fairness parameter set to true to guarantee first-in first-out granting of permits under contention
	Semaphore waiter = new Semaphore(2, true);

	// Constants
	static final int philosophersCount = 5;
	static final int forksCount = 5;
	static final int platesCount = 3;
	static final boolean isAvailable = true;
	static final boolean notAvailable = !isAvailable;

	/****************/

	public static void main(String[] args) {
		// Construct forks array
		for (int i = 1; i <= forksCount; i++) {	
			forks.add(new Fork(i, isAvailable));
		}
		// Construct plates array
		for (int i = 1; i <= platesCount; i++) {
			plates.add(new Plate(i, isAvailable));
		}
		// Construct philosophers and threads array
		for (int i = 0; i < philosophersCount; i++) {
			philosophers.add(new Philosopher((i + 1), philosopherNames[i], philosophersState.THINKING));
			threads.add(new Thread(philosophers.get(i)));
		}		
		
		// Start threads
		for (int i = 0; i < threads.size(); i++) {
			threads.get(i).start();
		}
	}

	/****************/

	public DiningRoom() {
		
	}
}
