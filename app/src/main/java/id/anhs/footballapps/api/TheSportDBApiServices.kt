package id.anhs.footballapps.api

import id.anhs.footballapps.BuildConfig
import id.anhs.footballapps.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface TheSportDBApiServices {

    // LEAGUE
    @GET("${BuildConfig.TSDB_API_KEY}/all_leagues.php")
    fun getListLeague(): Observable<LeaguesResponse>

    // MATCH EVENT
    @GET("${BuildConfig.TSDB_API_KEY}/eventsnextleague.php")
    fun getNextMatchEvents(@Query("id") idLeague: String): Observable<MatchEventResponse>

    @GET("${BuildConfig.TSDB_API_KEY}/eventspastleague.php")
    fun getPastMatchEvents(@Query("id") idLeague: String): Observable<MatchEventResponse>

    @GET("${BuildConfig.TSDB_API_KEY}/lookupevent.php")
    fun getEventDetails(@Query("id") idEvent: String): Observable<MatchResponse>

    @GET("${BuildConfig.TSDB_API_KEY}/searchevents.php")
    fun getSearchEvents(@Query("e") e: String): Observable<SearchMatchEventResponse>

    // TEAM
    @GET("${BuildConfig.TSDB_API_KEY}/lookupteam.php")
    fun getTeam(@Query("id") idTeam: String): Observable<TeamResponse>

    @GET("${BuildConfig.TSDB_API_KEY}/lookup_all_teams.php")
    fun getTeamsInLeague(@Query("id") idLeague: String): Observable<TeamResponse>

    @GET("${BuildConfig.TSDB_API_KEY}/searchteams.php")
    fun searchTeam(@Query("t") t: String): Observable<TeamResponse>

    // PLAYER
    @GET("${BuildConfig.TSDB_API_KEY}/lookup_all_players.php")
    fun getPLayersInTeam(@Query("id") idTeam: String): Observable<PlayerResponse>

}