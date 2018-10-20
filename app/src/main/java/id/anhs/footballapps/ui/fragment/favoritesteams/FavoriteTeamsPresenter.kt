package id.anhs.footballapps.ui.fragment.favoritesteams

import android.content.Context
import android.os.Handler
import id.anhs.footballapps.datastorage.database
import id.anhs.footballapps.model.Team
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteTeamsPresenter(private val view: FavoriteTeamsContract.View,
                             private val mContext: Context)
    : FavoriteTeamsContract.Presenter {

    override fun loadFavoriteTeams() {
        view.loading(true)

        mContext.database.use {
            val result = select(Team.TABLE_FAVORITE_TEAM)
            val favoriteTeams = result.parseList(classParser<Team>())

            Handler().postDelayed({
                view.loading(false)
                view.setFavoriteTeams(favoriteTeams)
            }, 800)
        }
    }
}