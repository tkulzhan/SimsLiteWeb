package sdp.simsliteweb.patterns.Characters;

import sdp.simsliteweb.patterns.RadioState.OffState;
import sdp.simsliteweb.patterns.RadioState.Radio;
import sdp.simsliteweb.patterns.WorkBehaviours.WorkBehaviour;

import java.util.ArrayList;

public class Subscriber implements Observer {
    private final String username;
    private ArrayList<String> unread = new ArrayList<>();
    private ArrayList<String> read = new ArrayList<>();
    private WorkBehaviour workBehaviour;
    private int savings;
    private boolean isSub;
    public Radio radio = new Radio(new OffState());
    private final String alias;

    public Subscriber(String username, WorkBehaviour workBehaviour, String alias) {
        this.username = username;
        this.workBehaviour = workBehaviour;
        this.savings = 10000;
        this.alias = alias;
    }

    public String work() {
        return this.workBehaviour.work();
    }

    public void getPaid() {
        this.setSavings(getSavings() + this.workBehaviour.getPaid());
    }

    @Override
    public void getNotification(String msg) {
        this.unread.add(msg);
    }

    @Override
    public ArrayList<String> readNewNotifications() {
        ArrayList<String> messages = new ArrayList<>(this.unread);
        this.read.addAll(this.unread);
        this.unread.removeAll(unread);
        return messages;
    }

    @Override
    public ArrayList<String> readOldNotifications() {
        return this.read;
    }

    public void subscribe(Acc acc) {
        acc.subscribe(this);
        this.isSub = true;
    }

    public void unsubscribe(Acc acc) {
        acc.unsubscribe(this);
        this.isSub = false;
    }

    public String getUsername() {
        return username;
    }

    public void setWorkBehaviour(WorkBehaviour workBehaviour) {
        this.workBehaviour = workBehaviour;
    }

    public int getSavings() {
        return this.savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    public boolean isSub() {
        return isSub;
    }

    public ArrayList<String> getRead() {
        return this.read;
    }

    public ArrayList<String> getUnread() {
        return this.unread;
    }

    public void setRead(ArrayList<String> read) {
        this.read = read;
    }

    public void setUnread(ArrayList<String> unread) {
        this.unread = unread;
    }

    public WorkBehaviour getWorkBehaviour() {
        return workBehaviour;
    }

    public void setSub(boolean sub) {
        this.isSub = sub;
    }

    public String getAlias() {
        return alias;
    }
}
