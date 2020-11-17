package net.gittab.basic.io;

/**
 * Main.
 *
 * @author rookiedev
 * @date 2020/9/17 14:34
 **/
public class Main {

    public static void main(String[] args) {
        // Java 中 IO 流可以从下面这些维度区分，按流向区分可分为输入和输出流，输入输出是相对于内存来说的，如果要将文件读取到内存使用输入流，将内存中的文件写到磁盘使用输出流
        // 按操作单元区分可分为字节流和字符流，
        // 按流的角色区分可分为节点流和处理流
        // Java 中的流基本上都是由下面这四个抽象类派生而来，InputStream/Reader, 所有输入流的基类，前者是字节流，后者是字符流，OutputStream/Writer，所有输出流的基类，前者是字节流，后者是字符流
        // 字符流是由 Java 虚拟机将字节转换得到的，这个过程相对来说比较耗时，而且如果编码类型不确定就很容易出现乱码问题。
        // 所以，I/O 流就干脆提供了一个直接操作字符的接口，方便我们平时对字符进行流操作。如果音频文件、图片等媒体文件用字节流比较好，如果涉及到字符的话使用字符流比较好。


    }
}
