package id.anhs.footballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.anhs.footballapps.ui.activity.dashboard.DashboardActivity
import id.anhs.footballapps.ui.activity.teamdetails.TeamDetailsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TeamsHomeFragmentTest {

    @Rule
    @JvmField
    val mainRule = IntentsTestRule(DashboardActivity::class.java)

    @Test
    fun testRvTeamsBehaviour() {
        onView(ViewMatchers.withId(R.id.dashboard_bot_nav_teams)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.teams_home_coordinator_main_layout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.teams_home_rv_matches))
                .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(ViewMatchers.withId(R.id.teams_home_rv_matches))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(4))
        onView(ViewMatchers.withId(R.id.teams_home_rv_matches))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(4, ViewActions.click()))
        Intents.intended(IntentMatchers.hasComponent(TeamDetailsActivity::class.java.name))
    }
}