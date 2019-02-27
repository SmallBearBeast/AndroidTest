package com.example.administrator.androidtest.Net.Okhttp;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import okhttp3.Dns;

public class HttpDns implements Dns {
    @Override
    public List<InetAddress> lookup(String hostname) throws UnknownHostException {
        return SYSTEM.lookup(hostname);
    }
}
