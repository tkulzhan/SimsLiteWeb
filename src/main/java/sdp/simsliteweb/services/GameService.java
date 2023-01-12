package sdp.simsliteweb.services;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import sdp.simsliteweb.Mongo.Mongo;
import sdp.simsliteweb.patterns.Characters.Acc;
import sdp.simsliteweb.patterns.Characters.Subscriber;
import sdp.simsliteweb.patterns.DrinkFactory.DrinkFact;
import sdp.simsliteweb.patterns.DrinkFactory.DrinkFactory;
import sdp.simsliteweb.patterns.FoodDecorators.*;
import sdp.simsliteweb.patterns.RadioState.OffState;
import sdp.simsliteweb.patterns.RadioState.OnState;
import sdp.simsliteweb.patterns.WorkBehaviours.WorkAsBuilder;
import sdp.simsliteweb.patterns.WorkBehaviours.WorkAsLawyer;
import sdp.simsliteweb.patterns.WorkBehaviours.WorkAsNot;
import sdp.simsliteweb.patterns.WorkBehaviours.WorkBehaviour;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Service
public class GameService {
    public static ObjectId uid;
    public static ObjectId gid;
    public static Subscriber champ = new Subscriber("Champ123", new WorkAsNot(), "champ");
    public static Subscriber vik = new Subscriber("Viktor228", new WorkAsLawyer(), "vik");
    public static Subscriber smash = new Subscriber("Smasher520", new WorkAsBuilder(), "smash");
    public static Acc mainChar = Acc.getInstance();
    public static String currCharType = "sub";
    public static Subscriber currCharSub = new Subscriber("Default", new WorkAsNot(), "def");
    public static ArrayList<String> lines = new ArrayList<>();

    public static void addNewGame(String name) {
        Mongo.addNewGame(uid, name, gid);
    }

    public static ArrayList<String> readNew() {
        ArrayList<String> messages = currCharSub.readNewNotifications();
        makeSame(currCharSub);
        return messages;
    }

    public static ArrayList<String> readOld() {
        return currCharSub.readOldNotifications();
    }

    public static Map<String, String> changeChar(String name) {
        Map<String, String> res = new HashMap<>();
        switch (name) {
            case "champ" -> {
                currCharSub = champ;
                res.put("img", "233");
            }
            case "vik" -> {
                currCharSub = vik;
                res.put("img", "366");
            }
            case "smash" -> {
                currCharSub = smash;
                res.put("img", "412");
            }
            case "mainChar" -> {
                currCharType = "acc";
                res.put("type", "acc");
                res.put("img", "459");
                return res;
            }
        }
        res.put("type", "sub");
        currCharType = "sub";
        return res;
    }

    public static Subscriber getSub(String name) {
        return switch (name) {
            case "Champ123" -> champ;
            case "Viktor228" -> vik;
            case "Smasher520" -> smash;
            default -> new Subscriber(name, new WorkAsNot(), "def");
        };
    }

    public static void makeSame(Subscriber currChar) {
        switch (currChar.getUsername()) {
            case "Champ123" -> champ = currChar;
            case "Viktor228" -> vik = currChar;
            case "Smasher520" -> smash = currChar;
            default -> {
                System.err.println("Unforeseen error occurred, the game ends now");
                exitGame();
            }
        }
    }

    public static void unsub() {
        switch (currCharSub.getUsername()) {
            case "Champ123" -> champ.unsubscribe(mainChar);
            case "Viktor228" -> vik.unsubscribe(mainChar);
            case "Smasher520" -> smash.unsubscribe(mainChar);
            default -> {
                System.err.println("Unforeseen error occurred, the game ends now");
                exitGame();
            }
        }
    }

    public static void sub() {
        switch (currCharSub.getUsername()) {
            case "Champ123" -> champ.subscribe(mainChar);
            case "Viktor228" -> vik.subscribe(mainChar);
            case "Smasher520" -> smash.subscribe(mainChar);
            default -> {
                System.err.println("Unforeseen error occurred, the game ends now");
                exitGame();
            }
        }
    }

    public static void work() {
        if (currCharType.equals("sub")) {
            currCharSub.getPaid();
            makeSame(currCharSub);
        } else {
            mainChar.getPaid();
        }
    }

    public static void drink(String drink) {
        DrinkFactory drinkFactory = new DrinkFactory();
        DrinkFact drinkF = drinkFactory.getDrink(drink);
        if (currCharType.equals("sub")) {
            currCharSub.setSavings(currCharSub.getSavings() - drinkF.getPrice());
            makeSame(currCharSub);
        } else {
            mainChar.setSavings(mainChar.getSavings() - drinkF.getPrice());
        }
    }

    public static String eat(String num) {
        Food food = null;
        switch (num) {
            case "1" -> food = new MashedPotato(new Sausage(new Dish()));
            case "2" -> food = new MashedPotato(new Pattie(new Dish()));
            case "3" -> food = new Rice(new Sausage(new Dish()));
            case "4" -> food = new Rice(new Pattie(new Dish()));
            case "5" -> food = new Pasta(new Sausage(new Dish()));
            case "6" -> food = new Pasta(new Pattie(new Dish()));
            default -> food = new Dish();
        }
        if (currCharType.equals("sub")) {
            currCharSub.setSavings(currCharSub.getSavings() - food.getFoodPrice());
            makeSame(currCharSub);
        } else {
            mainChar.setSavings(mainChar.getSavings() - food.getFoodPrice());
        }
        return food.getFoodName();
    }

    public static void radio() {
        if (currCharType.equals("sub")) {
            if (currCharSub.radio.getState().toString().equals("Off")) {
                currCharSub.radio.setState(new OnState());
                makeSame(currCharSub);
            } else {
                currCharSub.radio.setState(new OffState());
                makeSame(currCharSub);
            }
        } else {
            if (mainChar.radio.getState().toString().equals("Off")) {
                mainChar.radio.setState(new OnState());
            } else {
                mainChar.radio.setState(new OffState());
            }
        }
    }

    public static void changeJob(String job) {
        if (currCharType.equals("sub")) {
            currCharSub.setWorkBehaviour(getJob(job));
            makeSame(currCharSub);
        } else {
            mainChar.setWorkBehaviour(getJob(job));
        }
    }

    private static WorkBehaviour getJob(String job) {
        WorkBehaviour workBehaviour = null;
        switch (job) {
            case "builder" -> {
                workBehaviour = new WorkAsBuilder();
            }
            case "lawyer" -> {
                workBehaviour = new WorkAsLawyer();
            }
            case "none" -> {
                workBehaviour = new WorkAsNot();
            }
        }
        return workBehaviour;
    }

    public static void setUid(ObjectId id) {
        uid = id;
    }

    public static void loadGame() {
        Mongo.getSubs(gid);
        Mongo.getUnreadRead(gid);
        Mongo.getSavings(gid);
        Mongo.getMcName(gid);
        Mongo.getRadio(gid);
        Mongo.getWork(gid);
        currCharSub = champ;
    }

    public static void saveGame() {
        Mongo.saveSubs(gid);
        Mongo.saveUnreadRaed(gid);
        Mongo.saveSavings(gid);
        Mongo.saveMcName(gid);
        Mongo.saveRadio(gid);
        Mongo.saveWork(gid);
    }

    public static void exitGame() {
        saveGame();
        System.exit(0);
    }

    public static void deleteGame() {
        Mongo.deleteGame(getGid(), getUid());
    }

    public static ObjectId getUid() {
        return uid;
    }

    public static ObjectId getGid() {
        return gid;
    }

    public static void setGid(ObjectId id) {
        gid = id;
    }

    public static ArrayList<String> getLines() {
        return lines;
    }
}
