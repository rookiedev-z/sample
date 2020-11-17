package net.gittab.basic.loader;

/**
 * ClassModifier.
 *
 * @author rookiedev 2020/11/16 22:21
 **/
public class ClassModifier {

    private static final int CONSTANT_POOL_COUNT_INDEX = 8;

    private static final int CONSTANT_UTF8_INFO = 1;

    private static final int[] CONSTANT_ITEM_LENGTH = {-1, -1, -1, 5, 5, 9, 9, 3, 3, 5, 5, 5, 5};

    private static final int u1 = 1;

    private static final int u2 = 2;

    private byte[] classBytes;

    public ClassModifier(byte[] classBytes){
        this.classBytes = classBytes;
    }

    public byte[] modifyUTF8Constant(String oldStr, String newStr){
        int cpc = getConstantPoolCount();
        int offset = CONSTANT_POOL_COUNT_INDEX + u2;
        for(int i = 0; i < cpc; i++){
            int tag = ByteUtils.bytes2Int(classBytes, offset, u1);
            if(tag == CONSTANT_UTF8_INFO){
                int len = ByteUtils.bytes2Int(classBytes, offset + u1, u2);
                offset += (u1 + u2);
                String str = ByteUtils.byte2String(classBytes, offset, len);
                if(str.equalsIgnoreCase(oldStr)){
                    byte[] strBytes = ByteUtils.string2Byte(newStr);
                    byte[] strLen = ByteUtils.int2Bytes(newStr.length(), u2);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset - u2, u2, strLen);
                    classBytes = ByteUtils.bytesReplace(classBytes, offset, len, strBytes);
                    return classBytes;
                }
            }else{
                offset += CONSTANT_ITEM_LENGTH[tag];
            }
        }
        return classBytes;
    }

    public int getConstantPoolCount(){
        return ByteUtils.bytes2Int(classBytes, CONSTANT_POOL_COUNT_INDEX, u2);
    }
}
