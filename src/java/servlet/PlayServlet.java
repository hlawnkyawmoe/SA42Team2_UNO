package servlet;

import control.Card;
import control.CardControl;
import control.GAMESession;
import control.GAMESessionALL;
import control.Player;
import java.io.IOException;
import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.sse.OutboundEvent;

@WebServlet("/PlayServlet")
public class PlayServlet extends HttpServlet{

    @Inject private GAMESessionALL GAMES_ALL;
    private GAMESession Game;
    private CardControl cardCtrl = new CardControl();
    
    @Resource(name = "DefaultManagedScheduledExecutorService")
    private ManagedScheduledExecutorService svc;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String playerName = req.getParameter("playerName");
            String gameID = req.getParameter("gameID");
            
            Game = GAMES_ALL.get(gameID);
            Player p = Game.getPlayer(playerName);
            p.addCards(cardCtrl.getCard());
            
            JsonObjectBuilder obj = Json.createObjectBuilder()
                            .add("gameId" ,gameID)
                            .add("cmd", "playGame_DrawCard")
                            .add("CardList", p.CardJsonArray().build())
                            .add("playerName", p.getName());
            
            OutboundEvent.Builder evt = new OutboundEvent.Builder()
                    .name(gameID)
                    .mediaType(MediaType.APPLICATION_JSON_TYPE);
                    
            p.getEo().write(evt.data(obj.build()).build());
            
            //System.out.println(" >>>>>>>>>>> BroadcastTask_DrawCard Event -- DONE <<<<<<");

        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            String playerName = req.getParameter("playerName");
            String gameID = req.getParameter("gameID");
            String dropCard = req.getParameter("dropCard");
            
            //System.out.println("--------->> Drop a card --  "+ playerName + gameID + dropCard + " <<----------");

            Game = GAMES_ALL.get(gameID);
            Player p = Game.getPlayer(playerName);
            p.dropCard(dropCard);
            Game.addTo_DiscardPile(dropCard, playerName);
            
            p = Game.getPlayer("UNOtable");
            
            JsonObjectBuilder obj = Json.createObjectBuilder()
                            .add("gameId" ,gameID)
                            .add("cmd", "playGame_DropCard")
                            .add("DropCard", dropCard)
                            .add("playerName", p.getName());
            
            OutboundEvent.Builder evt = new OutboundEvent.Builder()
                    .name(gameID)
                    .mediaType(MediaType.APPLICATION_JSON_TYPE);
                    
            p.getEo().write(evt.data(obj.build()).build());
            
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
}
