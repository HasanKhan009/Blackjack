import java.util.ArrayList;
import java.util.Random;

/**
 * Represents the entire deck of 52 cards
 */
public class Deck {
    private final ArrayList<Card> cards;
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    private static final String[] SUITS = {"Clubs", "Diamonds", "Hearts", "Spades"};

    /**
     * Postcondition: Constructs a 52 card deck
     */
    public Deck() {
        cards = new ArrayList<>();
        buildStandardDeck();
    }

    /**
     * Precondition: stackedCards is not null
     * Postcondition: Constructs a stacked deck initialized with stackedCards for testing
     */
    public Deck(ArrayList<Card> stackedCards) {
        cards = new ArrayList<>(stackedCards);
    }

    private void buildStandardDeck() {
        for (String suit : SUITS) {
            for (String rank : RANKS) {
                cards.add(new Card(rank, suit));
            }
        }
    }

    /**
     * Postcondition: Shuffles the cards
     */
    public void shuffle() {
        Random rand = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }

    /**
     * Precondition: Deck is not empty (cardsLeft() > 0)
     * Postcondition: Removes and returns the top card of the deck
     */
    public Card deal() {
        if (cards.isEmpty()) {
            return null;
        }
        return cards.remove(cards.size() - 1);
    }

    /**
     * Postcondition: Returns the number of remaining cards in the deck
     */
    public int cardsLeft() {
        return cards.size();
    }
}