package com.cn.lk.androidexp.annotation

/**
 * 注解保留的生命周期
 * --SOURCE 注解仅存在于源码中，在class字节码文件中不包含
 * --BINARY 默认的保留策略，注解会在class字节码文件中存在，但运行时无法获得
 * --RUNTIME 注解会在class字节码文件中存在，在运行时可以通过反射获取到，默认行为
 */
@Retention(AnnotationRetention.RUNTIME)
/**
 * 与java的基本一样，拓展了范围
 * @see AnnotationTarget
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.FIELD, AnnotationTarget.FUNCTION, AnnotationTarget.EXPRESSION, AnnotationTarget.PROPERTY_GETTER)

// 可重复使用统一标签，比java简单，但是只能在AnnotationRetention.SOURCE时使用
//@Repeatable

/**
 * 表示注解是公共API的一部分。生成的API文档是否显示类或方法包含的注解，普通类不行
 * 参数类型
 *      KClass
 *      Java的基本类型对应的Kotlin类型
 *      字符串
 *      枚举
 *      其他注解
 *      上面类型的数组
 */
@MustBeDocumented
annotation class MyKotlinAnnotation(val name: String)
/**
 *  如果希望注解使用类作为类型参数的话:
 *      annotation class MyClassAnnotation(val clazz : KClass<out Any>)
 *  注意必须out Any,否则泛型参数无法协变导致只能使用Any类作为参数
 *
 *  -------------------------------------------------------------------
 *  如果要使用泛型类作为注解参数，则需要通过星形投影
 *  annotation class ListAnnotation(val clazz : KClass<out List<*>>)
 *
 *  把List作为注解参数
 *  @ListAnnotation(List::class)
 *  fun sum()
 */