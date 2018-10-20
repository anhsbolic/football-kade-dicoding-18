package id.anhs.footballapps.ui.fragment.teamshome

import id.anhs.footballapps.BasePresenterTest
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.League
import id.anhs.footballapps.model.LeaguesResponse
import id.anhs.footballapps.model.Team
import id.anhs.footballapps.model.TeamResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.inOrder
import org.mockito.MockitoAnnotations

class TeamsHomePresenterTest : BasePresenterTest() {

    @Mock
    private lateinit var view: TeamsHomeContract.View

    @Mock
    private var theSportDBApiServices = ApiServices.getTheSportDBApiServices()

    private lateinit var presenter: TeamsHomePresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamsHomePresenter(view, theSportDBApiServices)
    }

    @Test
    fun testLoadSpinnerDataSuccess() {
        val leagues = mutableListOf<League>().apply {
            add(League())
        }
        val leaguesResponse = LeaguesResponse(leagues)
        `when`(theSportDBApiServices.getListLeague())
                .thenReturn(Observable.just(leaguesResponse))

        val inOrder = inOrder(view)
        presenter.loadSpinnerData()
        inOrder.verify(view).loadingSpinnerData(true)
        inOrder.verify(view).loadingSpinnerData(false)
        inOrder.verify(view).setSpinnerData(leagues)
    }

    @Test
    fun testLoadSpinnerDataFailed() {
        val errorMsg = "Load leagues data failed"
        val error = Throwable(errorMsg)

        `when`(theSportDBApiServices.getListLeague())
                .thenReturn(Observable.error(error))

        val inOrder = inOrder(view)
        presenter.loadSpinnerData()
        inOrder.verify(view).loadingSpinnerData(true)
        inOrder.verify(view).loadingSpinnerData(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }

    @Test
    fun testGetTeamsInLeagueSuccess() {
        val teams = mutableListOf<Team>().apply {
            add(Team())
        }
        val teamResponse = TeamResponse(teams)
        `when`(theSportDBApiServices.getTeamsInLeague("4332"))
                .thenReturn(Observable.just(teamResponse))

        val inOrder = inOrder(view)
        presenter.getTeamsInLeague("4332")
        inOrder.verify(view).loadingTeamsData(true)
        inOrder.verify(view).loadingTeamsData(false)
        inOrder.verify(view).setTeams(teams)
    }

    @Test
    fun testGetTeamsInLeagueFailed() {
        val errorMsg = "Load teams data failed"
        val error = Throwable(errorMsg)

        `when`(theSportDBApiServices.getTeamsInLeague("4332"))
                .thenReturn(Observable.error(error))

        val inOrder = inOrder(view)
        presenter.getTeamsInLeague("4332")
        inOrder.verify(view).loadingTeamsData(true)
        inOrder.verify(view).loadingTeamsData(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }
}