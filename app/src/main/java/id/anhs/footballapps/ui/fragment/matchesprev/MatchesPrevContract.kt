package id.anhs.footballapps.ui.fragment.matchesprev

import id.anhs.footballapps.model.League
import id.anhs.footballapps.model.MatchEvent

interface MatchesPrevContract {

    interface View {

        fun loadingSpinnerData(loading: Boolean)

        fun setSpinnerData(leagues: List<League>)

        fun loadingPrevMatchesData(loading: Boolean)

        fun setMatchEvents(matchEvents: List<MatchEvent>)

        fun showToastMsg(msg: String)
    }

    interface Presenter {

        fun clearComposite()

        fun disposeComposite()

        fun loadSpinnerData()

        fun loadPrevMatchEvents(idLeague: String)
    }
}