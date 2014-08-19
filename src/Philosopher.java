import java.util.Random;

public class Philosopher extends DiningRoom implements Runnable {
	// Global variables of Philosopher class	
	private String name;
	private philosophersState state;
	private int id;
	public int eatCount = 0;

	private Fork leftFork = null;
	private Fork rightFork = null;
	private Plate plate = null;
	
	// Random number generator
	Random rnd = new Random();

	/****************/
	
	public Philosopher(int ID, String NAME, philosophersState STATE) {
		super();
		
		setId(ID);
		setName(NAME);
		setState(STATE);
		setForks();
	}

	/****************/

	public void run() {
		while (true) {
			think();
			if(takeForks() && takePlate()) {
				eat();
				putPlate();
				putForks();
			}
		}
	}
	
	/**
	 * Function that represents taking forks
	 * 
	 * @return boolean flag if forks taken or not
	 */
	private synchronized boolean takeForks() {
		setState(philosophersState.HUNGRY);
		
		/*
		 * Philosopher asks waiter (semaphore)
		 * for permission to take forks (acquire - aka V, wait)
		 * If less than two philosophers are eating
		 * then permission is granted
		 * else the thread blocks until a philosopher tells the waiter 
		 * that he has finished eating (release - aka P, signal)
		 */
		try {
			waiter.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		while (getState() == philosophersState.HUNGRY) {
			if (getState() == philosophersState.HUNGRY 
					&& leftFork.isAvailable() && rightFork.isAvailable()) {
				leftFork.setAvailable(notAvailable);
				rightFork.setAvailable(notAvailable);
				
				System.out.println(name + "[" + id + "]" + " has picked up fork "
						+ leftFork.getId() + " and fork " + rightFork.getId());
				
				return true;
			}
		}
		
		return false;
	}

	// Function that represents taking plate
	private synchronized boolean takePlate() {	
		/* Since only two philosophers can eat at a time due to the amount of forks,
		 * only two plates will ever be in use at any given time
		 * hence a couple of simple if/then/else statements are sufficient 
		 */
		if(plates.get(0).isAvailable()) plate = plates.get(0);
		else if(plates.get(1).isAvailable()) plate = plates.get(1);
		else if(plates.get(2).isAvailable()) plate = plates.get(2);
		
		plate.setAvailable(notAvailable);
		
		System.out.println(name + "[" + id + "]" 
							+ " has picked up plate " + plate.getId());
		
		return true;
	}

	// Function that represents putting down forks	
	private synchronized void putForks() {
		leftFork.setAvailable(isAvailable);
		rightFork.setAvailable(isAvailable);
		
		/*
		 * Philosopher tells waiter (semaphore)
		 * that he has finished eating (release - aka P, signal)
		 */
		waiter.release();
		
		System.out.println(name + "[" + id + "]" + " has put down fork "
				+ leftFork.getId() + " and fork " + rightFork.getId());
	}
	
	// Function that represents putting down plate
	private synchronized void putPlate() {
		plate.setAvailable(isAvailable);
		
		System.out.println(name + "[" + id + "]" 
				+ " has put down plate " + plate.getId());
	}
	
	/**
	 * Think function that sets philosopher state
	 * to thinking and waits for a random amount
	 * of time
	 */
	private synchronized void think() {
		setState(philosophersState.THINKING);
		
		waitrnd();
	}
	
	/**
	 * Eat function that sets philosopher state
	 * to eating and waits for a random amount
	 * of time
	 */
	private synchronized void eat() {
		eatCount++;
		setState(philosophersState.EATING);
		waitrnd();
	}
	
	/**
	 *  Function that delays thread randomly
	 *	range is [1-51) * 100 milliseconds
	 *	(= between 0.1 of a second to 5 seconds) 
	 */
	private void waitrnd() {
		try {
			this.wait((rnd.nextInt(50) + 1) * 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the state
	 */
	public philosophersState getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(philosophersState state) {
		this.state = state;
		if(getState() == philosophersState.EATING) System.out.println(this.name + "[" + id + "]" + " is " + this.state.toString() + " (" + eatCount + ")");
		else System.out.println(this.name + "[" + id + "]" + " is " + this.state.toString());
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	// Sets left and right fork ids of philosopher
	private void setForks() {
		leftFork = forks.get((id % 5));
		rightFork = forks.get((id - 1));
	}
}
