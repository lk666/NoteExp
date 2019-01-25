package lk.cn.com.lib_annotation

import android.app.Activity
import android.view.View

class InjectViewer {
    companion object {
        @JvmStatic
        fun bind(aty: Activity) {
            val view = aty.window.decorView
            bind(aty, view)
        }

        @JvmStatic
        fun bind(obj: Any, view: View) {
            val cls = obj.javaClass
            val cls_binder = cls.classLoader?.loadClass(cls.name)

            val constructor = cls_binder?.getConstructor(obj.javaClass)
            val binder = constructor?.newInstance(obj)
            val method = cls_binder?.getMethod("bind", view.javaClass)
            method?.invoke(binder, view)
        }
    }
}