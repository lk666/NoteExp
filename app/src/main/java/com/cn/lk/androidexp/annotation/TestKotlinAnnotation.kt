package com.cn.lk.androidexp.annotation


/**
 * kotlin注解
 */
@MyKotlinAnnotation("tag0")
@MyJavaAnnotation(role = "role0")
class TestKotlinAnnotation {
    /** 声明prot时对应了setter、getter，使用注解目标可以更精确的定位
     *  Kotlin中支持以下点目标:
     *      property:代表kotlin中的属性，不能被Java的注解所应用
     *      field:为属性生成的字段（包括后备字段）
     *      get:属性的getter
     *      set:属性的setter
     *      receiver:扩展函数/属性的接收者
     *      param:构造函数的参数
     *      setparam:属性setter的参数
     *      delegate:委托属性存储委托实例的字段
     *      file:在文件中声明的顶层函数与类
     */
    @get:MyKotlinAnnotation("getter")
    var prot = 1

    companion object {
        // lamdpa函数，fun0(3)，注解可用于表达式
        val fun0 = @MyKotlinAnnotation("fun0") { a: Int -> a + 1 }

        fun testRepeatable(): String {
            return TestKotlinAnnotation::class.annotations
                    .filter {
                        it is MyKotlinAnnotation
                    }
                    .map {
                        (it as MyKotlinAnnotation).name
                    }.joinToString {
                        it
                    }
        }
    }

}