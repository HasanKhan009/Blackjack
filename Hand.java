import java.util.ArrayList;

/**
 * Manages the cards held by a player
 */
public class Hand {
    private final ArrayList<Card> cards;
    private static final int MAX_SCORE = 21;

    /**
     * Postcondition: Constructs an empty Hand object
     */
    public Hand() {
        cards = new ArrayList<>();
    }

    /**
     * Precondition: card is non null
     * Postcondition: Adds card to the hand.
     */
    public void addCard(Card card) {
        cards.add(card);
    }

    /**
     * Postcondition: Returns total point value of the hand, adjusting Aces from 11 to 1 if needed
     */
    public int getValue() {
        int total = 0;
        int aceCount = 0;
        for (Card c : cards) {
            total += c.getValue();
            if (c.isAce()) aceCount++;
        }
        while (total > MAX_SCORE && aceCount > 0) {
            total -= 10;
            aceCount--;
        }
        return total;
    }

    /**
     * Postcondition: Returns true if the hand has an Ace counting as 11 without busting
     */
    public boolean isSoft() {
        int rawTotal = 0;
        int aceCount = 0;
        for (Card c : cards) {
            rawTotal += c.getValue();
            if (c.isAce()) aceCount++;
        }
        int total = rawTotal;
        int convertedAces = 0;
        while (total > MAX_SCORE && aceCount > 0) {
            total -= 10;
            aceCount--;
            convertedAces++;
        }
        int initialAces = 0;
        for (Card c : cards) {
            if (c.isAce()) initialAces++;
        }
        return initialAces > convertedAces;
    }

    /**
     * Postcondition: Returns true if hand score exceeds 21 and false otherwise
     */
    public boolean isBust() {
        return getValue() > MAX_SCORE;
    }

    /**
     * Postcondition: Returns true if hand has exactly 2 cards scoring 21 and false otherwise.
     */
    public boolean isBlackjack() {
        return cards.size() == 2 && getValue() == MAX_SCORE;
    }

    /**
     * Postcondition: Clears all cards from the hand
     */
    public void clear() {
        cards.clear();
    }

    /**
     * Postcondition: Returns number of cards currently in hand
     */
    public int getCardCount() {
        return cards.size();
    }

    /**
     * Postcondition: Returns String representation of cards in hand
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cards.size(); i++) {
            sb.append(cards.get(i));
            if (i < cards.size() - 1) sb.append(", ");
        }
        return sb.toString();
    }
}