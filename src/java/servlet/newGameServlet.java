package servlet;

import control.General;
import java.io.IOException;
import java.io.PrintWriter;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.ws.rs.core.MediaType;


@WebServlet (urlPatterns = {"/StartNewGame"})
public class newGameServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            General g = new General();
            String UNOID = g.getUNOID();
            
            JsonObject obj = Json.createObjectBuilder()
                .add("UNOID", UNOID)
                .build();
            
            PrintWriter l_writer = resp.getWriter();
            l_writer.write(obj.toString());
            resp.setContentType(MediaType.APPLICATION_JSON);
            
        }catch(Exception ex){
            
        }
    }
    
}
