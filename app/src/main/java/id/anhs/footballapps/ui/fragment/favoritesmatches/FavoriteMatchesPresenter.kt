package id.anhs.footballapps.ui.fragment.favoritesmatches

import android.content.Context
import android.os.Handler
import id.anhs.footballapps.datastorage.database
import id.anhs.footballapps.model.MatchEvent
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select

class FavoriteMatchesPresenter(private val view: FavoriteMatchesContract.View,
                               private val mContext: Context)
    : FavoriteMatchesContract.Presenter {

    override fun loadFavoriteMatches() {
        view.loading(true)

        mContext.database.use {
            val result = select(MatchEvent.TABLE_FAVORITE_MATCH)
            val favoriteMatches = result.parseList(classParser<MatchEvent>())

            Handler().postDelayed({
                view.loading(false)
                view.setFavoriteMatches(favoriteMatches)
            }, 800)
        }
    }
}