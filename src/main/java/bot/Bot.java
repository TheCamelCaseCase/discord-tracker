package bot;

import data.DatabaseFiller;
import listener.NickNameChange;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import test.MyListener;
import net.dv8tion.jda.api.entities.Member;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

/**
 * Starts the Discord bot.
 * <p>
 * A new {@link JDA} object is created using the provided bot token.
 * Then, the {@link GatewayIntent#MESSAGE_CONTENT} is enabled and a
 * {@link MyListener} is registered as an event listener.
 * After calling the {@code build()} method, {@code awaitReady()} is used
 * to wait until the bot is fully ready. Once the bot is online, a confirmation
 * message "Bot online!" is printed to the console.
 *
 * @throws InterruptedException if the thread is interrupted while waiting for the bot to be ready
 */

public class Bot {
    public static void start() throws InterruptedException {
        Properties props = new Properties();
        try {
            props.load(new FileInputStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String token = props.getProperty("DISCORD_TOKEN");
        JDA jda = JDABuilder.createDefault(token).
                enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.GUILD_PRESENCES,
                        GatewayIntent.GUILD_MESSAGES).
                enableCache(CacheFlag.ONLINE_STATUS).
                setMemberCachePolicy(MemberCachePolicy.ALL).setChunkingFilter(ChunkingFilter.ALL).build();
        jda.awaitReady();

        String guildID = props.getProperty("GUILD_ID");
        Guild guild = Objects.requireNonNull(jda.getGuildById(guildID));

        jda.addEventListener(new MyListener(), new NickNameChange(guild));

        System.out.println("Bot online!");

        List<Member> members = guild.getMembers();
        for (Member member : members) {
            System.out.println(member.getUser().getName() + " - " + member.getOnlineStatus());
        }
        DatabaseFiller.fill(guild, members);
        System.out.println("SQL finished!");
    }
}
