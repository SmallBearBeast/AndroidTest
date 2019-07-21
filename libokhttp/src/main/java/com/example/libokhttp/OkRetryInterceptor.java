package com.example.libokhttp;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import javax.net.ssl.SSLException;
import java.io.IOException;
import java.net.*;

public class OkRetryInterceptor implements Interceptor {
    private static final int HTTP_REQ_CANCELED = 1;
    private static final int HTTP_UNKNOWN_HOST = HTTP_REQ_CANCELED + 1;
    private static final int HTTP_CONNECT_TIMEOUT = HTTP_UNKNOWN_HOST + 1;
    private static final int HTTP_READ_TIMEOUT = HTTP_CONNECT_TIMEOUT + 1;
    private static final int HTTP_ERR_OTHERS = HTTP_READ_TIMEOUT + 1;
    private static final int HTTP_CONNECT_EXCEPTION = HTTP_ERR_OTHERS + 1;
    private static final int HTTP_NO_ROUTE_TO_HOST = HTTP_CONNECT_EXCEPTION + 1;
    private static final int HTTP_PORT_UNREACHABLE = HTTP_NO_ROUTE_TO_HOST + 1;
    private static final int HTTP_SOCKET_EXCEPTION = HTTP_PORT_UNREACHABLE + 1;
    private static final int HTTP_RETRY_EXCEPTION = HTTP_SOCKET_EXCEPTION + 1;
    private static final int HTTP_MALFORMED_URL = HTTP_RETRY_EXCEPTION + 1;
    private static final int HTTP_SSL_EXCEPTION = HTTP_MALFORMED_URL + 1;
    private static final int HTTP_NO_ERROR  =HTTP_SSL_EXCEPTION + 1;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        try{
            response = chain.proceed(request);
        }catch (IOException e){
            tryAgain(chain, request, e);
        }
        return response;
    }

    private Response tryAgain(Chain chain, Request request, IOException e) throws IOException{
        if(parseHttpError(e) == HTTP_REQ_CANCELED){
            throw e;
        }
        Request newRequest = request.newBuilder().build();
        Response response;
        try {
            response = chain.proceed(newRequest);
        }catch (Exception ee){
            throw ee;
        }
        return response;
    }

    private int parseHttpError(Exception e) {
        int err = HTTP_ERR_OTHERS;
        String exceptionMsg = (e == null || e.getMessage() == null) ? "" : e.getMessage();
        if (exceptionMsg.contains("Canceled")) {
            err = HTTP_REQ_CANCELED;
        } else if (e instanceof UnknownHostException) {
            err = HTTP_UNKNOWN_HOST;
        } else if (e instanceof SocketTimeoutException && exceptionMsg.contains("failed to connect")) {
            err = HTTP_CONNECT_TIMEOUT;
        } else if (e instanceof SocketTimeoutException || exceptionMsg.contains("ETIMEDOUT")) {
            err = HTTP_READ_TIMEOUT;
        } else if (e instanceof ConnectException) {
            err = HTTP_CONNECT_EXCEPTION;
        } else if (e instanceof NoRouteToHostException) {
            err = HTTP_NO_ROUTE_TO_HOST;
        } else if (e instanceof PortUnreachableException) {
            err = HTTP_PORT_UNREACHABLE;
        } else if (e instanceof SocketException) {
            err = HTTP_SOCKET_EXCEPTION;
        } else if (e instanceof HttpRetryException) {
            err = HTTP_RETRY_EXCEPTION;
        } else if (e instanceof MalformedURLException) {
            err = HTTP_MALFORMED_URL;
        } else if (e instanceof SSLException) {
            err = HTTP_SSL_EXCEPTION;
        }
        return err;
    }
}
