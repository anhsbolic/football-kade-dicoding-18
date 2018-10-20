package id.anhs.footballapps.ui.fragment.matchesnext

import id.anhs.footballapps.model.League
import id.anhs.footballapps.model.MatchEvent

interface MatchesNextContract {

    interface View {
        fun loadingSpinnerData(loading: Boolean)

        fun setSpinnerData(leagues: List<League>)

        fun loadingNextMatchesData(loading: Boolean)

        fun setMatchEvents(matchEvents: List<MatchEvent>)

        fun showToastMsg(msg: String)
    }

    interface Presenter {

        fun clearComposite()

        fun disposeComposite()

        fun loadSpinnerData()

        fun loadNextMatchEvents(idLeague: String)
    }
}