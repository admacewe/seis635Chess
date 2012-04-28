package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.domain.Queen;

@RequestMapping("/queens")
@Controller
@RooWebScaffold(path = "queens", formBackingObject = Queen.class)
@RooWebJson(jsonObject = Queen.class)
public class QueenController {
}
