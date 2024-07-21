package com.m44rk0.criticboxfx.model.search;
import com.m44rk0.criticboxfx.model.title.Film;
import com.m44rk0.criticboxfx.model.title.Title;
import com.m44rk0.criticboxfx.model.title.TvShow;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

/**
 * Testes unitários para a classe {@link Search}.
 * Testa as funcionalidades relacionadas à busca, como ordenação por popularidade e validação de títulos.
 */
public class SearchTest {

    /**
     * Testa a funcionalidade de ordenação por popularidade.
     * Verifica se a lista de títulos é corretamente ordenada pela popularidade em ordem decrescente.
     */
    @Test
    void testSortByPopularity() {

        ArrayList<Title> titles = new ArrayList<>();

        titles.add(new Film(1, "Filme Teste", "Teste de Descrição", "Teste de poster", "2024-07-19", 147, 9.7));
        titles.add(new TvShow(2, "Série Teste", 25, "Teste de poster", "2024-07-19", "2024-07-19", 5.7, 80));
        titles.add(new Film(3, "Filme Teste 2", "Teste de Descrição", "Teste de poster", "2024-07-19", 87, 2.7));

        Search.sortByPopularity(titles);

        assertTrue(isSortedByPopularity(titles));
    }

    /**
     * Verifica se a lista de títulos está ordenada por popularidade em ordem decrescente.
     *
     * @param titles Lista de títulos a ser verificada.
     * @return Verdadeiro se a lista estiver ordenada por popularidade, falso caso contrário.
     */
    private boolean isSortedByPopularity(ArrayList<Title> titles) {

        for (int i = 0; i < titles.size() - 1; i++) {
            if (titles.get(i).getPopularity() < titles.get(i + 1).getPopularity()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Testa a validação de filmes.
     * Verifica se um filme é considerado válido ou inválido com base em seus atributos.
     */
    @Test
    void testIsValidMovie() {

        Title validMovie = new Film(1, "Filme Teste", "Teste de Descrição", "Teste de poster", "2024-07-19", 67, 9.7);
        Title invalidMovie = new Film(2, "Filme Teste 2", null, "Teste de poster", null, 67, 9.7);

        assertTrue(Search.isValidMovie(validMovie));
        assertFalse(Search.isValidMovie(invalidMovie));
    }

    /**
     * Testa a validação de programas de TV.
     * Verifica se um programa de TV é considerado válido ou inválido com base em seus atributos.
     */
    @Test
    void testIsValidTvShow() {

        Title validTvShow = new TvShow(1, "Série Teste", 25, "Teste de poster", "2024-07-19", "2024-07-19", 5.7, 80);
        Title invalidTvShow = new TvShow(2, "Série Teste 2", 27, null, null, "2024-07-19", 5.7, 80);

        assertTrue(Search.isValidTvShow(validTvShow));
        assertFalse(Search.isValidTvShow(invalidTvShow));
    }

    /**
     * Testa a verificação de lançamento.
     * Verifica se uma data fornecida é considerada uma data de lançamento válida.
     */
    @Test
    void testIsReleased() {
        assertTrue(Search.isReleased("2022-01-01"));
        assertFalse(Search.isReleased("2999-01-01"));
        assertFalse(Search.isReleased(null));
        assertFalse(Search.isReleased("Data Inválida"));
    }

}

