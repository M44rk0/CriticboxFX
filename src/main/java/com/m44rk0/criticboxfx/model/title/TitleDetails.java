package com.m44rk0.criticboxfx.model.title;

import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import com.m44rk0.criticboxfx.utils.AlertMessage;
import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.NamedIdElement;
import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A classe {@code TitleDetails} fornece detalhes sobre o elenco, diretores, produtores, roteiristas,
 * equipe de efeitos visuais, equipe de fotografia, direção de arte e equipe de som para um título,
 * que pode ser um filme ou uma série de TV.
 * <p>
 * Os detalhes são obtidos através da API do TMDb, e a classe processa esses detalhes para preencher
 * os campos apropriados com as informações coletadas.
 * </p>
 */
public class TitleDetails {

    /**
     * Lista de nomes do elenco do título.
     */
    protected ArrayList<String> cast;

    /**
     * Lista de nomes dos diretores do título.
     */
    protected ArrayList<String> directors;

    /**
     * Lista de nomes dos produtores do título.
     */
    protected ArrayList<String> producers;

    /**
     * Lista de nomes dos roteiristas do título.
     */
    protected ArrayList<String> writers;

    /**
     * Lista de nomes da equipe de efeitos visuais do título.
     */
    protected ArrayList<String> visualEffectsTeam;

    /**
     * Lista de nomes da equipe de fotografia do título.
     */
    protected ArrayList<String> photographyTeam;

    /**
     * Lista de nomes responsáveis pela direção de arte do título.
     */
    protected ArrayList<String> artDirection;

    /**
     * Lista de nomes da equipe de som do título.
     */
    protected ArrayList<String> soundTeam;

    /**
     * Lista de gêneros associados ao título.
     */
    protected ArrayList<String> genres;

    /**
     * Construtor que inicializa {@code TitleDetails} com base no título fornecido.
     *
     * @param title O título (filme ou série de TV) para o qual os detalhes serão obtidos.
     */
    public TitleDetails(Title title){
        try {
            TmdbApi api = new TmdbApi(new TitleSearcher().getAPI_KEY());

            if (title instanceof Film){
                MovieDb movieDb = api.getMovies().getDetails(title.getTitleId(), "pt-BR");
                Credits credits = api.getMovies().getCredits(title.getTitleId(), "pt-BR");
                this.cast = getCast(credits);
                this.directors = getDirectors(credits);
                this.producers = getProducers(credits);
                this.writers = getWriters(credits);
                this.visualEffectsTeam = getVFX(credits);
                this.photographyTeam = getPhotography(credits);
                this.artDirection = getArtDirection(credits);
                this.soundTeam = getSound(credits);
                this.genres = getGenres(movieDb);

            } else if (title instanceof TvShow){
                TvSeriesDb seriesDb = api.getTvSeries().getDetails(title.getTitleId(), "pt-BR");
                info.movito.themoviedbapi.model.tv.core.credits.Credits credits = api.getTvSeries().getCredits(title.getTitleId(), "pt-BR");
                this.cast = getTvCast(credits);
                this.directors = getTvDirectors(seriesDb);
                this.producers = getTvProducers(credits);
                this.writers = getTvWriters(credits);
                this.visualEffectsTeam = getTvVFX(credits);
                this.photographyTeam = getTvPhotography(credits);
                this.artDirection = getTvArtDirection(credits);
                this.soundTeam = getTvSound(credits);
                this.genres = getTvGenres(seriesDb);
            }
        } catch (TmdbException e) {
            AlertMessage.showCommonAlert("Erro de Busca", "Erro de Busca");
        }
    }

    /**
     * Obtém uma lista de gêneros de um filme.
     *
     * @param movie O objeto {@code MovieDb} contendo detalhes do filme.
     * @return Uma lista de gêneros do filme.
     */
    protected ArrayList<String> getGenres(MovieDb movie){
        return (ArrayList<String>) movie.getGenres().stream()
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());
    }

    /**
     * Obtém uma lista de gêneros de uma série de TV.
     *
     * @param serie O objeto {@code TvSeriesDb} contendo detalhes da série de TV.
     * @return Uma lista de gêneros da série de TV.
     */
    protected ArrayList<String> getTvGenres(TvSeriesDb serie){
        return (ArrayList<String>) serie.getGenres().stream()
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());
    }

    /**
     * Obtém uma lista de nomes do elenco a partir dos créditos de um filme.
     *
     * @param credits O objeto {@code Credits} contendo informações de crédito do filme.
     * @return Uma lista de nomes do elenco.
     */
    protected ArrayList<String> getCast(Credits credits){
        List<String> cast = credits.getCast().stream()
                .map(Cast::getName)
                .collect(Collectors.toList());

        if (cast.isEmpty()) {
            cast.add("N/A");
        }

        return (ArrayList<String>) cast;
    }

    /**
     * Obtém uma lista de nomes do elenco a partir dos créditos de uma série de TV.
     *
     * @param credits O objeto {@code info.movito.themoviedbapi.model.tv.core.credits.Credits} contendo informações de crédito da série de TV.
     * @return Uma lista de nomes do elenco.
     */
    protected ArrayList<String> getTvCast(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> cast = credits.getCast().stream()
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (cast.isEmpty()) {
            cast.add("N/A");
        }

        return (ArrayList<String>) cast;
    }

    /**
     * Obtém uma lista de nomes dos diretores a partir dos créditos de um filme.
     *
     * @param credits O objeto {@code Credits} contendo informações de crédito do filme.
     * @return Uma lista de nomes dos diretores.
     */
    protected ArrayList<String> getDirectors(Credits credits){
        List<String> directors = credits.getCrew().stream()
                .filter(crewMember -> "Director".equals(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (directors.isEmpty()) {
            directors.add("N/A");
        }

        return (ArrayList<String>) directors;
    }

    /**
     * Obtém uma lista de nomes dos diretores a partir das informações de uma série de TV.
     *
     * @param seriesDb O objeto {@code TvSeriesDb} contendo informações da série de TV.
     * @return Uma lista de nomes dos diretores.
     */
    protected ArrayList<String> getTvDirectors(TvSeriesDb seriesDb){
        List<String> directors = seriesDb.getCreatedBy().stream()
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (directors.isEmpty()) {
            directors.add("N/A");
        }

        return (ArrayList<String>) directors;
    }

    /**
     * Obtém uma lista de nomes dos produtores a partir dos créditos de um filme.
     *
     * @param credits O objeto {@code Credits} contendo informações de crédito do filme.
     * @return Uma lista de nomes dos produtores.
     */
    protected ArrayList<String> getProducers(Credits credits){
        List<String> producerJobs = Arrays.asList("Producer", "Executive Producer");

        List<String> producers = credits.getCrew().stream()
                .filter(crewMember -> producerJobs.contains(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (producers.isEmpty()) {
            producers.add("N/A");
        }

        return (ArrayList<String>) producers;
    }

    /**
     * Obtém uma lista de nomes dos produtores a partir dos créditos de uma série de TV.
     *
     * @param credits O objeto {@code info.movito.themoviedbapi.model.tv.core.credits.Credits} contendo informações de crédito da série de TV.
     * @return Uma lista de nomes dos produtores.
     */
    protected ArrayList<String> getTvProducers(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> producerJobs = Arrays.asList("Producer", "Executive Producer");

        List<String> producers = credits.getCrew().stream()
                .filter(crewMember -> producerJobs.contains(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (producers.isEmpty()) {
            producers.add("N/A");
        }

        return (ArrayList<String>) producers;
    }

    /**
     * Obtém uma lista de nomes dos roteiristas a partir dos créditos de um filme.
     *
     * @param credits O objeto {@code Credits} contendo informações de crédito do filme.
     * @return Uma lista de nomes dos roteiristas.
     */
    protected ArrayList<String> getWriters(Credits credits){
        List<String> writers = credits.getCrew().stream()
                .filter(crewMember -> "Writing".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (writers.isEmpty()) {
            writers.add("N/A");
        }

        return (ArrayList<String>) writers;
    }

    /**
     * Obtém uma lista de nomes dos roteiristas a partir dos créditos de uma série de TV.
     *
     * @param credits O objeto {@code info.movito.themoviedbapi.model.tv.core.credits.Credits} contendo informações de crédito da série de TV.
     * @return Uma lista de nomes dos roteiristas.
     */
    protected ArrayList<String> getTvWriters(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> writers = credits.getCrew().stream()
                .filter(crewMember -> "Writing".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (writers.isEmpty()) {
            writers.add("N/A");
        }

        return (ArrayList<String>) writers;
    }

    /**
     * Obtém uma lista de nomes da equipe de efeitos visuais a partir dos créditos de um filme.
     *
     * @param credits O objeto {@code Credits} contendo informações de crédito do filme.
     * @return Uma lista de nomes da equipe de efeitos visuais.
     */
    protected ArrayList<String> getVFX(Credits credits){
        List<String> vfx = credits.getCrew().stream()
                .filter(crewMember -> "Visual Effects".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (vfx.isEmpty()) {
            vfx.add("N/A");
        }

        return (ArrayList<String>) vfx;
    }

    /**
     * Obtém uma lista de nomes da equipe de efeitos visuais a partir dos créditos de uma série de TV.
     *
     * @param credits O objeto {@code info.movito.themoviedbapi.model.tv.core.credits.Credits} contendo informações de crédito da série de TV.
     * @return Uma lista de nomes da equipe de efeitos visuais.
     */
    protected ArrayList<String> getTvVFX(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> vfx = credits.getCrew().stream()
                .filter(crewMember -> "Visual Effects".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (vfx.isEmpty()) {
            vfx.add("N/A");
        }

        return (ArrayList<String>) vfx;
    }

    /**
     * Obtém uma lista de nomes da equipe de fotografia a partir dos créditos de um filme.
     *
     * @param credits O objeto {@code Credits} contendo informações de crédito do filme.
     * @return Uma lista de nomes da equipe de fotografia.
     */
    protected ArrayList<String> getPhotography(Credits credits){
        List<String> photographyTeam = credits.getCrew().stream()
                .filter(crewMember -> "Camera".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (photographyTeam.isEmpty()) {
            photographyTeam.add("N/A");
        }

        return (ArrayList<String>) photographyTeam;
    }

    /**
     * Obtém uma lista de nomes da equipe de fotografia a partir dos créditos de uma série de TV.
     *
     * @param credits O objeto {@code info.movito.themoviedbapi.model.tv.core.credits.Credits} contendo informações de crédito da série de TV.
     * @return Uma lista de nomes da equipe de fotografia.
     */
    protected ArrayList<String> getTvPhotography(info.movito.themoviedbapi.model.tv.core.credits.Credits credits) {
        List<String> photographyTeam = credits.getCrew().stream()
                .filter(crewMember -> "Camera".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (photographyTeam.isEmpty()) {
            photographyTeam.add("N/A");
        }

        return (ArrayList<String>) photographyTeam;
    }

    /**
     * Obtém uma lista de nomes dos responsáveis pela direção de arte a partir dos créditos de um filme.
     *
     * @param credits O objeto {@code Credits} contendo informações de crédito do filme.
     * @return Uma lista de nomes da direção de arte.
     */
    protected ArrayList<String> getArtDirection(Credits credits){
        List<String> artDirection = credits.getCrew().stream()
                .filter(crewMember -> "Art Direction".equals(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (artDirection.isEmpty()) {
            artDirection.add("N/A");
        }

        return (ArrayList<String>) artDirection;
    }

    /**
     * Obtém uma lista de nomes dos responsáveis pela direção de arte a partir dos créditos de uma série de TV.
     *
     * @param credits O objeto {@code info.movito.themoviedbapi.model.tv.core.credits.Credits} contendo informações de crédito da série de TV.
     * @return Uma lista de nomes da direção de arte.
     */
    protected ArrayList<String> getTvArtDirection(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> artDirection = credits.getCrew().stream()
                .filter(crewMember -> "Art Direction".equals(crewMember.getJob()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (artDirection.isEmpty()) {
            artDirection.add("N/A");
        }

        return (ArrayList<String>) artDirection;
    }

    /**
     * Obtém uma lista de nomes da equipe de som a partir dos créditos de um filme.
     *
     * @param credits O objeto {@code Credits} contendo informações de crédito do filme.
     * @return Uma lista de nomes da equipe de som.
     */
    protected ArrayList<String> getSound(Credits credits){
        List<String> sound = credits.getCrew().stream()
                .filter(crewMember -> "Sound".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (sound.isEmpty()) {
            sound.add("N/A");
        }

        return (ArrayList<String>) sound;
    }

    /**
     * Obtém uma lista de nomes da equipe de som a partir dos créditos de uma série de TV.
     *
     * @param credits O objeto {@code info.movito.themoviedbapi.model.tv.core.credits.Credits} contendo informações de crédito da série de TV.
     * @return Uma lista de nomes da equipe de som.
     */
    protected ArrayList<String> getTvSound(info.movito.themoviedbapi.model.tv.core.credits.Credits credits){
        List<String> sound = credits.getCrew().stream()
                .filter(crewMember -> "Sound".equals(crewMember.getDepartment()))
                .map(NamedIdElement::getName)
                .collect(Collectors.toList());

        if (sound.isEmpty()) {
            sound.add("N/A");
        }

        return (ArrayList<String>) sound;
    }

    /**
     * Obtém a lista de gêneros do título.
     *
     * @return A lista de gêneros.
     */
    public ArrayList<String> getGenres() {
        return genres;
    }

    /**
     * Obtém a lista de nomes do elenco do título.
     *
     * @return A lista de nomes do elenco.
     */
    public ArrayList<String> getCast() {
        return cast;
    }

    /**
     * Obtém a lista de nomes dos diretores do título.
     *
     * @return A lista de nomes dos diretores.
     */
    public ArrayList<String> getDirectors() {
        return directors;
    }

    /**
     * Obtém a lista de nomes dos produtores do título.
     *
     * @return A lista de nomes dos produtores.
     */
    public ArrayList<String> getProducers() {
        return producers;
    }

    /**
     * Obtém a lista de nomes dos roteiristas do título.
     *
     * @return A lista de nomes dos roteiristas.
     */
    public ArrayList<String> getWriters() {
        return writers;
    }

    /**
     * Obtém a lista de nomes da equipe de efeitos visuais do título.
     *
     * @return A lista de nomes da equipe de efeitos visuais.
     */
    public ArrayList<String> getVisualEffectsTeam() {
        return visualEffectsTeam;
    }

    /**
     * Obtém a lista de nomes da equipe de fotografia do título.
     *
     * @return A lista de nomes da equipe de fotografia.
     */
    public ArrayList<String> getPhotographyTeam() {
        return photographyTeam;
    }

    /**
     * Obtém a lista de nomes da direção de arte do título.
     *
     * @return A lista de nomes da direção de arte.
     */
    public ArrayList<String> getArtDirection() {
        return artDirection;
    }

    /**
     * Obtém a lista de nomes da equipe de som do título.
     *
     * @return A lista de nomes da equipe de som.
     */
    public ArrayList<String> getSoundTeam() {
        return soundTeam;
    }
}


