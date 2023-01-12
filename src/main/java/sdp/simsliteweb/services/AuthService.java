package sdp.simsliteweb.services;

import org.bson.Document;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import sdp.simsliteweb.Mongo.Mongo;

@Service
public class AuthService {
    public String login(String username, String password, Model model) {
        Document user = Mongo.login(username, password);
        if (user != null) {
            GameService.setUid(user.getObjectId("_id"));
            if (user.getBoolean("isadmin")) {
                return "redirect:/admin";
            } else {
                return "redirect:/menu";
            }
        }
        model.addAttribute("message", "No user matching");
        return "login";
    }

    public String register(String username, String password, Model model) {
        if(!Mongo.isValid(username, password)) {
            model.addAttribute("message",
                    Mongo.specify(username, password));
            return "registration";
        }
        Mongo.register(username, password);
        return "redirect:/login";
    }
}
