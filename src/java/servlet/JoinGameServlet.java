package servlet;

import control.GAMESession;
import control.GAMESessionALL;
import control.General;
import control.Player;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.media.sse.OutboundEvent;

@WebServlet (urlPatterns = {"/JoinGame"})
public class JoinGameServlet extends HttpServlet{

    @Inject private GAMESessionALL GAMES_ALL;
    private GAMESession Game;
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        Map<String, GAMESession> map = GAMES_ALL.getMap();
//        System.out.println("------->> Inside Join Game Servlet <<--------");
        JsonArrayBuilder JSONarrBuilder = Json.createArrayBuilder();
        
        try{
            //String playerName = req.getParameter("playerName");
            //String gameID = req.getParameter("gameID");
            
            for (GAMESession gMap: map.values()) {
                try {
                    if(gMap.getStatus() == General.STATUS_PREGAME){
                        JsonObjectBuilder l_JsonObjectBuilder = Json.createObjectBuilder()
                                .add("sessionID", gMap.getGAMESessionID())
                                .add("starter", gMap.getStarter())
                                .add("playercount", gMap.getPlayerCount());
                        JSONarrBuilder.add(l_JsonObjectBuilder.build());
                    }
                }catch (Exception ex) { 
                    System.out.println("---------->> ERROR --------" + ex.toString() + " <<--------------");
                }
            }
            
            PrintWriter l_writer = resp.getWriter();
            l_writer.write(JSONarrBuilder.build().toString());
            resp.setContentType(MediaType.APPLICATION_JSON);
            
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
    }
    
}
