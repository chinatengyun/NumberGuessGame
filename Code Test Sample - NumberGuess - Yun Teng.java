import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NumberGuess {
	
	private static final int INITIAL_NUMBER = 20;
	
	private boolean isLowerBoundSet = false;
	private boolean isHigherBoundSet = false;
	
	public static void main(String[] args) {
		
		new NumberGuess().startNumberGuessingGame();
	}
	
	public void startNumberGuessingGame() {
		
		BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(System.in));
            
            boolean exit = false;
            boolean isHigher = true;
            NumberGuessData numberGuessData = new NumberGuessData(isLowerBoundSet, isHigherBoundSet, 
            		INITIAL_NUMBER, INITIAL_NUMBER, INITIAL_NUMBER, INITIAL_NUMBER, isHigher);
            
            while (true) {
                String userInput = br.readLine().toLowerCase().trim();

                if ("ready".equals(userInput)) { // start guessing
                    while (true) {
                    	System.out.println("Is the number " + numberGuessData.getCurrentGuess() + "?");
                    	
                    	while (true) {
                    		userInput = br.readLine().toLowerCase().trim();
                    		
                    		if ("end".equals(userInput) || "yes".equals(userInput)) {
                    			exit = true;
                    			break;
                    		} else if ("higher".equals(userInput)) {
                    			isLowerBoundSet = true;
                    			isHigher = true;
                    			break;
                    		} else if ("lower".equals(userInput)) {
                    			isHigherBoundSet = true;
                    			isHigher = false;
                    			break;
                    		} else {
                    			System.out.println("Please type 'higher', 'lower', 'yes' or 'end'.");
                    		}
                    	}
                    	
                    	if (exit)
                    		break;
                    	
                    	numberGuessData = generateCurrentGuess(numberGuessData, isHigher);
                    	
                    	if (!numberGuessData.isGuessValid() || numberGuessData.getCurrentGuess() == numberGuessData.getLastGuess()) {
                    		System.out.println("User answer is invalid, could not find correct answer. Exit.");
                    		exit = true;
                    		break;
                    	}
                    }
                }
                
                if (exit)
                	break;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/**
	 * custom number guessing algorithm
	 * assume positive integers only
	 * 
	 * @param lastGuess  last guess number
	 * @param previousGuess  guessed number before last guess
	 * @param isHigher  whether user's number is higher than last guess
	 * @return new guess
	 */
	private static NumberGuessData generateCurrentGuess(NumberGuessData data, boolean isHigher) {
		
		if (data.getLowerBound() == data.getHigherBound()) {
			if (isHigher) {
				return new NumberGuessData(data.isLowerBoundSet(), data.isHigherBoundSet(), 
						data.getLowerBound(), data.getHigherBound()*2, data.getHigherBound()*2, data.getCurrentGuess(), true);
			} else {
				return new NumberGuessData(data.isLowerBoundSet(), data.isHigherBoundSet(), 
						data.getLowerBound()/2, data.getHigherBound(), data.getLowerBound()/2, data.getCurrentGuess(), true);
			}
		} else {
			if (isHigher) {
				if (data.isHigherBoundSet() && data.getCurrentGuess() == data.getHigherBound()) {
					return new NumberGuessData(data.isLowerBoundSet(), data.isHigherBoundSet(), 
							data.getLowerBound(), data.getLowerBound(), data.getCurrentGuess(), data.getCurrentGuess(), false);
				} else if (data.getCurrentGuess() == data.getHigherBound()) {
					return new NumberGuessData(data.isLowerBoundSet(), data.isHigherBoundSet(), 
							data.getHigherBound(), data.getHigherBound()*2, data.getCurrentGuess()*2, data.getCurrentGuess(), true);
				} else {
					return new NumberGuessData(data.isLowerBoundSet(), data.isHigherBoundSet(), 
							data.getCurrentGuess(), data.getHigherBound(), (data.getCurrentGuess() + data.getHigherBound())/2, data.getCurrentGuess(), true);
				}
			} else {
				if (data.isLowerBoundSet() && data.getCurrentGuess() == data.getLowerBound()) {
					return new NumberGuessData(data.isLowerBoundSet(), data.isHigherBoundSet(), 
							data.getLowerBound(), data.getLowerBound(), data.getCurrentGuess(), data.getCurrentGuess(), false);
				} else if (data.getCurrentGuess() == data.getLowerBound()) {
					return new NumberGuessData(data.isLowerBoundSet(), data.isHigherBoundSet(), 
							data.getLowerBound()/2, data.getLowerBound(), data.getLowerBound()/2, data.getCurrentGuess(), true);
				} else {
					return new NumberGuessData(data.isLowerBoundSet(), data.isHigherBoundSet(), 
							data.getLowerBound(), data.getCurrentGuess(), (data.getLowerBound() + data.getCurrentGuess())/2, data.getCurrentGuess(), true);
				}
			}
		}
	}
}

class NumberGuessData {
	private boolean lowerBoundSet;
	private boolean higherBoundSet;
	private int lowerBound;
	private int higherBound;
	private int currentGuess;
	private int lastGuess;
	private boolean guessValid;
	
	public NumberGuessData(boolean lowerBoundSet, boolean higherBoundSet, int lowerBound, int higherBound, 
			int currentGuess, int lastGuess, boolean guessValid) {
		this.lowerBoundSet = lowerBoundSet;
		this.higherBoundSet = higherBoundSet;
		this.lowerBound = lowerBound;
		this.higherBound = higherBound;
		this.currentGuess = currentGuess;
		this.lastGuess = lastGuess;
		this.guessValid = guessValid;
	}
	
	public boolean isLowerBoundSet() {
		return lowerBoundSet;
	}
	
	public boolean isHigherBoundSet() {
		return higherBoundSet;
	}
	
	public int getCurrentGuess() {
		return currentGuess;
	}
	
	public int getLastGuess() {
		return lastGuess;
	}
	
	public int getLowerBound() {
		return lowerBound;
	}
	
	public int getHigherBound() {
		return higherBound;
	}
	
	public boolean isGuessValid() {
		return guessValid;
	}
	
	public String toString() {
		return "(" + lowerBound + ", " + higherBound + ", " + currentGuess + ", " + lastGuess + ")";
	}
}
