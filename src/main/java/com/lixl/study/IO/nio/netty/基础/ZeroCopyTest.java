package com.lixl.study.IO.nio.netty.基础;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

/**
 * @author : lixl
 * @date : 2020-06-11 16:01:29
 **/
public class ZeroCopyTest {
    public static void main(String[] args) {


        byte[] arr = {1,2,3,4,5};
        ByteBuf byteBuf3 = Unpooled.wrappedBuffer(arr);//java数组直接转为Netty的ByteBuf对象
        System.out.println(byteBuf3.getByte(4));
        /**
         * TODO 重点来了--------- 特别注意这里，我修改是java数组的，而不是Netty的ByteBuf
         * 可是NettyBuffer数组依然可以读取到新修改的值
         * 这就叫0拷贝机制
         */
        arr[4] = 6;
        System.out.println(byteBuf3.getByte(4));
        System.out.println("++++++++++++++++++++++++++++++");


        //拆分byteBuf
        ByteBuf byteBuf = Unpooled.wrappedBuffer("hello".getBytes());
        ByteBuf slice = byteBuf.slice(1, 2);
        slice.unwrap();
        /**
         * 拆分后，依旧保留着原来的数据（后面的UnpooledHeapByteBuf就是原来的数据）
         * UnpooledSlicedByteBuf(ridx: 0, widx: 2, cap: 2/2, unwrapped: UnpooledHeapByteBuf(ridx: 0, widx: 5, cap: 5/5))
         */
        System.out.println(slice.toString());
        System.out.println("---------------------");


        //合并byteBuf
        ByteBuf byteBuf1 = Unpooled.buffer(3);
        byteBuf1.writeByte(1);

        ByteBuf byteBuf2 = Unpooled.buffer(3);
        byteBuf2.writeByte(4);

        CompositeByteBuf compositeByteBuf = Unpooled.compositeBuffer();
        CompositeByteBuf newBuffer = compositeByteBuf.addComponents(true, byteBuf1, byteBuf2);
        System.out.println(newBuffer);

    }
}
