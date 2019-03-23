package testDemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

public class ByteBuffTest {
    public static void main(String[] args) {
        //初始一个长度为9，最大长度为100的buf

        ByteBuf buffer= ByteBufAllocator.DEFAULT.buffer(9,100);
        print("allocate ByteBuf(9,100)",buffer);
        //write写入内容
        buffer.writeBytes(new byte[]{1,2,3,4});
        print("writeBytes(1,2,3,4)",buffer);
        //写入内容，写入int类型后，写指针增加四个字节
        buffer.writeInt(1);
        print("writeInt(1)",buffer);
    }
    private static void print(String action, ByteBuf buffer) {
        System.out.println("after ===========" + action + "============");
        System.out.println("占用内存capacity(): " + buffer.capacity());
        System.out.println("最大可占用内存maxCapacity(): " + buffer.maxCapacity());
        System.out.println("当前读指针位置readerIndex(): " + buffer.readerIndex());
        System.out.println("可读长度readableBytes(): " + buffer.readableBytes());
        System.out.println("是否有可读的内容isReadable(): " + buffer.isReadable());
        System.out.println("当前写指针位置writerIndex(): " + buffer.writerIndex());
        System.out.println("当前可写容量writableBytes(): " + buffer.writableBytes());
        System.out.println("当前是否可写isWritable(): " + buffer.isWritable());
        System.out.println("当前最大可写长度maxWritableBytes(): " + buffer.maxWritableBytes());
        System.out.println();
    }
}
