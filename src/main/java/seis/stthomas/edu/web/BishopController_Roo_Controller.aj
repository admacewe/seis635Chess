// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.web;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;
import seis.stthomas.edu.domain.Bishop;
import seis.stthomas.edu.service.BoardService;
import seis.stthomas.edu.web.BishopController;

privileged aspect BishopController_Roo_Controller {
    
    @Autowired
    BoardService BishopController.boardService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String BishopController.create(@Valid Bishop bishop, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bishop);
            return "bishops/create";
        }
        uiModel.asMap().clear();
        bishop.persist();
        return "redirect:/bishops/" + encodeUrlPathSegment(bishop.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String BishopController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Bishop());
        return "bishops/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String BishopController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("bishop", Bishop.findBishop(id));
        uiModel.addAttribute("itemId", id);
        return "bishops/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String BishopController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("bishops", Bishop.findBishopEntries(firstResult, sizeNo));
            float nrOfPages = (float) Bishop.countBishops() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("bishops", Bishop.findAllBishops());
        }
        return "bishops/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String BishopController.update(@Valid Bishop bishop, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, bishop);
            return "bishops/update";
        }
        uiModel.asMap().clear();
        bishop.merge();
        return "redirect:/bishops/" + encodeUrlPathSegment(bishop.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String BishopController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Bishop.findBishop(id));
        return "bishops/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String BishopController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Bishop bishop = Bishop.findBishop(id);
        bishop.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/bishops";
    }
    
    void BishopController.populateEditForm(Model uiModel, Bishop bishop) {
        uiModel.addAttribute("bishop", bishop);
        uiModel.addAttribute("boards", boardService.findAllBoards());
    }
    
    String BishopController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
