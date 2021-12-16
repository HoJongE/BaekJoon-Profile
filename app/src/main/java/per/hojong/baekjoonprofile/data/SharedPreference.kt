package per.hojong.baekjoonprofile.data

import android.app.Application
import android.content.Context
import kotlinx.coroutines.delay

fun putSolvedID(context: Context, ID: String) {
    context.getSharedPreferences("profile", Context.MODE_PRIVATE)
        .edit()
        .putString("name", ID)
        .apply()
}

fun getSolvedID(context: Context): String =
    context.getSharedPreferences("profile", Context.MODE_PRIVATE).getString("name", "as00098")!!


