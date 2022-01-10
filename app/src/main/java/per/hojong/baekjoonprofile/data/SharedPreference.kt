package per.hojong.baekjoonprofile.data

import android.content.Context

fun putSolvedID(context: Context, ID: String, widgetId: Int) {
    context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        .edit()
        .putString(widgetId.toString(), ID)
        .apply()
}

fun getSolvedID(context: Context, widgetId: Int): String =
    context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        .getString(widgetId.toString(), "as00098")!!