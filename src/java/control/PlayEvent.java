package control;

import java.io.IOException;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.SseFeature;


@RequestScoped
@Path("play/{GameSessionID}")
public class PlayEvent {
    
    @Inject private GAMESessionALL GAMES_ALL; 
    private GAMESession Game;
    
    @GET
    @Produces(SseFeature.SERVER_SENT_EVENTS)
    public EventOutput play(@PathParam("GameSessionID") String GameID, @QueryParam("playerName") String playerName, @QueryParam("pStarter") String pStarter, @QueryParam("pCount") int pCount) {

        EventOutput eo = new EventOutput();
        
        try{
            //System.out.println("--------->> Play Event Starting <<----------");
                    
            Game = new GAMESession();
            Game.setGAMESessionID(GameID);
            Game.setPlayerCount(pCount);
            Game.setStatus(General.STATUS_PREGAME);
            Game.setStarter(pStarter);
            
            Player p = new Player(playerName);
            p.setEo(eo);
            Game.add(p);
            GAMES_ALL.add(Game);
          
            return eo;
        }catch(Exception ex){
            System.out.println("---------->> PLAY EVENT ERROR --------" + ex.toString() + " <<--------------");
            return null;
        }
    }
}
