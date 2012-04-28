package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.domain.Knight;

@RequestMapping("/knights")
@Controller
@RooWebScaffold(path = "knights", formBackingObject = Knight.class)
@RooWebJson(jsonObject = Knight.class)
public class KnightController {
}
