package id.anhs.footballapps.model

import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class MatchEvent (
        val id: Int? = null,
        @SerializedName("idEvent") @Expose var idEvent: String? = null,
        @SerializedName("strHomeTeam") @Expose var strHomeTeam: String? = null,
        @SerializedName("strAwayTeam") @Expose var strAwayTeam: String? = null,
        @SerializedName("intHomeScore") @Expose var intHomeScore: String? = null,
        @SerializedName("intAwayScore") @Expose var intAwayScore: String? = null,
        @SerializedName("strFilename") var strFilename: String? = null,
        @SerializedName("dateEvent") @Expose var dateEvent: String? = null,
        @SerializedName("strTime") @Expose var strTime: String? = null
) {
    companion object {
        const val TABLE_FAVORITE_MATCH: String = "TABLE_FAVORITE_MATCH"
        const val ID : String = "ID_"
        const val ID_EVENT : String = "ID_EVENT"
        const val STR_HOME_TEAM : String = "STR_HOME_TEAM"
        const val STR_AWAY_TEAM : String = "STR_AWAY_TEAM"
        const val INT_HOME_SCORE : String = "INT_HOME_SCORE"
        const val INT_AWAY_SCORE : String = "INT_AWAY_SCORE"
        const val STR_FILENAME : String = "STR_FILENAME"
        const val DATE_EVENT : String = "DATE_EVENT"
        const val STR_TIME : String = "STR_TIME"
    }
}