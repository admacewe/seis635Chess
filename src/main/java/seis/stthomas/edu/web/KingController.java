package seis.stthomas.edu.web;

import org.springframework.roo.addon.web.mvc.controller.json.RooWebJson;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seis.stthomas.edu.domain.King;

@RequestMapping("/kings")
@Controller
@RooWebScaffold(path = "kings", formBackingObject = King.class)
@RooWebJson(jsonObject = King.class)
public class KingController {
}
