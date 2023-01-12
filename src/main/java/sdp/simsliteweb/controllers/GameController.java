package sdp.simsliteweb.controllers;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sdp.simsliteweb.Mongo.Mongo;
import sdp.simsliteweb.services.GameService;

import java.util.*;

@Controller
public class GameController {

    @GetMapping("/")
    public String WelcomePage() {
        return "redirect:/login";
    }
    @GetMapping("/menu")
    public String MenuPage(Model model) {
        if(GameService.uid == null) {
            model.addAttribute("message", "You are not logged in");
            return "login";
        }
        List<Game> g = new ArrayList<>();
        ArrayList<ObjectId> games = (ArrayList<ObjectId>) Mongo.getGames(GameService.getUid());
        for (ObjectId o:games) {
            g.add(new Game(Mongo.getGameName(o), o.toString()));
        }
        model.addAttribute("games", g);
        return "menu";
    }
    @GetMapping("/new_game")
    public String NewGamePage(@RequestParam Map<String, String> req, Model model) {
        if(GameService.uid == null) {
            model.addAttribute("message", "You are not logged in");
            return "login";
        }
        GameService.lines.add("GAME START!!!");
        GameService.gid = new ObjectId();
        GameService.addNewGame(req.get("name"));
        GameService.loadGame();
        model.addAttribute("job", "Unemployed");
        model.addAttribute("savings", "10000");
        model.addAttribute("isSub", "Subscribed");
        model.addAttribute("img", "233");
        model.addAttribute("mcname", GameService.mainChar.getAlias());
        model.addAttribute("workstatus", GameService.currCharSub.work());
        model.addAttribute("name", GameService.currCharSub.getUsername());
        model.addAttribute("radiostate", "Off");
        model.addAttribute("status", "Idle");
        return "game";
    }
    @GetMapping("/game")
    public String GamePage(@RequestParam Map<String, String> req, Model model) {
        if(GameService.uid == null) {
            model.addAttribute("message", "You are not logged in");
            return "login";
        }
        GameService.lines.add("Resuming game session");
        GameService.gid = new ObjectId(req.get("game"));
        GameService.loadGame();
        if(Objects.equals(GameService.champ.getWorkBehaviour().toString(), "None")) {
            model.addAttribute("job", "Unemployed");
        } else {
            model.addAttribute("job", GameService.champ.getWorkBehaviour());
        }
        model.addAttribute("savings", GameService.champ.getSavings());
        if(GameService.champ.isSub()) {
            model.addAttribute("isSub", "Subscribed");
        } else {
            model.addAttribute("isSub", "Not subscribed");
        }
        model.addAttribute("img", "233");
        model.addAttribute("mcname", GameService.mainChar.getAlias());
        model.addAttribute("workstatus", GameService.currCharSub.work());
        model.addAttribute("name", GameService.currCharSub.getUsername());
        model.addAttribute("radiostate", GameService.currCharSub.radio.getState().toString());
        model.addAttribute("status", "Idle");
        return "game";
    }
    @PostMapping("/read_new")
    public String readNew(Model model) {
        GameService.lines.add("Reading new posts");
        ArrayList<String> messages = GameService.readNew();
        formatMsg(model, messages);
        model.addAttribute("author", "New posts from " + GameService.mainChar.getAlias());
        return "read";
    }

    @PostMapping("/read_old")
    public String readOld(Model model) {
        GameService.lines.add("Reading old posts");
        ArrayList<String> messages = GameService.readOld();
        formatMsg(model, messages);
        model.addAttribute("author", "Old posts from " + GameService.mainChar.getAlias());
        return "read";
    }

    private void formatMsg(Model model, ArrayList<String> messages) {
        ArrayList<Message> msgs = new ArrayList<>();
        for (String m:messages) {
            String[] msg = m.split(":");
            msgs.add(new Message(msg[1], msg[0].toUpperCase().substring(0, 1)));
        }
        model.addAttribute("messages", msgs);
    }

    @PostMapping("/change_char")
    public String change_char(Model model, @RequestParam Map<String, String> req) {
        if(GameService.uid == null) {
            model.addAttribute("message", "You are not logged in");
            return "login";
        }
        GameService.lines.add("Switching characters");
        Map<String, String> res = GameService.changeChar(req.get("char_name"));
        if(res.get("type").equals("sub")) {
            if(Objects.equals(GameService.currCharSub.getWorkBehaviour().toString(), "None")) {
                model.addAttribute("job", "Unemployed");
            } else {
                model.addAttribute("job", GameService.currCharSub.getWorkBehaviour());
            }
            if(GameService.currCharSub.isSub()) {
                model.addAttribute("isSub", "Subscribed");
            } else {
                model.addAttribute("isSub", "Not subscribed");
            }
            model.addAttribute("img", res.get("img"));
            model.addAttribute("mcname", GameService.mainChar.getAlias());
            model.addAttribute("workstatus", GameService.currCharSub.work());
            model.addAttribute("savings", GameService.currCharSub.getSavings());
            model.addAttribute("name", GameService.currCharSub.getUsername());
            model.addAttribute("radiostate", GameService.currCharSub.radio.getState().toString());
            if(req.get("needStatus").equals("true")) {
                model.addAttribute("status", "Idle");
            }
            return "game";
        } else {
            model.addAttribute("savings", GameService.mainChar.getSavings());
            if(Objects.equals(GameService.mainChar.getWorkBehaviour().toString(), "None")) {
                model.addAttribute("job", "Unemployed");
            } else {
                model.addAttribute("job", GameService.mainChar.getWorkBehaviour());
            }
            model.addAttribute("img", "459");
            model.addAttribute("mcname", GameService.mainChar.getAlias());
            model.addAttribute("radiostate", GameService.mainChar.radio.getState().toString());
            model.addAttribute("workstatus", GameService.mainChar.work());
            if(req.get("needStatus").equals("true")) {
                model.addAttribute("status", "Idle");
            }
            return "gameMain";
        }
    }

    @PostMapping("/post_msg")
    public void post(@RequestParam Map<String, String> req) {
        GameService.lines.add("Posting new message");
        String message = req.get("text");
        GameService.mainChar.notify(message);
    }

    @PostMapping("/change_name")
    public String change_name(@RequestParam Map<String, String> req, Model model) {
        GameService.lines.add("Change main character name");
        GameService.mainChar.customize(req.get("text"));
        return change_char(model, getMap("true"));
    }

    @PostMapping("/subscribe")
    public void sub() {
        GameService.lines.add("Subscribe");
        GameService.sub();
    }

    @PostMapping("/unsubscribe")
    public void unsub() {
        GameService.lines.add("Unsubscribe");
        GameService.unsub();
    }

    @PostMapping("/unsub")
    public void unsubb(@RequestParam Map<String, String> req) {
        GameService.currCharSub = GameService.getSub(req.get("name"));
        GameService.unsub();
    }

    @PostMapping("/work")
    public void work() {
        GameService.lines.add("Working");
        GameService.work();
    }

    @PostMapping("/get_food")
    public String eat(@RequestParam Map<String, String> req, Model model) {
        if(GameService.uid == null) {
            model.addAttribute("message", "You are not logged in");
            return "login";
        }
        GameService.lines.add("Eating");
        String food = GameService.eat(req.get("food_set"));
        Map<String, String> rq = getMap("false");
        model.addAttribute("status", "Eating " + food);
        return change_char(model, rq);
    }
    @PostMapping("/get_drink")
    public String drink(@RequestParam Map<String, String> req, Model model) {
        if(GameService.uid == null) {
            model.addAttribute("message", "You are not logged in");
            return "login";
        }
        GameService.lines.add("Drinking");
        GameService.drink(req.get("drink"));
        Map<String, String> rq = getMap("false");
        model.addAttribute("status", "Drinking " + req.get("drink"));
        return change_char(model, rq);
    }

    private Map<String, String> getMap(String needStatus) {
        Map<String, String> rq = new HashMap<>();
        if(GameService.currCharType.equals("sub")) {
            rq.put("char_name", GameService.currCharSub.getAlias());
        } else {
            rq.put("char_name", "mainChar");
        }
        rq.put("needStatus", needStatus);
        return rq;
    }

    @PostMapping("/radio")
    public void radio() {
        GameService.lines.add("Switching radio");
        GameService.radio();
    }

    @PostMapping("/change_job")
    public String changeJob(@RequestParam Map<String, String> req, Model model) {
        GameService.lines.add("Changing jobs");
        GameService.changeJob(req.get("new_job"));
        Map<String, String> rq = getMap("true");
        return change_char(model, rq);
    }

    @PostMapping("/delete")
    public String deleteGame() {
        GameService.deleteGame();
        return "redirect:/menu";
    }

    @PostMapping("/save")
    public void save() {
        GameService.saveGame();
    }
}
