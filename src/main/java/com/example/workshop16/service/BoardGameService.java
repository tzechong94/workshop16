package com.example.workshop16.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.workshop16.model.Mastermind;

@Service
public class BoardGameService {
    
    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    public int saveGame(final Mastermind mds) {
        redisTemplate.opsForValue().set(mds.getId(), mds.toJSON().toString());
        String result = (String) redisTemplate.opsForValue().get(mds.getId());
        if (result != null)
            return 1;
        return 0;
    }

    public Mastermind findById(final String msid) throws IOException {
        String mdsStr = (String) redisTemplate.opsForValue().get(msid);
        Mastermind m = Mastermind.create(mdsStr);
        m.setId(msid);
        return m;
    }

    public int update(final Mastermind mds) {
        
        if (mds.isUpsert()) {
            redisTemplate.opsForValue().set(mds.getId(), mds.toJSON().toString());
        } else {
            redisTemplate.opsForValue().setIfAbsent(mds.getId(), mds.toJSON().toString());
        }
        String result = (String) redisTemplate.opsForValue()
                                .get(mds.getId());
        if (null != result){
            return 1;
        }
        return 0;
    }


}
