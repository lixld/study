/*
package com.lixl.study.IO.nio.netty.基础;


import java.util.Arrays;

*/
/**
 * @author : lixl
 * @date : 2020-06-11 11:41:07
 **//*

public class NettyByteBufDemo {


    public static void apiTest() {
        //Netty官方建议用工具包的方式来创建ByteBuffer ,而不是直接new对象
        ByteBuf buf = Unpooled.buffer(10);

        //1.创建一个非池化的ByteBuf,大小为10字节
        System.out.println("原始ByteBuf为===============>" + buf.toString());
        System.out.println("原始ByteBuf中内容为===============>" + Arrays.toString(buf.array()));

        //2.写入一段内容
        byte[] bytes = {1, 2, 3, 4, 5};
        buf.writeBytes(bytes);
        System.out.println("写入的bytes为===============>" + Arrays.toString(bytes));
        System.out.println("写入一段内容后ByteBuf为：" + buf.toString());
        System.out.println("2.ByteBuf中内容为：" + Arrays.toString(buf.array()));

        //3.读取一段内容
        byte b1 = buf.readByte();
        byte b2 = buf.readByte();
        System.out.println("读取的bytes为========>" + Arrays.toString(new byte[]{b1, b2}));
        System.out.println("读取一段内容后ByteBuf为========>" + buf.toString());
        System.out.println("3.ByteBuf中内容为：" + Arrays.toString(buf.array()));

        //4.将读取的内容丢弃
        buf.discardReadBytes();
        System.out.println("将读取的内容丢弃后ByteBuf为：" + buf.toString());
        System.out.println("4.ByteBuf中内容为：" + Arrays.toString(buf.array()));

        //5.清空读写指针
        buf.clear();
        System.out.println("将读取的内容丢弃后ByteBuf为：" + buf.toString());
        System.out.println("5.ByteBuf中内容为：" + Arrays.toString(buf.array()));

        //6.再次写入一段内容，比第一段内容少
        buf.clear();
        System.out.println("将读取的内容丢弃后ByteBuf为：" + buf.toString());
        System.out.println("6.ByteBuf中内容为：" + Arrays.toString(buf.array()));
        byte[] bytes2 = {1, 2, 3};
        buf.writeBytes(bytes2);

        //7.将ByteBuf清空
        buf.setZero(0, buf.capacity());
        System.out.println("将内容清零后ByteBuffer为：=======》" + buf.toString());
        System.out.println("7.ByteBuf中内容为：=======》" + Arrays.toString(buf.array()));

        //8.再次写入一段超过容量的内容
        byte[] bytes3 = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        buf.writeBytes(bytes3);
        System.out.println("写入的bytes为======>" + Arrays.toString(bytes3));
        System.out.println("将内容清零后ByteBuffer为：=======》" + buf.toString());
        System.out.println("8.ByteBuf中内容为：=======》" + Arrays.toString(buf.array()));
    }

    public static void main(String[] args) {
        apiTest();

        int newCapacity = 64;
        int minNewCapacity = 14;
        while (newCapacity < minNewCapacity) {
            newCapacity <<= 1;
        }
    }
}
*/
