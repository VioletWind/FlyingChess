package com.example.peng.flyingchess;

/**
 * Created by peng on 2018/6/28.
 */

public class Room {
    public String name;
    public Long id;
    public Short num;

    Room() {};
    Room(String n, Long i, Short m) {
        name = n;
        id = i;
        num = m;
    }

    void create(String n, Long i) {
        name = n;
        id = i;
        num = 1;
    }


}
