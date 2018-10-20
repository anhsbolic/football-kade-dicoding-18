package id.anhs.footballapps.ui.activity.searchteam

import id.anhs.footballapps.BasePresenterTest
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.Team
import id.anhs.footballapps.model.TeamResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchTeamPresenterTest : BasePresenterTest() {

    @Mock
    private lateinit var view: SearchTeamContract.View

    @Mock
    private var theSportDBApiServices = ApiServices.getTheSportDBApiServices()

    private lateinit var presenter: SearchTeamPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchTeamPresenter(view, theSportDBApiServices)
    }

    @Test
    fun testSearchTeamSuccess() {
        val teams = mutableListOf<Team>().apply {
            add(Team())
        }

        val teamResponse = TeamResponse(teams)
        Mockito.`when`(theSportDBApiServices.searchTeam("Arsenal"))
                .thenReturn(Observable.just(teamResponse))

        val inOrder = Mockito.inOrder(view)
        presenter.searchTeam("Arsenal")
        inOrder.verify(view).loading(true)
        inOrder.verify(view).loading(false)
        inOrder.verify(view).setTeamsSearchResult(teams)
    }

    @Test
    fun testSearchTeamEmpty() {
        val teams: MutableList<Team> = mutableListOf()

        val teamResponse = TeamResponse(teams)
        Mockito.`when`(theSportDBApiServices.searchTeam("Arsenal"))
                .thenReturn(Observable.just(teamResponse))

        val inOrder = Mockito.inOrder(view)
        presenter.searchTeam("Arsenal")
        inOrder.verify(view).loading(true)
        inOrder.verify(view).loading(false)
        inOrder.verify(view).setNoResult()
    }

    @Test
    fun testSearchTeamFailed() {
        val errorMsg = "Search team data failed"
        val error = Throwable(errorMsg)

        Mockito.`when`(theSportDBApiServices.searchTeam("Arsenal"))
                .thenReturn(Observable.error(error))

        val inOrder = Mockito.inOrder(view)
        presenter.searchTeam("Arsenal")
        inOrder.verify(view).loading(true)
        inOrder.verify(view).loading(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }
}