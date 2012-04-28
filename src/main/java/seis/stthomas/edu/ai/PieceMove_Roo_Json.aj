// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.ai;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import seis.stthomas.edu.ai.PieceMove;

privileged aspect PieceMove_Roo_Json {
    
    public String PieceMove.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static PieceMove PieceMove.fromJsonToPieceMove(String json) {
        return new JSONDeserializer<PieceMove>().use(null, PieceMove.class).deserialize(json);
    }
    
    public static String PieceMove.toJsonArray(Collection<PieceMove> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<PieceMove> PieceMove.fromJsonArrayToPieceMoves(String json) {
        return new JSONDeserializer<List<PieceMove>>().use(null, ArrayList.class).use("values", PieceMove.class).deserialize(json);
    }
    
}