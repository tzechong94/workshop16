package com.example.workshop16.model;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Pieces {
    private DecodingBoard decoding_board;
    private Pegs pegs;
    private Rulebook rulebook;

    public DecodingBoard getDecoding_board() {
        return decoding_board;
    }
    public void setDecoding_board(DecodingBoard decoding_board) {
        this.decoding_board = decoding_board;
    }
    public Pegs getPegs() {
        return pegs;
    }
    public void setPegs(Pegs pegs) {
        this.pegs = pegs;
    }
    public Rulebook getRulebook() {
        return rulebook;
    }
    public void setRulebook(Rulebook rulebook) {
        this.rulebook = rulebook;
    }

    public JsonObjectBuilder toJSON() {
        return Json.createObjectBuilder()
                .add("decoding_board", this.getDecoding_board().toJSON())
                .add("pegs", this.getPegs().toJSON())
                .add("rulebook", this.getRulebook().toJSON());
    }

    public static Pieces createJson(JsonObject o) {
        Pieces pieces = new Pieces();
        JsonObject decodingBoard = o.getJsonObject("decoding_board");
        JsonObject pegs = o.getJsonObject("pegs");
        JsonObject rulebook = o.getJsonObject("rulebook");
        pieces.decoding_board = DecodingBoard.createJson(decodingBoard);
        pieces.pegs = Pegs.createJson(pegs);
        pieces.rulebook = Rulebook.createJson(rulebook);
        return pieces;


    }
    
}
