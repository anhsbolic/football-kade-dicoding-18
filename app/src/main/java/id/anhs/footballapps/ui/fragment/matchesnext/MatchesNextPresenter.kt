package id.anhs.footballapps.ui.fragment.matchesnext

import id.anhs.footballapps.api.TheSportDBApiServices
import id.anhs.footballapps.model.LeaguesResponse
import id.anhs.footballapps.model.MatchEventResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MatchesNextPresenter(private val view: MatchesNextContract.View,
                           private val theSportDBApiServices: TheSportDBApiServices)
    : MatchesNextContract.Presenter {

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

    override fun loadNextMatchEvents(idLeague: String) {
        view.loadingNextMatchesData(true)

        composite.add(
                theSportDBApiServices.getNextMatchEvents(idLeague)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({matchEventsResponse: MatchEventResponse? ->
                            view.loadingNextMatchesData(false)
                            matchEventsResponse?.matchEvents?.let {matchEvents ->
                                view.setMatchEvents(matchEvents)
                            }
                        }, {
                            view.loadingNextMatchesData(false)
                            view.showToastMsg("${it.message}")
                        })
        )
    }


}