package id.anhs.footballapps.ui.activity.splash

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import id.anhs.footballapps.R
import id.anhs.footballapps.ui.activity.dashboard.DashboardActivity
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity<DashboardActivity>()
            finish()
        }, 1800)
    }
}
