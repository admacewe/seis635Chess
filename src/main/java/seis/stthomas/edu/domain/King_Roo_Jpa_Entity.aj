// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.domain;

import javax.persistence.Entity;
import seis.stthomas.edu.domain.King;

privileged aspect King_Roo_Jpa_Entity {
    
    declare @type: King: @Entity;
    
    public King.new() {
        super();
    }

}
