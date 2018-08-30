package com.example.administrator.androidtest.SerImpl;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Teacher implements SerInterface {
    private String name;
    private int age;
    private List<Student> students = new ArrayList<>();
    private School school;
    private Map<String, String> otherMap = new HashMap<>();
    @Override
    public int size() {
        if(school == null){
            school = new School();
        }
        int size = SerHelper.calSerSize(name) + 4 + SerHelper.calSerSize(students) + school.size() + SerHelper.calSerSize(otherMap);
        return size;
    }

    @Override
    public ByteBuffer serOut() {
        ByteBuffer buf = ByteBuffer.allocate(size());
        SerHelper.serOut(buf, name, String.class);
        buf.putInt(age);
        SerHelper.serOut(buf, students, SerInterface.class);
        SerHelper.serOut(buf, school, SerInterface.class);
        SerHelper.serOut(buf, otherMap, String.class, String.class);
        return null;
    }

    @Override
    public void serIn(ByteBuffer in) {

    }
}
