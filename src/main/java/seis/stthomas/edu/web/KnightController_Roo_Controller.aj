// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import seis.stthomas.edu.domain.Board;
import seis.stthomas.edu.domain.Knight;
import seis.stthomas.edu.web.KnightController;

privileged aspect KnightController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String KnightController.create(@Valid Knight knight, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, knight);
            return "knights/create";
        }
        uiModel.asMap().clear();
        knight.persist();
        return "redirect:/knights/" + encodeUrlPathSegment(knight.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String KnightController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Knight());
        return "knights/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String KnightController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("knight", Knight.findKnight(id));
        uiModel.addAttribute("itemId", id);
        return "knights/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String KnightController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("knights", Knight.findKnightEntries(firstResult, sizeNo));
            float nrOfPages = (float) Knight.countKnights() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("knights", Knight.findAllKnights());
        }
        return "knights/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String KnightController.update(@Valid Knight knight, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, knight);
            return "knights/update";
        }
        uiModel.asMap().clear();
        knight.merge();
        return "redirect:/knights/" + encodeUrlPathSegment(knight.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String KnightController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Knight.findKnight(id));
        return "knights/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String KnightController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Knight knight = Knight.findKnight(id);
        knight.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/knights";
    }
    
    void KnightController.populateEditForm(Model uiModel, Knight knight) {
        uiModel.addAttribute("knight", knight);
        uiModel.addAttribute("boards", Board.findAllBoards());
    }
    
    String KnightController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
