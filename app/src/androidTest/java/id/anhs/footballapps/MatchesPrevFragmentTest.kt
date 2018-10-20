package id.anhs.footballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import id.anhs.footballapps.ui.activity.dashboard.DashboardActivity
import id.anhs.footballapps.ui.activity.matchdetails.MatchDetailsActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchesPrevFragmentTest {

    @Rule
    @JvmField
    val mainRule = IntentsTestRule(DashboardActivity::class.java)

    @Test
    fun testRvPrevMatchBehaviour() {
        onView(withId(R.id.dashboard_bot_nav_matches)).perform(click())
        onView(withId(R.id.matches_home_linear_main_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.matches_home_viewpager)).check(matches(isDisplayed()))
        onView(withId(R.id.matches_home_viewpager)).perform(swipeLeft())
        onView(withId(R.id.prev_matches_rv_matches))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        onView(withId(R.id.prev_matches_rv_matches))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(5))
        onView(withId(R.id.prev_matches_rv_matches))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(5, click()))
        intended(IntentMatchers.hasComponent(MatchDetailsActivity::class.java.name))
    }
}