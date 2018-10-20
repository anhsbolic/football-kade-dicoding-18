package id.anhs.footballapps.ui.activity.playerdetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import id.anhs.footballapps.R
import id.anhs.footballapps.model.Player
import kotlinx.android.synthetic.main.activity_player_details.*

class PlayerDetailsActivity : AppCompatActivity() {

    private lateinit var player: Player

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player_details)
        setTitle(R.string.player_details_page_title)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // get intent data
        if (intent.hasExtra(INTENT_PLAYER)) {
            player = intent.getParcelableExtra(INTENT_PLAYER)
            setPlayerDetails(player)
        }
    }

    private fun setPlayerDetails(player: Player) {
        // title
        val name = player.strPlayer ?: ""
        title = name

        // fan art
        val fanArt = player.strFanart1 ?: ""
        if (fanArt.isNotEmpty()) Glide.with(this).load(fanArt).into(player_details_img_fan_art)

        // weight
        val weight = player.strWeight ?: ""
        player_details_txt_weight.text = weight

        // height
        val height = player.strHeight?: ""
        player_details_txt_height.text = height

        // position
        val position = player.strPosition ?: ""
        player_details_txt_position.text = position

        // overview
        val overview = player.strDescriptionEN ?: ""
        player_details_txt_overview.text = overview
    }

    companion object {
        const val INTENT_PLAYER = "player"
    }
}
