package id.anhs.footballapps.ui.fragment.matchesnext

import android.content.Intent
import android.os.Bundle
import android.provider.CalendarContract
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import id.anhs.footballapps.R
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.League
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.ui.activity.matchdetails.MatchDetailsActivity
import id.anhs.footballapps.ui.adapter.MatchEventAdapter
import id.anhs.footballapps.utils.MyDateFormat
import id.anhs.footballapps.utils.gone
import id.anhs.footballapps.utils.visible
import kotlinx.android.synthetic.main.fragment_matches_next.*
import org.jetbrains.anko.support.v4.ctx
import org.jetbrains.anko.support.v4.longToast
import org.jetbrains.anko.support.v4.startActivity
import java.util.concurrent.TimeUnit

class MatchesNextFragment : Fragment(), MatchesNextContract.View {

    private lateinit var presenter: MatchesNextPresenter
    private var loading = false
    private val leagues: MutableList<League> = mutableListOf()
    private lateinit var adapterRvMatchEvents: RecyclerView.Adapter<*>
    private var matchEvents: MutableList<MatchEvent> = mutableListOf()
    private var idLeague = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_matches_next, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // init Views
        initRvMatchEvents()

        // init presenter
        presenter = MatchesNextPresenter(this@MatchesNextFragment, ApiServices.getTheSportDBApiServices())

        // load spinner data
        presenter.loadSpinnerData()

        // swipe refresh handler
        next_matches_swipe_refresh.setOnRefreshListener {
            if (!loading) {
                if (idLeague.isNotEmpty()) {
                    presenter.loadNextMatchEvents(idLeague)
                } else {
                    next_matches_swipe_refresh.isRefreshing = false
                }
            } else {
                next_matches_swipe_refresh.isRefreshing = false
            }
        }
    }

    private fun initRvMatchEvents() {
        adapterRvMatchEvents = MatchEventAdapter(matchEvents, true,
                // on item click
                {idMatch ->
                    startActivity<MatchDetailsActivity>(MatchDetailsActivity.INTENT_ID_MATCH to idMatch)
                },
                // on alarm click
                {matchEvent ->
                    addToCalendar(matchEvent)
                }
        )
        next_matches_rv_matches.layoutManager = LinearLayoutManager(ctx)
        next_matches_rv_matches.addItemDecoration(DividerItemDecoration(ctx, LinearLayoutManager.VERTICAL))
        next_matches_rv_matches.adapter = adapterRvMatchEvents
    }

    override fun loadingSpinnerData(loading: Boolean) {
        this.loading = loading

        if (loading) {
            next_matches_progress_bar?.visible()
        } else {
            next_matches_progress_bar?.gone()
        }
    }

    override fun setSpinnerData(leagues: List<League>) {
        this.leagues.clear()
        this.leagues.addAll(leagues)

        val spinnerItems = arrayOfNulls<String>(leagues.size)
        for (i in leagues.indices) {
            spinnerItems[i] = leagues[i].name
        }

        val spinnerAdapter = ArrayAdapter(ctx, android.R.layout.simple_spinner_dropdown_item, spinnerItems)
        next_matches_spinner.adapter = spinnerAdapter
        next_matches_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                idLeague = leagues[position].id ?: ""
                if (idLeague.isNotEmpty()) presenter.loadNextMatchEvents(idLeague)
            }

        }
    }

    override fun loadingNextMatchesData(loading: Boolean) {
        this.loading = loading
        next_matches_swipe_refresh?.isRefreshing = loading
    }

    override fun setMatchEvents(matchEvents: List<MatchEvent>) {
        this.matchEvents.clear()
        this.matchEvents.addAll(matchEvents)
        adapterRvMatchEvents.notifyDataSetChanged()
    }

    override fun showToastMsg(msg: String) {
        longToast(msg)
    }

    private fun addToCalendar(matchEvent: MatchEvent) {
        val date = matchEvent.dateEvent ?: ""
        val time = matchEvent.strTime ?: "00:00"

        if (date.isNotEmpty()) {
            val startTimeInMillis = MyDateFormat.gregorianDateTimeInMillis(date, time)
            val endTimeInMillis = startTimeInMillis + TimeUnit.MINUTES.toMillis(90)

            val intent = Intent(Intent.ACTION_EDIT)
            intent.type = "vnd.android.cursor.item/event"
            intent.putExtra(CalendarContract.Events.TITLE, "${matchEvent.strFilename}")
            intent.putExtra(CalendarContract.Events.EVENT_LOCATION, "${matchEvent.strHomeTeam}")
            intent.putExtra(CalendarContract.Events.DESCRIPTION, "${matchEvent.strFilename}")
            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTimeInMillis)
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTimeInMillis)
            intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false)
            intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE)
            intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY)
            startActivity(intent)
        } else {
            showToastMsg("Schedule not available")
        }

    }

    override fun onStop() {
        presenter.clearComposite()
        super.onStop()
    }

    override fun onDestroy() {
        presenter.disposeComposite()
        super.onDestroy()
    }

}
