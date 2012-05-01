// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import seis.stthomas.edu.domain.Piece;
import seis.stthomas.edu.service.PieceServiceImpl;

privileged aspect PieceServiceImpl_Roo_Service {
    
    declare @type: PieceServiceImpl: @Service;
    
    declare @type: PieceServiceImpl: @Transactional;
    
    public long PieceServiceImpl.countAllPieces() {
        return Piece.countPieces();
    }
    
    public void PieceServiceImpl.deletePiece(Piece piece) {
        piece.remove();
    }
    
    public Piece PieceServiceImpl.findPiece(Long id) {
        return Piece.findPiece(id);
    }
    
    public List<Piece> PieceServiceImpl.findAllPieces() {
        return Piece.findAllPieces();
    }
    
    public List<Piece> PieceServiceImpl.findPieceEntries(int firstResult, int maxResults) {
        return Piece.findPieceEntries(firstResult, maxResults);
    }
    
    public void PieceServiceImpl.savePiece(Piece piece) {
        piece.persist();
    }
    
    public Piece PieceServiceImpl.updatePiece(Piece piece) {
        return piece.merge();
    }
    
}