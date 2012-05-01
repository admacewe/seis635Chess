package seis.stthomas.edu.domain;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.json.RooJson;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@RooJson
@Transactional
public class Square {

    @Value("false")
    private boolean inRange;

    @ManyToOne(targetEntity=seis.stthomas.edu.domain.Board.class,fetch=FetchType.EAGER)
    @JoinColumn(name="board", insertable=false,updatable=false, referencedColumnName="id")
    private Board board;
}
