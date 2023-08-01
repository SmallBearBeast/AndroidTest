package com.example.administrator.androidtest

import android.util.Log
import io.mockk.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MockkTest {
    @Test
    fun testMock_1() {
        // Should make relaxed = true to make sure call those method that does not use every.
        val car = mockk<Car>(relaxed = true)
        every { car.start() } returns "Start_Mock"
        every { car.stop() } answers {
            println("testMock: every { car.stop() }")
            return@answers "Stop_Mock"
        }
        val start = car.start()
        val stop = car.stop()
        println("I am a testMock start = $start, stop = $stop")

        car.reset()
        car.reset()
        verify(exactly = 2) {
            car.reset()
        }
        /*
        ORDERED: not include all calls.
        ALL: include all calls, but not support order.
        SEQUENCE: include all calls, but support order.
         */
        verify(ordering = Ordering.ORDERED) {
            car.start()
            car.stop()
        }
        confirmVerified(car)
        verify(ordering = Ordering.SEQUENCE) {
            car.start()
            car.stop()
            car.reset()
            car.reset()
        }

        val car_1 = mockk<Car>()
        verify { car_1 wasNot Called }
    }

    @Test
    fun testMock_2() {
        val spyCar = spyk(Car("BenZ"))
        val mockCar = mockk<Car>(relaxed = true)
        println("spyCar.start = ${spyCar.start()}, mockCar.start = ${mockCar.start()}")

        every { spyCar.start() } returns "Start_Spy"
        every { mockCar.start() } returns "Start_Mock"
        println("spyCar.start = ${spyCar.start()}, mockCar.start = ${mockCar.start()}")
    }

    @Test
    fun testMock_3() {
        mockkObject(Companion)
        every { add(any(), any()) } returns 10
        every { minus(any(), any()) } returns 10
        println("1 + 2 = ${add(1, 2)}")
        println("1 + 2 = ${minus(1, 2)}")
    }

    @Test
    fun testMock_4_1() {
        // Object use mockkStatic to mock.
        mockkStatic(CarUtil::class)
        every { CarUtil.add(any(), any()) } returns 10
        Log.d(TAG, "1 + 2 = ${CarUtil.add(1, 2)}")
        // Companion object use mockkObject to mock.
        mockkObject(Car)
        every { Car.add(any(), any()) } returns 10
        Log.d(TAG, "1 + 2 = ${Car.add(1, 2)}")
    }

    @Test
    fun testMock_5() {
        val car = mockkClass(Car::class)
        every { car.start() } returns "Start_Mock"
        println("car.start = ${car.start()}")
    }

    @Test
    fun testMock_6() {
        mockkConstructor(Tire::class)
        every { anyConstructed<Tire>().rotate(any()) } returns "Rotate_Mock"
        Log.d(TAG, "car.start = ${Car("Hello").start()}")
    }

    @Test
    fun testMock_6_1() {
        mockkConstructor(Tire_1::class)
        every { anyConstructed<Tire_1>().rotate(any()) } returns "Rotate_Mock"
        println("car.start = ${Car_1("Hello").start()}")
    }

    @Test
    fun testMock_7() {
        val slot = slot<String>()
        mockkConstructor(Car::class)
        every { anyConstructed<Car>().print(capture(slot), any()) } answers {
            println("slot.captured = ${slot.captured}")
        }
        Car("").print("BW", "54088")
        val list = mutableListOf<String>()
        every { anyConstructed<Car>().print(capture(list), any()) } answers {
            println("list = $list")
        }
        Car("").print("Benz", "123123")
        Car("").print("YiQi", "123123")
    }

    @Test
    fun testMock_8() {
        mockk<Car>().apply {
            val objExtension = ObjExtension("Obj")
            every { objExtension.printNameInCar() } answers {
                println("mock ObjExtension.printNameInCar")
            }
            objExtension.printNameInCar()
        }

//        mockkStatic(ObjExtension::printNameInObjExtension)
        mockkStatic("com.example.administrator.androidtest.ObjExtensionKt")
        val objExtension = ObjExtension("Obj")
        every { objExtension.printNameInObjExtension() } answers {
            println("mock ObjExtension.printNameInObjExtension")
        }
        objExtension.printNameInObjExtension()
    }

    @Test
    fun testMock_9() {
        // spy必须是无参构造函数
        val spyCar = spyk<Car>(recordPrivateCalls = true)
        every { spyCar["doPrivate"]() } returns "spy doPrivate"
        println("doPublic = ${spyCar.doPublic()}")
//        seem no work to private member, but has effect to getProperty
        every { spyCar getProperty "privateName" } returns "spy privateName"
        spyCar.printPrivateName()
    }

    @Test
    fun testMock_10() {
        val car = mockk<Car>(relaxed = true)
        every { car.start() } returns "Start_Mock"
        println("I am a testMock start = ${car.start()}")
//        No clear effect
//        unmockkAll()
        clearAllMocks()
        println("I am a testMock start = ${car.start()}")
    }

    companion object {
        private const val TAG = "MockkTest"

        fun add(a: Int, b: Int) = a + b

        fun minus(a: Int, b: Int) = a - b
    }
}