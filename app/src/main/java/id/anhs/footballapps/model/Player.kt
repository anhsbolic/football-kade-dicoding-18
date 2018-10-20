package id.anhs.footballapps.model

import android.os.Parcelable
import android.support.annotation.Keep
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Keep
data class Player (
        @SerializedName("idPlayer") @Expose var idPlayer: String? = null,
        @SerializedName("strPlayer") @Expose var strPlayer: String? = null,
        @SerializedName("strDescriptionEN") @Expose var strDescriptionEN: String? = null,
        @SerializedName("strPosition") @Expose var strPosition: String? = null,
        @SerializedName("strHeight") @Expose var strHeight: String? = null,
        @SerializedName("strWeight") @Expose var strWeight: String? = null,
        @SerializedName("strCutout") @Expose var strCutout: String? = null,
        @SerializedName("strFanart1") @Expose var strFanart1: String? = null,
        @SerializedName("strFanart2") @Expose var strFanart2: String? = null,
        @SerializedName("strFanart3") @Expose var strFanart3: String? = null,
        @SerializedName("strFanart4") @Expose var strFanart4: String? = null
) : Parcelable