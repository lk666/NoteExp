package com.cn.lk.androidexp.annotation

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.annotation.*
import android.support.annotation.IntRange
import android.support.v4.app.FragmentActivity
import android.view.View
import com.cn.lk.androidexp.R
import com.cn.lk.androidexp.util.FileUtil
import com.cn.lk.androidexp.util.ViewUtil
import kotlinx.android.synthetic.main.activity_annotation.*
import org.jetbrains.annotations.NotNull
import java.io.File


class SupportAnnotationActivity : FragmentActivity(), View.OnClickListener {

    // Declare the constants
    companion object {
        const val A = "a"
        const val B = "b"
        const val C = "c"

        var TAG = "SupportAnnotationActivity-Client"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn1 -> takePhoto()
        }
    }

    // todo 此注解@Retention(CLASS)，只是编译时提示调用是否已获取权限
    @RequiresPermission(Manifest.permission.CAMERA)
    private val TAKEPHOTE = MediaStore.ACTION_IMAGE_CAPTURE

    private fun takePhoto() {
        val intent = Intent(TAKEPHOTE)
        val out = File(FileUtil.PATH_CAMERA)
        if (!out.exists()) {
            out.mkdirs()
        }
        val uri = Uri.fromFile(File("${FileUtil.PATH_CAMERA}/tmp123456.jpg"))
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            1 -> ViewUtil.toast("拍照结果${data?.toString()}")
        }
    }

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annotation)

        resFun(R.string.abc_capital_off, R.mipmap.ic_launcher, R.dimen
                .abc_action_bar_content_inset_material, R.color.cardview_shadow_end_color, R
                .id.btn_reset)

        setData(100, 0.8f, "12345678")

        typeFun()
    }

    /**
     *  使用 @IntDef 和 @StringDef 注解，以便能够创建整型和字符串集的枚举注解来验证其他类型的代码引用
     *
     *  实测都不会警告
     */
    private fun typeFun() {
        setNavigationMode("")
        k = getNavigationMode()
    }

    // Define the list of accepted constants and declare the NavigationMode annotation
    @Retention(AnnotationRetention.SOURCE)
    @StringDef(value = [A, B, C])
    annotation class NavigationMode

    @NavigationMode var k: String? = null

    // Decorate the target methods with the annotation
    @NavigationMode
    fun getNavigationMode() = k

    // Attach the annotation
    fun setNavigationMode(@NavigationMode mode: String) {
        k = mode
    }


    /**
     * @Size 注解可以检查集合或数组的大小，以及字符串的长度。
     * value 确切大小
     * multiple 大小必须为此倍数的数字
     */
    private fun setData(@IntRange(from = 0, to = 500) a: Int, @FloatRange(from = 0.0, to = 1.0)
    b: Float, @Size(min = 8, max = 20) size: String) {

    }


    /**
     * 线程注解，声明方法运行在什么线程
     *
     * @MainThread  标注与应用生命周期关联的方法
     * @UiThread 标注与应用的视图层次结构关联的方法
     * @WorkerThread
     * @BinderThread
     * @AnyThread
     *
     */
    @UiThread
    fun mainWrok() {
        btn1.setText("asdsad")
    }

    /**
     * 资源注解
     */
    private fun resFun(@StringRes str: Int, @DrawableRes draw: Int, @DimenRes dim: Int,
                       @ColorRes color: Int, @IdRes id: Int) {
    }

    /**
     * Nullness 注解 kotlin中 @Nullable @NotNull无多大意义
     */
    @Nullable
    fun nullFun(@NotNull param: Int) {
    }

//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
//    private fun picFix() {
//        // android 7.0系统解决拍照的问题  android.os.FileUriExposedException
//        // 这是安卓7.0以上版本，做了一些系统权限更改，为了提高私有文件的安全性。禁止在您的应用外部公开 file:// URI，也就是说不能直接在应用间进行文件共享，需要URI 临时访问权限。
//        // 有权限的话不会出现
//        val builder = StrictMode.VmPolicy.Builder()
//        StrictMode.setVmPolicy(builder.build())
//        builder.detectFileUriExposure()
//    }
}