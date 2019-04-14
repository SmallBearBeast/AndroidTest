package com.example.administrator.androidtest.SerImpl;


import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class School implements SerInterface {
    private String name;
    private List<List<Integer>> list = new ArrayList<>();

    @Override
    public int size() {
        return SerHelper.calSerSize(name, String.class) + SerHelper.calSerSize(list, Integer.class);
    }

    @Override
    public ByteBuffer outBuffer() {
        ByteBuffer buf = ByteBuffer.allocate(size());
        SerHelper.serOut(buf, name, String.class);
        SerHelper.serOut(buf, list, Integer.class);
        buf.flip();
        return buf;
    }

    @Override
    public byte[] outBytes() {
        ByteBuffer buf = outBuffer();
        byte[] bytes = new byte[size()];
        buf.get(bytes);
        return bytes;
    }

    @Override
    public void in(ByteBuffer in) {
        String nameUnit = "";
        name = (String) SerHelper.serIn(in, nameUnit, String.class);
        List<List<Integer>> listUnit = Arrays.asList(Arrays.asList(0));
        list = (List<List<Integer>>) SerHelper.serIn(in, listUnit, List.class);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setList(List<List<Integer>> list) {
        this.list = list;
    }

    public static School getInstance(){
        School s = new School();
        s.name = "";
        s.list = Arrays.asList(Arrays.asList(0));
        return s;
    }
}
