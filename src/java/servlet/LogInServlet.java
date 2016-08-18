package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.jms.Session;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;

@WebServlet (urlPatterns = {"/Login"})
public class LogInServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            
            HttpSession session = req.getSession();
            String username = req.getUserPrincipal().getName();
            System.out.println(username);
            session.setAttribute("username", username);
            
            JsonObject obj = Json.createObjectBuilder()
                .add("username",username)
                .add("result", "AUTHENTICATION SUCCESSED")
                .build();
            
            PrintWriter l_writer = resp.getWriter();
            l_writer.write(obj.toString());
            resp.setContentType(MediaType.APPLICATION_JSON);                   
        }catch(Exception ex){
            System.out.println("---------->> ERROR --------" + ex.toString() + " <<--------------");
        }
    }
}
