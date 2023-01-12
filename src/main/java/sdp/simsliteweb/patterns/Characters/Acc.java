package sdp.simsliteweb.patterns.Characters;

import sdp.simsliteweb.patterns.WorkBehaviours.*;
import sdp.simsliteweb.patterns.RadioState.OffState;
import sdp.simsliteweb.patterns.RadioState.Radio;

import java.util.HashSet;
import java.util.Set;

public class Acc implements InstaSevice {
    private Set<Observer> subscribers = new HashSet<>();
    private String alias;
    private WorkBehaviour workBehaviour;
    private static Acc instance = new Acc("Stranger", new WorkAsNot());
    private int savings;
    public Radio radio = new Radio(new OffState());

    private Acc(String alias, WorkBehaviour workBehaviour) {
        this.alias = alias;
        this.workBehaviour = workBehaviour;
        this.savings = 100000;
    }

    public String work() {
        return this.workBehaviour.work();
    }

    public void getPaid() {
        this.setSavings(getSavings() + this.workBehaviour.getPaid());
    }

    @Override
    public void subscribe(Observer o) {
        this.subscribers.add(o);
    }

    @Override
    public void unsubscribe(Observer o) {
        this.subscribers.remove(o);
    }

    @Override
    public void notify(String m) {
        for (Observer o : this.subscribers) {
            o.getNotification(this.alias + ":" + m);
        }
        System.out.println(this.subscribers);
    }

    public String seeAccDetails() {
        return "You have " + subscribers.size() + " subs.";
    }

    public String getAlias() {
        return alias;
    }

    public int getSavings() {
        return savings;
    }

    public void setSavings(int savings) {
        this.savings = savings;
    }

    public static Acc getInstance() {
        return instance;
    }

    public void customize(String alias) {
        instance.alias = alias;
    }

    public void setSubscribers(Set<Observer> subscribers) {
        this.subscribers = subscribers;
    }

    public void setWorkBehaviour(WorkBehaviour workBehaviour) {
        instance.workBehaviour = workBehaviour;
    }

    public WorkBehaviour getWorkBehaviour() {
        return instance.workBehaviour;
    }

    public Set<Observer> getSubscribers() {
        return this.subscribers;
    }
}
