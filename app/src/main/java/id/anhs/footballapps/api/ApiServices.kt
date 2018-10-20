package id.anhs.footballapps.api

import id.anhs.footballapps.BuildConfig

object ApiServices {

    fun getTheSportDBApiServices(): TheSportDBApiServices {
        return RetrofitClient
                .getClient(BuildConfig.TSDB_BASE_URL)
                .create(TheSportDBApiServices::class.java)
    }
}