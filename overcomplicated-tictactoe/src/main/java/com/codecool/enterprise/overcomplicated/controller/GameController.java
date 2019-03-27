package com.codecool.enterprise.overcomplicated.controller;

import com.codecool.enterprise.overcomplicated.model.Player;
import com.codecool.enterprise.overcomplicated.model.TictactoeGame;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;


@Controller
@SessionAttributes({"player", "game" })
public class GameController {


    private TictactoeGame game;
    private int step;
    private String avatarUri;

    @ModelAttribute("player")
    public Player getPlayer() {
        return new Player();
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    @ModelAttribute("funfact")
    private String getFunfact() {
        String url = "http://localhost:63200/";
        RestTemplate template = new RestTemplate();
        JSONObject result = template.getForObject(url, JSONObject.class);
        return result.get("value").toString();
    }



    @ModelAttribute("game")
    public TictactoeGame getGame() {
        this.game = new TictactoeGame();
        game.fillFields();
        step = 0;
        return this.game;
    }


    private String getComic() {
        String url = "http://localhost:63202";
        RestTemplate template = new RestTemplate();
        JSONObject result = template.getForObject(url, JSONObject.class);
        return (String) result.get("img");
    }

    @PostConstruct
    private void setAvatarUri() {
        String url = "http://localhost:63201/";
        RestTemplate template = new RestTemplate();
        String result = template.getForObject(url, String.class);
        this.avatarUri = result;
    }

    @GetMapping(value = "/")
    public String welcomeView(@ModelAttribute Player player, @ModelAttribute("funfact") String funfact, Model model){
        setAvatarUri();
        model.addAttribute("avatar_uri", getAvatarUri());
        model.addAttribute("player", player);
        return "welcome";
    }

    @PostMapping(value="/changeplayerusername")
    public String changPlayerUserName(@ModelAttribute Player player) {
        return "redirect:/game";
    }

    @RequestMapping(value = "/game", method = {RequestMethod.GET, RequestMethod.POST})
    public String gameView(@ModelAttribute("player") Player player, Model model) {
        getGame();
        model.addAttribute("funfact", getFunfact());
        model.addAttribute("comic_uri", getComic());
        model.addAttribute("avatar_uri", getAvatarUri());
        return "game";
    }

    @PostMapping(value = "/game-move")
    @ResponseBody
    public Map<String, String> gameMove(@ModelAttribute("player") Player player, @RequestBody String square) {
        Integer squareId = Integer.parseInt(square.substring(square.length() - 2, square.length() - 1));
        step++;
        String sign = step % 2 == 0? "X": "O";
        game.updateField(squareId, sign);
        Map<String, String> response = new HashMap<>();
        response.put("sign", sign);
        String won = game.isGameWon(sign)? "yes":"no";
        response.put("won", won);
        return response;
    }
}