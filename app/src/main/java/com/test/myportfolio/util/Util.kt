package com.test.myportfolio.util

import android.content.Context
import android.content.SharedPreferences
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL

object PreferenceManager {
    const val PREFERENCES_NAME = "portpolio"
    const val DEFAULT_VALUE_STRING = ""
    private const val DEFAULT_VALUE_BOOLEAN = false
    private const val DEFAULT_VALUE_INT = -1
    private const val DEFAULT_VALUE_LONG = -1L
    private const val DEFAULT_VALUE_FLOAT = -1f
    open fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }
    fun setString(context: Context, key: String?, value: String?) {
        val prefs = getPreferences(context)
        val editor = prefs.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun getString(context: Context, key: String?): String? {
        val prefs = getPreferences(context)
        return prefs.getString(key, DEFAULT_VALUE_STRING)
    }

    fun getAll(context: Context): MutableCollection<out Any?> {
        val prefs = getPreferences(context)
        return prefs.all.keys
    }
}



object Util {
    @BindingAdapter("setImages")
    @JvmStatic
    fun ImageView.setImages(path: String?) {
        this.run {
            Glide.with(context).load(path).override(SIZE_ORIGINAL).into(this)

        }
    }
}
fun titleConvert(title: String, type: String): String {
    if(title.isEmpty()) return ""
    return when (type) {
        "네이버" -> title.replace("<b>", "").replace("</b>", "")
        else -> title
    }
}

fun contentConvert(content: String, type: String): String {
    if(content.isEmpty()) return ""
    return when (type) {
        "네이버" -> content.substring(0,content.length - 1).replace("|", ", ").replace("<b>", "").replace("</b>", "")
        else -> content
    }
}