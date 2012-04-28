package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.domain.Rook;

@RequestMapping("/rooks")
@Controller
@RooWebScaffold(path = "rooks", formBackingObject = Rook.class)
@RooWebJson(jsonObject = Rook.class)
public class RookController {
}
