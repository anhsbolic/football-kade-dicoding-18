package id.anhs.footballapps.ui.fragment.favoritesmatches

import id.anhs.footballapps.model.MatchEvent

interface FavoriteMatchesContract {

    interface View {
        fun loading(loading: Boolean)

        fun setFavoriteMatches(matches: List<MatchEvent>)
    }

    interface Presenter {
        fun loadFavoriteMatches()
    }
}