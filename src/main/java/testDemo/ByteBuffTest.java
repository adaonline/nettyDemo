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
        //写完后的写指针等于capacity,此时不可写
        buffer.writeBytes(new byte[]{5});
        print("writeBytes(new byte[]{5})",buffer);
        //写的时候发现不可写，则开始扩容，capacity改变
        buffer.writeBytes(new byte[]{6});
        print("writeBytes(new byte[]{6})",buffer);

        //get方法不改写读写指针
        System.out.println("getByte(3) return:"+buffer.getByte(3));
        System.out.println("getShort(3) return:"+buffer.getShort(3));
        System.out.println("getInt(3) return:"+buffer.getInt(3));
        print("getbyte()",buffer);

        // set 方法不改变读写指针
        buffer.setByte(buffer.readableBytes() + 1, 0);
        print("setByte()", buffer);

        // read 方法改变读指针
        byte[] dst = new byte[buffer.readableBytes()];
        buffer.readBytes(dst);
        print("readBytes(" + dst.length + ")", buffer);
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
