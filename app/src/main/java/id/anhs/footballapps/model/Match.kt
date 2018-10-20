package id.anhs.footballapps.model

import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Keep
data class Match (
        @SerializedName("idEvent") @Expose var idEvent: String? = null,
        @SerializedName("strEvent") @Expose var strEvent: String? = null,
        @SerializedName("strHomeTeam") @Expose var strHomeTeam: String? = null,
        @SerializedName("strAwayTeam") @Expose var strAwayTeam: String? = null,
        @SerializedName("intHomeScore") @Expose var intHomeScore: String? = null,
        @SerializedName("intRound") @Expose var intRound: String? = null,
        @SerializedName("intAwayScore") @Expose var intAwayScore: String? = null,
        @SerializedName("dateEvent") @Expose var dateEvent: String? = null,
        @SerializedName("strDate") @Expose var strDate: String? = null,
        @SerializedName("strTime") @Expose var strTime: String? = null,
        @SerializedName("idHomeTeam") @Expose var idHomeTeam: String? = null,
        @SerializedName("idAwayTeam") @Expose var idAwayTeam: String? = null,
        @SerializedName("strHomeGoalDetails") @Expose var strHomeGoalDetails: String? = null,
        @SerializedName("strHomeRedCards") @Expose var strHomeRedCards: String? = null,
        @SerializedName("strHomeYellowCards") @Expose var strHomeYellowCards: String? = null,
        @SerializedName("strHomeLineupGoalkeeper") @Expose var strHomeLineupGoalkeeper: String? = null,
        @SerializedName("strHomeLineupDefense") @Expose var strHomeLineupDefense: String? = null,
        @SerializedName("strHomeLineupMidfield") @Expose var strHomeLineupMidfield: String? = null,
        @SerializedName("strHomeLineupForward") @Expose var strHomeLineupForward: String? = null,
        @SerializedName("strHomeLineupSubstitutes") @Expose var strHomeLineupSubstitutes: String? = null,
        @SerializedName("strHomeFormation") @Expose var strHomeFormation: String? = null,
        @SerializedName("strAwayRedCards") @Expose var strAwayRedCards: String? = null,
        @SerializedName("strAwayYellowCards") @Expose var strAwayYellowCards: String? = null,
        @SerializedName("strAwayGoalDetails") @Expose var strAwayGoalDetails: String? = null,
        @SerializedName("strAwayLineupGoalkeeper") @Expose var strAwayLineupGoalkeeper: String? = null,
        @SerializedName("strAwayLineupDefense") @Expose var strAwayLineupDefense: String? = null,
        @SerializedName("strAwayLineupMidfield") @Expose var strAwayLineupMidfield: String? = null,
        @SerializedName("strAwayLineupForward") @Expose var strAwayLineupForward: String? = null,
        @SerializedName("strAwayLineupSubstitutes") @Expose var strAwayLineupSubstitutes: String? = null,
        @SerializedName("strAwayFormation") @Expose var strAwayFormation: String? = null,
        @SerializedName("intHomeShots") @Expose var intHomeShots: String? = null,
        @SerializedName("intAwayShots") @Expose var intAwayShots: String? = null,
        @SerializedName("strFilename") @Expose var strFilename: String? = null
)