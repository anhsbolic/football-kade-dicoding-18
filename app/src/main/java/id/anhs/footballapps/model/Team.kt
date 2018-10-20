package id.anhs.footballapps.model

import android.os.Parcelable
import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Keep
@Parcelize
data class Team (
        var id: Int? = null,
        @SerializedName("idTeam") @Expose var teamId: String? = null,
        @SerializedName("strTeam") @Expose var teamName: String? = null,
        @SerializedName("strTeamBadge") @Expose var teamBadge: String? = null,
        @SerializedName("intFormedYear") @Expose var intFormedYear: String? = null,
        @SerializedName("strStadium") @Expose var strStadium: String? = null,
        @SerializedName("strDescriptionEN") @Expose var strDescriptionEN: String? = null
) : Parcelable {
    companion object {
        const val TABLE_FAVORITE_TEAM: String = "TABLE_FAVORITE_TEAM"
        const val ID : String = "ID_"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_BADGE: String = "TEAM_BADGE"
        const val INT_FORMED_YEAR: String = "INT_FORMED_YEAR"
        const val STR_STADIUM: String = "STR_STADIUM"
        const val STR_DESCRIPTION_EN: String = "STR_DESCRIPTION_EN"
    }
}