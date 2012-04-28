// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.web;

import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import seis.stthomas.edu.domain.King;
import seis.stthomas.edu.web.KingController;

privileged aspect KingController_Roo_Controller_Json {
    
    @RequestMapping(value = "/{id}", headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> KingController.showJson(@PathVariable("id") Long id) {
        King king = King.findKing(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        if (king == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(king.toJson(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(headers = "Accept=application/json")
    @ResponseBody
    public ResponseEntity<String> KingController.listJson() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        List<King> result = King.findAllKings();
        return new ResponseEntity<String>(King.toJsonArray(result), headers, HttpStatus.OK);
    }
    
    @RequestMapping(method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> KingController.createFromJson(@RequestBody String json) {
        King king = King.fromJsonToKing(json);
        king.persist();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.POST, headers = "Accept=application/json")
    public ResponseEntity<String> KingController.createFromJsonArray(@RequestBody String json) {
        for (King king: King.fromJsonArrayToKings(json)) {
            king.persist();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> KingController.updateFromJson(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        King king = King.fromJsonToKing(json);
        if (king.merge() == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/jsonArray", method = RequestMethod.PUT, headers = "Accept=application/json")
    public ResponseEntity<String> KingController.updateFromJsonArray(@RequestBody String json) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        for (King king: King.fromJsonArrayToKings(json)) {
            if (king.merge() == null) {
                return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
            }
        }
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, headers = "Accept=application/json")
    public ResponseEntity<String> KingController.deleteFromJson(@PathVariable("id") Long id) {
        King king = King.findKing(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        if (king == null) {
            return new ResponseEntity<String>(headers, HttpStatus.NOT_FOUND);
        }
        king.remove();
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }
    
}
