package control;

import java.util.List;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import org.glassfish.jersey.media.sse.EventOutput;

public class Player {
    private String name;
    private EventOutput eo;
    private List<Card> player_cardList;
    
    public Player(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EventOutput getEo() {
        return eo;
    }

    public void setEo(EventOutput eo) {
        this.eo = eo;
    }

    public List<Card> getPlayer_cardList() {
        return player_cardList;
    }

    public void setPlayer_cardList(List<Card> player_cardList) {
        this.player_cardList = player_cardList;
    }
    
    public List<Card> addCards(Card cardNo){
        this.player_cardList.add(cardNo);
        return this.player_cardList;
    }
    
    public void dropCard(String cardNo){
        try{
            int size = this.player_cardList.size();
            for(int i =0; i< size; i++){
                if(cardNo.equals(player_cardList.get(i).getCard_name())){
                    player_cardList.remove(i);
                    break;
                }
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public JsonArrayBuilder CardJsonArray()
    {
        JsonArrayBuilder data=Json.createArrayBuilder();
        
        for(Card c : player_cardList)
        {
            JsonObjectBuilder builder=Json.createObjectBuilder()
                    .add("Cards",c.getCard_name());
                    data.add(builder.build());
        }
        return data;
    }
}
