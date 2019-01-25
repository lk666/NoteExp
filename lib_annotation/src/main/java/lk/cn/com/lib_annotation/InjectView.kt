package lk.cn.com.lib_annotation

import android.support.annotation.IdRes

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD)
annotation class InjectView(@IdRes val value: Int = 0)