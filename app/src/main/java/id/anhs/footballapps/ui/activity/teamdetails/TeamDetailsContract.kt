package id.anhs.footballapps.ui.activity.teamdetails

import id.anhs.footballapps.model.Team

interface TeamDetailsContract {
    interface View {
        fun setTeamFavoriteState(isFavorite: Boolean)

        fun onAddToFavoriteSuccess(msg: String)

        fun onAddToFavoriteFailed(msg: String)

        fun onRemoveFromFavoriteSuccess(msg: String)

        fun onRemoveFromFavoriteFailed(msg: String)
    }

    interface Presenter {
        fun teamFavoriteState(idTeam: String)

        fun addToFavorite(team: Team)

        fun removeFromFavorite(idTeam: String)

    }
}