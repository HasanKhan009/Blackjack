/**
 * A single playing card
 */
public class Card {
    private final String rank;
    private final String suit;

    /**
     * Precondition: rank and suit are non null valid playing card strings
     * Postcondition: Constructs a 'Card' object with the given rank and suit
     */
    public Card(String rank, String suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Postcondition: Returns the rank of the card.
     */
    public String getRank() {
        return rank;
    }

    /**
     * Postcondition: Returns the suit of the card.
     */
    public String getSuit() {
        return suit;
    }

    /**
     * Postcondition: Returns the Blackjack point value (2-10, 10 for J/Q/K, 11 for Ace)
     */
    public int getValue() {
        if (rank.equals("Ace")) {
            return 11;
        } else if (rank.equals("Jack") || rank.equals("Queen") || rank.equals("King")) {
            return 10;
        } else {
            return Integer.parseInt(rank);
        }
    }

    /**
     * Postcondition: Returns true if the card is an Ace and false otherwise
     */
    public boolean isAce() {
        return rank.equals("Ace");
    }

    /**
     * Postcondition: Returns a formatted String representation of the card
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    /**
     * Precondition: obj is any Object.
     * Postcondition: Returns true if obj is a Card with matching rank and suit and false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Card other = (Card) obj;
        return rank.equals(other.rank) && suit.equals(other.suit);
    }
}