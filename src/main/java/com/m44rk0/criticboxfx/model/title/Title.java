package com.m44rk0.criticboxfx.model.title;
import java.util.*;

/**
 * Representa uma classe abstrata para um título, fornecendo informações como
 * nome do título, visão geral, caminho do pôster, data de lançamento, duração e popularidade.
 */
public abstract class Title {

    /**
     * Identificador único para o título.
     */
    protected Integer titleId;

    /**
     * O nome do título.
     */
    protected String name;

    /**
     * Uma visão geral ou resumo do título.
     */
    protected String overview;

    /**
     * Caminho para a imagem do pôster do título.
     */
    protected String posterPath;

    /**
     * A data de lançamento do título.
     */
    protected String releaseDate;

    /**
     * A duração do título em minutos.
     */
    protected Integer duration;

    /**
     * A classificação de popularidade do título.
     */
    protected Double popularity;

    /**
     * Obtém o identificador único do título.
     *
     * @return O identificador do título.
     */
    public Integer getTitleId() {
        return titleId;
    }

    /**
     * Obtém o nome do título.
     *
     * @return O nome do título.
     */
    public String getName() {
        return name;
    }

    /**
     * Obtém a visão geral ou resumo do título.
     *
     * @return A visão geral do título.
     */
    public String getOverview() {
        return overview;
    }

    /**
     * Obtém o caminho para a imagem do pôster do título.
     *
     * @return O caminho do pôster.
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * Obtém a data de lançamento do título.
     *
     * @return A data de lançamento.
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * Obtém a duração do título em minutos.
     *
     * @return A duração do título.
     */
    public Integer getDuration() {
        return duration;
    }

    /**
     * Obtém a classificação de popularidade do título.
     *
     * @return A popularidade do título.
     */
    public Double getPopularity() {
        return popularity;
    }

    /**
     * Compara este título com outro objeto para verificar a igualdade com base no nome, visão geral,
     * caminho do pôster, data de lançamento, duração e popularidade.
     *
     * @param o O objeto a ser comparado.
     * @return {@code true} se os títulos forem iguais, {@code false} caso contrário.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Title title)) return false;

        return titleId.equals(title.titleId);
    }

    /**
     * Gera um código hash para o título com base no nome, visão geral, caminho do pôster, data de lançamento,
     * duração e popularidade.
     *
     * @return O código hash do título.
     */
    @Override
    public int hashCode() {
        return titleId.hashCode();
    }

    /**
     * Retorna o nome do Título.
     *
     * @return O nome do título.
     */
    @Override
    public String toString() {
        return "Title{" +
                "name='" + name + '\'' +
                '}';
    }
}
