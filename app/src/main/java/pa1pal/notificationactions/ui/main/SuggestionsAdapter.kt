package pa1pal.notificationactions.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.ConversationAction
import android.view.textclassifier.ConversationActions
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.suggestion_view.view.*
import pa1pal.notificationactions.R

class SuggestionsAdapter(
    private val conversationActions: ConversationActions,
    val context: Context
) :
    RecyclerView.Adapter<SuggestionsAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.suggestion_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        if (conversationActions.conversationActions.size == 0){
            Toast.makeText(context, "No action found", Toast.LENGTH_SHORT).show()
        }
        return conversationActions.conversationActions.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(conversationActions.conversationActions[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(conversationAction: ConversationAction) {
            if (!conversationAction.textReply.isNullOrEmpty()) {
                itemView.suggestionName.text = conversationAction.textReply
            } else {
                itemView.suggestionName.text = conversationAction.type
            }
        }
    }
}