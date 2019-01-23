package com.example.administrator.androidtest.Common.Util;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

/**
 * IO工具类
 */
public class IOUtil {
    /**
     * 安全关闭流，内部捕获异常
     */
    public static void close(final Reader input) {
        close((Closeable) input);
    }

    public static void close(final Writer output) {
        close((Closeable) output);
    }

    public static void close(final InputStream input) {
        close((Closeable) input);
    }

    public static void close(final OutputStream output) {
        close((Closeable) output);
    }

    public static void close(final Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (final IOException ioe) {
            // ignore
        }
    }

    public static void close(final Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (final Closeable closeable : closeables) {
            close(closeable);
        }
    }
    /**安全关闭流，内部捕获异常**/

    /**
     * 安全socket，内部捕获异常
     */
    public static void close(final Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (final IOException ioe) {
                // ignored
            }
        }
    }
    /**安全socket，内部捕获异常**/
}
