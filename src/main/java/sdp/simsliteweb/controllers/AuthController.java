package sdp.simsliteweb.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sdp.simsliteweb.Mongo.Mongo;
import sdp.simsliteweb.services.AuthService;
import sdp.simsliteweb.services.GameService;

import java.util.Map;

@Controller
public class AuthController {
    AuthService authService = new AuthService();
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("message", "      ");
        return "login";
    }
    @GetMapping("/registration")
    public String regPage(Model model) {
        model.addAttribute("message", "       ");
        return "registration";
    }
    @PostMapping("/login")
    public String login(@RequestParam Map<String, String> req, Model model) {
        return authService.login(req.get("username"), req.get("password"), model);
    }
    @PostMapping("/registration")
    public String register(@RequestParam Map<String, String> req, Model model) {
        return authService.register(req.get("username"), req.get("password"), model);
    }
    @PostMapping("/logout")
    public String logout() {
        GameService.saveGame();
        Mongo.saveLog();
        GameService.setUid(null);
        GameService.setGid(null);
        return "login";
    }
}
