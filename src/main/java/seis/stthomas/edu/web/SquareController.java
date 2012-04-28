package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.domain.Square;

@RequestMapping("/squares")
@Controller
@RooWebScaffold(path = "squares", formBackingObject = Square.class)
@RooWebJson(jsonObject = Square.class)
public class SquareController {
}
