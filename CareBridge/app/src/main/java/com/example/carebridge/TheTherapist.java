package com.example.carebridge;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.carebridge.fixtures.MessagesFixtures;
import com.example.carebridge.helper.SendMessageInBg;
import com.example.carebridge.helper.ChatAdapter;
import com.example.carebridge.helper.BotReply;
import com.example.carebridge.helper.ChatMessage;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.stfalcon.chatkit.messages.MessageInput;
import com.example.carebridge.fixtures.FixtureMessage;

import com.google.api.gax.core.FixedCredentialsProvider;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.dialogflow.v2.DetectIntentResponse;
import com.google.cloud.dialogflow.v2.QueryInput;
import com.google.cloud.dialogflow.v2.SessionName;
import com.google.cloud.dialogflow.v2.SessionsClient;
import com.google.cloud.dialogflow.v2.SessionsSettings;
import com.google.cloud.dialogflow.v2.TextInput;
import com.google.common.collect.Lists;
import com.stfalcon.chatkit.messages.MessagesList;
import com.stfalcon.chatkit.messages.MessagesListAdapter;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

public class TheTherapist extends BaseMessageListActivity
        implements MessageInput.InputListener,
        MessageInput.AttachmentsListener,
        MessageInput.TypingListener,
        BotReply {
    RecyclerView chatView0;
    ChatAdapter chatAdapter0;
    List<ChatMessage> messageList0 = new ArrayList<>();
    EditText editMessage0;
    ImageButton btnSend0;

    private LinearLayout mbottomLinearLayout;
    private BottomSheetBehavior sheetBehavior;
    private MessageInput input;
    private TextView mbotlastseentxt;
    private ImageButton mbackBtn;
    private ImageButton mmoreBtn;
    private CircleImageView mprofilepic;
    private MessagesList messagesList;

    //dialogFlow
    private SessionsClient sessionsClient;
    private SessionName sessionName;
    private final String uuid = UUID.randomUUID().toString();
    private final String TAG = "thetherapist1";

    public static void open(Context context) {
        context.startActivity(new Intent(context, TheTherapist.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //initViews0();
        //setupAdapter0();
        initViews();
        setupAdapter();
        setUpBot();
    }

    private void initViews() {
        setContentView(R.layout.activity_default_messages);
        mbotlastseentxt = (TextView) findViewById(R.id.botlastseentxt);
        mbackBtn = (ImageButton) findViewById(R.id.backButton);
        mmoreBtn = (ImageButton) findViewById(R.id.moreBtn);
        mprofilepic = (CircleImageView) findViewById(R.id.profilepic);

        mbottomLinearLayout = (LinearLayout) findViewById(R.id.bottomLinearLayout);
        // init the bottom sheet behavior
        sheetBehavior = BottomSheetBehavior.from(mbottomLinearLayout);
        messagesList = (MessagesList) findViewById(R.id.messagesList);

        input = (MessageInput) findViewById(R.id.input);
        input.setInputListener(this);
        input.setTypingListener(this);
        input.setAttachmentsListener(this);

        mbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mmoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "More button is clicked!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setupAdapter() {
        super.messagesAdapter = new MessagesListAdapter<>(super.senderId, super.imageLoader);
        super.messagesAdapter.enableSelectionMode(this);
        super.messagesAdapter.setLoadMoreListener(this);
        super.messagesAdapter.registerViewClickListener(R.id.messageText,
                new MessagesListAdapter.OnMessageViewClickListener<FixtureMessage>() {
                    @Override
                    public void onMessageViewClick(View view, FixtureMessage message) {
                        Toast.makeText(TheTherapist.this, message.getId() + " avatar click", Toast.LENGTH_SHORT).show();
                    }
                });
        this.messagesList.setAdapter(super.messagesAdapter);

        mbotlastseentxt.setTypeface(null, Typeface.BOLD_ITALIC);
        mbotlastseentxt.setText("Typing...");
    }

    private void updateViews(String text, boolean isBot, boolean BotOnline) {
        messagesAdapter.addToStart(MessagesFixtures.getTextMessage(text, isBot), true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        mbotlastseentxt.setTypeface(null, Typeface.NORMAL);
        mbotlastseentxt.setText(BotOnline ? "Online" : "Offline");
    }

    private void initViews0() {
        setContentView(R.layout.activity_the_therapist);
        chatView0 = findViewById(R.id.chatView);
        editMessage0 = findViewById(R.id.editMessage);
        btnSend0 = findViewById(R.id.btnSend);
    }

    private void setupAdapter0() {
        chatAdapter0 = new ChatAdapter(messageList0, this);
        chatView0.setAdapter(chatAdapter0);

        btnSend0.setOnClickListener(view -> {
            String chatInput = editMessage0.getText().toString();
            if (!chatInput.isEmpty()) {
                messageList0.add(new ChatMessage(chatInput, true));
                editMessage0.setText("");
                sendMessageToBot(chatInput);
                Objects.requireNonNull(chatView0.getAdapter()).notifyDataSetChanged();
                Objects.requireNonNull(chatView0.getLayoutManager())
                        .scrollToPosition(messageList0.size() - 1);
            } else {
                Toast.makeText(TheTherapist.this, "Please enter text!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateAdapter0(String botReply) {
        messageList0.add(new ChatMessage(botReply, true));
        chatAdapter0.notifyDataSetChanged();
        Objects.requireNonNull(chatView0.getLayoutManager()).scrollToPosition(messageList0.size() - 1);
    }

    private void setUpBot() {
        try {
            InputStream stream = this.getResources().openRawResource(R.raw.credentials1);
            GoogleCredentials credentials = GoogleCredentials.fromStream(stream)
                    .createScoped(Lists.newArrayList("https://www.googleapis.com/auth/cloud-platform"));
            String projectId = ((ServiceAccountCredentials) credentials).getProjectId();

            SessionsSettings.Builder settingsBuilder = SessionsSettings.newBuilder();
            SessionsSettings sessionsSettings = settingsBuilder.setCredentialsProvider(
                    FixedCredentialsProvider.create(credentials)).build();
            sessionsClient = SessionsClient.create(sessionsSettings);
            sessionName = SessionName.of(projectId, uuid);

            Log.d(TAG, "projectId : " + projectId);
        } catch (Exception e) {
            Log.d(TAG, "setUpBot: " + e.getMessage());
        }
    }

    private void sendMessageToBot(String message) {
        QueryInput input = QueryInput.newBuilder()
                .setText(TextInput.newBuilder().setText(message).setLanguageCode("en-US")).build();
        new SendMessageInBg(this, sessionName, sessionsClient, input).execute();
    }

    @Override
    public void callback(DetectIntentResponse returnResponse) {
        String botReply = "";
        boolean botOnline = true;
        if (returnResponse != null) {
            botReply = returnResponse.getQueryResult().getFulfillmentText();
            if (!botReply.isEmpty()) {
                //updateAdapter0(botReply);
            } else {
                botOnline = false;
                botReply = "Got empty response";
                //Toast.makeText(this, "Got empty response", Toast.LENGTH_SHORT).show();
            }
        } else {
            botOnline = false;
            botReply = "Unable to connect";
            //Toast.makeText(this, "Unable to connect", Toast.LENGTH_SHORT).show();
        }

        updateViews(botReply, true, botOnline);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
        Toast.makeText(this, "Pointer Captured!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAddAttachments() {
        super.messagesAdapter.addToStart(
                MessagesFixtures.getImageMessage(true, ""), true);
    }

    private void showTextInput() {
        input.setVisibility(View.VISIBLE);
        //Show Bottom View
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    @Override
    public boolean onSubmit(CharSequence input) {
        //Hide keyboard after pressing submit
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
        }
        sheetBehavior.setHideable(true);
        sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        mbotlastseentxt.setTypeface(null, Typeface.BOLD_ITALIC);
        mbotlastseentxt.setText("Typing...");

        String inputMsg= input.toString();
        super.messagesAdapter.addToStart(MessagesFixtures.getTextMessage(inputMsg, false), true);

        //Send response
        sendMessageToBot(inputMsg);
        return true;
    }

    @Override
    public void onStartTyping() {
        Log.v("Typing listener", getString(R.string.start_typing_status));
    }

    @Override
    public void onStopTyping() {
        Log.v("Typing listener", getString(R.string.stop_typing_status));
    }
}