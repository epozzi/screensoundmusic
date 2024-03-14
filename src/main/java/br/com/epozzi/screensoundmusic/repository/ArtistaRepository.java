package br.com.epozzi.screensoundmusic.repository;

import br.com.epozzi.screensoundmusic.model.Artista;
import br.com.epozzi.screensoundmusic.model.Musica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ArtistaRepository extends JpaRepository<Artista, Long> {

    Optional<Artista> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT m FROM Artista a JOIN a.musicas m")
    List<Musica> listarMusicas();

    @Query("SELECT m FROM Artista a JOIN a.musicas m WHERE a.nome = :nomeArtista")
    List<Musica> listarMusicasPorArtista(String nomeArtista);
}
