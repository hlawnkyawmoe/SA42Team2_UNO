package control;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;
import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.sse.OutboundEvent;

@ApplicationScoped
public class GAMESession {
    
    private String GAMESessionID;
    private int playerCount;
    private String status;
    private String starter;
    private CardControl cardCtrl = new CardControl();
    private Stack discard_pile_player = new Stack();
    private Stack discard_pile_card = new Stack();
    
    private Map<String, Player> map = new HashMap<>();
    
    public void add(Player p) {
        getMap().put(p.getName(), p);
    }

    public String getGAMESessionID() {
        return GAMESessionID;
    }

    public void setGAMESessionID(String GAMESessionID) {
        this.GAMESessionID = GAMESessionID;
    }
    
    public Player getPlayer(final String name){
       return getMap().get(name);
    }

    public int getPlayerCount() {
        return playerCount;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public Map<String, Player> getMap() {
        return map;
    }
    
    public void addTo_DiscardPile(String card, String playerName){
        discard_pile_card.push((Object)card);
        discard_pile_player.push((Object)playerName);
    }
    
    public String takeOutCard_DiscardPile(){
        String card = discard_pile_card.pop().toString();
        return card;
    }
   
    public String takeOutPlayer_DiscardPile(){
        String player = discard_pile_player.pop().toString();
        return player;
    }

    
    //start_game
    public void BroadcastTask(){    
        try{
            //System.out.println("---> Inside GAMESession : BroadcastTask Function ----");
            for (Player p: map.values()) {
                if("UNOtable".equals(p.getName())){
                    
                    Card c = cardCtrl.getCard();
                    addTo_DiscardPile(c.getCard_name(), "UNOtable");
                    JsonObject obj = Json.createObjectBuilder()
                            .add("first_card", c.getCard_name())
                            .add("cmd", "start_game")
                            .build();
                    OutboundEvent evt = new OutboundEvent.Builder()
                            .name(GAMESessionID)
                            .mediaType(MediaType.APPLICATION_JSON_TYPE)
                            .data(JsonObject.class, obj)
                            .build();
                    //System.out.println("Card Size : " + cardCtrl.UnoCollection.size());
                    p.getEo().write(evt);
                }
                else{
                    p.setPlayer_cardList(cardCtrl.get_seven_card());
                    //System.out.println(p.getPlayer_cardList().size());
                    
                    JsonObjectBuilder obj = Json.createObjectBuilder()
                            .add("gameId" ,GAMESessionID)
                            .add("cmd", "start_game")
                            .add("CardList", p.CardJsonArray().build())
                            .add("playerName", p.getName());
            
                    OutboundEvent.Builder evt = new OutboundEvent.Builder()
                            .name(GAMESessionID)
                            .mediaType(MediaType.APPLICATION_JSON_TYPE);
                            
                    p.getEo().write(evt.data(obj.build()).build());
                }
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
    
    public Stack getDiscard_pile_card() {
        return discard_pile_card;
    }
}
