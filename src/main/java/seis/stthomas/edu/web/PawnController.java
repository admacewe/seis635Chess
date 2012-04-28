package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.domain.Pawn;

@RequestMapping("/pawns")
@Controller
@RooWebScaffold(path = "pawns", formBackingObject = Pawn.class)
@RooWebJson(jsonObject = Pawn.class)
public class PawnController {
}
