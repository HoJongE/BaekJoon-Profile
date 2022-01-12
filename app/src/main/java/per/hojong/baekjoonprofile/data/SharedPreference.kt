package per.hojong.baekjoonprofile.data

import android.content.Context


fun putDefaultSolvedID(context: Context, ID: String) {
    context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        .edit()
        .putString("defaultID", ID)
        .apply()
}


fun getDefaultSolvedID(context: Context) =
    context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        .getString("defaultID", "")!!

fun putSolvedID(context: Context, ID: String, widgetId: Int) {
    context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        .edit()
        .putString(widgetId.toString(), ID)
        .apply()
}

fun getSolvedID(context: Context, widgetId: Int): String =
    context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        .getString(widgetId.toString(), "as00098")!!

fun clear(context: Context) {
    context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        .edit()
        .clear()
        .apply()
}

fun putTodaySolvedCount(context: Context, count: Int) {
    context.getSharedPreferences("count", Context.MODE_PRIVATE)
        .edit()
        .putInt("count", count)
        .apply()
}

fun getTodaySolvedCount(context: Context) =
    context.getSharedPreferences("count", Context.MODE_PRIVATE)
        .getInt("count", 0)