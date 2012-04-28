package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.ai.PieceMove;

@RequestMapping("/piecemoves")
@Controller
@RooWebScaffold(path = "piecemoves", formBackingObject = PieceMove.class)
@RooWebJson(jsonObject = PieceMove.class)
public class PieceMoveController {
}
