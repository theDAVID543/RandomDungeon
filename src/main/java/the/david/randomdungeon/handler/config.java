package the.david.randomdungeon.handler;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import the.david.randomdungeon.RandomDungeon;
import the.david.randomdungeon.utils.Pair;
import the.david.randomdungeon.utils.Vector2;
import the.david.randomdungeon.utils.Vector3;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class config{
	public config(RandomDungeon plugin, String filePath){
		this.plugin = plugin;
		this.filePath = filePath;
	}

	private final RandomDungeon plugin;
	private File dataConfigFile;
	private FileConfiguration dataConfig;
	private final String filePath;
	public Map<String, String> defaults = new HashMap<>();

	public void createCustomConfig(){
		dataConfigFile = new File(plugin.instance.getDataFolder(), filePath);
		if(!dataConfigFile.exists()){
			dataConfigFile.getParentFile().mkdirs();
			try{
				dataConfigFile.createNewFile();
			}catch(IOException e){
				throw new RuntimeException(e);
			}
//            plugin.instance.saveResource(filePath, false);
		}
		dataConfig = new YamlConfiguration();
		try{
			dataConfig.load(dataConfigFile);
		}catch(IOException | InvalidConfigurationException e){
			e.printStackTrace();
		}
		defaults.forEach((k, v) -> {
			dataConfig.set(k, v);
		});
		try{
			dataConfig.save(dataConfigFile);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public void setObject(String path, Object value){
		dataConfig.set(path, value);
		try{
			dataConfig.save(dataConfigFile);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

//	public void setLocation(String path, Location location){
//		double x = location.getX();
//		double y = location.getY();
//		double z = location.getZ();
//		dataConfig.set(path + ".X", x);
//		dataConfig.set(path + ".Y", y);
//		dataConfig.set(path + ".Z", z);
//		try{
//			dataConfig.save(dataConfigFile);
//		}catch(IOException e){
//			throw new RuntimeException(e);
//		}
//	}
	public String vector2ToString(Vector2 vector2){
		return vector2.x + "," + vector2.z;
	}
	public void setDoor(String path, Vector2 doorGridPos, Vector3 doorPos1, Vector3 doorPos2){
		setVector3(path + "." + vector2ToString(doorGridPos) + ".Pos1", doorPos1);
		setVector3(path + "." + vector2ToString(doorGridPos) + ".Pos2", doorPos2);
	}
	public void setVector3(String path, Vector3 vector3){
		dataConfig.set(path + ".X", vector3.x);
		dataConfig.set(path + ".Y", vector3.y);
		dataConfig.set(path + ".Z", vector3.z);
		try{
			dataConfig.save(dataConfigFile);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public void removeKey(String path){
		dataConfig.getConfigurationSection(path).getKeys(true).forEach(v -> dataConfig.getConfigurationSection(path).set(v, null));
		dataConfig.set(path, null);
		try{
			dataConfig.save(dataConfigFile);
		}catch(IOException e){
			throw new RuntimeException(e);
		}
	}

	public Boolean hasKey(String path){
		return dataConfig.contains(path);
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
	public Map<Vector2, Pair<Vector3, Vector3>> getDoors(String path){
		Map<Vector2, Pair<Vector3, Vector3>> doors = new HashMap<>();
		if(dataConfig.getConfigurationSection(path) == null){
			return null;
		}
		dataConfig.getConfigurationSection(path).getKeys(false).forEach(v -> {
			Vector2 doorGridPos = new Vector2(Integer.parseInt(v.split(",")[0]), Integer.parseInt(v.split(",")[1]));
			Vector3 doorPos1 = getVector3(path + "." + v + ".Pos1");
			Vector3 doorPos2 = getVector3(path + "." + v + ".Pos2");
			doors.put(doorGridPos, new Pair<>(doorPos1, doorPos2));
		});
		return doors;
	}

	public Integer getInteger(String path){
		if(hasKey(path)){
			return dataConfig.getInt(path);
		}else{
			return null;
		}
	}

	public Location getLocation(String path){
		double x = dataConfig.getDouble(path + ".X");
		double y = dataConfig.getDouble(path + ".Y");
		double z = dataConfig.getDouble(path + ".Z");
		String fileName = dataConfigFile.getName();
		return new Location(Bukkit.getWorld(fileName.replaceAll(".yml", "")), x, y, z);
	}
	public Vector3 getVector3(String path){
		int x = dataConfig.getInt(path + ".X");
		int y = dataConfig.getInt(path + ".Y");
		int z = dataConfig.getInt(path + ".Z");
		return new Vector3(x, y, z);
	}

	public Boolean getBoolean(String path){
		return dataConfig.getBoolean(path);
	}
}
