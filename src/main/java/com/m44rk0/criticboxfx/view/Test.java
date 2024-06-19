package com.m44rk0.criticboxfx.view;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbMovieLists;
import info.movito.themoviedbapi.TmdbReviews;
import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Credits;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;

public class Test {

    public static TmdbApi apiKey = new TmdbApi("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMTZhMDk1MTFkNTA1YTBiMTRiNDY4NjY2NjM2YWQ4NiIsInN1YiI6IjY2NDEzMjQxODQ1YjQzNmViZTM2ZTU3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zahQKdj9aOHNBqXwf03KslJsI5tNcDYSdYjX-08E6NE");

    public static void main(String[] args) throws TmdbException {
        TmdbApi apiKey = new TmdbApi("eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMTZhMDk1MTFkNTA1YTBiMTRiNDY4NjY2NjM2YWQ4NiIsInN1YiI6IjY2NDEzMjQxODQ1YjQzNmViZTM2ZTU3MyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.zahQKdj9aOHNBqXwf03KslJsI5tNcDYSdYjX-08E6NE");


        var movie = apiKey.getMovieLists().getNowPlaying("pt-BR", 1, "BR").getResults();
        var upcoming = apiKey.getMovieLists().getUpcoming("pt-BR", 1, "BR").getResults();

        for(Movie movi : movie){
            System.out.print(movi.getTitle() + " - ");
        }

        System.out.println(" ");

        for(Movie upc : upcoming){
            System.out.print(upc.getTitle() + " - ");
        }



    }


}
