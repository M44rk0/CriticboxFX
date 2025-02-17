package com.m44rk0.criticboxfx.utils;

/**
 * A enumeração {@code Icon} define ícones gráficos usados na aplicação.
 * <p>
 * Cada constante nesta enumeração representa um ícone específico, armazenado como um caminho de string SVG.
 * </p>
 */
public enum Icon {

    /**
     * Ícone de estrela não preenchida.
     * <p>
     * Representa uma estrela que não está preenchida, geralmente usada para indicar uma classificação
     * que ainda não foi completada.
     * </p>
     */
    UNFILLED_STAR("M22,9.67A1,1,0,0,0,21.14,9l-5.69-.83L12.9,3a1,1,0,0,0-1.8,0L8.55,8.16,2.86,9a1,1,0,0,0-.81.68,1,1,0,0,0,.25,1l4.13,4-1,5.68A1,1,0,0,0,6.9,21.44L12,18.77l5.1,2.67a.93.93,0,0,0,.46.12,1,1,0,0,0,.59-.19,1,1,0,0,0,.4-1l-1-5.68,4.13-4A1,1,0,0,0,22,9.67Zm-6.15,4a1,1,0,0,0-.29.88l.72,4.2-3.76-2a1.06,1.06,0,0,0-.94,0l-3.76,2,.72-4.2a1,1,0,0,0-.29-.88l-3-3,4.21-.61a1,1,0,0,0,.76-.55L12,5.7l1.88,3.82a1,1,0,0,0,.76.55l4.21.61Z"),

    /**
     * Ícone de estrela preenchida.
     * <p>
     * Representa uma estrela que está preenchida, geralmente usada para indicar uma classificação completa.
     * </p>
     */
    FILLED_STAR("M17.562 21.56a1 1 0 0 1-.465-.116L12 18.764l-5.097 2.68a1 1 0 0 1-1.45-1.053l.973-5.676-4.124-4.02a1 1 0 0 1 .554-1.705l5.699-.828 2.549-5.164a1.04 1.04 0 0 1 1.793 0l2.548 5.164 5.699.828a1 1 0 0 1 .554 1.705l-4.124 4.02.974 5.676a1 1 0 0 1-.985 1.169Z"),

    /**
     * Ícone que representa um título assistido.
     * <p>
     * Utilizado para indicar que um título foi assistido pelo usuário.
     * </p>
     */
    WATCHED("M10.94,6.08A6.93,6.93,0,0,1,12,6c3.18,0,6.17,2.29,7.91,6a15.23,15.23,0,0,1-.9,1.64a1,1,0,0,0-.16.55a1,1,0,0,0,1.86.5a15.77,15.77,0,0,0,1.21-2.3a1,1,0,0,0,0-.79C19.9,6.91,16.1,4,12,4a7.77,7.77,0,0,0-1.4.12a1,1,0,1,0,.34,2ZM3.71,2.29A1,1,0,0,0,2.29,3.71L5.39,6.8a14.62,14.62,0,0,0-3.31,4.8a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20a9.26,9.26,0,0,0,5.05-1.54l3.24,3.25a1,1,0,0,0,1.42,0a1,1,0,0,0,0-1.42Zm6.36,9.19l2.45,2.45A1.81,1.81,0,0,1,12,14a2,2,0,0,1-2-2A1.81,1.81,0,0,1,10.07,11.48ZM12,18c-3.18,0-6.17-2.29-7.9-6A12.09,12.09,0,0,1,6.8,8.21L8.57,10A4,4,0,0,0,14,15.43L15.59,17A7.24,7.24,0,0,1,12,18Z"),

    /**
     * Ícone que representa um título não assistido.
     * <p>
     * Utilizado para indicar que um título ainda não foi assistido pelo usuário.
     * </p>
     */
    NOT_WATCHED("M21.92,11.6C19.9,6.91,16.1,4,12,4S4.1,6.91,2.08,11.6a1,1,0,0,0,0,.8C4.1,17.09,7.9,20,12,20s7.9-2.91,9.92-7.6A1,1,0,0,0,21.92,11.6ZM12,18c-3.17,0-6.17-2.29-7.9-6C5.83,8.29,8.83,6,12,6s6.17,2.29,7.9,6C18.17,15.71,15.17,18,12,18ZM12,8a4,4,0,1,0,4,4A4,4,0,0,0,12,8Zm0,6a2,2,0,1,1,2-2A2,2,0,0,1,12,14Z");

    private final String path;

    /**
     * Construtor para a enumeração {@code Icon}.
     *
     * @param path O caminho SVG do ícone.
     */
    Icon(String path) {
        this.path = path;
    }

    /**
     * Obtém o caminho SVG do ícone.
     *
     * @return O caminho SVG do ícone.
     */
    public String getPath() {
        return path;
    }
}
