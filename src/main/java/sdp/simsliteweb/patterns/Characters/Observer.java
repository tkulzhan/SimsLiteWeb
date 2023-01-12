package sdp.simsliteweb.patterns.Characters;

import java.util.ArrayList;

public interface Observer {
    public void getNotification(String msg);
    public ArrayList<String> readNewNotifications();
    public ArrayList<String> readOldNotifications();
}
