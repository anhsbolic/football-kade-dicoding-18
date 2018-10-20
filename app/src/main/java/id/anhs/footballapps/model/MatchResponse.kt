package id.anhs.footballapps.model

import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class MatchResponse(
        @SerializedName("events") @Expose val matches: List<Match>
)