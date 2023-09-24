package the.david.randomdungeon.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import the.david.randomdungeon.RandomDungeon;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class config {
    public config(RandomDungeon plugin, String filePath){
        this.plugin = plugin;
        this.filePath = filePath;
    }
    private RandomDungeon plugin;
    private File dataConfigFile;
    private FileConfiguration dataConfig;
    private String filePath;
    public Map<String,String> defaults = new HashMap<>();
    public void createCustomConfig() {
        dataConfigFile = new File(plugin.instance.getDataFolder(), filePath);
        if (!dataConfigFile.exists()) {
            dataConfigFile.getParentFile().mkdirs();
            try {
                dataConfigFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
//            plugin.instance.saveResource(filePath, false);
        }
        dataConfig = new YamlConfiguration();
        try {
            dataConfig.load(dataConfigFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }
        defaults.forEach((k,v) ->{
            dataConfig.set(k,v);
        });
        try {
            dataConfig.save(dataConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setObject(String path, Object value){
        dataConfig.set(path, value);
        try {
            dataConfig.save(dataConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setLocation(String path, Location location){
        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();
        dataConfig.set(path + ".X", x);
        dataConfig.set(path + ".Y", y);
        dataConfig.set(path + ".Z", z);
        try {
            dataConfig.save(dataConfigFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public String getString(String path){
        return dataConfig.getString(path);
    }
    public Set<String> getKeys(@Nullable String path){
        if(path != null){
            if(dataConfig.getConfigurationSection(path) == null){
                return null;
            }
            return dataConfig.getConfigurationSection(path).getKeys(false);
        }else{
            return dataConfig.getKeys(false);
        }
    }
    public Location getLocation(String path){
        double x = dataConfig.getDouble(path + ".X");
        double y = dataConfig.getDouble(path + ".Y");
        double z = dataConfig.getDouble(path + ".Z");
        String fileName = dataConfigFile.getName();
        return new Location(Bukkit.getWorld(fileName.replaceAll(".yml", "")), x, y, z);
    }
}
