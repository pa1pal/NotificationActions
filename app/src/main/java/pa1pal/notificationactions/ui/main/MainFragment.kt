package pa1pal.notificationactions.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.ConversationActions
import android.view.textclassifier.TextClassificationManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pa1pal.notificationactions.R

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val notificationTypesPair: MutableList<Pair<String, String>> = Data.getData()
    private lateinit var notificationTypesAdapter: ItemTypesAdapter
    private lateinit var textClassificationManager: TextClassificationManager
    private var conversationActions: ConversationActions? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        GlobalScope.launch {
            textClassificationManager =
                activity!!.getSystemService(Context.TEXT_CLASSIFICATION_SERVICE) as TextClassificationManager
        }
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        notificationTypesAdapter = ItemTypesAdapter(notificationTypesPair, conversationActions
            , -1
            , textClassificationManager)

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = notificationTypesAdapter
    }
}