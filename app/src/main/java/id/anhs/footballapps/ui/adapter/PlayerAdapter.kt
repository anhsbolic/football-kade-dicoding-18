package id.anhs.footballapps.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import id.anhs.footballapps.R
import id.anhs.footballapps.model.Player
import kotlinx.android.synthetic.main.adapter_player.view.*

class PlayerAdapter(private val players: List<Player>,
                  private val onItemClickListener: (Player) -> Unit)
    : RecyclerView.Adapter<PlayerAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.adapter_player, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(mContext, players[position], onItemClickListener)
    }

    override fun getItemCount(): Int = players.size

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        private val imgAvatar: ImageView = itemView.adapter_player_img_avatar
        private val txtName: TextView = itemView.adapter_player_txt_name
        private val txtPosition: TextView = itemView.adapter_player_txt_position

        fun bindItem(mContext: Context, player: Player, onItemClickListener: (Player) -> Unit) {
            // avatar
            val avatar = player.strCutout ?: ""
            if (avatar.isNotEmpty()) Glide.with(mContext).load(avatar).into(imgAvatar)

            // name
            val name = player.strPlayer ?: ""
            txtName.text = name

            // position
            val position = player.strPosition ?: ""
            txtPosition.text = position

            // on item click listener
            itemView.setOnClickListener { onItemClickListener(player) }
        }
    }
}