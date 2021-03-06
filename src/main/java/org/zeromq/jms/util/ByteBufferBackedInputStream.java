package org.zeromq.jms.util;

/*
 * http://stackoverflow.com/questions/4332264/wrapping-a-bytebuffer-with-an-inputstream
 * 
 * Ripped from the WEB, has not warranty or support.
 */

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Nothing in JDK 1.7, but lots of code on how to wrap an input stream around a byte buffer. I have
 * copied the code into ZMQ just to ensure zero dependencies.
 * 
 * This was lifted from;
 * 
 *     http://stackoverflow.com/questions/4332264/wrapping-a-bytebuffer-with-an-inputstream
 */
public class ByteBufferBackedInputStream extends InputStream {

    ByteBuffer buf;

    /**
     * Construct input stream around a byte buffer/
     * @param buf  the buffer
     */
    public ByteBufferBackedInputStream(ByteBuffer buf) {
        this.buf = buf;
    }

    @Override
    public int read() throws IOException {
        if (!buf.hasRemaining()) {
            return -1;
        }
        return buf.get() & 0xFF;
    }

    @Override
    public int read(byte[] bytes, int off, int len)
            throws IOException {
        if (!buf.hasRemaining()) {
            return -1;
        }

        len = Math.min(len, buf.remaining());
        buf.get(bytes, off, len);
        return len;
    }
}