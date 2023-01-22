package com.example.workshop16.model;

import jakarta.json.Json;
import jakarta.json.JsonNumber;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class DecodingBoard {
    
    private int total_count;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }
    
    public JsonObjectBuilder toJSON(){
        return Json.createObjectBuilder()
                    .add("total_count", this.getTotal_count());
    }

    public static DecodingBoard createJson(JsonObject o){
        DecodingBoard d = new DecodingBoard();
        JsonNumber totalCnt = o.getJsonNumber("total_count");
        System.out.println(totalCnt);
        d.total_count = totalCnt.intValue();
        return d;
    }
}
