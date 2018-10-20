package id.anhs.footballapps.ui.fragment.matchesnext

import id.anhs.footballapps.BasePresenterTest
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.League
import id.anhs.footballapps.model.LeaguesResponse
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.model.MatchEventResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class MatchesNextPresenterTest : BasePresenterTest() {

    @Mock
    private lateinit var view: MatchesNextContract.View

    @Mock
    private var theSportDBApiServices = ApiServices.getTheSportDBApiServices()

    private lateinit var presenter: MatchesNextPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchesNextPresenter(view, theSportDBApiServices)
    }

    @Test
    fun testLoadSpinnerDataSuccess() {
        val leagues = mutableListOf<League>().apply {
            add(League())
        }
        val leaguesResponse = LeaguesResponse(leagues)
        Mockito.`when`(theSportDBApiServices.getListLeague())
                .thenReturn(Observable.just(leaguesResponse))

        val inOrder = Mockito.inOrder(view)
        presenter.loadSpinnerData()
        inOrder.verify(view).loadingSpinnerData(true)
        inOrder.verify(view).loadingSpinnerData(false)
        inOrder.verify(view).setSpinnerData(leagues)
    }

    @Test
    fun testLoadSpinnerDataFailed() {
        val errorMsg = "Load leagues data failed"
        val error = Throwable(errorMsg)

        Mockito.`when`(theSportDBApiServices.getListLeague())
                .thenReturn(Observable.error(error))

        val inOrder = Mockito.inOrder(view)
        presenter.loadSpinnerData()
        inOrder.verify(view).loadingSpinnerData(true)
        inOrder.verify(view).loadingSpinnerData(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }

    @Test
    fun testLoadNextMatchEventsSuccess() {
        val events = mutableListOf<MatchEvent>().apply {
            add(MatchEvent())
        }
        val matchEventResponse = MatchEventResponse(events)
        Mockito.`when`(theSportDBApiServices.getNextMatchEvents("4332"))
                .thenReturn(Observable.just(matchEventResponse))

        val inOrder = Mockito.inOrder(view)
        presenter.loadNextMatchEvents("4332")
        inOrder.verify(view).loadingNextMatchesData(true)
        inOrder.verify(view).loadingNextMatchesData(false)
        inOrder.verify(view).setMatchEvents(events)
    }

    @Test
    fun testLoadNextMatchEventsFailed() {
        val errorMsg = "Load events data failed"
        val error = Throwable(errorMsg)

        Mockito.`when`(theSportDBApiServices.getNextMatchEvents("4332"))
                .thenReturn(Observable.error(error))

        val inOrder = Mockito.inOrder(view)
        presenter.loadNextMatchEvents("4332")
        inOrder.verify(view).loadingNextMatchesData(true)
        inOrder.verify(view).loadingNextMatchesData(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }
}