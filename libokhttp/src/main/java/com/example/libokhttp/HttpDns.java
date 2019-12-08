package com.example.libokhttp;

import okhttp3.Dns;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class HttpDns implements Dns {
    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        return SYSTEM.lookup(hostname);
    }
}
