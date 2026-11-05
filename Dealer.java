/**
 * represents the dealer
 */
public class Dealer {
    private final Hand hand;
    private boolean holeCardHidden;
    private static final int STAND_THRESHOLD = 17;

    /**
     * Postcondition: Constructs a Dealer object with holeCardHidden set to true
     */
    public Dealer() {
        this.hand = new Hand();
        this.holeCardHidden = true;
    }

    /**
     * Postcondition: Returns true if total score < 17 and false otherwise
     */
    public boolean shouldHit() {
        return hand.getValue() < STAND_THRESHOLD;
    }

    /**
     * Postcondition: Sets holeCardHidden to false
     */
    public void revealHoleCard() {
        this.holeCardHidden = false;
    }

    /**
     * Postcondition: Sets holeCardHidden to true
     */
    public void hideHoleCard() {
        this.holeCardHidden = true;
    }

    /**
     * Postcondition: Returns dealers hand
     */
    public Hand getHand() {
        return hand;
    }

    /**
     * Postcondition: Returns string display of hand (hiding second card if holeCardHidden is true)
     */
    @Override
    public String toString() {
        if (holeCardHidden && hand.getCardCount() >= 2) {
            String fullStr = hand.toString();
            String[] parts = fullStr.split(", ");
            return parts[0] + ", [Hidden Card]";
        }
        return hand.toString();
    }
}