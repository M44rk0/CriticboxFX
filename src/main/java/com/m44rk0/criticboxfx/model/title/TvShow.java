package com.m44rk0.criticboxfx.model.title;

import com.m44rk0.criticboxfx.model.search.Search;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.TmdbTvSeasons;
import info.movito.themoviedbapi.TmdbApi;
import java.util.ArrayList;

public class TvShow extends Title {

    private final ArrayList<Season> seasons;
    private final Integer totalEpisodes;

    public TvShow(TvSeriesDb tvshow) throws TmdbException {
        this.titleId = tvshow.getId();
        this.name = tvshow.getName();
        this.overview = tvshow.getOverview();
        this.posterPath = tvshow.getPosterPath();
        this.releaseDate = tvshow.getFirstAirDate();
        this.popularity = tvshow.getPopularity();
        this.seasons = getTvShowInfo(tvshow);
        this.totalEpisodes = getEpisodes();
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public ArrayList<Integer> getAllSeasons(){
        ArrayList<Integer> seasonNumber = new ArrayList<>();
        for(Season season : seasons){
            seasonNumber.add(season.getSeasonNumber());
        }

        return seasonNumber;
    }

    public Integer getTotalEpisodes() {
        return totalEpisodes;
    }

    private ArrayList<Season> getTvShowInfo(TvSeriesDb seriesDb) throws TmdbException {
        TmdbApi apiKey = new TmdbApi(new TitleSearcher().getAPI_KEY());
        TvSeriesDb serie = apiKey.getTvSeries().getDetails(seriesDb.getId(), "pt-BR");

        ArrayList<Season> seasons = new ArrayList<>();
        TmdbTvSeasons tvSeasons = apiKey.getTvSeasons();

        for (int i = 0; i < serie.getSeasons().size(); i++) {
            String seasonAirDate = serie.getSeasons().get(i).getAirDate();

            if (Search.isReleased(seasonAirDate)) {
                Integer seasonNumber = serie.getSeasons().get(i).getSeasonNumber();
                ArrayList<Episode> episodes = new ArrayList<>();

                if (seasonNumber != 0) {
                    TvSeasonDb tvSeasonDb = tvSeasons.getDetails(seriesDb.getId(), seasonNumber, "pt-BR");

                    for (int j = 0; j < tvSeasonDb.getEpisodes().size(); j++) {
                        String episodeAirDate = tvSeasonDb.getEpisodes().get(j).getAirDate();

                        if (Search.isReleased(episodeAirDate)) {
                            Episode episode = new Episode(tvSeasonDb.getEpisodes().get(j).getName(), tvSeasonDb.getEpisodes().get(j).getRuntime());
                            episodes.add(episode);
                        }
                    }

                    Season season = new Season(seasonNumber, episodes);
                    seasons.add(season);
                }
            }
        }
        return seasons;
    }

    private Integer getEpisodes(){
        int episodeCount = 0;
        for (Season season : seasons) {
             episodeCount += season.getEpisodeList().size();
        }
        return episodeCount;
    }

}
