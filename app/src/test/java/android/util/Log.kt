package android.util

object Log {
    @JvmStatic
    fun e(tag: String?, msg: String?, tr: Throwable? = null): Int {
        // no-op for unit tests
        return 0
    }

    @JvmStatic
    fun d(tag: String?, msg: String?): Int {
        // no-op for unit tests
        return 0
    }

    @JvmStatic
    fun i(tag: String?, msg: String?): Int {
        // no-op
        return 0
    }

    @JvmStatic
    fun w(tag: String?, msg: String?): Int {
        // no-op
        return 0
    }
}
