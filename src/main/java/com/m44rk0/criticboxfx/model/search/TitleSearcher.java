package com.m44rk0.criticboxfx.model.search;

import com.m44rk0.criticboxfx.model.title.Title;
import info.movito.themoviedbapi.tools.TmdbException;
import io.github.cdimascio.dotenv.Dotenv;

import java.util.List;

public class TitleSearcher {

    private final Dotenv dotenv = Dotenv.load();
    private final String API_KEY = dotenv.get("API_KEY");

    public List<Title> search(String searchArgument) throws TmdbException {
        return Search.searchAll(API_KEY, searchArgument);
    }

    public List<Title> searchMovie(String movieName) throws TmdbException {
        return Search.searchMovie(API_KEY, movieName);
    }

    public List<Title> searchTvShow(String tvShowName) throws TmdbException {
        return Search.searchTVShow(API_KEY, tvShowName);
    }

    public String getAPI_KEY() {
        return API_KEY;
    }
}