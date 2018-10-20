package id.anhs.footballapps.ui.activity.matchdetails

import id.anhs.footballapps.model.Match
import id.anhs.footballapps.model.MatchEvent

interface MatchDetailsContract {

    interface View {
        fun loadingData(loading: Boolean)

        fun setMatchData(match: Match)

        fun setHomeTeamBadge(teamBadge: String)

        fun setAwayTeamBadge(teamBadge: String)

        fun showToastMsg(msg: String)

        fun setMatchFavoriteState(isFavorite: Boolean)

        fun onAddToFavoriteSuccess(msg: String)

        fun onAddToFavoriteFailed(msg: String)

        fun onRemoveFromFavoriteSuccess(msg: String)

        fun onRemoveFromFavoriteFailed(msg: String)
    }

    interface Presenter {

        fun clearComposite()

        fun disposeComposite()

        fun loadMatchData(idMatch: String)

        fun loadHomeTeamDetails(idHomeTeam: String)

        fun loadAwayTeamDetails(idAwayTeam: String)

        fun matchFavoriteState(idEvent: String)

        fun addToFavorite(matchEvent: MatchEvent)

        fun removeFromFavorite(idEvent: String)
    }
}