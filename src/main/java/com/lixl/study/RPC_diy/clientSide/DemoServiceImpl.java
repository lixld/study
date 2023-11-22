package com.lixl.study.RPC_diy.clientSide;

/**
 * @author : lixl
 * @date : 2021-01-13 22:20:40
 **/
public class DemoServiceImpl implements DemoService {
    public String sayHello(String message){
        return " hello "+ message;
    }

    public Point multipont(Point point){
        Point point1 = new Point();
        point1.setX(point.getX()*2);
        point1.setY(point.getY()*2);
        return point1;
    }
}
