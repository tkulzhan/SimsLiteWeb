package sdp.simsliteweb.controllers;

import com.mongodb.client.FindIterable;
import org.bson.Document;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import sdp.simsliteweb.Mongo.Mongo;
import sdp.simsliteweb.services.GameService;

import java.util.ArrayList;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String AdminPage(Model model) {
        if(GameService.uid == null) {
            model.addAttribute("message", "You are not logged in");
            return "login";
        }
        FindIterable<Document> logs = Mongo.getLog();
        ArrayList<Log> l = new ArrayList<>();
        for (Document d:logs) {
            if(d.get("User_id") != null && d.get("User_id") != null)
                l.add(new Log(d.get("_id").toString(),
                        d.get("Game_id").toString(),
                        d.get("User_id").toString(),
                        d.get("timestamp").toString(),
                        (ArrayList<String>) d.getList("Log", String.class))
                );
        }
        model.addAttribute("logs", l);
        return "admin";
    }
}
