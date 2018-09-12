package com.cn.lk.androidexp.thread

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Message
import android.support.v4.app.FragmentActivity
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.config.Configuration
import com.birbit.android.jobqueue.log.CustomLogger
import com.cn.lk.androidexp.AppContext
import com.cn.lk.androidexp.R
import kotlinx.android.synthetic.main.activity_thread.*
import java.util.concurrent.*


/**
 * 1. Handler+Thread
 * 2. AsyncTask
 * 3. ThreadPoolExecutor
 * 4. IntentService
 */
class ThreadActivity : FragmentActivity() {

    companion object {
        var TAG = "ThreadActivity"
    }

    private lateinit var handler: MyHandler

    private lateinit var ht: HandlerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_thread)

        tv.text = "${tv.text}\nMain--${Thread.currentThread().id}\n"
        // Thread
        MyThread().start()

        // runable
        Thread({
            Thread.sleep(1000)
            val id = Thread.currentThread().id
            tv.post {
                tv.text = "${tv.text}\nRunable--${id}\n"
            }
        }).start()

        // FutureTask
        val f = FutureTask<Int>(Callable {
            Thread.sleep(1000)
            val id = Thread.currentThread().id
            tv.post {
                tv.text = "${tv.text}\nFutureTask--${id}\n"
            }
            return@Callable 666
        })
        Executors.newCachedThreadPool().submit(f)
        Thread({
            Thread.sleep(1500)
            tv.post {
                tv.text = "${tv.text}\nFutureTask--result->${f.get()}\n"
            }
        }).start()

        ///////////////// 主要手段 //////////////////////////
        // handler + thread，多个异步任务的更新UI，handler.post也是用sendMessage，msg.callback=runable
        handler = MyHandler(this)
        Thread({
            Thread.sleep(2000)

            // 使用已用过的消息队列的msg
            val msg = handler.obtainMessage(0)
            msg.data = Bundle().apply {
                putString("str", "dataStr")
            }
            handler.sendMessage(msg)
        }).start()

        // asyncTask，单个异步任务的处理，生命周期太过于耦合
        val task = MyAsyncTask(tv).execute("start")
        Thread({
            Thread.sleep(4500)
            // 不直接中断，可以执行完并调用cancel
            task.cancel(false)
        }).start()

        // ThreadPoolExecutor(&ExecutorService), 批处理任务,submit会返回future对象
        Executors.newCachedThreadPool().submit {
            Thread.sleep(5000)
            val id = Thread.currentThread().id
            tv.post {
                tv.text = "${tv.text}\nExecutors--${id}\n"
            }
        }
        // 使用PriorityBlockingQueue的ThreadPoolExecutor，以及实现Comparable的Runnable实现优先队列
        val tpe = ThreadPoolExecutor(3, 5, 1, TimeUnit.MINUTES, PriorityBlockingQueue<Runnable>())

        // IntentService  一个可以处理异步任务的简单Service，如下载等
        val intent = Intent(this, MyIntentService::class.java)
        startService(intent)

        // HandlerThread
        ht = HandlerThread("ttt")
        ht.start()
        val t1 = object : Handler(ht.looper) {
            override fun handleMessage(msg: Message?) {
                Thread.sleep(4500)
                val id = Thread.currentThread().id
                tv.post {
                    tv.text = "${tv.text}\nHandlerThread--${id}\n"
                }
            }
        }
        t1.sendMessage(t1.obtainMessage())

        // https://github.com/yigit/android-priority-jobqueue
        val configuration = Configuration.Builder(AppContext.instance())
                .customLogger(object : CustomLogger {
                    override fun v(text: String?, vararg args: Any?) {
                    }

                    override fun e(t: Throwable?, text: String?, vararg args: Any?) {
                    }

                    override fun e(text: String?, vararg args: Any?) {
                    }

                    override fun d(text: String?, vararg args: Any?) {
                    }

                    override fun isDebugEnabled() = true
                })
                .minConsumerCount(1)//always keep at least one consumer alive
                .maxConsumerCount(3)//up to 3 consumers at a time
                .loadFactor(3)//3 jobs per consumer
                .consumerKeepAlive(120)//wait 2 minute
                .build()
        val jobManager = JobManager(configuration)
        jobManager.addJobInBackground(MyJob(),
                // The callback to be invoked once Job is saved in the JobManager's queues
                {
                    tv.text = "${tv.text}\nMyJob--callBack\n"
                })
    }

    override fun onDestroy() {
        // 手动清空message队列
        handler?.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    inner class MyThread : Thread() {
        override fun run() {
            super.run()
            Thread.sleep(3000)
            val id = Thread.currentThread().id
            tv.post {
                tv.text = "${tv.text}\nThread--${id}\n"
            }
        }
    }

}