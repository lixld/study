package com.lixl.study.IO.nio.netty.基础;

import java.nio.ByteBuffer;

/**
 * @author : lixl
 * @date : 2020-06-05 20:28:35
 **/
public class JDKByteBufferDemo1 {
    public static void main(String[] args) {
//        ByteBuffer byteBuffer =  ByteBuffer.allocate(4);//非直接内存，heap堆内内存
        ByteBuffer byteBuffer =  ByteBuffer.allocateDirect(4);//直接内存，堆外内存
        //减少一次拷贝，性能确实可观时才去使用：分配给大型、长寿命（网络传输 、文件读写场景）
        //通过虚拟机参数 MaxDirectMemorySize限制大小，防止耗尽整个机器内存；
        System.out.println(String.format("容量%s,位置%s,limit%s",byteBuffer.capacity(),byteBuffer.position(),byteBuffer.limit()));

        //写数据
        byteBuffer.put((byte)1);
        byteBuffer.put((byte)2);
        byteBuffer.put((byte)3);

        System.out.println("写入3字节后：");
        System.out.println(String.format("容量%s,位置%s,limit%s",byteBuffer.capacity(),byteBuffer.position(),byteBuffer.limit()));


        System.out.println("转换为读模式，不转换也可以读，只不过位置下标不对");
        byteBuffer.flip();//读模式
        System.out.println("----------------开始读取----------------");
        byte b = byteBuffer.get();
        byte c = byteBuffer.get();
        byte d = byteBuffer.get();
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);

        //此时读模式下limit =3  position =2  继续写入只能覆盖写入一条数据
        //cler()方法清空缓存区
        //compact()方法仅清除已阅读的数据，转为写模式

        byteBuffer.compact();
        byteBuffer.put((byte)4);
        byteBuffer.put((byte)5);
        byteBuffer.put((byte)6);
        System.out.println("最终情况：");
        System.out.println(String.format("容量%s,位置%s,limit%s",byteBuffer.capacity(),byteBuffer.position(),byteBuffer.limit()));


    }
}
