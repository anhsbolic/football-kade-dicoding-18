package id.anhs.footballapps.ui.fragment.teamplayers

import id.anhs.footballapps.model.Player

interface TeamPlayersContract {

    interface View {
        fun loadingData(loading: Boolean)

        fun setPlayers(players: List<Player>)

        fun showToastMsg(msg: String)
    }

    interface Presenter {

        fun clearComposite()

        fun disposeComposite()

        fun loadPlayersData(idTeam: String)
    }
}