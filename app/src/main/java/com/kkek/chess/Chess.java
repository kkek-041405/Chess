package com.kkek.chess;

import java.util.HashMap;
import java.util.Map;

public class Chess {
    Map<String,Peice> pos = new HashMap<String, Peice>();
    public Chess(){
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                pos.put(""+i+j,Peice.NONE);
            }
        }
        reset();
    }
    public  void reset(){
        pos.put("11",Peice.Rook);
        pos.put("81",Peice.Rook);
        pos.put("88",Peice.Rook);
        pos.put("18",Peice.Rook);
        System.out.println(pos);
    }
}

enum Peice{
    King,
    Queen,
    Rook,
    Bishop,
    Knight,
    Pawn,
    NONE
}

