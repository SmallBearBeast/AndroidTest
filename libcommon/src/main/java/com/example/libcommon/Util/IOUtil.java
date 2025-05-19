package com.example.libcommon.Util;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

/**
 * IO工具类
 */
public class IOUtil {
    /**
     * 安全关闭流，内部捕获异常
     */
    private static void close(Closeable closeable) {
        try {
            if (closeable != null) {
                closeable.close();
            }
        } catch (IOException ioe) {
            // ignore
        }
    }

    public static void close(Closeable... closeables) {
        if (closeables == null) {
            return;
        }
        for (Closeable closeable : closeables) {
            close(closeable);
        }
    }
    /**安全关闭流，内部捕获异常**/

    /**
     * 安全socket，内部捕获异常
     */
    public static void close(Socket sock) {
        if (sock != null) {
            try {
                sock.close();
            } catch (IOException ioe) {
                // ignored
            }
        }
    }
    /**安全socket，内部捕获异常**/
}
