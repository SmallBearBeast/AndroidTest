package com.example.administrator.androidtest.Common.Util.SerImpl;


import com.example.administrator.androidtest.Common.Util.Mmf.WrapUtil;

import java.nio.ByteBuffer;

public class Student implements SerInterface {
    private String name;
    private int age;
    private int[][] money = new int[][]{
            {1, 2},{3, 4}
    };


    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int size() {
        Integer[][] moneyObj = (Integer[][]) WrapUtil.priToObjArray(2, Integer.class, money);
        return 4 + SerHelper.calSerSize(name, String.class) + SerHelper.calSerSize(moneyObj, Integer.class);
    }

    @Override
    public ByteBuffer outBuffer() {
        ByteBuffer buf = ByteBuffer.allocate(size());
        SerHelper.serOut(buf, name, String.class);
        buf.putInt(age);
        Integer[][] moneyObj = (Integer[][]) WrapUtil.priToObjArray(2, Integer.class, money);
        SerHelper.serOut(buf, moneyObj, Integer.class);
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
        age = (int) SerHelper.serIn(in, Integer.valueOf(0), Integer.class);
        Integer[][] moneyUnit = new Integer[][]{{0}};
//        Integer[][] moneyUnit = (Integer[][]) WrapUtil.arrayUnit(Integer.class, 2);
        Integer[][] moneyObj = (Integer[][]) SerHelper.serIn(in, moneyUnit, Integer.class);
        money = WrapUtil.objToPriArray(2, int.class, moneyObj);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
