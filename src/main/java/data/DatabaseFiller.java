package data;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import sql.DatabaseManager;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DatabaseFiller {
    public DatabaseFiller() {
    }

    public static void fill(Guild guild, List<Member> members) {
        members.forEach(DatabaseFiller::insertMember);
    }

    private static void insertMember(Member member) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement fillMember = conn.prepareStatement(
                     "INSERT OR IGNORE INTO users (user_id, first_resignation) VALUES (?, ?);"
             );
             PreparedStatement insertNames = conn.prepareStatement("""
                            INSERT INTO usernames (user_id, name, nick_name, timestamp)
                                    SELECT ?, ?, ?, ?
                                    WHERE NOT EXISTS (
                                        SELECT 1
                                        FROM (
                                            SELECT name, nick_name
                                            FROM usernames
                                            WHERE user_id = ?
                                            ORDER BY username_id DESC
                                            LIMIT 1
                                        ) AS last
                                        WHERE last.name = ? AND last.nick_name = ?
                                    );
                     """);
        ) {
            System.out.println("---");
            String memberId = member.getId();
            String firstJoinedAt = member.getTimeJoined().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            usersFiller(fillMember, memberId, firstJoinedAt);

            String memberName = member.getUser().getName();
            System.out.println(memberName);
            String memberNickName = member.getNickname() != null ? member.getNickname() : "";
            System.out.println(memberNickName);
            namesFiller(insertNames, memberId, memberName, memberNickName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static void usersFiller (PreparedStatement usersStatement, String memberId, String firstJoinedAt) throws SQLException{
        usersStatement.setString(1, memberId);
        usersStatement.setString(2, firstJoinedAt);
        usersStatement.executeUpdate();
        System.out.println(memberId);
    }
    private static void namesFiller (PreparedStatement namesStatement, String memberId, String name, String nickName) throws SQLException {
        namesStatement.setString(1, memberId);
        namesStatement.setString(2, name);
        namesStatement.setString(3, nickName);
        namesStatement.setString(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        namesStatement.setString(5, memberId);
        namesStatement.setString(6, name);
        namesStatement.setString(7, nickName);
        namesStatement.executeUpdate();
    }
}
