package com.codecool.enterprise.overcomplicated.controller;

import com.codecool.enterprise.overcomplicated.model.Player;
import com.codecool.enterprise.overcomplicated.model.TictactoeGame;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.json.*;

import java.util.HashMap;
import java.util.Map;

@Controller
@SessionAttributes({"player", "game"})
public class GameController {


    private TictactoeGame game;
    private int step;

    @ModelAttribute("player")
    public Player getPlayer() {
        return new Player();
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



    @ModelAttribute("avatar_uri")
    public String getAvatarUri() {
        return "https://robohash.org/codecool";
    }

    @GetMapping(value = "/")
    public String welcomeView(@ModelAttribute Player player, @ModelAttribute("funfact") String funfact) {
        return "welcome";
    }

    @PostMapping(value="/changeplayerusername")
    public String changPlayerUserName(@ModelAttribute Player player) {
        return "redirect:/game";
    }

    @RequestMapping(value = "/game", method = {RequestMethod.GET, RequestMethod.POST})
    public String gameView(@ModelAttribute("player") Player player, Model model ) {
        getGame();
        model.addAttribute("funfact", "&quot;Chuck Norris knows the last digit of pi.&quot;");
        model.addAttribute("comic_uri", "https://imgs.xkcd.com/comics/bad_code.png");
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