package control;

import java.security.Timestamp;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.glassfish.enterprise.concurrent.AbstractManagedExecutorService;
import org.glassfish.enterprise.concurrent.ManagedScheduledExecutorServiceImpl;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

@RequestScoped
@Path("playerplay")
public class PlayPlayerEvent {
    
    @Inject private GAMESessionALL GAMES_ALL;
    private GAMESession Game;
    private CardControl CardCtrl;
    
    @Resource(name = "DefaultManagedScheduledExecutorService")
    ManagedScheduledExecutorService svc;
    
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput playerplay(@QueryParam("playerName") String playerName, @QueryParam("gameID") String gameID){
        
        EventOutput eo = new EventOutput();
        try{
            Game = GAMES_ALL.get(gameID);
            //no of players + table
            int totalPlayerCount = Game.getPlayerCount() + 1;
            if(Game.getMap().size() !=  totalPlayerCount){
                Player p = new Player(playerName);
                p.setEo(eo);
                Game.add(p);
                
                            
          
            //Game = GAMES_ALL.get(gameID);
            Player p1 = Game.getPlayer("UNOtable");
            //p.dropCard(dropCard);
            //Game.addTo_DiscardPile(dropCard, playerName);
            
            //p = Game.getPlayer("UNOtable");
            
            JsonObjectBuilder obj = Json.createObjectBuilder()
                            .add("gameId" ,gameID)
                            .add("cmd", "player_Join")
                            .add("playerName", p.getName());
            
            OutboundEvent.Builder evt = new OutboundEvent.Builder()
                    .name(gameID)
                    .mediaType(MediaType.APPLICATION_JSON_TYPE);
                    
            p1.getEo().write(evt.data(obj.build()).build());
      
            
                if(Game.getMap().size() == totalPlayerCount){
                    Game.setStatus(General.STATUS_RUNNING);

                    BroadcastTask t = new BroadcastTask(Game);
                    t.what = "start_game";
                    svc.schedule(t, 100, TimeUnit.MILLISECONDS);
                }
            }
            else
                return null;
            
            return eo;
            
        }catch(Exception ex){
            System.out.println("---------->> PLAY EVENT ERROR --------" + ex.toString() + " <<--------------");
            svc.shutdown();
            return null;
        }
    }
}
