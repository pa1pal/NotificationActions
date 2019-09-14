package pa1pal.notificationactions.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*
import pa1pal.notificationactions.R

class ItemTypesAdapter(private val notificationTypesPair: MutableList<Pair<String, String>>,
                       private var clickListener: (Pair<String, String>) -> Unit
) :
    RecyclerView.Adapter<ItemTypesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return notificationTypesPair.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(notificationTypesPair[position], clickListener)
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pair: Pair<String, String>, clickListener: (Pair<String, String>) -> Unit) {
            itemView.actionTxtVw.text = pair.first
            itemView.requestTxtVw.text = pair.second
            itemView.setOnClickListener { clickListener(pair) }
        }

    }
}