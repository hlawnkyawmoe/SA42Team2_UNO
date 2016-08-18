package control;

import java.util.UUID;

public class General {
    
    private String UNOSessionID;
    public static String STATUS_PREGAME = "preGame";
    public static String STATUS_RUNNING = "running";
    public static String STATUS_ENDED = "ended";

    public String getUNOID() {
        UUID id = UUID.randomUUID();
        UNOSessionID = id.toString();
        return UNOSessionID;
    }

    public void setUNOID(String UNOID) {
        this.UNOSessionID = UNOID;
    }
    
}
