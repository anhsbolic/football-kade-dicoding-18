package id.anhs.footballapps.ui.fragment.teamoverview

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import id.anhs.footballapps.R
import kotlinx.android.synthetic.main.fragment_team_overview.*

class TeamOverviewFragment : Fragment() {
    private var teamOverview: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            teamOverview = it.getString(ARG_TEAM_OVERVIEW)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_team_overview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        teamOverview?.let { setTeamOverview(teamOverview ?: "") }
    }

    private fun setTeamOverview(teamOverview: String) {
        team_overview_txt_overview.text = teamOverview
    }

    companion object {

        private const val ARG_TEAM_OVERVIEW = "teamOverview"

        @JvmStatic
        fun newInstance(teamOverview: String) =
                TeamOverviewFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_TEAM_OVERVIEW, teamOverview)
                    }
                }
    }
}
