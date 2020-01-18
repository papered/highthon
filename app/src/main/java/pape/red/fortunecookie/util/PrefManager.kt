package pape.red.fortunecookie.util

import android.content.Context
import android.content.SharedPreferences


private fun getPref(context: Context): SharedPreferences {
    return context.getSharedPreferences("pref", Context.MODE_PRIVATE)
}

fun saveToken(context: Context, token: String, isAccess: Boolean = true) {
    getPref(context).edit().let {
        it.putString(getKey(isAccess), token)
        it.apply()
    }
}

fun saveName(context: Context, name: String) {
    getPref(context).edit().let {
        it.putString("name", name)
        it.apply()
    }
}

fun saveId(context: Context, id: String) {
    getPref(context).edit().let {
        it.putString("id", id)
        it.apply()
    }
}

fun saveImage(context: Context, image: String) {
    getPref(context).edit().let {
        it.putString("image", image)
        it.apply()
    }
}

fun removeToken(context: Context, isAccess: Boolean = true) {
    getPref(context).edit().let {
        it.remove(getKey(isAccess))
        it.apply()
    }
}

fun getToken(context: Context, isAccess: Boolean = true): String {
    return "" + getPref(context).getString(
        getKey(
            isAccess
        ), ""
    )
}

fun getName(context: Context): String {
    return "" + getPref(context).getString("name", "")
}

fun getId(context: Context): String {
    return "" + getPref(context).getString("id", "")
}

fun getImage(context: Context): String {
    return "" + getPref(context).getString("image", "")
}

private fun getKey(isAccess: Boolean): String = if (isAccess) "Access" else "Refresh"