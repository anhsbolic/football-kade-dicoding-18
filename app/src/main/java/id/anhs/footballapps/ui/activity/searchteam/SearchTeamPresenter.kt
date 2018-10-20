package id.anhs.footballapps.ui.activity.searchteam

import id.anhs.footballapps.api.TheSportDBApiServices
import id.anhs.footballapps.model.TeamResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchTeamPresenter (private val view: SearchTeamContract.View,
                           private val theSportDBApiServices: TheSportDBApiServices)
    : SearchTeamContract.Presenter {

    private val composite = CompositeDisposable()

    override fun clearComposite() {
        composite.clear()
    }

    override fun disposeComposite() {
        composite.dispose()
    }

    override fun searchTeam(query: String) {
        view.loading(true)

        composite.add(
                theSportDBApiServices.searchTeam(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({teamResponse: TeamResponse? ->
                            view.loading(false)
                            teamResponse?.teams?.let {teams ->
                                if (teams.isNotEmpty()) {
                                    view.setTeamsSearchResult(teams)
                                } else {
                                    view.setNoResult()
                                }
                            } ?: view.setNoResult()
                        }, {
                            view.loading(false)
                            view.showToastMsg("${it.message}")
                        })
        )
    }

}