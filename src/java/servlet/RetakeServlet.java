package servlet;

import control.BroadcastTask;
import control.Card;
import control.CardControl;
import control.GAMESession;
import control.GAMESessionALL;
import control.Player;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
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

@WebServlet("/RetakeServlet")
public class RetakeServlet extends HttpServlet{

    @Inject private GAMESessionALL GAMES_ALL;
    private GAMESession Game;
    private CardControl cardCtrl = new CardControl();
    
    //@Resource(mappedName = "concurrent/myThreadpool")
    @Resource(name = "DefaultManagedScheduledExecutorService")
    private ManagedScheduledExecutorService svc;
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            System.out.println("In the Retake Servlet");
            String gameID = req.getParameter("gameID");
            Game = GAMES_ALL.get(gameID);
            
            String player = Game.takeOutPlayer_DiscardPile();
            String card = Game.takeOutCard_DiscardPile();
            
            Player p = Game.getPlayer(player);
            Card c = new Card();
            c.setCard_name(card);
            p.addCards(c);
            
            JsonObjectBuilder obj = Json.createObjectBuilder()
                            .add("gameId" ,gameID)
                            .add("cmd", "playGame_RetakeCard")
                            .add("CardList", p.CardJsonArray().build())
                            .add("playerName", p.getName());
            
            OutboundEvent.Builder evt = new OutboundEvent.Builder()
                    .name(gameID)
                    .mediaType(MediaType.APPLICATION_JSON_TYPE);
                    
            p.getEo().write(evt.data(obj.build()).build());
            
            card = Game.getDiscard_pile_card().lastElement().toString();
            p = Game.getPlayer("UNOtable");
            obj = Json.createObjectBuilder()
                            .add("gameId" ,gameID)
                            .add("cmd", "playGame_RetakeCard")
                            .add("Card", card)
                            .add("playerName", p.getName());
            
            evt = new OutboundEvent.Builder()
                    .name(gameID)
                    .mediaType(MediaType.APPLICATION_JSON_TYPE);
                    
            p.getEo().write(evt.data(obj.build()).build());
            
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
}
