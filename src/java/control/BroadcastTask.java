package control;

import org.glassfish.jersey.media.sse.OutboundEvent;

public class BroadcastTask implements Runnable{

    private GAMESession mgame;
    public String what;
    public String playerName;
    
    public BroadcastTask(GAMESession game){
        this.mgame = game;
    }
    
    @Override
    public void run() {
        try{
            switch(what){
                case "start_game" :mgame.BroadcastTask();
                    break;
                    
                default:
                    break;
            }
        }catch(Exception ex){
            System.out.println(ex.toString());
        }
    }
}
