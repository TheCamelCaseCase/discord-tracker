package listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.update.GuildMemberUpdateNicknameEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import sql.DatabaseManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class NickNameChange extends ListenerAdapter {
    Guild guild;

    public NickNameChange(Guild guild) {
        this.guild = guild;
    }

    @Override
    public void onGuildMemberUpdateNickname(GuildMemberUpdateNicknameEvent event) {
        if (!guild.equals(event.getGuild()))
            return;
        System.out.println("Nickname-Update erkannt!");
        String nickName = event.getMember().getNickname();
        String nickNotNull = nickName != null ? nickName : "";
        System.out.println(nickNotNull);
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement insertNick = conn.prepareStatement("""
                     INSERT INTO usernames (user_id, name, nick_name, timestamp)
                     VALUES (?, ?, ?, ?)
                     """);
        ) {
            String userId = event.getUser().getId();
            String userName = event.getUser().getName();

            insertNick.setString(1, userId);
            insertNick.setString(2, userName);
            insertNick.setString(3, nickName);
            insertNick.setString(4, LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            insertNick.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUserUpdateName(UserUpdateNameEvent event) {
        System.out.println("Nickname-Update erkannt!");
        String userName = event.getUser().getName();
        System.out.println(userName);
    }
}
