package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.domain.Bishop;

@RequestMapping("/bishops")
@Controller
@RooWebScaffold(path = "bishops", formBackingObject = Bishop.class)
@RooWebJson(jsonObject = Bishop.class)
public class BishopController {
}
