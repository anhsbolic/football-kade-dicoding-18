package id.anhs.footballapps.ui.fragment.teamplayers

import id.anhs.footballapps.BasePresenterTest
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.Player
import id.anhs.footballapps.model.PlayerResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.inOrder
import org.mockito.MockitoAnnotations

class TeamPlayersPresenterTest : BasePresenterTest() {

    @Mock
    private lateinit var view: TeamPlayersContract.View

    @Mock
    private var theSportDBApiServices = ApiServices.getTheSportDBApiServices()

    private lateinit var presenter: TeamPlayersPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = TeamPlayersPresenter(view, theSportDBApiServices)
    }

    @Test
    fun testLoadPlayersDataSuccess() {
        val players = mutableListOf<Player>().apply {
            add(Player())
        }
        val playerResponse = PlayerResponse(players)
        `when`(theSportDBApiServices.getPLayersInTeam("133604"))
                .thenReturn(Observable.just(playerResponse))

        val inOrder = inOrder(view)
        presenter.loadPlayersData("133604")
        inOrder.verify(view).loadingData(true)
        inOrder.verify(view).loadingData(false)
        inOrder.verify(view).setPlayers(players)
    }

    @Test
    fun testLoadPlayersDataFailed() {
        val errorMsg = "Load players data failed"
        val error = Throwable(errorMsg)

        `when`(theSportDBApiServices.getPLayersInTeam("133604"))
                .thenReturn(Observable.error(error))

        val inOrder = inOrder(view)
        presenter.loadPlayersData("133604")
        inOrder.verify(view).loadingData(true)
        inOrder.verify(view).loadingData(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }
}