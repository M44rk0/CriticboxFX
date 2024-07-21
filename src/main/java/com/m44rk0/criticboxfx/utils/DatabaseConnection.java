package com.m44rk0.criticboxfx.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A classe {@code DatabaseConnection} gerencia a conexão com o banco de dados MySQL para a aplicação.
 * <p>
 * Esta classe fornece métodos estáticos para obter e fechar a conexão com o banco de dados.
 * </p>
 * <p>
 * A classe utiliza um padrão de design Singleton para garantir que apenas uma conexão com o banco de dados
 * seja criada e gerenciada.
 * </p>
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/criticbox";
    private static final String USER = "root";
    private static final String PASSWORD = "marco";

    private static Connection connection;

    /**
     * Construtor privado para impedir a criação de instâncias desta classe.
     */
    private DatabaseConnection() { }

    /**
     * Obtém a conexão com o banco de dados. Se a conexão ainda não foi criada, ela será inicializada.
     *
     * @return A conexão com o banco de dados {@link Connection}.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            } catch (SQLException e) {
                AlertMessage.showErrorAlert("SQL Error", e.getMessage());
            }
        }
        return connection;
    }

    /**
     * Fecha a conexão com o banco de dados, se estiver aberta.
     * <p>
     * Este método deve ser chamado quando a aplicação terminar de usar a conexão para liberar recursos.
     * </p>
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                AlertMessage.showErrorAlert("SQL Error", e.getMessage());
            }
        }
    }
}

