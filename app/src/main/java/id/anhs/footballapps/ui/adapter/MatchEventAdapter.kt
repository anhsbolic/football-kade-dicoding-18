package id.anhs.footballapps.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import id.anhs.footballapps.R
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.utils.MyDateFormat
import id.anhs.footballapps.utils.gone
import id.anhs.footballapps.utils.visible
import kotlinx.android.synthetic.main.adapter_match_event.view.*

class MatchEventAdapter(private val matchEvents: List<MatchEvent>,
                        private val alarmEnabled: Boolean,
                        private val onItemClickListener: (idMatch: String) -> Unit,
                        private val onAlarmClickListener: (matchEvent: MatchEvent) -> Unit)
    : RecyclerView.Adapter<MatchEventAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_match_event, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(matchEvents[position],alarmEnabled, onItemClickListener, onAlarmClickListener)
    }

    override fun getItemCount(): Int = matchEvents.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imgAddToCalendar: ImageView = itemView.adapter_match_event_img_add_to_calendar
        private val txtDate: TextView = itemView.adapter_match_event_txt_date
        private val txtTime: TextView = itemView.adapter_match_event_txt_time
        private val txtHomeTeam: TextView = itemView.adapter_match_event_txt_home_team
        private val txtAwayTeam: TextView = itemView.adapter_match_event_txt_away_team
        private val txtHomeScore: TextView = itemView.adapter_match_event_txt_home_score
        private val txtAwayScore: TextView = itemView.adapter_match_event_txt_away_score

        fun bindItem(matchEvent: MatchEvent,
                     alarmEnabled: Boolean,
                     onItemClickListener: (idMatch: String) -> Unit,
                     onAlarmClickListener: (matchEvent: MatchEvent) -> Unit) {
            // date
            val date = matchEvent.dateEvent ?: ""
            if (date.isNotEmpty()) txtDate.text = MyDateFormat.dateEn(date)

            // time
            val time = matchEvent.strTime ?: ""
            if (time.isNotEmpty()) txtTime.text = MyDateFormat.time(time)

            // homeTeam
            val homeTeam = matchEvent.strHomeTeam ?: ""
            txtHomeTeam.text = homeTeam

            // awayTeam
            val awayTeam = matchEvent.strAwayTeam ?: ""
            txtAwayTeam.text = awayTeam

            // homeScore
            val homeScore = matchEvent.intHomeScore ?: ""
            txtHomeScore.text = homeScore

            // awayScore
            val awayScore = matchEvent.intAwayScore ?: ""
            txtAwayScore.text = awayScore

            // on item click listener
            itemView.setOnClickListener { onItemClickListener(matchEvent.idEvent ?: "") }

            // on alarm click listener
            if (alarmEnabled) {
                imgAddToCalendar.visible()
                imgAddToCalendar.setOnClickListener {
                    onAlarmClickListener(matchEvent)
                }
            } else {
                imgAddToCalendar.gone()
            }
        }
    }
}