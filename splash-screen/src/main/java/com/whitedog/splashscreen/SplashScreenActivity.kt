package com.whitedog.splashscreen

import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Semaphore

abstract class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private var active: Boolean = false

        const val KEY_START_TASK: String = "startTask"
        const val KEY_START_TIMER: String = "startTimer"
    }

    private val semaphore: Semaphore = Semaphore(0)
    private val semaphoreExtras: Semaphore = Semaphore(1)

    //===============================================================

    override fun onStart() {
        super.onStart()

        active = true
    }

    override fun onStop() {
        active = false

        super.onStop()
    }

    //===============================================================

    /**
     * Starts the splash screen with a minimum duration.
     *
     * Invokes the [doInBackgroundSplashTask] function in one thread while waiting for the specified time to elapse in another thread.
     *
     * Once both threads have finished, the [onSplashTaskFinished] function will be called.
     *
     * @param minDuration Minimum duration in milliseconds that the screen will be displayed.
     */
    @Synchronized
    fun startSplashTask(minDuration: Long) {
        if(readBooleanExtra(KEY_START_TASK, true)) {
            startTaskThread()
        }

        if(readBooleanExtra(KEY_START_TIMER, true)) {
            startTimerThread(minDuration)
        }
    }

    private fun startTaskThread() {
        Thread {
            putBooleanExtra(KEY_START_TASK, false)

            doInBackgroundSplashTask()

            semaphore.release()
        }.start()
    }

    private fun startTimerThread(minDuration: Long) {
        Thread {
            putBooleanExtra(KEY_START_TIMER, false)

            val duration: Long = if(minDuration < 0) {
                0
            }
            else {
                minDuration
            }

            Thread.sleep(duration)

            semaphore.acquire()

            if(active) {
                runOnUiThread {
                    onSplashTaskFinished()
                }
            }

            finish()
        }.start()
    }

    private fun putBooleanExtra(key: String, value: Boolean) {
        semaphoreExtras.acquire()
        intent.putExtra(key, value)
        semaphoreExtras.release()
    }

    private fun readBooleanExtra(key: String, defaultValue: Boolean): Boolean {
        semaphoreExtras.acquire()
        val value: Boolean = intent.getBooleanExtra(key, defaultValue)
        semaphoreExtras.release()

        return value
    }

    //===============================================================

    /**
     * Override this method to perform a computation on a background thread.
     *
     * The function is invoked by [startSplashTask].
     */
    abstract fun doInBackgroundSplashTask()


    /**
     * Runs on UI thread after [doInBackgroundSplashTask] ensuring that the milliseconds indicated in [startSplashTask] have elapsed.
     *
     * Use it to start the next activity.
     */
    abstract fun onSplashTaskFinished()

}