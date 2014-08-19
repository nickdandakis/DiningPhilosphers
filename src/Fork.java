public class Fork {
	// Global variables
	private int id;
	private boolean available; // boolean flag that states if fork is in use or not

	/****************/

	public Fork(int ID, boolean AVAILABLE) {
		setId(ID);
		setAvailable(AVAILABLE);
	}

	/****************/

	/**
	 * @return the id
	 */
	public synchronized int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public synchronized void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the available
	 */
	public synchronized boolean isAvailable() {
		return available;
	}

	/**
	 * @param available
	 *            the available to set
	 */
	public synchronized void setAvailable(boolean available) {
		this.available = available;
	}

}
