package com.m44rk0.criticboxfx.model.title;
import java.util.*;

public abstract class Title {

    protected Integer titleId;
    protected String name;
    protected String overview;
    protected String posterPath;
    protected String releaseDate;
    protected Integer duration;
    protected Double popularity;

    public Integer getTitleId() {
        return titleId;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public Double getPopularity() {
        return popularity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Title title)) return false;

        if (!Objects.equals(name, title.name)) return false;
        if (!Objects.equals(overview, title.overview)) return false;
        if (!Objects.equals(posterPath, title.posterPath)) return false;
        if (!Objects.equals(releaseDate, title.releaseDate)) return false;
        if (!Objects.equals(duration, title.duration)) return false;
        return Objects.equals(popularity, title.popularity);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (duration != null ? duration.hashCode() : 0);
        result = 31 * result + (popularity != null ? popularity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Title{" +
                "name='" + name + '\'' +
                '}';
    }
}
