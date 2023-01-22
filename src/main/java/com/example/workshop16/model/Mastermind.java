package com.example.workshop16.model;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;


import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

public class Mastermind {
    private String name;
    private Pieces pieces;
    private String id;
    private int insertCount;
    private int updateCount;
    private boolean isUpsert;

    public Mastermind(){
        this.id = generateId(8);
    }
    
    private synchronized String generateId(int numChars) {
        Random r = new Random();
        StringBuilder strBuilder = new StringBuilder();
        while (strBuilder.length() < numChars) {
            strBuilder.append(Integer.toHexString(r.nextInt()));
        }
        return strBuilder.toString().substring(0, numChars);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Pieces getPieces() {
        return pieces;
    }
    public void setPieces(Pieces pieces) {
        this.pieces = pieces;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public int getInsertCount() {
        return insertCount;
    }
    public void setInsertCount(int insertCount) {
        this.insertCount = insertCount;
    }
    public int getUpdateCount() {
        return updateCount;
    }
    public void setUpdateCount(int updateCount) {
        this.updateCount = updateCount;
    }
    public boolean isUpsert() {
        return isUpsert;
    }
    public void setUpsert(boolean isUpsert) {
        this.isUpsert = isUpsert;
        
    }

    public JsonObject toJSON() {
        return Json.createObjectBuilder()
                .add("name", this.getName())
                .add("pieces", this.getPieces().toJSON())
                .add("id", this.getId())
                .add("insert_count", this.getInsertCount())
                .build();
    }

    public JsonObject toJSONInsert() {
        return Json.createObjectBuilder()
                .add("id", this.getId())
                .add("insert_count", this.getInsertCount())
                .build();
    }

    public JsonObject toJSONUpdate() {
        return Json.createObjectBuilder()
                .add("id", this.getId())
                .add("update_count", this.getUpdateCount())
                .build();
    }


    public static Mastermind create(String json) throws IOException {
        Mastermind m = new Mastermind();
        try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            m.setName(o.getString("name"));
            JsonObject pieces = o.getJsonObject("pieces");
            m.setPieces(Pieces.createJson(pieces));
        }
        return m;
    }

    @Override
    public String toString() {
        return this.getId() + " " + this.getName();
    }




}
