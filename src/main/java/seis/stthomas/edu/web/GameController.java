package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.domain.Game;

@RequestMapping("/games")
@Controller
@RooWebScaffold(path = "games", formBackingObject = Game.class)
@RooWebJson(jsonObject = Game.class)
public class GameController {
}
