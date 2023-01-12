package sdp.simsliteweb.controllers;

import java.util.ArrayList;

public class Log {
    public String id;
    public String uid;
    public String gid;
    public String timestamp;
    public ArrayList<String> Log;

    public Log(String id, String uid, String gid, String timestamp, ArrayList<String> Log) {
        this.id = id;
        this.gid = gid;
        this.uid = uid;
        this.Log = Log;
        this.timestamp = timestamp;
    }
}
