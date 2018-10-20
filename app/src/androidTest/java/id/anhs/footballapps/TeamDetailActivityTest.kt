package id.anhs.footballapps

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import id.anhs.footballapps.model.Team
import id.anhs.footballapps.ui.activity.teamdetails.TeamDetailsActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TeamDetailActivityTest {

    private val team = Team(
            null,
            "133604",
            "Arsenal",
            "https://www.thesportsdb.com/images/media/team/badge/vrtrtp1448813175.png",
            "1892",
            "Emirates Stadium",
            "Arsenal Football Club is a professional football club based in Holloway, " +
                    "London which currently plays in the Premier League, the highest level of English " +
                    "football. One of the most successful clubs in English football, they have won 13 " +
                    "First Division and Premier League titles and a joint record 11 FA Cups." +
                    "\r\n\r\nArsenal's success has been particularly consistent: the club has " +
                    "accumulated the second most points in English top-flight football, hold the " +
                    "ongoing record for the longest uninterrupted period in the top flight, and " +
                    "would be placed first in an aggregated league of the entire 20th century. " +
                    "Arsenal is the second side to complete an English top-flight season unbeaten " +
                    "(in the 2003\u201304 season), playing almost twice as many matches as the " +
                    "previous invincibles Preston North End in the 1888\u201389 season." +
                    "\r\n\r\nArsenal was founded in 1886 in Woolwich and in 1893 became the first " +
                    "club from the south of England to join the Football League. In 1913, they" +
                    " moved north across the city to Arsenal Stadium in Highbury. In the 1930s, " +
                    "they won five League Championship titles and two FA Cups. After a lean period " +
                    "in the post-war years they won the League and FA Cup Double, in the 1970\u201371 " +
                    "season, and in the 1990s and first decade of the 21st century, won two more " +
                    "Doubles and reached the 2006 UEFA Champions League Final. Since neighbouring " +
                    "Tottenham Hotspur, the two clubs have had a fierce rivalry, the North London derby." +
                    "\r\n\r\nArsenal have one of the highest incomes and largest fanbases in the world. " +
                    "The club was named the fifth most valuable association football club in the world, " +
                    "valued at \u00a31.3 billion in 2014."
    )

    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<TeamDetailsActivity> =
            object : ActivityTestRule<TeamDetailsActivity>(TeamDetailsActivity::class.java) {
                override fun getActivityIntent(): Intent {
                    val intent = Intent()
                    intent.putExtra(TeamDetailsActivity.INTENT_TEAM, team)
                    return intent
                }
            }

    @Test
    fun testTeamDetailsBehaviour() {
        onView(withId(R.id.team_details_coordinator_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.team_details_txt_formed_year)).check(matches(isDisplayed()))
        onView(withId(R.id.team_details_txt_formed_year)).check(matches(withText(team.intFormedYear!!)))

        onView(withId(R.id.team_details_txt_stadium)).check(matches(isDisplayed()))
        onView(withId(R.id.team_details_txt_stadium)).check(matches(withText(team.strStadium!!)))

        onView(withId(R.id.team_details_view_pager)).check(matches(isDisplayed()))
        onView(withId(R.id.team_overview_txt_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.team_overview_txt_overview)).check(matches(withText(team.strDescriptionEN!!)))

        onView(withId(R.id.team_details_menu_favorite)).check(matches(isDisplayed()))
        Thread.sleep(500)
        onView(withId(R.id.team_details_menu_favorite)).perform(click())
        Thread.sleep(1000)
        onView(allOf(
                withId(R.id.snackbar_text),
                withText(containsString("Added to Favorites"))))
        Thread.sleep(2000)
        onView(withId(R.id.team_details_menu_favorite)).perform(click())
        Thread.sleep(1000)
        onView(allOf(
                withId(R.id.snackbar_text),
                withText(containsString("Removed from favorite"))))
    }
}