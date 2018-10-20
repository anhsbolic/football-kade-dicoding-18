package id.anhs.footballapps.ui.activity.searchmatch

import id.anhs.footballapps.api.TheSportDBApiServices
import id.anhs.footballapps.model.SearchMatchEventResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchMatchPresenter(private val view: SearchMatchContract.View,
                           private val theSportDBApiServices: TheSportDBApiServices)
    : SearchMatchContract.Presenter {

    private val composite = CompositeDisposable()

    override fun clearComposite() {
        composite.clear()
    }

    override fun disposeComposite() {
        composite.dispose()
    }

    override fun searchMatches(query: String) {
        view.loading(true)

        composite.add(
                theSportDBApiServices.getSearchEvents(query)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({searchMatchEventResponse: SearchMatchEventResponse? ->
                            view.loading(false)
                            searchMatchEventResponse?.matchEvents?.let {matchEvents ->
                                if (matchEvents.isNotEmpty()) {
                                    view.setMatchesSearchResult(matchEvents)
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