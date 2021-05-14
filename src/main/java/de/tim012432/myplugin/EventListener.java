package de.tim012432.myplugin;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {
    MyPlugin plugin;

    public EventListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void PlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendChat("Hi " + player.getName() + ",");
        player.sendChat("Welcome to this Server!");
    }
}
