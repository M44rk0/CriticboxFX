package com.m44rk0.criticboxfx.service;

import com.m44rk0.criticboxfx.model.title.Title;
import info.movito.themoviedbapi.tools.TmdbException;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

/**
 * Classe responsável pela busca de títulos (filmes e séries) utilizando a API do TMDb.
 */
public class TitleSearcher {

    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("API_KEY");

    /**
     * Realiza uma busca de todos os títulos (filmes e séries) relacionados ao argumento de busca fornecido.
     *
     * @param searchArgument O termo de busca para encontrar filmes e séries.
     * @return Uma lista de títulos encontrados que correspondem ao termo de busca.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public List<Title> search(String searchArgument) throws TmdbException {
        return Search.searchAll(API_KEY, searchArgument);
    }

    /**
     * Busca um filme específico pelo ID fornecido.
     *
     * @param id O ID do filme a ser pesquisado.
     * @return O filme encontrado, ou {@code null} se não for válido.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public Title searchMovieById(Integer id) throws TmdbException {
        return Search.searchMovieById(API_KEY, id);
    }

    /**
     * Busca uma série específica pelo ID fornecido.
     *
     * @param id O ID da série a ser pesquisada.
     * @return A série encontrada, ou {@code null} se não for válida.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public Title searchTvShowById(Integer id) throws TmdbException {
        return Search.searchTVShowById(API_KEY, id);
    }

    /**
     * Busca filmes relacionados ao nome fornecido.
     *
     * @param movieName O nome do filme a ser pesquisado.
     * @return Uma lista de filmes encontrados que correspondem ao nome fornecido.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public List<Title> searchMovie(String movieName) throws TmdbException {
        return Search.searchMovie(API_KEY, movieName);
    }

    /**
     * Busca séries relacionadas ao nome fornecido.
     *
     * @param tvShowName O nome da série a ser pesquisada.
     * @return Uma lista de séries encontradas que correspondem ao nome fornecido.
     * @throws TmdbException Se ocorrer um erro ao acessar a API do TMDb.
     */
    public List<Title> searchTvShow(String tvShowName) throws TmdbException {
        return Search.searchTVShow(API_KEY, tvShowName);
    }

    /**
     * Obtém a chave da API utilizada para autenticação com o TMDb.
     *
     * @return A chave da API.
     */
    public String getAPI_KEY() {
        return API_KEY;
    }
}
