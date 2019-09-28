package pa1pal.notificationactions.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.ConversationActions
import android.view.textclassifier.TextClassificationManager
import android.view.textclassifier.TextClassifier
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_view.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pa1pal.notificationactions.R

class ItemTypesAdapter(
    private val notificationTypesPair: MutableList<Pair<String, String>>,
    private var ca: ConversationActions?,
    private var actionPosition: Int,
    textClassificationManager: TextClassificationManager
) :
    RecyclerView.Adapter<ItemTypesAdapter.ViewHolder>() {

    private val viewPool = RecyclerView.RecycledViewPool()
    private var textClassifier: TextClassifier = textClassificationManager.textClassifier
    var request: ConversationActions.Request

    init {
        request = makeRequest(" ")
    }

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
        val childLayoutManager = LinearLayoutManager(holder.itemView.suggestionRecyclerView.context,
            LinearLayoutManager.HORIZONTAL, false)

        if (actionPosition == position){
            holder.itemView.suggestionRecyclerView.visibility = View.VISIBLE
        } else {
            holder.itemView.suggestionRecyclerView.visibility = View.GONE
        }
        holder.itemView.suggestionRecyclerView.apply {
            layoutManager = childLayoutManager
            setRecycledViewPool(viewPool)
        }

        holder.bind(notificationTypesPair[position], ca, position)
    }

    /**
     * Generate replies for the generated request using on device machine learning model
     */
    private fun generateReplies() {
        GlobalScope.launch(Dispatchers.Main) {
            val conversationActions = withContext(Dispatchers.IO) {
                textClassifier.suggestConversationActions(request)
            }
            showReplies(conversationActions)
        }
    }

    /**
     * show all the available conversation action on view
     */
    private fun showReplies(conversationActions: ConversationActions) {
        ca = conversationActions
        notifyDataSetChanged()
    }

    /**
     * make request
     */
    private fun makeRequest(text: String): ConversationActions.Request {
        return ConversationActions.Request.Builder(
            listOf(
                ConversationActions.Message.Builder(
                    ConversationActions.Message
                        .PERSON_USER_OTHERS)
                    .setText(text)
                    .build()
            )).build()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(
            pair: Pair<String, String>,
            conversationActions: ConversationActions?,
            position: Int) {
            itemView.actionTxtVw.text = pair.first
            itemView.requestTxtVw.text = pair.second
            itemView.suggestionRecyclerView.apply {
                if (conversationActions != null) {
                    adapter = SuggestionsAdapter(conversationActions, context)
                }
            }
            itemView.setOnClickListener {
                request = makeRequest(pair.second)
                generateReplies()
                actionPosition = position
            }
        }
    }
}