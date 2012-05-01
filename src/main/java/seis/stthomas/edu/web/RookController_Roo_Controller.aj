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
import seis.stthomas.edu.domain.Rook;
import seis.stthomas.edu.service.BoardService;
import seis.stthomas.edu.web.RookController;

privileged aspect RookController_Roo_Controller {
    
    @Autowired
    BoardService RookController.boardService;
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String RookController.create(@Valid Rook rook, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, rook);
            return "rooks/create";
        }
        uiModel.asMap().clear();
        rook.persist();
        return "redirect:/rooks/" + encodeUrlPathSegment(rook.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String RookController.createForm(Model uiModel) {
        populateEditForm(uiModel, new Rook());
        return "rooks/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String RookController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("rook", Rook.findRook(id));
        uiModel.addAttribute("itemId", id);
        return "rooks/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String RookController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("rooks", Rook.findRookEntries(firstResult, sizeNo));
            float nrOfPages = (float) Rook.countRooks() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("rooks", Rook.findAllRooks());
        }
        return "rooks/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String RookController.update(@Valid Rook rook, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, rook);
            return "rooks/update";
        }
        uiModel.asMap().clear();
        rook.merge();
        return "redirect:/rooks/" + encodeUrlPathSegment(rook.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String RookController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, Rook.findRook(id));
        return "rooks/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String RookController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        Rook rook = Rook.findRook(id);
        rook.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/rooks";
    }
    
    void RookController.populateEditForm(Model uiModel, Rook rook) {
        uiModel.addAttribute("rook", rook);
        uiModel.addAttribute("boards", boardService.findAllBoards());
    }
    
    String RookController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
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
