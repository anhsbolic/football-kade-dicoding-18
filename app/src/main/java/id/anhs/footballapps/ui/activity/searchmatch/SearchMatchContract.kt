package id.anhs.footballapps.ui.activity.searchmatch

import id.anhs.footballapps.model.MatchEvent

interface SearchMatchContract {

    interface View {

        fun loading(loading: Boolean)

        fun setMatchesSearchResult(matchEvents: List<MatchEvent>)

        fun setNoResult()

        fun showToastMsg(msg: String)
    }

    interface Presenter {

        fun clearComposite()

        fun disposeComposite()

        fun searchMatches(query: String)
    }
}