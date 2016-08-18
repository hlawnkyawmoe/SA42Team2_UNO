package control;

import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GAMESessionALL {
    
    Map<String, GAMESession> map = new HashMap<>();
    
    public GAMESessionALL(){
    }
    
    public void add(GAMESession uno) {
        try{
            //System.out.println("---> add game " + uno.getGAMESessionID());
            map.put(uno.getGAMESessionID(), uno);
        }catch(Exception ex){
        }
    }
    
    public void setSingleGame(GAMESession game){
        add(game);
    }
    
    public GAMESession get(String GameID){
        return map.get(GameID);
    }
    
    public Map<String, GAMESession> getMap(){
        return this.map;
    }
    
    public boolean hasUNOGame(String ID){
        return map.containsKey(ID);
    }
}
