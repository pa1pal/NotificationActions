package pa1pal.notificationactions.ui.main

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.ConversationActions
import android.view.textclassifier.TextClassificationManager
import android.view.textclassifier.TextClassifier
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import pa1pal.notificationactions.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    //    lateinit var conversationActions: ConversationActions
    lateinit var textClassifier: TextClassifier
    lateinit var request: ConversationActions.Request
    lateinit var textClassificationManager: TextClassificationManager
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        GlobalScope.launch {
            textClassificationManager =
                activity!!.getSystemService(Context.TEXT_CLASSIFICATION_SERVICE) as TextClassificationManager
            textClassifier = textClassificationManager.textClassifier
        }
        request = makeRequest(" ")
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private fun generateReplies() {
        GlobalScope.launch(Dispatchers.Main) {
            val conversationActions = withContext(Dispatchers.IO) {
                textClassifier.suggestConversationActions(request)
            }
            showReplies(conversationActions)
            Log.d("ca size", conversationActions.conversationActions.size.toString())
        }
    }

    private fun makeRequest(p0: String): ConversationActions.Request {
        return ConversationActions.Request.Builder(
            listOf(
                ConversationActions.Message.Builder(
                    ConversationActions.Message
                        .PERSON_USER_SELF
                )
                    .setText(p0)
                    .build()
            )
        )
            .build()
    }

    private fun showReplies(conversationActions: ConversationActions) {
        message.text = ""
        conversationActions.conversationActions.forEach {
            message.text = message.text.toString().plus("_").plus(it.textReply)
            Log.d("ca type", it.type)
            Log.d("ca type", it.textReply.toString())
            Log.d("ca score", it.confidenceScore.toString())
            Log.d("ca action", it.action.toString())
        }
    }
}
