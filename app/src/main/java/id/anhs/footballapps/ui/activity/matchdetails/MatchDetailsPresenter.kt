package id.anhs.footballapps.ui.activity.matchdetails

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import id.anhs.footballapps.api.TheSportDBApiServices
import id.anhs.footballapps.datastorage.database
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.model.MatchResponse
import id.anhs.footballapps.model.TeamResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class MatchDetailsPresenter(private val view: MatchDetailsContract.View,
                            private val mContext: Context,
                            private val theSportDBApiServices: TheSportDBApiServices)
    : MatchDetailsContract.Presenter {

    private val composite = CompositeDisposable()

    override fun clearComposite() {
        composite.clear()
    }

    override fun disposeComposite() {
        composite.dispose()
    }

    override fun loadMatchData(idMatch: String) {
        view.loadingData(true)

        composite.add(
                theSportDBApiServices.getEventDetails(idMatch)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({matchResponse: MatchResponse? ->
                            view.loadingData(false)
                            matchResponse?.matches?.let {matches ->
                                if(matches.isNotEmpty()) {
                                    view.setMatchData(matches[0])
                                } else {
                                    view.showToastMsg("Get data failed")
                                }
                            }
                        }, {
                            view.loadingData(false)
                            view.showToastMsg("${it.message}")
                        })
        )
    }

    override fun loadHomeTeamDetails(idHomeTeam: String) {
        view.loadingData(true)

        composite.add(
                theSportDBApiServices.getTeam(idHomeTeam)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({teamResponse: TeamResponse? ->
                            view.loadingData(false)
                            teamResponse?.teams?.let {teams->
                                if(teams.isNotEmpty()) {
                                    val teamBadge = teams[0].teamBadge?:""
                                    if (teamBadge.isNotEmpty()) {
                                        view.setHomeTeamBadge(teamBadge)
                                    } else {
                                        view.showToastMsg("Get home team badge failed")
                                    }
                                } else {
                                    view.showToastMsg("Get data failed")
                                }
                            }
                        }, {
                            view.loadingData(false)
                            view.showToastMsg("${it.message}")
                        })
        )
    }

    override fun loadAwayTeamDetails(idAwayTeam: String) {
        view.loadingData(true)

        composite.add(
                theSportDBApiServices.getTeam(idAwayTeam)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({teamResponse: TeamResponse? ->
                            view.loadingData(false)
                            teamResponse?.teams?.let {teams->
                                if(teams.isNotEmpty()) {
                                    val teamBadge = teams[0].teamBadge?:""
                                    if (teamBadge.isNotEmpty()) {
                                        view.setAwayTeamBadge(teamBadge)
                                    } else {
                                        view.showToastMsg("Get home team badge failed")
                                    }
                                } else {
                                    view.showToastMsg("Get data failed")
                                }
                            }
                        }, {
                            view.loadingData(false)
                            view.showToastMsg("${it.message}")
                        })
        )
    }

    override fun matchFavoriteState(idEvent: String) {
        mContext.database.use {
            val result = select(MatchEvent.TABLE_FAVORITE_MATCH)
                    .whereArgs("(${MatchEvent.ID_EVENT} = {idEvent})",
                            "idEvent" to idEvent)
            val favorite = result.parseList(classParser<MatchEvent>())
            if (!favorite.isEmpty()) {
                view.setMatchFavoriteState(true)
            }
        }
    }

    override fun addToFavorite(matchEvent: MatchEvent) {
        try {
            mContext.database.use {
                insert(MatchEvent.TABLE_FAVORITE_MATCH,
                        MatchEvent.ID_EVENT to matchEvent.idEvent,
                        MatchEvent.STR_HOME_TEAM to matchEvent.strHomeTeam,
                        MatchEvent.STR_AWAY_TEAM to matchEvent.strAwayTeam,
                        MatchEvent.INT_HOME_SCORE to matchEvent.intHomeScore,
                        MatchEvent.INT_AWAY_SCORE to matchEvent.intAwayScore,
                        MatchEvent.STR_FILENAME to matchEvent.strFilename,
                        MatchEvent.DATE_EVENT to matchEvent.dateEvent,
                        MatchEvent.STR_TIME to matchEvent.strTime
                )
            }
            view.onAddToFavoriteSuccess("Added to Favorites")
        } catch (e: SQLiteConstraintException){
            view.onAddToFavoriteFailed(e.localizedMessage)
        }
    }

    override fun removeFromFavorite(idEvent: String) {
        try {
            mContext.database.use {
                delete(MatchEvent.TABLE_FAVORITE_MATCH,
                        "(${MatchEvent.ID_EVENT} = {idEvent})",
                        "idEvent" to idEvent)
            }
            view.onRemoveFromFavoriteSuccess("Removed from favorite")
        } catch (e: SQLiteConstraintException){
            view.onRemoveFromFavoriteFailed(e.localizedMessage)
        }
    }

}