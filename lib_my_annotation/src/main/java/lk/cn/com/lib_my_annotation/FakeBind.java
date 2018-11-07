package lk.cn.com.lib_my_annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS) // 编译后的字节码中不存在
public @interface FakeBind {
    int value() default 0;
}
