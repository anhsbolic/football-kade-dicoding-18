package id.anhs.footballapps.datastorage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.model.Team
import org.jetbrains.anko.db.*

class MyDatabaseOpenHelper(mContext: Context) :
        ManagedSQLiteOpenHelper(mContext, "Favorites.db", null, 1) {

    companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(mContext: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(mContext.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // FAVORITE TEAM
        db.createTable(Team.TABLE_FAVORITE_TEAM, true,
                Team.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Team.TEAM_ID to TEXT + UNIQUE,
                Team.TEAM_NAME to TEXT,
                Team.TEAM_BADGE to TEXT,
                Team.INT_FORMED_YEAR to TEXT,
                Team.STR_STADIUM to TEXT,
                Team.STR_DESCRIPTION_EN to TEXT
        )

        // FAVORITE MATCH
        db.createTable(MatchEvent.TABLE_FAVORITE_MATCH, true,
                MatchEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                MatchEvent.ID_EVENT to TEXT + UNIQUE,
                MatchEvent.STR_HOME_TEAM to TEXT,
                MatchEvent.STR_AWAY_TEAM to TEXT,
                MatchEvent.INT_HOME_SCORE to TEXT,
                MatchEvent.INT_AWAY_SCORE to TEXT,
                MatchEvent.STR_FILENAME to TEXT,
                MatchEvent.DATE_EVENT to TEXT,
                MatchEvent.STR_TIME to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(Team.TABLE_FAVORITE_TEAM, true)
        db.dropTable(MatchEvent.TABLE_FAVORITE_MATCH, true)
    }
}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)