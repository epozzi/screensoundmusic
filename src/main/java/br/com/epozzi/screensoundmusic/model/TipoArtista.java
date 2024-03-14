package br.com.epozzi.screensoundmusic.model;

public enum TipoArtista {
    SOLO("solo"),
    DUPLA("dupla"),
    BANDA("banda");

    private String tipo;

    TipoArtista(String tipo) {
        this.tipo = tipo;
    }

    public static TipoArtista fromString(String text) {
        for (TipoArtista tipoArtista : TipoArtista.values()) {
            if (tipoArtista.tipo.equalsIgnoreCase(text)) {
                return tipoArtista;
            }
        }
        throw new IllegalArgumentException(text + "Não corresponde a uma categoria válida, escolha solo, dupla ou banda");
    }
}
