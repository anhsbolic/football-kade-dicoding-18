package id.anhs.footballapps.ui.fragment.matchesprev

import id.anhs.footballapps.api.TheSportDBApiServices
import id.anhs.footballapps.model.LeaguesResponse
import id.anhs.footballapps.model.MatchEventResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MatchesPrevPresenter(private val view: MatchesPrevContract.View,
                           private val theSportDBApiServices: TheSportDBApiServices)
    : MatchesPrevContract.Presenter {

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

    override fun loadPrevMatchEvents(idLeague: String) {
        view.loadingPrevMatchesData(true)

        composite.add(
                theSportDBApiServices.getPastMatchEvents(idLeague)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({matchEventsResponse: MatchEventResponse? ->
                            view.loadingPrevMatchesData(false)
                            matchEventsResponse?.matchEvents?.let {matchEvents ->
                                view.setMatchEvents(matchEvents)
                            }
                        }, {
                            view.loadingPrevMatchesData(false)
                            view.showToastMsg("${it.message}")
                        })
        )
    }

}