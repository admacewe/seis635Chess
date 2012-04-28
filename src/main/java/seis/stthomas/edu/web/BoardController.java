package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.domain.Board;

@RequestMapping("/boards")
@Controller
@RooWebScaffold(path = "boards", formBackingObject = Board.class)
@RooWebJson(jsonObject = Board.class)
public class BoardController {
}
