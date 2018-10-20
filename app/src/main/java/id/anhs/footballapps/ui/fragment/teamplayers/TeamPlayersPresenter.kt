package id.anhs.footballapps.ui.fragment.teamplayers

import id.anhs.footballapps.api.TheSportDBApiServices
import id.anhs.footballapps.model.PlayerResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TeamPlayersPresenter(private val view: TeamPlayersContract.View,
                           private val theSportDBApiServices: TheSportDBApiServices)
    : TeamPlayersContract.Presenter {

    private val composite = CompositeDisposable()

    override fun clearComposite() {
        composite.clear()
    }

    override fun disposeComposite() {
        composite.dispose()
    }

    override fun loadPlayersData(idTeam: String) {
        view.loadingData(true)

        composite.add(
                theSportDBApiServices.getPLayersInTeam(idTeam)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({playerResponse: PlayerResponse? ->
                            view.loadingData(false)
                            playerResponse?.players?.let {players ->
                                if (players.isNotEmpty()) view.setPlayers(players)
                            }
                        }, {
                            view.loadingData(false)
                            view.showToastMsg("${it.message}")
                        })
        )
    }

}