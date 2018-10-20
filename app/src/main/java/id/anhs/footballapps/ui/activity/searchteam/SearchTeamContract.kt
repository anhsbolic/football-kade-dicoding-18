package id.anhs.footballapps.ui.activity.searchteam

import id.anhs.footballapps.model.Team

interface SearchTeamContract {

    interface View {

        fun loading(loading: Boolean)

        fun setTeamsSearchResult(teams: List<Team>)

        fun setNoResult()

        fun showToastMsg(msg: String)
    }

    interface Presenter {

        fun clearComposite()

        fun disposeComposite()

        fun searchTeam(query: String)
    }
}