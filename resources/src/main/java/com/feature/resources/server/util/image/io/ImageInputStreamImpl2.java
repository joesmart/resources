package com.feature.resources.server.util.image.io;

import javax.imageio.stream.ImageInputStreamImpl;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 * Created with IntelliJ IDEA.
 * User: yanjianzou
 * Date: 12/19/12
 * Time: 3:53 PM
 */
public abstract  class ImageInputStreamImpl2 extends ImageInputStreamImpl{
    private static final int BYTE_BUF_LENGTH = 8192;

    byte[] byteBuf = new byte[BYTE_BUF_LENGTH];

    @Override
    public short readShort() throws IOException {
        readFully(byteBuf,0,2);

        if(byteOrder == ByteOrder.BIG_ENDIAN){
            return (short) (((byteBuf[0] & 0xff) << 8) | ((byteBuf[1] & 0xff) << 0));
        }else{
            return (short) (((byteBuf[1] & 0xff) << 8) | ((byteBuf[0] & 0xff) << 0));
        }

    }

    @Override
    public int readInt() throws IOException {
        readFully(byteBuf, 0, 4);
        if (byteOrder == ByteOrder.BIG_ENDIAN) {
            return (((byteBuf[0] & 0xff) << 24) | ((byteBuf[1] & 0xff) << 16)
                    | ((byteBuf[2] & 0xff) << 8) | ((byteBuf[3] & 0xff) << 0));
        }else {
            return (((byteBuf[3] & 0xff) << 24) | ((byteBuf[2] & 0xff) << 16)
                    | ((byteBuf[1] & 0xff) << 8) | ((byteBuf[0] & 0xff) << 0));
        }
    }
}
