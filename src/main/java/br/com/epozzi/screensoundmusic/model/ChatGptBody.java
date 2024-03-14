package br.com.epozzi.screensoundmusic.model;

import java.util.List;

public class ChatGptBody {
    private String model;
    private List<ChatGptMessage> messages;
    private Double temperature;

    public ChatGptBody(List<ChatGptMessage> messages) {
        this.model = "gpt-3.5-turbo";
        this.temperature = 0.7;
        this.messages = messages;
    }
}
