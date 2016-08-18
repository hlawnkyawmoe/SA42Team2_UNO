package control;

import java.util.ArrayList;
import java.util.List;

public class CardControl {
    static List<Card> UnoCollection = new ArrayList<>();
    static final int Total_Card = 108;
    
    public CardControl()
    {
        for(int i=0; i<Total_Card; i++)
        {
            UnoCollection.add(i,new Card());
            UnoCollection.get(i).setCard_name("c"+i+".png");
        }
        this.shuffle();
    }
    
    public void shuffle()
    {
        List<Card> UnoCollectionShuffle = new ArrayList<>();
        for(int i = 0; i < Total_Card; i++)
        {
            UnoCollectionShuffle.add(i,null);
        }
        for(int i = 0; i < Total_Card; i++)
        {
            int random = (int)(Math.random()*108);
            while(UnoCollectionShuffle.get(random)!= null)
            {
                random = (random+1) % 108;
            }
            UnoCollectionShuffle.set(random,UnoCollection.get(i));
        }
        UnoCollection.clear();
        for(int i = 0; i < Total_Card; i++)
        {
            UnoCollection.add(i,UnoCollectionShuffle.get(i));
        }
    }
    
    public void Display()
    {
        for(int i=0; i<Total_Card; i++)
        {
            System.out.println(UnoCollection.get(i).getCard_name());
        }
    }
    
    public Card getCard()
    {
        if(UnoCollection.size() < 0)
        {
            return null;
        }
        else
        {
            Card card = UnoCollection.get(UnoCollection.size()-1);
            UnoCollection.remove(UnoCollection.size()-1);
            //System.out.println("Retrieve Card >>"+card.getCard_name());
            return card;           
        }
    }
    
    public List<Card> get_seven_card()
    {
        List<Card> cards = new ArrayList<>();
        for(int i=0; i < 7; i++)
        {
            cards.add(i,getCard());
            //System.out.println("final 7 cards >> "+cards.get(i).getCard_name());
        }
        return cards;
    }
    
    public Card getCard(String CardName){
        try{
            Card c = new Card();
            c.setCard_name(CardName);
            return c;
            
        }catch(Exception ex){
            System.out.println(ex.toString());
            return null;
        }
    }
}
