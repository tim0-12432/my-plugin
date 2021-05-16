package de.tim012432.myplugin;

import java.util.function.BiFunction;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;

import com.creeperface.nukkit.placeholderapi.api.PlaceholderAPI;

/**
 * Definition od the welcome form.
 */
public class Form {
    private Player player;
    /** display variables. */
    private String title;
    private String welcoming;
    private String text;
    private String button;

    private BiFunction<String, Player, String> placeholderFunc;

    /**
     * constructor.
     * 
     * @param server the nukkit server instance.
     * @param py the player instance whom the form should be displayed.
     * @param tt the text for the window title.
     * @param wc the text as welcoming.
     * @param tx the text which should be displayed as main content.
     * @param bt the text on the button.
     */
    public Form(Server server, Player py, String tt, String wc, String tx, String bt) {
        initPlaceholders(server);

        player = py;
        title = tt;
        welcoming = wc;
        text = tx;
        button = bt;

        createForm();
    }

    /** placeholder function initialization. */
    private void initPlaceholders(Server server) {
        if (server.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderFunc = (s, p) -> PlaceholderAPI.getInstance().translateString(s, p);
        } else {
            placeholderFunc = (s, p) -> s;
        }
    }

    /** creates the form window. */
    private void createForm() {
        FormWindowSimple form = new FormWindowSimple(
            placeholderFunc.apply(title, player),
            placeholderFunc.apply(welcoming, player) + " " + player.getName() + ",\n\n" + placeholderFunc.apply(text, player)
        );
        form.addButton(new ElementButton(placeholderFunc.apply(button, player)));
        player.showFormWindow(form);
    }
}
