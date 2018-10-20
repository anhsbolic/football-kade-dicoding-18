package id.anhs.footballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.action.ViewActions.swipeLeft
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.runner.AndroidJUnit4
import id.anhs.footballapps.ui.activity.dashboard.DashboardActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FavoritesTeamsFragmentTest {

    @Rule
    @JvmField
    val mainRule = IntentsTestRule(DashboardActivity::class.java)

    @Test
    fun testRvFavTeamBehaviour() {
        onView(ViewMatchers.withId(R.id.dashboard_bot_nav_favorites)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.favorite_home_linear_main_layout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.favorite_home_viewpager)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.favorite_home_viewpager)).perform(swipeLeft())
        onView(ViewMatchers.withId(R.id.favorite_team_rv_teams))
    }
}