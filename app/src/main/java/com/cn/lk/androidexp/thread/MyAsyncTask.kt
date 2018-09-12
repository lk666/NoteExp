package com.cn.lk.androidexp.thread

import android.os.AsyncTask
import android.widget.TextView
import com.orhanobut.logger.Logger
import java.lang.ref.WeakReference

/**
 * 异步任务
 * Created by lk on 2018/7/19.
 */
class MyAsyncTask(tv: TextView) :AsyncTask<String,Int, String>(){
    private val mTv = WeakReference(tv)

    companion object{
        val TAG = "MyAsyncTask"
    }

    override fun onPreExecute() {
        // 这个方法会在后台任务开始执行之前调用，用于进行一些界面上的初始化操作，比如显示一个进度条对话框等。
        mTv.get()?.text = "onPreExecute--${Thread.currentThread().id}"
    }

    override fun doInBackground(vararg params: String?): String {
        Logger.t(TAG).d("doInBackground${params}--${Thread.currentThread().id}")
        Thread.sleep(1000)
        publishProgress(50)
        Thread.sleep(3000)
        publishProgress(100)
        Thread.sleep(3000)
        return "finish"
    }

    override fun onProgressUpdate(vararg values: Int?) {
        mTv.get()?.text = "${mTv.get()?.text}\n" +
                "MyAsyncTask--onProgressUpdate${values}--${Thread.currentThread().id}"
    }

    override fun onPostExecute(result: String?) {
        mTv.get()?.text = "${mTv.get()?.text}\n" +
                "MyAsyncTask--onPostExecute${result}--${Thread.currentThread().id}"
    }

    override fun onCancelled() {
        mTv.get()?.text = "${mTv.get()?.text}\n" +
                "MyAsyncTask--onCancelled--${Thread.currentThread().id}"
    }

    override fun onCancelled(result: String?) {
        mTv.get()?.text = "${mTv.get()?.text}\nMyAsyncTask--onCancelled(${result})--${Thread
                .currentThread().id}"
    }
}