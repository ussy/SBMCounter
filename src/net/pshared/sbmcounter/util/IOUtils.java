package net.pshared.sbmcounter.util;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class IOUtils {

    private static int DEFAULT_BUFFER_SIZE = 8192;

    public static byte[] read(InputStream in) {
        return read(in, DEFAULT_BUFFER_SIZE);
    }

    public static byte[] read(InputStream in, int bufferSize) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(bufferSize);
        try {
            byte[] buffer = new byte[bufferSize];
            int length = -1;
            while ((length = in.read(buffer)) != -1) {
                out.write(buffer, 0, length);
            }

            return out.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            close(out);
        }
    }

    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                // ignore
            }
        }
    }
}
