package id.anhs.footballapps.ui.fragment.teamshome

import id.anhs.footballapps.model.League
import id.anhs.footballapps.model.Team

interface TeamsHomeContract {

    interface View {
        fun loadingSpinnerData(loading: Boolean)

        fun setSpinnerData(leagues: List<League>)

        fun loadingTeamsData(loading: Boolean)

        fun setTeams(teams: List<Team>)

        fun showToastMsg(msg: String)
    }

    interface Presenter {

        fun clearComposite()

        fun disposeComposite()

        fun loadSpinnerData()

        fun getTeamsInLeague(idLeague: String)
    }
}