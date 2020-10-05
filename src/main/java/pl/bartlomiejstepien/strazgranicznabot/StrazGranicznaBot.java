package pl.bartlomiejstepien.strazgranicznabot;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import pl.bartlomiejstepien.strazgranicznabot.listener.MessageListener;

import javax.security.auth.login.LoginException;
import java.io.File;

public class StrazGranicznaBot
{
    private String botToken;
    private long registeredRoleId;
    private long listeningChannelId;
    private String listeningWord;

    private JDA jda;

    public static void main(String[] args)
    {
        final StrazGranicznaBot strazGranicznaBot = new StrazGranicznaBot();
        try
        {
            strazGranicznaBot.start();
        }
        catch (LoginException e)
        {
            e.printStackTrace();
        }
    }

    public void loadConfiguration()
    {
        Config config = ConfigFactory.parseFile(new File("application.conf"));
        botToken = config.getString("bot-token");
        registeredRoleId = config.getLong("registered-role-id");
        listeningChannelId = config.getLong("listening-channel-id");
        listeningWord = config.getString("listening-word");

    }

    public String getBotToken()
    {
        return this.botToken;
    }

    public long getListeningChannelId()
    {
        return this.listeningChannelId;
    }

    public long getRegisteredRoleId()
    {
        return this.registeredRoleId;
    }

    public String getListeningWord()
    {
        return this.listeningWord;
    }

    public JDA getJda()
    {
        return this.jda;
    }

    private void start() throws LoginException
    {
        loadConfiguration();

        this.jda = JDABuilder.createDefault(botToken)
                .addEventListeners(new MessageListener(this))
                .build();

        System.out.println("JDA successfully loaded!");
    }
}