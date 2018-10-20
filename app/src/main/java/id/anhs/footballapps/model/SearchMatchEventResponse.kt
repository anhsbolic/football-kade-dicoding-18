package id.anhs.footballapps.model

import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class SearchMatchEventResponse (
        @SerializedName("event") @Expose val matchEvents: List<MatchEvent>
)