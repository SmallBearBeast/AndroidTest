package com.example.administrator.androidtest.Common.Util.SerImpl;

import com.example.administrator.androidtest.Common.Util.WrapUtil;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SerImplTest {
    private static final int[] Data_1 = new int[]{
            1, 2, 3, 4
    };

    private static final Integer[][] Data_2 = new Integer[][]{
            {1, 2}, {3, 4}
    };

    private static final Object[][] Data_3 = new Object[][]{
            {1, 2}, {3, 4}
    };

    private static final int Ser_Student_Test = 1;
    private static final int Ser_School_Test = 2;
    private static final int Ser_Teacher_Test = 3;
    private static final int Ser_Map_Test = 4;
    private static int flag = Ser_Map_Test;

    public static void main(String[] args) {
//        List<List<Integer>> list = new ArrayList<>();
//        List<Integer> list_1 = new ArrayList<>();
//        list_1.add(123);
//        List<Integer> list_2 = new ArrayList<>();
//        list_2.add(456);
//        list_2.add(789);
//        list.add(list_1);
//        list.add(list_2);
//
//        int size = SerHelper.calSerSize(list, Integer.class);
//        ByteBuffer buf = ByteBuffer.allocate(size);
//        SerHelper.serOut(buf, list, Integer.class);
//        buf.flip();
//        List<List<Integer>> listUnit = new ArrayList<>();
//        List<Integer> listUnit_1 = new ArrayList<>();
//        listUnit_1.add(0);
//        listUnit.add(listUnit_1);
//
//        List<Integer> otherList = (List<Integer>) SerHelper.serIn(buf, listUnit, Integer.class);
        switch (flag) {
            case Ser_Student_Test:
                serStudentTest();
                break;

            case Ser_School_Test:
                serSchoolTest();
                break;

            case Ser_Teacher_Test:
                serTeacherTest();
                break;

            case Ser_Map_Test:
                serMapTest();
                break;
        }
    }

    private static void serMapTest() {
        Map<Integer, String>[] maps = new Map[]{
                WrapUtil.asMap(new Integer[]{1, 2}, new String[]{"111", "222"}),
                WrapUtil.asMap(new Integer[]{1, 2}, new String[]{"111", "222"})
        };
        Map<Integer, Map<Integer, String>> map = WrapUtil.asMap(new Integer[]{1, 2}, maps);
        int size = SerHelper.calSerSize(map, Map.class);
        ByteBuffer buf = ByteBuffer.allocate(size);
        SerHelper.serOut(buf, map, Map.class);
        buf.flip();
        Map mapUnit = WrapUtil.asMap(new Integer[]{0}, new Map[]{WrapUtil.asMap(new Integer[]{0}, new String[]{""})});
        Map<Integer, Map<Integer, String>> result = (Map<Integer, Map<Integer, String>>) SerHelper.serIn(buf, mapUnit, Map.class);
        String s = null;
    }

    private static void serTeacherTest() {
        Teacher teacher = new Teacher();
        teacher.setName("wutianyun");

        School school = new School();
        school.setName("dongshanyizhong");
        List<Integer> list_1 = new ArrayList<>();
        list_1.add(1);
        list_1.add(1);
        List<Integer> list_2 = new ArrayList<>();
        list_2.add(2);
        list_2.add(2);
        List<List<Integer>> list = new ArrayList<>();
        list.add(list_1);
        list.add(list_2);
        school.setList(list);

        teacher.setSchool(school);

        ByteBuffer buf = teacher.outBuffer();
        Teacher otherTeacher = new Teacher();
        otherTeacher.in(buf);
        String s = null;
    }

    private static void serSchoolTest() {
        School school = new School();
        school.setName("dongshanyizhong");
        List<Integer> list_1 = new ArrayList<>();
        list_1.add(1);
        list_1.add(1);
        List<Integer> list_2 = new ArrayList<>();
        list_2.add(2);
        list_2.add(2);
        List<List<Integer>> list = new ArrayList<>();
        list.add(list_1);
        list.add(list_2);
        school.setList(list);

        ByteBuffer buf = school.outBuffer();
        School otherSchool = new School();
        otherSchool.in(buf);
        String s = null;
    }

    private static void serStudentTest() {
        Student student = new Student();
        student.setAge(10);
        student.setName("XiaoMing");
        ByteBuffer buf = student.outBuffer();
        Student otherStudent = new Student();
        otherStudent.in(buf);
    }


}
