/**
 * Instructions for this intermediate challenge task
 * Gameplay
 * This two player game is played using a standard 52-card deck. The objective of the game is to win all the cards. The
 * deck is divided evenly among the players, giving each a deck of face-down cards. In unison, each player reveals the
 * top card of their deck – this is a battle – and the player with the higher card adds both cards to the bottom of their
 * deck. If the cards are of equal value, it's war!
 *
 * This process is repeated until one player runs out of cards, at which point the other player is declared the winner.
 *
 * War
 * Both players place their next three cards face down, then a card face-up. The owner of the higher face-up card wins
 * the war and adds all cards on the table to the bottom of their deck. If the face-up cards are again equal then the
 * war repeats with another set of face-down/up cards, until one player's face-up card is higher than their opponent's,
 * or both players run out of cards
 *
 * If, when a war begins:
 *
 *      either player does not have enough cards for the war, both players reduce the number of cards to allow the war
 *      to complete (e.g. if P2 has only three cards remaining, both players play two cards down and one card up. If
 *      P2 has only one card remaining, no cards are played face-down and each player only plays one card up).
 *
 *      either player has no cards remaining, the other player wins.
 *
 *      both players have no cards remaining, the game is a draw (this is exceptionally rare in random games).
 *
 * Post-battle/war
 *
 * For consistency (so we all end up with the same result for the same input), cards used in a battle or war should be
 * added to the bottom of the winner's deck in a particular order.
 *
 * After a battle, the winner's card is added to the bottom the winner's deck first, then the loser's card.
 *
 * After a war or wars, cards used in the war(s) are added to the deck first, followed by the two tying cards. "Cards
 * used in the war(s)" is defined as follows:
 *
 *     1 Cards from any sub-wars (recursive, using this ordering)
 *
 *     2 Winner's face-down cards (in the order they were drawn, first card draw is first added to bottom, etc)
 *
 *     3 Winner's face-up card
 *
 *     4 Loser's face-down cards (in the order they were drawn, first card draw is first added to bottom, etc)
 *
 *     5 Loser's face-up card
 *
 * @author Ryan Peters
 * @date 7/14/2017
 */
public class WarCardGame {



}
