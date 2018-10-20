package id.anhs.footballapps.ui.fragment.favoritesteams

import id.anhs.footballapps.model.Team

interface FavoriteTeamsContract {

    interface View {
        fun loading(loading: Boolean)

        fun setFavoriteTeams(teams: List<Team>)
    }

    interface Presenter {
        fun loadFavoriteTeams()
    }
}