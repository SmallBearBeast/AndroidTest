package com.example.administrator.androidtest.Common.Util.SerImpl;


import java.nio.ByteBuffer;

public class Teacher implements SerInterface {
    private String name;
    private School school;
    @Override
    public int size() {
        int size = SerHelper.calSerSize(name, String.class) + SerHelper.calSerSize(school, SerInterface.class);
        return size;
    }

    @Override
    public ByteBuffer outBuffer() {
        ByteBuffer buf = ByteBuffer.allocate(size());
        SerHelper.serOut(buf, name, String.class);
        SerHelper.serOut(buf, school, SerInterface.class);
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
        name = (String) SerHelper.serIn(in, "", String.class);
        school = (School) SerHelper.serIn(in, School.getInstance(), SerInterface.class);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public static Teacher getInstance(){
        Teacher t = new Teacher();
        t.name = "";
        t.school = School.getInstance();
        return t;
    }
}
