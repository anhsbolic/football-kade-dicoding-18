package id.anhs.footballapps.ui.activity.teamdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.bumptech.glide.Glide
import id.anhs.footballapps.R
import id.anhs.footballapps.model.Team
import kotlinx.android.synthetic.main.activity_team_details.*
import org.jetbrains.anko.design.snackbar

class TeamDetailsActivity : AppCompatActivity(), TeamDetailsContract.View {

    private val tabTitles = arrayOf<CharSequence>("Overview", "Players")
    private val totalTabs = 2
    private lateinit var presenter: TeamDetailsPresenter
    private var team: Team? = null
    private var idTeam = ""
    private var teamOverview = ""
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team_details)
        title = null
        setSupportActionBar(team_details_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        team_details_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // init tab adapter
        var adapter = TeamDetailsViewPagerAdapter(supportFragmentManager, tabTitles, totalTabs,
                idTeam, teamOverview)

        // init presenter
        presenter = TeamDetailsPresenter(this@TeamDetailsActivity, this@TeamDetailsActivity)

        // get intent data
        if (intent.hasExtra(INTENT_TEAM)) {
            team = intent.getParcelableExtra(INTENT_TEAM)
            team?.let {
                title = it.teamName ?: ""
                setTeamData(it)
                idTeam = it.teamId ?: ""
                teamOverview = it.strDescriptionEN ?: ""
                adapter = TeamDetailsViewPagerAdapter(supportFragmentManager, tabTitles, totalTabs,
                        idTeam, teamOverview)
                presenter.teamFavoriteState(idTeam)
            }
        }

        // set tab
        team_details_view_pager.adapter = adapter
        team_details_tab_layout.setupWithViewPager(team_details_view_pager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.team_details_menu, menu)
        menuItem = menu
        if (isFavorite) {
            menuItem?.findItem(R.id.team_details_menu_favorite)?.setIcon(R.drawable.added_to_fav)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.team_details_menu_favorite-> {
                if (isFavorite) {
                    if (idTeam.isNotEmpty()) presenter.removeFromFavorite(idTeam)
                } else {
                    team?.let {
                        presenter.addToFavorite(it)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setTeamData(team: Team) {
        // team badge
        val teamBadge = team.teamBadge ?: ""
        if (teamBadge.isNotEmpty()) {
            Glide.with(this).load(teamBadge).into(team_details_img_team_badge)
        }

        // formed year
        val formedYear = team.intFormedYear ?: ""
        team_details_txt_formed_year.text = formedYear

        // stadium
        val stadium = team.strStadium ?: ""
        team_details_txt_stadium.text = stadium
    }

    override fun setTeamFavoriteState(isFavorite: Boolean) {
        this.isFavorite = isFavorite
    }

    override fun onAddToFavoriteSuccess(msg: String) {
        snackbar(team_details_coordinator_layout, msg).show()
        isFavorite = true
        menuItem?.findItem(R.id.team_details_menu_favorite)?.setIcon(R.drawable.added_to_fav)
    }

    override fun onAddToFavoriteFailed(msg: String) {
        snackbar(team_details_coordinator_layout, msg).show()
    }

    override fun onRemoveFromFavoriteSuccess(msg: String) {
        snackbar(team_details_coordinator_layout, msg).show()
        isFavorite = false
        menuItem?.findItem(R.id.team_details_menu_favorite)?.setIcon(R.drawable.add_to_fav)
    }

    override fun onRemoveFromFavoriteFailed(msg: String) {
        snackbar(team_details_coordinator_layout, msg).show()
    }

    companion object {
        const val INTENT_TEAM = "team"
    }
}
