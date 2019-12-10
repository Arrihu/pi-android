package app.perdana.indonesia.core.utils

import android.content.Context
import android.content.SharedPreferences

object LocalStorage {
    private var sharedPreferences: SharedPreferences? = null

    private fun getSharedPreferences(context: Context): SharedPreferences? {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(
                Constants.SHARED_PREFERENCES_NAME,
                Context.MODE_PRIVATE
            )
        }
        return sharedPreferences
    }

    fun put(context: Context, key: String, value: String) {
        getSharedPreferences(context)?.edit()?.putString(key, value)?.apply()
    }

    fun getString(context: Context, key: String) =
        getSharedPreferences(context)?.getString(key, null)

    fun clear(context: Context){
        getSharedPreferences(context)?.edit()?.clear()?.apply()
    }
}