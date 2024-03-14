package br.com.epozzi.screensoundmusic.service;

import br.com.epozzi.screensoundmusic.model.ChatGptBody;
import br.com.epozzi.screensoundmusic.model.ChatGptMessage;
import br.com.epozzi.screensoundmusic.model.RespostaGpt;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class ConsultaChatGpt {
    private ConsumoApi consumoApi = new ConsumoApi();
    private Gson gson = new Gson();
    private final String ENDERECOGPT = "https://api.openai.com/v1/chat/completions";
    public String buscaDadosArtista(String nomeArtista) {
        List<ChatGptMessage> messageList = new ArrayList<>();
        ChatGptMessage message = new ChatGptMessage(nomeArtista);
        messageList.add(message);

        String body = gson.toJson(new ChatGptBody(messageList));

        var json = consumoApi.obterDadosJsonBody(ENDERECOGPT, body);
        var detalhes = gson.fromJson(json, RespostaGpt.class);
        return detalhes.choices().get(0).message().content();
    }
}
