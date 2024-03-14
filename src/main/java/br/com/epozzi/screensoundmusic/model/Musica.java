package br.com.epozzi.screensoundmusic.model;

import jakarta.persistence.*;

@Entity
@Table(
        name="musicas",
        uniqueConstraints = @UniqueConstraint (name = "TituloArtistaUnico" , columnNames = {"titulo", "artista_id"})
)
public class Musica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String album;
    private Integer anoLancamento;

    @ManyToOne
    @JoinColumn(name="artista_id", referencedColumnName = "id")
    private Artista artista;

    public Musica() {}

    public Musica(String titulo, String album, Integer anoLancamento, Artista artista) {
        this.titulo = titulo;
        this.album = album;
        this.anoLancamento = anoLancamento;
        this.artista = artista;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public Integer getAnoLancamento() {
        return anoLancamento;
    }

    public void setAnoLancamento(Integer anoLancamento) {
        this.anoLancamento = anoLancamento;
    }

    public Artista getArtista() {
        return artista;
    }

    public void setArtista(Artista artista) {
        this.artista = artista;
    }

    @Override
    public String toString() {
        return "Musica: " + titulo + '\'' +
                ", Album: '" + album + '\'' +
                ", Artista: " + artista.getNome();
    }
}
