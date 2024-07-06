package com.m44rk0.criticboxfx.model.title;

import com.m44rk0.criticboxfx.model.search.Search;
import com.m44rk0.criticboxfx.model.search.TitleSearcher;
import info.movito.themoviedbapi.model.tv.season.TvSeasonDb;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.TmdbTvSeasons;
import info.movito.themoviedbapi.TmdbApi;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.m44rk0.criticboxfx.controller.ViewController.seasonPosterCache;

public class TvShow extends Title {

    private ArrayList<Season> seasons;
    private Integer totalEpisodes;

    public TvShow(TvSeriesDb tvshow) throws TmdbException {
        this.titleId = tvshow.getId();
        this.name = tvshow.getName();
        this.duration = 0;
        this.overview = tvshow.getOverview();
        this.posterPath = tvshow.getPosterPath();
        this.releaseDate = tvshow.getFirstAirDate();
        this.popularity = tvshow.getPopularity();
        this.seasons = getTvShowInfo(tvshow);
        this.totalEpisodes = getEpisodes();
    }

    public TvShow(){
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public Season getSeasonByNumber(Integer seasonNumber){
        for(Season season : seasons){
            if(Objects.equals(season.getSeasonNumber(), seasonNumber)){
                return season;
            }
        }
        return null;
    }

    public ArrayList<Integer> getAllSeasons(){
        return seasons.stream()
                .map(Season::getSeasonNumber)
                .collect(Collectors.toCollection(ArrayList::new));
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
                String seasonPosterPath = serie.getSeasons().get(i).getPosterPath();
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

                    Image posterImage = new Image("https://image.tmdb.org/t/p/w500/" +
                            seasonPosterPath, 250, 350, false, false);

                    Season season = new Season(seasonNumber, episodes, seasonPosterPath);
                    seasonPosterCache.put(season, posterImage);

                    seasons.add(season);
                }
            }
        }
        return seasons;
    }

    private Integer getEpisodes(){
        return seasons.stream()
                .mapToInt(s -> s.getEpisodeList().size())
                .sum();
    }

    public void setTotalEpisodes(Integer totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }
}
