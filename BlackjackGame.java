import java.util.Scanner;

/**
 * This class manages the main gameplay loop
 */
public class BlackjackGame {
    private Deck deck;
    private final Player player;
    private final Dealer dealer;
    private final Scanner scanner;

    private static final int STARTING_BANKROLL = 100;
    private static final int MIN_BET = 2;
    private static final int RESHUFFLE_THRESHOLD = 15;

    /**
     * Postcondition: Constructs BlackjackGame initializing deck, player, dealer, and a scanner.
     */
    public BlackjackGame() {
        this.deck = new Deck();
        this.deck.shuffle();
        this.player = new Player("Player", STARTING_BANKROLL);
        this.dealer = new Dealer();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Postcondition: Starts the main session until the player quits
     */
    public void play() {
        System.out.println("--- Welcome to Blackjack ---");
        while (player.getBankroll() >= MIN_BET) {
            if (deck.cardsLeft() < RESHUFFLE_THRESHOLD) {
                System.out.println("\n[Deck low. Reshuffling full deck.]");
                deck = new Deck();
                deck.shuffle();
            }
            playRound();
            if (player.getBankroll() < MIN_BET) {
                System.out.println("\nYou cannot cover the minimum bet of " + MIN_BET + ". Game Over!");
                break;
            }
            System.out.print("\nPlay another round? (y/n): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("n")) break;
        }
        System.out.println("\nFinal Summary: Final Bankroll = " + player.getBankroll() + " chips.");
    }

    /**
     * Precondition: Player has enough bankroll for MIN_BET
     * Postcondition: Plays one round of Blackjack and updates player bankroll
     */
    public void playRound() {
        player.getHand().clear();
        dealer.getHand().clear();
        dealer.hideHoleCard();

        System.out.println("\nCurrent Bankroll: " + player.getBankroll());
        int bet = getValidBet();
        player.placeBet(bet);

        player.getHand().addCard(deck.deal());
        dealer.getHand().addCard(deck.deal());
        player.getHand().addCard(deck.deal());
        dealer.getHand().addCard(deck.deal());

        System.out.println("\nDealer shows: " + dealer);
        System.out.println("Your hand: " + player.getHand() + " (Total: " + player.getHand().getValue() + ")");

        if (checkNaturals()) return;
        playerTurn();

        if (!player.getHand().isBust()) {
            dealerTurn();
            settleRound();
        } else {
            System.out.println("You busted! You lose " + player.getCurrentBet() + " chips.");
            player.settle(-player.getCurrentBet());
        }
    }

    private int getValidBet() {
        while (true) {
            System.out.print("Enter bet (" + MIN_BET + " - " + player.getBankroll() + "): ");
            String input = scanner.nextLine().trim();
            try {
                int bet = Integer.parseInt(input);
                if (bet >= MIN_BET && bet <= player.getBankroll()) return bet;
                System.out.println("Invalid bet amount. Out of range.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a whole number.");
            }
        }
    }

    private boolean checkNaturals() {
        boolean pNat = player.getHand().isBlackjack();
        boolean dNat = dealer.getHand().isBlackjack();
        if (pNat || dNat) {
            dealer.revealHoleCard();
            System.out.println("Dealer reveals hole card: " + dealer);
            if (pNat && dNat) {
                System.out.println("Both have Blackjack! Push.");
            } else if (pNat) {
                int payout = (int)(player.getCurrentBet() * 1.5);
                System.out.println("Blackjack! You win " + payout + " chips.");
                player.settle(payout);
            } else {
                System.out.println("Dealer has Blackjack! You lose " + player.getCurrentBet() + " chips.");
                player.settle(-player.getCurrentBet());
            }
            return true;
        }
        return false;
    }

    private void playerTurn() {
        boolean canDouble = (player.getBankroll() >= player.getCurrentBet() * 2);
        while (!player.getHand().isBust()) {
            String prompt = canDouble ? "Choose action: hit, stand, or double (h/s/d): " : "Choose action: hit or stand (h/s): ";
            System.out.print(prompt);
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("h")) {
                canDouble = false;
                player.getHand().addCard(deck.deal());
                System.out.println("Your hand: " + player.getHand() + " (Total: " + player.getHand().getValue() + ")");
            } else if (choice.equals("s")) {
                break;
            } else if (choice.equals("d") && canDouble) {
                player.placeBet(player.getCurrentBet() * 2);
                System.out.println("Doubled bet to: " + player.getCurrentBet());
                player.getHand().addCard(deck.deal());
                System.out.println("Your hand: " + player.getHand() + " (Total: " + player.getHand().getValue() + ")");
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void dealerTurn() {
        dealer.revealHoleCard();
        System.out.println("\nDealer's turn. Cards: " + dealer + " (Total: " + dealer.getHand().getValue() + ")");
        while (dealer.shouldHit()) {
            Card dealt = deck.deal();
            System.out.println("Dealer hits and gets: " + dealt);
            dealer.getHand().addCard(dealt);
            System.out.println("Dealer hand: " + dealer + " (Total: " + dealer.getHand().getValue() + ")");
        }
    }

    private void settleRound() {
        int pVal = player.getHand().getValue();
        int dVal = dealer.getHand().getValue();
        int bet = player.getCurrentBet();

        if (dealer.getHand().isBust()) {
            System.out.println("Dealer busted! You win " + bet + " chips.");
            player.settle(bet);
        } else if (pVal > dVal) {
            System.out.println("You win! (" + pVal + " vs " + dVal + "). Won " + bet + " chips.");
            player.settle(bet);
        } else if (dVal > pVal) {
            System.out.println("Dealer wins! (" + dVal + " vs " + pVal + "). Lost " + bet + " chips.");
            player.settle(-bet);
        } else {
            System.out.println("Push! (" + pVal + " vs " + dVal + "). Bet returned.");
        }
    }
}