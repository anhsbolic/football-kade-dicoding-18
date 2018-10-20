package id.anhs.footballapps.ui.activity.matchdetails

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintSet.PARENT_ID
import android.support.design.widget.CoordinatorLayout
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.*
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import id.anhs.footballapps.R
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.Match
import id.anhs.footballapps.model.MatchEvent
import id.anhs.footballapps.utils.MyDateFormat
import id.anhs.footballapps.utils.gone
import id.anhs.footballapps.utils.visible
import org.jetbrains.anko.*
import org.jetbrains.anko.constraint.layout.constraintLayout
import org.jetbrains.anko.design.coordinatorLayout
import org.jetbrains.anko.design.snackbar

class MatchDetailsActivity : AppCompatActivity(), MatchDetailsContract.View {

    private lateinit var coordLayout: CoordinatorLayout
    private lateinit var progressBar: ProgressBar
    private lateinit var txtDate: TextView
    private lateinit var txtTime: TextView
    private lateinit var txtHomeScore: TextView
    private lateinit var txtAwayScore: TextView
    private lateinit var imgHome: ImageView
    private lateinit var imgAway: ImageView
    private lateinit var txtHomeTeam: TextView
    private lateinit var txtAwayTeam: TextView
    private lateinit var txtHomeFormation: TextView
    private lateinit var txtAwayFormation: TextView
    private lateinit var txtHomeGoals: TextView
    private lateinit var txtAwayGoals: TextView
    private lateinit var txtHomeShots: TextView
    private lateinit var txtAwayShots: TextView
    private lateinit var txtHomeGK: TextView
    private lateinit var txtAwayGK: TextView
    private lateinit var txtHomeDefense: TextView
    private lateinit var txtAwayDefense: TextView
    private lateinit var txtHomeMidfield: TextView
    private lateinit var txtAwayMidfield: TextView
    private lateinit var txtHomeForward: TextView
    private lateinit var txtAwayForward: TextView
    private lateinit var txtHomeSubstitutes: TextView
    private lateinit var txtAwaySubstitutes: TextView

    private lateinit var presenter: MatchDetailsPresenter
    private var loading = false
    private var idMatch = ""
    private var matchEvent: MatchEvent? = null
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MatchDetailsActivityUI().setContentView(this)
        setTitle(R.string.match_details_page_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // init coordinator layout
        coordLayout = find(R.id.match_detail_coordinator_layout)

        // init presenter
        presenter = MatchDetailsPresenter(this@MatchDetailsActivity,
                this@MatchDetailsActivity, ApiServices.getTheSportDBApiServices())

        // get intent data
        if (intent.hasExtra(INTENT_ID_MATCH)) {
            idMatch = intent.getStringExtra(INTENT_ID_MATCH)
            if (idMatch.isNotEmpty()) {
                presenter.matchFavoriteState(idMatch)
                presenter.loadMatchData(idMatch)
            } else {
                showToastMsg("event id not found ...")
            }
        }

    }

    override fun loadingData(loading: Boolean) {
        this.loading = loading
        if (loading) {
            progressBar.visible()
        } else {
            progressBar.gone()
        }
    }

    override fun setMatchData(match: Match) {
        // set matchevent
        matchEvent = MatchEvent(
                null,
                match.idEvent,
                match.strHomeTeam,
                match.strAwayTeam,
                match.intHomeScore,
                match.intAwayScore,
                match.strFilename,
                match.dateEvent,
                match.strTime
        )

        // load badge
        val idHomeTeam = match.idHomeTeam ?: ""
        val idAwayTeam = match.idAwayTeam ?: ""
        presenter.loadHomeTeamDetails(idHomeTeam)
        presenter.loadAwayTeamDetails(idAwayTeam)

        // date
        val date = match.dateEvent ?.let { MyDateFormat.dateEn(it) } ?: ""
        txtDate.text = date

        // time
        val time = match.strTime ?.let { MyDateFormat.time(it) } ?: ""
        txtTime.text = time

        // home score
        val homeScore = match.intHomeScore ?: ""
        txtHomeScore.text = homeScore

        // away score
        val awayScore = match.intAwayScore ?: ""
        txtAwayScore.text = awayScore

        // home team
        val homeTeam = match.strHomeTeam ?: ""
        txtHomeTeam.text = homeTeam

        // away team
        val awayTeam = match.strAwayTeam ?: ""
        txtAwayTeam.text = awayTeam

        // home formation
        val homeFormation = match.strHomeFormation ?: ""
        txtHomeFormation.text = homeFormation

        // away formation
        val awayFormation = match.strAwayFormation ?: ""
        txtAwayFormation.text = awayFormation

        // home goals
        val homeGoals = match.strHomeGoalDetails ?: ""
        txtHomeGoals.text = homeGoals

        // away goals
        val awayGoals = match.strAwayGoalDetails ?: ""
        txtAwayGoals.text = awayGoals

        // home shots
        val homeShots = match.intHomeShots ?: ""
        txtHomeShots.text = homeShots

        // away shots
        val awayShots = match.intAwayShots ?: ""
        txtAwayShots.text = awayShots

        // home goal keeper
        val homeGoalKeeper = match.strHomeLineupGoalkeeper ?: ""
        txtHomeGK.text = homeGoalKeeper

        // away goal keeper
        val awayGoalKeeper = match.strAwayLineupGoalkeeper ?: ""
        txtAwayGK.text = awayGoalKeeper

        // home defense
        val homeDefense = match.strHomeLineupDefense ?: ""
        txtHomeDefense.text = homeDefense

        // away defense
        val awayDefense = match.strAwayLineupDefense ?: ""
        txtAwayDefense.text = awayDefense

        // home midfield
        val homeMidfield = match.strHomeLineupMidfield ?: ""
        txtHomeMidfield.text = homeMidfield

        // away midfield
        val awayMidfield = match.strAwayLineupMidfield ?: ""
        txtAwayMidfield.text = awayMidfield

        // home forward
        val homeForward = match.strHomeLineupForward ?: ""
        txtHomeForward.text = homeForward

        // away forward
        val awayForward = match.strAwayLineupForward ?: ""
        txtAwayForward.text = awayForward

        // home substitutes
        val homeSubstitutes = match.strHomeLineupSubstitutes ?: ""
        txtHomeSubstitutes.text = homeSubstitutes

        // away substitutes
        val awaySubstitutes = match.strAwayLineupSubstitutes ?: ""
        txtAwaySubstitutes.text = awaySubstitutes
    }

    override fun setHomeTeamBadge(teamBadge: String) {
        Glide.with(this).load(teamBadge).into(imgHome)
    }

    override fun setAwayTeamBadge(teamBadge: String) {
        Glide.with(this).load(teamBadge).into(imgAway)
    }

    override fun showToastMsg(msg: String) {
        longToast(msg)
    }

    override fun setMatchFavoriteState(isFavorite: Boolean) {
        this.isFavorite = isFavorite
    }

    override fun onAddToFavoriteSuccess(msg: String) {
        snackbar(coordLayout, msg).show()
        isFavorite = true
        menuItem?.findItem(R.id.match_details_menu_favorite)?.setIcon(R.drawable.added_to_fav)
    }

    override fun onAddToFavoriteFailed(msg: String) {
        snackbar(coordLayout, msg).show()
    }

    override fun onRemoveFromFavoriteSuccess(msg: String) {
        snackbar(coordLayout, msg).show()
        isFavorite = false
        menuItem?.findItem(R.id.match_details_menu_favorite)?.setIcon(R.drawable.add_to_fav)
    }

    override fun onRemoveFromFavoriteFailed(msg: String) {
        snackbar(coordLayout, msg).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.match_details_menu, menu)
        menuItem = menu
        if (isFavorite) {
            menuItem?.findItem(R.id.match_details_menu_favorite)?.setIcon(R.drawable.added_to_fav)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.match_details_menu_favorite -> {
                if (isFavorite) {
                    if (idMatch.isNotEmpty()) presenter.removeFromFavorite(idMatch)
                } else {
                    matchEvent?.let {
                        presenter.addToFavorite(it)
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
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

    inner class MatchDetailsActivityUI: AnkoComponent<MatchDetailsActivity> {
        override fun createView(ui: AnkoContext<MatchDetailsActivity>) = with(ui){
            coordinatorLayout {
                id = R.id.match_detail_coordinator_layout

                scrollView {
                    isFillViewport = true

                    verticalLayout {

                        txtDate = textView {
                            id = R.id.match_details_txt_date
                            textSize = 14f
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                            textColorResource = R.color.textPrimary
                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(20)
                        }

                        txtTime = textView {
                            id = R.id.match_details_txt_time
                            textSize = 14f
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                            textColorResource = R.color.textPrimary
                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(5)
                        }

                        constraintLayout {
                            id = R.id.match_details_score_layout

                            textView {
                                id = R.id.match_details_txt_vs
                                textResource = R.string.match_details_vs
                                textSize = 12f
                                gravity = Gravity.CENTER
                                textColorResource = R.color.textContent

                            }.lparams(width = dip(20), height = 0) {
                                topToTop = PARENT_ID
                                bottomToBottom = PARENT_ID
                                startToStart = PARENT_ID
                                endToEnd = PARENT_ID
                                verticalBias = 0.5f
                                horizontalBias = 0.5f
                            }

                            txtHomeScore =  textView {
                                id = R.id.match_details_txt_home_score
                                textSize = 24f
                                gravity = Gravity.CENTER
                                textColorResource = R.color.textContent

                            }.lparams(width = dip(40), height = 0) {
                                topToTop = PARENT_ID
                                bottomToBottom = PARENT_ID
                                startToStart = PARENT_ID
                                endToStart = R.id.match_details_txt_vs
                                verticalBias = 0.5f
                                horizontalBias = 1.0f
                                marginEnd = dip(5)
                            }

                            txtAwayScore = textView {
                                id = R.id.match_details_txt_away_score
                                textSize = 24f
                                gravity = Gravity.CENTER
                                textColorResource = R.color.textContent

                            }.lparams(width = dip(40), height = 0) {
                                topToTop = PARENT_ID
                                bottomToBottom = PARENT_ID
                                startToEnd = R.id.match_details_txt_vs
                                endToEnd = PARENT_ID
                                verticalBias = 0.5f
                                horizontalBias = 0.0f
                                marginStart = dip(5)
                            }

                            imgHome = imageView {
                                id = R.id.match_details_img_home
                                backgroundColorResource = R.color.bgLightGray

                            }.lparams(width = dip(60), height = dip(60)) {
                                topToTop = PARENT_ID
                                bottomToBottom = PARENT_ID
                                startToStart = PARENT_ID
                                endToStart = R.id.match_details_txt_home_score
                                verticalBias = 0.5f
                                horizontalBias = 0.5f
                            }

                            imgAway = imageView {
                                id = R.id.match_details_img_away
                                backgroundColorResource = R.color.bgLightGray
                            }.lparams(width = dip(60), height = dip(60)) {
                                topToTop = PARENT_ID
                                bottomToBottom = PARENT_ID
                                startToEnd = R.id.match_details_txt_away_score
                                endToEnd = PARENT_ID
                                verticalBias = 0.5f
                                horizontalBias = 0.5f
                            }
                        }.lparams(width = matchParent, height = dip(80)) {
                            topMargin = dip(20)
                        }

                        linearLayout {
                            id = R.id.match_details_team_layout

                            txtHomeTeam =  textView {
                                id = R.id.match_details_txt_home_team
                                textSize = 16f
                                gravity = Gravity.CENTER
                                textColorResource = R.color.textPrimary
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                            textView {
                            }.lparams(width = dip(110), height = wrapContent) {
                            }

                            txtAwayTeam =  textView {
                                id = R.id.match_details_txt_away_team
                                textSize = 16f
                                gravity = Gravity.CENTER
                                textColorResource = R.color.textPrimary
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(10)
                        }

                        linearLayout {

                            txtHomeFormation =  textView {
                                id = R.id.match_details_txt_home_formation
                                textSize = 14f
                                gravity = Gravity.CENTER
                                textColorResource = R.color.textContent
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                            textView {
                            }.lparams(width = dip(110), height = wrapContent) {
                            }

                            txtAwayFormation =  textView {
                                id = R.id.match_details_txt_away_formation
                                textSize = 14f
                                gravity = Gravity.CENTER
                                textColorResource = R.color.textContent
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(3)
                        }

                        view {
                            backgroundColorResource = R.color.bgLineSeparator
                        }.lparams(width = matchParent, height = dip(1)) {
                            topMargin = dip(5)
                        }

                        linearLayout {

                            txtHomeGoals =  textView {
                                id = R.id.match_details_txt_home_goals
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_START
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                            textView {
                                textSize = 14f
                                textResource = R.string.match_details_goals
                                textColorResource = R.color.textPrimary
                                textAlignment = TEXT_ALIGNMENT_CENTER
                            }.lparams(width = dip(110), height = wrapContent) {
                            }

                            txtAwayGoals =  textView {
                                id = R.id.match_details_txt_away_goals
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_END
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(5)
                            horizontalPadding = dip(8)
                        }

                        linearLayout {

                            txtHomeShots =  textView {
                                id = R.id.match_details_txt_home_shots
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_START
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                            textView {
                                textSize = 14f
                                textResource = R.string.match_details_shots
                                textColorResource = R.color.textPrimary
                                textAlignment = TEXT_ALIGNMENT_CENTER
                            }.lparams(width = dip(110), height = wrapContent) {
                            }

                            txtAwayShots =  textView {
                                id = R.id.match_details_txt_away_shots
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_END
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(10)
                            horizontalPadding = dip(8)
                        }

                        view {
                            backgroundColorResource = R.color.bgLineSeparator
                        }.lparams(width = matchParent, height = dip(1)) {
                            topMargin = dip(5)
                        }

                        textView {
                            textResource = R.string.match_details_lineups
                            textSize = 16f
                            textAlignment = View.TEXT_ALIGNMENT_CENTER
                            textColorResource = R.color.textContent
                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(10)
                        }

                        linearLayout {

                            txtHomeGK =  textView {
                                id = R.id.match_details_txt_home_goal_keeper
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_START
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                            textView {
                                textSize = 14f
                                textResource = R.string.match_details_goal_keeper
                                textColorResource = R.color.textPrimary
                                textAlignment = TEXT_ALIGNMENT_CENTER
                            }.lparams(width = dip(110), height = wrapContent) {
                            }

                            txtAwayGK =  textView {
                                id = R.id.match_details_txt_away_goal_keeper
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_END
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(10)
                            horizontalPadding = dip(8)
                        }

                        linearLayout {

                            txtHomeDefense =  textView {
                                id = R.id.match_details_txt_home_defense
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_START
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                            textView {
                                textSize = 14f
                                textResource = R.string.match_details_defense
                                textColorResource = R.color.textPrimary
                                textAlignment = TEXT_ALIGNMENT_CENTER
                            }.lparams(width = dip(110), height = wrapContent) {
                            }

                            txtAwayDefense =  textView {
                                id = R.id.match_details_txt_away_defense
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_END
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(10)
                            horizontalPadding = dip(8)
                        }

                        linearLayout {

                            txtHomeMidfield =  textView {
                                id = R.id.match_details_txt_home_midfield
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_START
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                            textView {
                                textSize = 14f
                                textResource = R.string.match_details_midfield
                                textColorResource = R.color.textPrimary
                                textAlignment = TEXT_ALIGNMENT_CENTER
                            }.lparams(width = dip(110), height = wrapContent) {
                            }

                            txtAwayMidfield =  textView {
                                id = R.id.match_details_txt_away_midfield
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_END
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(10)
                            horizontalPadding = dip(8)
                        }

                        linearLayout {

                            txtHomeForward =  textView {
                                id = R.id.match_details_txt_home_forward
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_START
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                            textView {
                                textSize = 14f
                                textResource = R.string.match_details_forward
                                textColorResource = R.color.textPrimary
                                textAlignment = TEXT_ALIGNMENT_CENTER
                            }.lparams(width = dip(110), height = wrapContent) {
                            }

                            txtAwayForward =  textView {
                                id = R.id.match_details_txt_away_forward
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_END
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(10)
                            horizontalPadding = dip(8)
                        }

                        linearLayout {

                            txtHomeSubstitutes =  textView {
                                id = R.id.match_details_txt_home_substitutes
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_START
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                            textView {
                                textSize = 14f
                                textResource = R.string.match_details_substitutes
                                textColorResource = R.color.textPrimary
                                textAlignment = TEXT_ALIGNMENT_CENTER
                            }.lparams(width = dip(110), height = wrapContent) {
                            }

                            txtAwaySubstitutes =  textView {
                                id = R.id.match_details_txt_away_substitutes
                                textSize = 14f
                                textColorResource = R.color.textContent
                                textAlignment = TEXT_ALIGNMENT_TEXT_END
                            }.lparams(width = 0, height = wrapContent, weight = 1f) {

                            }

                        }.lparams(width = matchParent, height = wrapContent) {
                            topMargin = dip(10)
                            bottomMargin = dip(30)
                            horizontalPadding = dip(8)
                        }

                    }.lparams(width = matchParent, height = wrapContent)

                }.lparams(width = matchParent, height = matchParent)

                progressBar = themedProgressBar(theme = R.style.ProgressPrimary) {
                    gone()
                }.lparams(width = dip(60), height = dip(60)) {
                    gravity = Gravity.CENTER
                }
            }
        }
    }

    companion object {
        const val INTENT_ID_MATCH = "idMatch"
    }
}
