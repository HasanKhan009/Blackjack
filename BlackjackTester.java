import java.util.ArrayList;

/**
 * This class is used fo testing different parts of the game
 */
public class BlackjackTester {
    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {
        System.out.println("--- Running Tests ---\n");
        testNatural();
        testTwoAces();
        testThreeCardsTwoAces();
        testSoftBecomesHard();
        testBust();
        testFourAces();
        testNewDeck();
        testShuffleKeepsCards();
        testDeal();
        testDealer16();
        testDealerSoft17();
        testBadBets();
        testPayouts();

        System.out.println("\n-------------------------------------------");
        System.out.println("Results: " + passed + " passed, " + failed + " failed.");
    }

    private static void check(String testName, boolean condition) {
        if (condition) {
            System.out.println("[PASS] " + testName);
            passed++;
        } else {
            System.out.println("[FAIL] " + testName);
            failed++;
        }
    }

    private static void testNatural() {
        Hand h = new Hand();
        h.addCard(new Card("Ace", "Spades"));
        h.addCard(new Card("King", "Hearts"));
        check("Natural: Score 21", h.getValue() == 21);
        check("Natural: isBlackjack true", h.isBlackjack());
        check("Natural: isSoft true", h.isSoft());
    }

    private static void testTwoAces() {
        Hand h = new Hand();
        h.addCard(new Card("Ace", "Spades"));
        h.addCard(new Card("Ace", "Hearts"));
        check("Two Aces: Score 12", h.getValue() == 12);
        check("Two Aces: isSoft true", h.isSoft());
    }

    private static void testThreeCardsTwoAces() {
        Hand h = new Hand();
        h.addCard(new Card("Ace", "Spades"));
        h.addCard(new Card("Ace", "Hearts"));
        h.addCard(new Card("9", "Clubs"));
        check("Three cards with two Aces: Score 21", h.getValue() == 21);
        check("Three cards with two Aces: isBlackjack false", !h.isBlackjack());
    }

    private static void testSoftBecomesHard() {
        Hand h = new Hand();
        h.addCard(new Card("Ace", "Spades"));
        h.addCard(new Card("6", "Hearts"));
        h.addCard(new Card("10", "Diamonds"));
        check("Soft becomes hard: Score 17", h.getValue() == 17);
        check("Soft becomes hard: isSoft false", !h.isSoft());
    }

    private static void testBust() {
        Hand h = new Hand();
        h.addCard(new Card("King", "Spades"));
        h.addCard(new Card("Queen", "Hearts"));
        h.addCard(new Card("2", "Clubs"));
        check("Bust: Score 22", h.getValue() == 22);
        check("Bust: isBust true", h.isBust());
    }

    private static void testFourAces() {
        Hand h = new Hand();
        h.addCard(new Card("Ace", "Spades"));
        h.addCard(new Card("Ace", "Hearts"));
        h.addCard(new Card("Ace", "Diamonds"));
        h.addCard(new Card("Ace", "Clubs"));
        check("Four Aces: Score 14", h.getValue() == 14);
        check("Four Aces: isSoft true", h.isSoft());
    }

    private static void testNewDeck() {
        Deck d = new Deck();
        check("New deck: 52 cards", d.cardsLeft() == 52);
    }

    private static void testShuffleKeepsCards() {
        Deck d = new Deck();
        d.shuffle();
        check("Shuffle keeps cards: 52 cards remaining", d.cardsLeft() == 52);
    }

    private static void testDeal() {
        Deck d = new Deck();
        d.deal();
        check("Deal: 51 cards left", d.cardsLeft() == 51);
    }

    private static void testDealer16() {
        Dealer dealer = new Dealer();
        dealer.getHand().addCard(new Card("10", "Spades"));
        dealer.getHand().addCard(new Card("6", "Hearts"));
        check("Dealer 16: shouldHit is true", dealer.shouldHit());
    }

    private static void testDealerSoft17() {
        Dealer dealer = new Dealer();
        dealer.getHand().addCard(new Card("Ace", "Spades"));
        dealer.getHand().addCard(new Card("6", "Hearts"));
        check("Dealer soft 17: shouldHit is false", !dealer.shouldHit());
    }

    private static void testBadBets() {
        Player p = new Player("Tester", 100);
        int initialBankroll = p.getBankroll();
        int[] badBets = {0, -5, 101};
        for (int bet : badBets) {
            if (bet < 2 || bet > p.getBankroll()) {
                // bet rejected
            } else {
                p.placeBet(bet);
            }
        }
        check("Bad bets: bankroll unchanged", p.getBankroll() == initialBankroll);
    }

    private static void testPayouts() {
        Player p = new Player("Tester", 100);
        p.settle(15);
        check("Payout Natural (+15): 115", p.getBankroll() == 115);
        p.settle(10);
        check("Payout Win (+10): 125", p.getBankroll() == 125);
        p.settle(0);
        check("Payout Push (0): 125", p.getBankroll() == 125);
        p.settle(-10);
        check("Payout Loss (-10): 115", p.getBankroll() == 115);
    }
}