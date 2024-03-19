package br.com.epozzi.screensoundmusic.principal;

import br.com.epozzi.screensoundmusic.model.*;
import br.com.epozzi.screensoundmusic.repository.ArtistaRepository;
import br.com.epozzi.screensoundmusic.service.ConsultaChatGpt;
import br.com.epozzi.screensoundmusic.service.ConsumoApi;
import com.google.gson.Gson;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConsultaChatGpt consultaChatGpt = new ConsultaChatGpt();

    private final String ENDERECOGPT = "https://api.openai.com/v1/chat/completions";

    private ArtistaRepository artistaRepository;

    public Principal(ArtistaRepository artistaRepository) {
        this.artistaRepository = artistaRepository;
    }

    public void exibeMenu() {
        String menu = """
                
                *** Escolha a opção ***
                
                1 - Cadastrar artistas
                2 - Cadastrar músicas
                3 - Listar músicas
                4 - Buscar músicas por artistas
                5 - Pesquisar dados sobre um artista
                
                9 - Sair
                """;

        System.out.println(menu);
        var opcaoMenu = leitura.nextInt();
        leitura.nextLine();

        switch (opcaoMenu) {
            case 1:
                cadastrarArtista();
                break;
            case 2:
                cadastrarMusica();
                break;
            case 3:
                listarMusicas();
                break;
            case 4:
                listarMusicasPorArtista();
                break;
            case 5:
                consultarDadosArtista();
                break;
            case 9:
                System.out.println("\nEncerrando aplicação!\n");;
                break;
            default:
                System.out.println("Opção inválida!");
        }
    }

    private void cadastrarArtista() {
        var continuar = "S";

        while (continuar.equalsIgnoreCase("s")) {
            System.out.println("\nDigite o nome do artista: ");
            var nomeArtista = leitura.nextLine();
            System.out.println("\nInfomre o tipo desse artista (solo, dupla, banda): ");
            var tipoArtista = leitura.nextLine();

            Artista artista = new Artista(nomeArtista, TipoArtista.fromString(tipoArtista));
            artistaRepository.save(artista);
            System.out.println("\n" + nomeArtista + " cadastrado!");
            System.out.println("\nDeseja cadastrar outro artista? (S/N)");
            continuar = leitura.nextLine();
        }

        exibeMenu();

    }


    private void cadastrarMusica() {
        System.out.println("\nLista de artistas cadastrados:");
        List<Artista> artistas = artistaRepository.findAll();
        artistas.forEach(System.out::println);
        System.out.println("\nEscolha um artista para cadastrar musicas:");
        var artistaEscolhido = leitura.nextLine();

        Optional<Artista> artista = artistaRepository.findByNomeContainingIgnoreCase(artistaEscolhido);

        if ( artista.isPresent() ) {
            Artista artistaParaCadastrar = artista.get();
            System.out.println("Musica para: " + artistaParaCadastrar.getNome());
            List<Musica> musicasParaCadastrar = new ArrayList<>();
            String cadastrarMais = "S";

            while ( cadastrarMais.equalsIgnoreCase("S")) {
                System.out.println("\nDigite o título da música:");
                var tituloMusica = leitura.nextLine();
                tituloMusica = StringUtils.capitalize(tituloMusica);
                System.out.println("\nDigite o álbum da música:");
                var albumMusica = leitura.nextLine();
                System.out.println("\nDigite o ano de lançamento:");
                var anoMusica = leitura.nextInt();
                leitura.nextLine();
                Musica musica = new Musica(tituloMusica, albumMusica, anoMusica, artistaParaCadastrar);
                musicasParaCadastrar.add(musica);
                System.out.println("\nDeseja continuar cadastrando música para o mesmo artista? (S/N)");
                cadastrarMais = leitura.nextLine();
            }

            artistaParaCadastrar.setMusicas(musicasParaCadastrar);
            artistaRepository.save(artistaParaCadastrar);
            System.out.println("\nMúsicas cadastradas com sucesso!\n");

            exibeMenu();
        } else {
            System.out.println("Artista não cadastrado. Deseja cadastrar? (S/N)");
            var desejaCadastrar = leitura.nextLine();
            if ( desejaCadastrar.equalsIgnoreCase("S")) {
                cadastrarArtista();
            } else {
                exibeMenu();
            }
        }

    }

    public void listarMusicas() {
        System.out.println("\nMúsicas cadastradas: \n");
        List<Musica> musicas = artistaRepository.listarMusicas();
        musicas.forEach(System.out::println);
        exibeMenu();
    }

    private void listarMusicasPorArtista() {
        System.out.println("\nLista de artistas cadastrados:");
        List<Artista> artistas = artistaRepository.findAll();
        artistas.forEach(System.out::println);
        System.out.println("\nEscolha um artista para listar musicas:");
        var artistaEscolhido = leitura.nextLine();
        String artista = artistas.stream()
                .map(Artista::getNome)
                .filter(nome -> nome.toLowerCase().contains(artistaEscolhido.toLowerCase()))
                .findFirst()
                .orElse(null);

        List<Musica> musicas = artistaRepository.listarMusicasPorArtista(artista);
        if ( musicas.isEmpty() ) {
            System.out.println("\nNão há músicas cadastradas para o artista " + artista);
        } else {
            System.out.println("\nMúsicas de " + artista);
            musicas.forEach(m ->
                    System.out.println(
                            "Música: " + m.getTitulo() +
                            " - Álbum: " + m.getAlbum() +
                            " - Lançamento: " + m.getAnoLancamento()));
        }


        exibeMenu();
    }

    private void consultarDadosArtista() {
        System.out.println("\nEscolha um artista para buscar dados:");
        var artista = leitura.nextLine();

        var dadosArtista = consultaChatGpt.buscaDadosArtista(artista);
        System.out.println("\n" + dadosArtista.trim());

        exibeMenu();
    }
}
