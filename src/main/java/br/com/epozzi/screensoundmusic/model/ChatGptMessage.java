package br.com.epozzi.screensoundmusic.model;

public class ChatGptMessage {
    private String role;
    private String content;

    public ChatGptMessage(String artista) {
        this.role = "user";
        this.content = "Informações sobre o artista " + artista;
    }
}
