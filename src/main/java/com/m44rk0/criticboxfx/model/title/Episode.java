package com.m44rk0.criticboxfx.model.title;

/**
 * Representa um episódio de uma série de TV, contendo o nome do episódio e a duração do episódio em minutos.
 *
 * @param episodeName   O nome do episódio.
 * @param episodeRuntime A duração do episódio em minutos.
 */
public record Episode(String episodeName, Integer episodeRuntime) {
}
