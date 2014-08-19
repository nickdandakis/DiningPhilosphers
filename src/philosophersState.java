/**
 *	States philosophers can be in 
 */

public enum philosophersState {
	HUNGRY("hungry"), EATING("eating"), THINKING("thinking");
	
	// String representation of philosopher states
	private final String str;
	
	// Constructor for philosopher states
	philosophersState(String str) {
		this.str = str;
	}
	
	// Returns string representation of philosopher state
	@Override
	public String toString() {
		return str;
	}
}
