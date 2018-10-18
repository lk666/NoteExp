package com.cn.lk.androidexp

import com.cn.lk.androidexp.annotation.TestKotlinAnnotation
import org.junit.Assert
import org.junit.Test

/**
 * Created by lk on 2018/10/9.
 */
class TestKTest {
    @Test
    fun testTwoSum() {
//        Assert.assertEquals(listOf(listOf(-1,  0, 0, 1),listOf(-2, -1, 1, 2),listOf(-2,  0, 0, 2)),
//                TestK.fourSum(intArrayOf(1, 0, -1, 0, -2, 2), 0))
        Assert.assertEquals("asdsad", TestKotlinAnnotation.testRepeatable())
    }

}