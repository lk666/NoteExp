package com.cn.lk.androidexp.annotation;


import android.os.Build;
import android.support.annotation.RequiresApi;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 注解就是标签
 */

/**
 * @see ElementType
 * 标签的作用范围，可多个
 */
//@Target(ElementType.FIELD)

/**
 * 标签的有效期：
 * SOURCE-源码有效
 * CLASS-编译时有效，不在jvm中可见，默认行为（butterknife的实现）
 * RUNTIME-JVM中可使用
 */
@Retention(RetentionPolicy.RUNTIME)

////////////////////////////////////////以下不常用
/**
 * 将注解中的元素包含到 Javadoc 中去
 */
//@Documented

/**
 * 以下代码中，B继承了A的Test注解，也即是指示标签是否可继承
 * @Inherited
 * @Retention(RetentionPolicy.RUNTIME)
 * @interface Test {}
 *
 * @Test
 * public class A {}
 *
 * public class B extends A {}
 */
//@Inherited

/**
 * 需要最低支持版本api24以上，可以实现打上多个同种的标签：
 */
@RequiresApi(api = Build.VERSION_CODES.N)
@Repeatable(MyJavaAnnotations.class)
@interface MyJavaAnnotation {
    String role() default "";
}

// 容器注解，必须与MyJavaAnnotation具有相同的Target、Retention、Documented等注解
@Retention(RetentionPolicy.RUNTIME)
@interface MyJavaAnnotations {
    MyJavaAnnotation[] value();
}

// 打上多个同种的标签
//@MyJavaAnnotation(role = "asdsad")
//@MyJavaAnnotation(role = "gfg")
//class A