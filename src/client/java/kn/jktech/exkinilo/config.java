package kn.jktech.exkinilo;

import com.fox2code.foxloader.config.ConfigEntry;

public class config {
    public static final config INSTANCE = new config();
    public config(){}
    @ConfigEntry(configName = "no block break delay")
    public boolean noblockdelay = false;
    @ConfigEntry(configName = "flat nether")
    public boolean flatnether = true;

}
