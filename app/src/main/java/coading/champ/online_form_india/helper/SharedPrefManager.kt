package coading.champ.online_form_india.helper

import coading.champ.online_form_india.modal.UserModal
import android.content.Context


class SharedPrefManager {
    private constructor(context: Context) {
        mCtx = context
    }

    fun userLogin(user: UserModal) {
        val editor = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit()
        editor.putString(KEY_ID, user.id)
        editor.putString(KEY_NAME, user.username)
        editor.putString(KEY_MOBILE, user.mobile)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_PROFILE, user.profile)
        editor.putString(KEY_PASSWORD, user.password)
        editor.putString(KEY_CREATED_AT, user.created_at)
        editor.putString(KEY_STATUS, user.status)
        editor.apply()
    }

    val isLoggedIn: Boolean
        get() = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, 0)
            .getString(KEY_MOBILE, null as String?) != null


    val user: UserModal
        get() {
            val sharedPreferences =
                mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE)
            return UserModal(
                sharedPreferences.getString(KEY_CREATED_AT, null as String?).toString(),
                sharedPreferences.getString(KEY_EMAIL, null as String?).toString(),
                sharedPreferences.getString(KEY_ID, null as String?).toString(),
                sharedPreferences.getString(KEY_PROFILE, null as String?).toString(),
                sharedPreferences.getString(KEY_MOBILE, null as String?).toString(),
                sharedPreferences.getString(KEY_PASSWORD, null as String?).toString(),
                sharedPreferences.getString(KEY_STATUS, null as String?).toString(),
                sharedPreferences.getString(KEY_NAME, null as String?).toString()
            )
        }

    fun logout() {
        val editor = mCtx!!.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE).edit()
        editor.clear()
        editor.apply()
    }

    companion object {
        private const val KEY_EMAIL = "email"
        private const val KEY_ID = "id"
        private const val KEY_PROFILE = "profile"
        private const val KEY_MOBILE = "mobile"
        private const val KEY_NAME = "name"
        private const val KEY_TOKEN = "token"
        private const val KEY_PASSWORD = "password"
        private const val KEY_STATUS = "status"
        private const val SHARED_PREF_NAME = "Form Point"
        private const val KEY_CREATED_AT = "created_at"
        var mCtx: Context? = null
        private var mInstance: SharedPrefManager? = null
        @Synchronized
        fun getInstance(context: Context): SharedPrefManager? {
            var sharedPrefManager: SharedPrefManager?
            synchronized(SharedPrefManager::class.java) {
                if (mInstance == null) {
                    mInstance = SharedPrefManager(context)
                }
                sharedPrefManager = mInstance
            }
            return sharedPrefManager
        }
    }
}
