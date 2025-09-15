package sql;

import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {
    public static void init() {
        try (Statement statement = DatabaseManager.getConnection().createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                        user_id VARCHAR(20) PRIMARY KEY,
                        first_resignation DATE NOT NULL
                    );
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS usernames (
                        username_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id VARCHAR(20) NOT NULL,
                        name VARCHAR(32) NOT NULL,
                        nick_name VARCHAR(32) NOT NULL,
                        timestamp DATE NOT NULL,
                        FOREIGN KEY(user_id) REFERENCES users(user_id)
                    );
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS registrations (
                        registration_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id VARCHAR(20) NOT NULL,
                        date DATE NOT NULL,
                        user_left INTEGER NOT NULL CHECK (user_left IN (0, 1)),
                        kicked_by VARCHAR(20),
                        FOREIGN KEY(user_id) REFERENCES users(user_id),
                        FOREIGN KEY(kicked_by) REFERENCES users(user_id)
                    );
                    """);
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS user_activity (
                        activity_id INTEGER PRIMARY KEY AUTOINCREMENT,
                        user_id VARCHAR(20) NOT NULL,
                        was_streaming INTEGER NOT NULL CHECK (was_streaming IN (0, 1)),
                        FOREIGN KEY(user_id) REFERENCES users(user_id)
                    );
                    """);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
