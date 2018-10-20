package id.anhs.footballapps.ui.fragment.teamshome

import id.anhs.footballapps.api.TheSportDBApiServices
import id.anhs.footballapps.model.LeaguesResponse
import id.anhs.footballapps.model.TeamResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class TeamsHomePresenter(private val view: TeamsHomeContract.View,
                         private val theSportDBApiServices: TheSportDBApiServices)
    : TeamsHomeContract.Presenter {

    private val composite = CompositeDisposable()

    override fun clearComposite() {
        composite.clear()
    }

    override fun disposeComposite() {
        composite.dispose()
    }

    override fun loadSpinnerData() {
        view.loadingSpinnerData(true)

        composite.add(
                theSportDBApiServices.getListLeague()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({leaguesResponse: LeaguesResponse? ->
                            view.loadingSpinnerData(false)
                            leaguesResponse?.leagues?.let {leagues ->
                                view.setSpinnerData(leagues)
                            }
                        }, {
                            view.loadingSpinnerData(false)
                            view.showToastMsg("${it.message}")
                        })
        )
    }

    override fun getTeamsInLeague(idLeague: String) {
        view.loadingTeamsData(true)

        composite.add(
                theSportDBApiServices.getTeamsInLeague(idLeague)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({teamResponse: TeamResponse? ->
                            view.loadingTeamsData(false)
                            teamResponse?.teams?.let {teams ->
                                view.setTeams(teams)
                            }
                        }, {
                            view.loadingTeamsData(false)
                            view.showToastMsg("${it.message}")
                        })
        )
    }

}