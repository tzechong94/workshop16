package com.example.workshop16.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.workshop16.model.Mastermind;
import com.example.workshop16.service.BoardGameService;

@RestController
@RequestMapping(path="/api/boardgame")
public class BoardGameController {
    
    @Autowired
    private BoardGameService bgSvc;

    @PostMapping
    public ResponseEntity<String> createBoardGame(@RequestBody Mastermind ms) {
        int insert_count = bgSvc.saveGame(ms);
        Mastermind result = new Mastermind();
        result = ms;
        result.setId(ms.getId());
        result.setInsertCount(insert_count);

        if (insert_count == 0) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(result.toJSONInsert().toString());
        }
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(result.toJSON().toString());
    }

    @GetMapping("{boardGameId}")
    public ResponseEntity<String> getBoardGame(@PathVariable String boardGameId) throws IOException{
        Mastermind ms = bgSvc.findById(boardGameId);
        System.out.println(ms);
        if (ms == null){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("");
        }

        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ms.toJSON().toString());
    }

    @PutMapping("{boardGameId}")
    public ResponseEntity<String> updateBoardGame(@RequestBody Mastermind ms, 
        @PathVariable String boardGameId, @RequestParam(required=false) boolean isUpsert) throws IOException {
            Mastermind result = null;
            ms.setUpsert(isUpsert);

            if (!isUpsert) {
                result = bgSvc.findById(boardGameId);

                if (null == result){
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body("400");
                }
            }
            if (isUpsert){
                ms.setId(boardGameId);
            } 
            int updatedCount = bgSvc.update(ms);
            ms.setUpdateCount(updatedCount);
            return ResponseEntity
                        .status(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(ms.toJSONUpdate().toString());
        }


}