package the.david.randomdungeon.dungeon;

import the.david.randomdungeon.RandomDungeon;

public class dungeonManager {
    public dungeonManager(RandomDungeon plugin){
        this.plugin = plugin;
    }
    RandomDungeon plugin;
    public Boolean createDungeon(String name){
        if(plugin.dungeonsConfig.getKeys("").contains(name)){
            return false;
        }
        return true;
    }
}
