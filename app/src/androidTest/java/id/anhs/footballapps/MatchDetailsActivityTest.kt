package id.anhs.footballapps

import android.content.Intent
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import id.anhs.footballapps.ui.activity.matchdetails.MatchDetailsActivity
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.containsString
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MatchDetailsActivityTest {

    @Rule
    @JvmField
    val activityTestRule: ActivityTestRule<MatchDetailsActivity> =
            object : ActivityTestRule<MatchDetailsActivity>(MatchDetailsActivity::class.java) {
                override fun getActivityIntent(): Intent {
                    val intent = Intent()
                    intent.putExtra(MatchDetailsActivity.INTENT_ID_MATCH, "582125")
                    return intent
                }
            }

    @Test
    fun testMatchDetailsBehaviour() {
        onView(withId(R.id.match_detail_coordinator_layout)).check(matches(isDisplayed()))

        onView(withId(R.id.match_details_txt_date)).check(matches(isDisplayed()))
        onView(withId(R.id.match_details_txt_home_score)).check(matches(isDisplayed()))
        onView(withId(R.id.match_details_txt_away_score)).check(matches(isDisplayed()))
        onView(withId(R.id.match_details_txt_home_team)).check(matches(isDisplayed()))
        onView(withId(R.id.match_details_txt_away_team)).check(matches(isDisplayed()))

        onView(withId(R.id.match_details_menu_favorite)).check(matches(isDisplayed()))
        Thread.sleep(500)
        onView(withId(R.id.match_details_menu_favorite)).perform(click())
        Thread.sleep(1000)
        onView(allOf(
                withId(R.id.snackbar_text),
                withText(containsString("Added to Favorites"))))
        Thread.sleep(2000)
        onView(withId(R.id.match_details_menu_favorite)).perform(click())
        Thread.sleep(1000)
        onView(allOf(
                withId(R.id.snackbar_text),
                withText(containsString("Removed from favorite"))))
    }
}