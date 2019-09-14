package pa1pal.notificationactions.ui.main

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.textclassifier.ConversationActions
import android.view.textclassifier.TextClassificationManager
import android.view.textclassifier.TextClassifier
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
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

    private val CHANNEL_ID: String? = "test"
    private val notificationTypesPair: MutableList<Pair<String, String>> = Data.getData()
    private lateinit var notificationTypesAdapter: ItemTypesAdapter
    lateinit var textClassifier: TextClassifier
    lateinit var request: ConversationActions.Request
    lateinit var textClassificationManager: TextClassificationManager
    lateinit var notificationManager: NotificationManager
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        notificationManager =
            activity!!.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        GlobalScope.launch {
            textClassificationManager =
                activity!!.getSystemService(Context.TEXT_CLASSIFICATION_SERVICE) as TextClassificationManager
            textClassifier = textClassificationManager.textClassifier
        }
        request = makeRequest(" ")
        makeRequest("")
        generateReplies()

        notificationTypesAdapter = ItemTypesAdapter(notificationTypesPair) {
            Toast.makeText(context, "abksfgd", Toast.LENGTH_SHORT).show()
            makeRequest(it.second)
            displayNotification(it)
        }
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    private fun displayNotification(pair: Pair<String, String>) {
        // Get the layouts to use in the custom notification
        val notificationLayout = RemoteViews(getPackageName(), R.layout.notification)
        notificationLayout.setTextViewText(R.id.notification_title, pair.first)
        notificationLayout.setTextViewText(R.id.notificationDetailTxtVw, pair.second)

        createNotificationChannel()

        val customNotification = this.context?.let {
            NotificationCompat.Builder(it, CHANNEL_ID.toString())
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setStyle(NotificationCompat.DecoratedCustomViewStyle())
                .setContentTitle(pair.first)
                .setContentText(pair.second)
                .build()
        }

        notificationManager.notify(1, customNotification)
    }

    private fun getPackageName() = "pa1pal.notificationactions"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        recyclerview.adapter = notificationTypesAdapter
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
//                ConversationActions.Message.Builder(
//                    ConversationActions.Message
//                        .PERSON_USER_SELF
//                )
//                    .setText("Dear Mr Pal -Your IndiGo PNR is LWMDJR- 6E 2929 05Nov DEL(T2)-BLR(T1), 0755-1045 hrs. Web check-in now by clicking here - http://I9f.in/7kI9d5433j")
//                    .build(),
//                ConversationActions.Message.Builder(
//                    ConversationActions.Message
//                        .PERSON_USER_OTHERS
//                )
//                    .setText("Hi Pawan,Your visit has been scheduled on 2019-07-03 at 08:00 PM for property OYO LIFE BLR1035 Koramangala.The address for the property is 226 5th Block, KHB Colony, Koramangala.Map location: http://bit.ly/2PG4H1r.Property link: https://www.oyolife.in/bangalore/40553-oyo-life-blr1035-koramangala.Please reach out to - Harish Sr 7483261279, our Stay Supervisor for any inquiries and a seamless show-around")
//                    .build()
//                ,

                ConversationActions.Message.Builder(
                    ConversationActions.Message
                        .PERSON_USER_OTHERS
                )
                    .setText("[Reminder] You are waitlisted for HRX Workout class at 6:30PM. Your waitlist number is currently 4. We will notify you as soon as your waitlist is confirmed. In case you change your mind, you can leave the waitlist queue anytime.\n")
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            notificationManager.createNotificationChannel(channel)
        }
    }
}
