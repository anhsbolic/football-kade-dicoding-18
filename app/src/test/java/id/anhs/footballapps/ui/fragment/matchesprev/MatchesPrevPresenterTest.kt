package id.anhs.footballapps.ui.fragment.matchesprev

import id.anhs.footballapps.BasePresenterTest
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.League
import id.anhs.footballapps.model.LeaguesResponse
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.model.MatchEventResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.inOrder
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)

class MatchesPrevPresenterTest : BasePresenterTest() {

    @Mock
    private lateinit var view: MatchesPrevContract.View

    @Mock
    private var theSportDBApiServices = ApiServices.getTheSportDBApiServices()

    private lateinit var presenter: MatchesPrevPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchesPrevPresenter(view, theSportDBApiServices)
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
    fun testLoadPrevMatchEventsSuccess() {
        val events = mutableListOf<MatchEvent>().apply {
            add(MatchEvent())
        }
        val matchEventResponse = MatchEventResponse(events)
        `when`(theSportDBApiServices.getPastMatchEvents("4332"))
                .thenReturn(Observable.just(matchEventResponse))

        val inOrder = inOrder(view)
        presenter.loadPrevMatchEvents("4332")
        inOrder.verify(view).loadingPrevMatchesData(true)
        inOrder.verify(view).loadingPrevMatchesData(false)
        inOrder.verify(view).setMatchEvents(events)
    }

    @Test
    fun testLoadPrevMatchEventsFailed() {
        val errorMsg = "Load events data failed"
        val error = Throwable(errorMsg)

        `when`(theSportDBApiServices.getPastMatchEvents("4332"))
                .thenReturn(Observable.error(error))

        val inOrder = inOrder(view)
        presenter.loadPrevMatchEvents("4332")
        inOrder.verify(view).loadingPrevMatchesData(true)
        inOrder.verify(view).loadingPrevMatchesData(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }
}