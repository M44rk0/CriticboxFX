package com.m44rk0.criticboxfx.model.title;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Representa uma temporada de uma série de TV, contendo informações como número da temporada,
 * caminho do pôster da temporada e uma lista de episódios.
 */
public class Season {

    /**
     * O número da temporada.
     */
    private final Integer seasonNumber;

    /**
     * O caminho do pôster da temporada.
     */
    private final String seasonPosterPath;

    /**
     * A lista de episódios da temporada.
     */
    private ArrayList<Episode> episodes;

    /**
     * Construtor que inicializa uma temporada com o número da temporada, uma lista de episódios e o caminho do pôster da temporada.
     *
     * @param seasonNumber    O número da temporada.
     * @param episodes        A lista de episódios da temporada.
     * @param seasonPosterPath O caminho do pôster da temporada.
     */
    public Season(Integer seasonNumber, ArrayList<Episode> episodes, String seasonPosterPath) {
        this.seasonNumber = seasonNumber;
        this.episodes = episodes;
        this.seasonPosterPath = seasonPosterPath;
    }

    /**
     * Construtor que inicializa uma temporada com o número da temporada e o caminho do pôster da temporada.
     *
     * @param seasonNumber    O número da temporada.
     * @param seasonPosterPath O caminho do pôster da temporada.
     */
    public Season(Integer seasonNumber, String seasonPosterPath) {
        this.seasonNumber = seasonNumber;
        this.seasonPosterPath = seasonPosterPath;
    }

    /**
     * Obtém o número da temporada.
     *
     * @return O número da temporada.
     */
    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    /**
     * Obtém o caminho do pôster da temporada.
     *
     * @return O caminho do pôster da temporada.
     */
    public String getSeasonPosterPath() {
        return seasonPosterPath;
    }

    /**
     * Obtém a lista de nomes dos episódios da temporada.
     *
     * @return A lista de nomes dos episódios.
     */
    public ArrayList<String> getEpisodeList() {
        return episodes.stream()
                .map(Episode::episodeName)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Obtém a lista de episódios da temporada.
     *
     * @return A lista de episódios.
     */
    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    /**
     * Define a lista de episódios da temporada.
     *
     * @param episodes A nova lista de episódios.
     */
    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }
}


