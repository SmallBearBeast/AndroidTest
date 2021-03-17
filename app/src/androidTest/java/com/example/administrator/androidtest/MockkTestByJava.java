package com.example.administrator.androidtest;

import org.junit.Test;

import io.mockk.MockKKt;
import io.mockk.MockKMatcherScope;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;

public class MockkTestByJava {
    @Test
    public void test() {
        MockKKt.mockkStatic(JvmClassMappingKt.getKotlinClass(Car.class));
        MockKKt.every(new Function1<MockKMatcherScope, Object>() {
            @Override
            public Object invoke(MockKMatcherScope mockKMatcherScope) {
                return Car.add(1, 2);
            }
        }).returns(10);
        System.out.println(Car.add(1, 2));
    }
}
