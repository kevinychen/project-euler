
package level03;

import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;

import lib.EulerTest;
import lombok.Data;

public class p054 extends EulerTest {

    final int N = 5;

    /**
     * Find the number of winning Poker hands for Player 1 in the specified list of games with
     * N-card hands.
     */
    @Test
    public void test() {
        read().forEach(line -> {
            String[] data = line.split(" ");
            List<Card> cards1 = list();
            for (int i : range(N))
                cards1.add(parseCard(data[i]));
            List<Card> cards2 = list();
            for (int i : range(N))
                cards2.add(parseCard(data[N + i]));
            List<Integer> score1 = getScore(cards1);
            List<Integer> score2 = getScore(cards2);
            if (better(score1, cards1, score2, cards2))
                ans++;
        });
        check(376);
    }

    Card parseCard(String card) {
        int val = "  23456789TJQKA".indexOf(card.charAt(0));
        int suit = "DCHS".indexOf(card.charAt(1));
        return new Card(val, suit);
    }

    List<Integer> getScore(List<Card> cards) {
        cards.sort((card1, card2) -> card2.val - card1.val);
        List<Integer> cardVals = Lists.transform(cards, card -> card.val);
        List<Integer> freqs = list();
        for (int cardVal : set(cardVals))
            freqs.add(Collections.frequency(cardVals, cardVal));
        Collections.sort(freqs);
        if (isFlush(cards) && isStraight(cards))
            return list(8);
        else if (freqs.equals(list(1, 4)))
            return list(7, cards.get(2).val);
        else if (freqs.equals(list(2, 3)))
            return list(6, cards.get(2).val);
        else if (isFlush(cards))
            return list(5);
        else if (isStraight(cards))
            return list(4);
        else if (freqs.equals(list(1, 1, 3)))
            return list(3, cards.get(2).val);
        else if (freqs.equals(list(1, 2, 2)))
            return list(2, cards.get(1).val, cards.get(3).val);
        else if (freqs.equals(list(1, 1, 1, 2)))
            return list(1, findOnePair(cards));
        else
            return list(0);
    }

    boolean isFlush(List<Card> cards) {
        for (int i : range(1, N))
            if (cards.get(i).suit != cards.get(0).suit)
                return false;
        return true;
    }

    boolean isStraight(List<Card> cards) {
        for (int i : range(1, N))
            if (cards.get(i - 1).val - cards.get(i).val != 1)
                return false;
        return true;
    }

    int findOnePair(List<Card> cards) {
        for (int i : range(1, N))
            if (cards.get(i - 1).val == cards.get(i).val)
                return cards.get(i).val;
        throw die();
    }

    boolean better(List<Integer> score1, List<Card> cards1, List<Integer> score2, List<Card> cards2) {
        for (int i : range(score1.size()))
            if (score1.get(i) != score2.get(i))
                return score1.get(i) > score2.get(i);
        for (int i : range(N))
            if (cards1.get(i).val != cards2.get(i).val)
                return cards1.get(i).val > cards2.get(i).val;
        return false;
    }

    @Data
    static class Card {
        final int val, suit;
    }
}
