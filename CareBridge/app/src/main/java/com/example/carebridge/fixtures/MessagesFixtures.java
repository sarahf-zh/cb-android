package com.example.carebridge.fixtures;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public final class MessagesFixtures extends FixturesData {
    private MessagesFixtures() {
        throw new AssertionError();
    }

    public static FixtureMessage getImageMessage(Boolean isBot, String imgurl) {
        FixtureMessage message = new FixtureMessage(getRandomId(), getUser(isBot), null);
        message.setImage(new FixtureMessage.Image(imgurl));
        return message;
    }

    public static FixtureMessage getLoadingImageMessage(String id, Boolean isBot, String imgurl) {
        FixtureMessage message = new FixtureMessage(id, getUser(isBot), null);
        message.setImage(new FixtureMessage.Image(imgurl));
        return message;
    }

    public static FixtureMessage getReportMessage(String reportId, Boolean isBot, String text) {
        return new FixtureMessage(reportId, getUser(isBot), text);
    }

    public static FixtureMessage getVoiceMessage(Boolean isBot) {
        FixtureMessage message = new FixtureMessage(getRandomId(), getUser(isBot), null);
        message.setVoice(new FixtureMessage.Voice("http://example.com", rnd.nextInt(200) + 30));
        return message;
    }

    public static FixtureMessage getTextMessage() {
        return getTextMessage(getRandomMessage(), true);
    }

    public static FixtureMessage getTextMessage(String text, Boolean isBot) {
        return new FixtureMessage(getRandomId(), getUser(isBot), text);
    }

    public static ArrayList<FixtureMessage> getMessages(Date startDate) {
        ArrayList<FixtureMessage> messages = new ArrayList<>();
        for (int i = 0; i < 10/*days count*/; i++) {
            int countPerDay = rnd.nextInt(5) + 1;

            for (int j = 0; j < countPerDay; j++) {
                FixtureMessage message;
                if (i % 2 == 0 && j % 3 == 0) {
                    message = getImageMessage(false, "");
                } else {
                    message = getTextMessage();
                }

                Calendar calendar = Calendar.getInstance();
                if (startDate != null) calendar.setTime(startDate);
                calendar.add(Calendar.DAY_OF_MONTH, -(i * i + 1));

                message.setCreatedAt(calendar.getTime());
                messages.add(message);
            }
        }
        return messages;
    }

    private static FixtureUser getUser(Boolean isBot) {
        return new FixtureUser(
                !isBot ? "0" : "1",
                !isBot ? names.get(0) : names.get(1),
                !isBot ? avatars.get(0) : avatars.get(1),
                true);
    }
}
