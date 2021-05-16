package de.tim012432.myplugin;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.event.player.PlayerInteractEvent;
import cn.nukkit.event.player.PlayerLocallyInitializedEvent;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.item.ItemID;
import cn.nukkit.level.Position;
import cn.nukkit.scheduler.NukkitRunnable;

/**
 * Event listener for my plugin.
 */
public class EventListener implements Listener {
    MyPlugin plugin;

    /** constructor. */
    public EventListener(MyPlugin plugin) {
        this.plugin = plugin;
    }

    /** handler for player is spawning. */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onPlayerSpawned(PlayerLocallyInitializedEvent event) {
        Player player = event.getPlayer();
        if(plugin.config.joinDelay <= 0) {
            displayForm(player);
        } else {
            new NukkitRunnable(){
                public void run() {
                    if(player.isOnline()) {
                        displayForm(player);
                    }
                }
            }.runTaskLater(plugin, plugin.config.joinDelay);
        }
    }

    /** handler for form response. */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onFormResponse(PlayerFormRespondedEvent event) {
        if (event.getResponse() == null) return;
        if (event.getWindow().wasClosed()) return;
        if (event.getWindow() instanceof FormWindowSimple) {
            if (plugin.config.title.equals(((FormWindowSimple) event.getWindow()).getTitle())) {
                if (plugin.config.button.equals(((FormWindowSimple) event.getWindow()).getResponse().getClickedButton().getText())) {
                    return;
                }
            }
        }
    }

    /** handler for interacting with the form. */
    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == PlayerInteractEvent.Action.PHYSICAL) return;
        Player player = event.getPlayer();
        if (event.getItem().getId() == ItemID.COMPASS && !player.isSpectator()) {
            Position playerPosition = player.getPosition();
            player.sendChat("x", String.valueOf(playerPosition.x));
            player.sendChat("y", String.valueOf(playerPosition.y));
            player.sendChat("z", String.valueOf(playerPosition.z));
        }
    }

    /**
     * method for displaying the form.
     * 
     * @param player the player instance whom the window should be displayed.
     */
    public void displayForm(Player player) {
        ConfigFetcher config = plugin.config;
        new Form(plugin.getServer(), player, config.title, config.welcoming, config.text, config.button);
    }
}
