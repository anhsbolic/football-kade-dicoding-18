package id.anhs.footballapps.model

import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class League (
        @SerializedName("idLeague") @Expose var id: String? = null,
        @SerializedName("strLeague") @Expose var name: String? = null
)