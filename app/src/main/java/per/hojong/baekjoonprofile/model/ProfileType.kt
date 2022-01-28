package per.hojong.baekjoonprofile.model

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson

class ProfileType : NavType<Profile>(isNullableAllowed = false) {
    override fun get(bundle: Bundle, key: String): Profile? {
        return bundle.getSerializable(key) as? Profile
    }

    override fun parseValue(value: String): Profile {
        return Gson().fromJson(value, Profile::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Profile) {
        bundle.putSerializable(key, value)
    }


}