package id.anhs.footballapps.ui.activity.teamdetails

import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import id.anhs.footballapps.datastorage.database
import id.anhs.footballapps.model.Team
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class TeamDetailsPresenter(private val view: TeamDetailsContract.View,
                           private val mContext: Context)
    : TeamDetailsContract.Presenter{

    override fun addToFavorite(team: Team) {
        try {
            mContext.database.use {
                insert(Team.TABLE_FAVORITE_TEAM,
                        Team.TEAM_ID to team.teamId,
                        Team.TEAM_NAME to team.teamName,
                        Team.TEAM_BADGE to team.teamBadge,
                        Team.INT_FORMED_YEAR to team.intFormedYear,
                        Team.STR_STADIUM to team.strStadium,
                        Team.STR_DESCRIPTION_EN to team.strDescriptionEN)
            }
            view.onAddToFavoriteSuccess("Added to Favorites")
        } catch (e: SQLiteConstraintException){
            view.onAddToFavoriteFailed(e.localizedMessage)
        }
    }

    override fun removeFromFavorite(idTeam: String) {
        try {
            mContext.database.use {
                delete(Team.TABLE_FAVORITE_TEAM, "(${Team.TEAM_ID} = {idTeam})",
                        "idTeam" to idTeam)
            }
            view.onRemoveFromFavoriteSuccess("Removed from favorite")
        } catch (e: SQLiteConstraintException){
            view.onRemoveFromFavoriteFailed(e.localizedMessage)
        }
    }

    override fun teamFavoriteState(idTeam: String) {
        mContext.database.use {
            val result = select(Team.TABLE_FAVORITE_TEAM)
                    .whereArgs("(${Team.TEAM_ID} = {teamId})",
                            "teamId" to idTeam)
            val favorite = result.parseList(classParser<Team>())
            if (!favorite.isEmpty()) {
                view.setTeamFavoriteState(true)
            }
        }
    }

}