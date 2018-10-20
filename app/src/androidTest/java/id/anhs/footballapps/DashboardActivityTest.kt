package id.anhs.footballapps

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isClickable
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.runner.AndroidJUnit4
import id.anhs.footballapps.R.id.*
import id.anhs.footballapps.ui.activity.dashboard.DashboardActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DashboardActivityTest {

    @Rule
    @JvmField
    val mainRule = IntentsTestRule(DashboardActivity::class.java)

    @Test
    fun testOnClickBottomNavigation() {
        onView(withId(dashboard_bot_nav_matches)).check(matches(isClickable()))
        onView(withId(dashboard_bot_nav_teams)).check(matches(isClickable()))
        onView(withId(dashboard_bot_nav_favorites)).check(matches(isClickable()))
    }

    @Test
    fun testIsMatchesPageDisplayed() {
        onView(withId(dashboard_bot_nav_matches)).perform(click())
        onView(withId(matches_home_linear_main_layout)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testIsTeamsPageDisplayed() {
        onView(withId(dashboard_bot_nav_teams)).perform(click())
        onView(withId(teams_home_coordinator_main_layout)).check(matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testIsFavoritesPageDisplayed() {
        onView(withId(dashboard_bot_nav_favorites)).perform(click())
        onView(withId(favorite_home_linear_main_layout)).check(matches(ViewMatchers.isDisplayed()))
    }
}