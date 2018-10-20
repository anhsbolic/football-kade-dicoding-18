package id.anhs.footballapps.ui.activity.searchmatch

import id.anhs.footballapps.BasePresenterTest
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.model.SearchMatchEventResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SearchMatchPresenterTest : BasePresenterTest() {

    @Mock
    private lateinit var view: SearchMatchContract.View

    @Mock
    private var theSportDBApiServices = ApiServices.getTheSportDBApiServices()

    private lateinit var presenter: SearchMatchPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = SearchMatchPresenter(view, theSportDBApiServices)
    }

    @Test
    fun testSearchMatchesSuccess() {
        val matches = mutableListOf<MatchEvent>().apply {
            add(MatchEvent())
        }

        val searchMatchEventResponse = SearchMatchEventResponse(matches)
        Mockito.`when`(theSportDBApiServices.getSearchEvents("Arsenal"))
                .thenReturn(Observable.just(searchMatchEventResponse))

        val inOrder = Mockito.inOrder(view)
        presenter.searchMatches("Arsenal")
        inOrder.verify(view).loading(true)
        inOrder.verify(view).loading(false)
        inOrder.verify(view).setMatchesSearchResult(matches)
    }

    @Test
    fun testSearchMatchesEmpty() {
        val matches: MutableList<MatchEvent> = mutableListOf()

        val searchMatchEventResponse = SearchMatchEventResponse(matches)
        Mockito.`when`(theSportDBApiServices.getSearchEvents("Arsenal"))
                .thenReturn(Observable.just(searchMatchEventResponse))

        val inOrder = Mockito.inOrder(view)
        presenter.searchMatches("Arsenal")
        inOrder.verify(view).loading(true)
        inOrder.verify(view).loading(false)
        inOrder.verify(view).setNoResult()
    }

    @Test
    fun testSearchMatchesFailed() {
        val errorMsg = "Search event data failed"
        val error = Throwable(errorMsg)

        Mockito.`when`(theSportDBApiServices.getSearchEvents("Arsenal"))
                .thenReturn(Observable.error(error))

        val inOrder = Mockito.inOrder(view)
        presenter.searchMatches("Arsenal")
        inOrder.verify(view).loading(true)
        inOrder.verify(view).loading(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }
}