// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package seis.stthomas.edu.domain;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import seis.stthomas.edu.domain.Rook;

privileged aspect Rook_Roo_Json {
    
    public String Rook.toJson() {
        return new JSONSerializer().exclude("*.class").serialize(this);
    }
    
    public static Rook Rook.fromJsonToRook(String json) {
        return new JSONDeserializer<Rook>().use(null, Rook.class).deserialize(json);
    }
    
    public static String Rook.toJsonArray(Collection<Rook> collection) {
        return new JSONSerializer().exclude("*.class").serialize(collection);
    }
    
    public static Collection<Rook> Rook.fromJsonArrayToRooks(String json) {
        return new JSONDeserializer<List<Rook>>().use(null, ArrayList.class).use("values", Rook.class).deserialize(json);
    }
    
}