package the.david.randomdungeon.handler;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import the.david.randomdungeon.RandomDungeon;

import java.io.File;
import java.io.IOException;

public class config {
    public config(RandomDungeon plugin, String filePath){
        this.plugin = plugin;
        this.filePath = filePath;
    }
    private RandomDungeon plugin;
    private File dataConfigFile;
    private FileConfiguration dataConfig;
    private String filePath;
    public void createCustomConfig() {
        dataConfigFile = new File(plugin.instance.getDataFolder(), filePath);
        if (!dataConfigFile.exists()) {
            dataConfigFile.getParentFile().mkdirs();
            plugin.instance.saveResource(filePath, false);
            try {
                dataConfigFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        dataConfig = new YamlConfiguration();
        try {
            dataConfig.load(dataConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
    }
}
