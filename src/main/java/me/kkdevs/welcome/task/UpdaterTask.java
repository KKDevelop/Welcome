package me.kkdevs.welcome.task;

import cn.nukkit.Server;
import cn.nukkit.scheduler.AsyncTask;
import me.kkdevs.welcome.BarManager;

import java.util.Arrays;
import java.util.List;

import static me.kkdevs.welcome.Loader.*;

public class UpdaterTask extends AsyncTask {

    private int BAR_LENGTH = 0, MESSAGES_SIZE = -1;
    private static List<String> messagesList;

    static {
        try {
            messagesList = config.getStringList("messages");
        } catch (Exception exception) {
            exception.printStackTrace();
            messagesList = Arrays.asList("Missing entries in the config", "Please declare an entry in the config plugins");
        }
    }

    @Override
    public void onRun() {
        BAR_LENGTH += 10;
        if (BAR_LENGTH > 100) {
            BAR_LENGTH = 10;
        }

        Server.getInstance().getOnlinePlayers().values().forEach(player -> {
            int length;
            if (config.getBoolean("flexible", true)) {
                length = BAR_LENGTH;
            } else {
                length = config.getInt("flexibleLength", 100);
            }

            MESSAGES_SIZE = MESSAGES_SIZE + 1;

            String message = messagesList.get(MESSAGES_SIZE);

            if (MESSAGES_SIZE == messagesList.size() - 1) {
                MESSAGES_SIZE = -1;
            }

            BarManager.updateBossBar(player, replace(message, player), length);
        });
    }
}