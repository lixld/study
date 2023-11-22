package com.lixl.study.RPC_diy.clientSide;

import lombok.Data;

@Data
public class Point {
    int x, y;

    public Point() {
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
