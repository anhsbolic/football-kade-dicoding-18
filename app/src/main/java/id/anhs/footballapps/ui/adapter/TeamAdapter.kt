package id.anhs.footballapps.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import id.anhs.footballapps.R
import id.anhs.footballapps.model.Team
import kotlinx.android.synthetic.main.adapter_team.view.*

class TeamAdapter(private val teams: List<Team>,
                  private val onItemClickListener: (Team) -> Unit)
    : RecyclerView.Adapter<TeamAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_team, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(mContext, teams[position], onItemClickListener)
    }

    override fun getItemCount(): Int = teams.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val imgTeamBadge: ImageView = itemView.adapter_team_img_badge
        private val txtTeamName: TextView = itemView.adapter_team_txt_team_name

        fun bindItem(mContext: Context, team: Team, onItemClickListener: (Team) -> Unit) {
            // img badge
            val teamBadge = team.teamBadge ?: ""
            if (teamBadge.isNotEmpty()) Glide.with(mContext).load(teamBadge).into(imgTeamBadge)

            // team name
            val teamName = team.teamName ?: ""
            txtTeamName.text = teamName

            // on item click listener
            itemView.setOnClickListener { onItemClickListener(team) }
        }
    }
}