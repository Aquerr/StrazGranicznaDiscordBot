package pl.bartlomiejstepien.strazgranicznabot.listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import pl.bartlomiejstepien.strazgranicznabot.StrazGranicznaBot;

public class MessageListener extends ListenerAdapter
{
    private final StrazGranicznaBot bot;

    public MessageListener(final StrazGranicznaBot bot)
    {
        this.bot = bot;
    }

    @Override
    public void onGuildMessageReceived(@NotNull GuildMessageReceivedEvent event)
    {
        if (event.getChannel().getIdLong() == bot.getListeningChannelId())
        {
            final Message message = event.getMessage();
            final Guild guild = event.getGuild();
            final Member member = event.getMember();

            if (message.getContentDisplay().equals(this.bot.getListeningWord()))
            {
                if (member.getRoles().stream().noneMatch(role -> role.getIdLong() == this.bot.getRegisteredRoleId()))
                {
                    final Role role = guild.getRoleById(this.bot.getRegisteredRoleId());
                    if (role != null)
                        guild.addRoleToMember(member.getIdLong(), role).queue();
                }
            }
            message.delete().queue();
        }
    }
}
