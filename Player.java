/**
 * Represents the player managing bets and stuff
 */
public class Player {
    private final String name;
    private int bankroll;
    private int currentBet;
    private final Hand hand;

    /**
     * Precondition: name is not null, initialBankroll > 0
     * Postcondition: Constructs a Player with specified name and initial bankroll
     */
    public Player(String name, int initialBankroll) {
        this.name = name;
        this.bankroll = initialBankroll;
        this.currentBet = 0;
        this.hand = new Hand();
    }

    /**
     * Precondition: betAmount >= 2 and betAmount <= bankroll
     * Postcondition: Sets currentBet to betAmount.
     */
    public void placeBet(int betAmount) {
        this.currentBet = betAmount;
    }

    /**
     * Postcondition: Returns current bankroll
     */
    public int getBankroll() {
        return bankroll;
    }

    /**
     * Postcondition: Returns current bet amount
     */
    public int getCurrentBet() {
        return currentBet;
    }

    /**
     * Postcondition: Returns player's Hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Postcondition: Updates bankroll by adding chipChange
     */
    public void settle(int chipChange) {
        this.bankroll += chipChange;
    }
}