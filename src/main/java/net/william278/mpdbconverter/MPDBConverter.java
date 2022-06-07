package net.william278.mpdbconverter;

import net.craftersland.data.bridge.PD;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

/**
 * Class used to convert <a href="https://www.spigotmc.org/resources/mysql-player-data-bridge.8117/">MySQLPlayerDataBridge</a>
 * serialized data to Spigot API {@link ItemStack} data.
 */
public class MPDBConverter {

    /**
     * Working instance of the MySQLPlayerDataBridge plugin API
     */
    @NotNull
    private final PD mySqlPlayerDataBridge;

    /**
     * <b>(Internal use only)</b> Initialize the converter
     *
     * @param mySqlPlayerDataBridge Instance of the MySQLPlayerDataBridge {@link Plugin};
     * @throws ClassCastException If the plugin instance passed was not valid
     */
    private MPDBConverter(@NotNull Plugin mySqlPlayerDataBridge) throws ClassCastException {
        this.mySqlPlayerDataBridge = (PD) mySqlPlayerDataBridge;
    }

    /**
     * Get an instance of the {@link MPDBConverter}
     *
     * @param mySqlPlayerDataBridge Instance of the MySQLPlayerDataBridge {@link Plugin};
     *                              can be retrieved via {@link org.bukkit.plugin.PluginManager#getPlugin(String)}
     * @return Instance of the {@link MPDBConverter}
     */
    public static MPDBConverter getInstance(@NotNull Plugin mySqlPlayerDataBridge) {
        try {
            return new MPDBConverter(mySqlPlayerDataBridge);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Invalid instance of MySQLPlayerDataBridge passed");
        }
    }

    /**
     * Returns a Spigot {@link ItemStack[]} array from serialized MySQLPlayerDataBridge
     *
     * @param data the serialized MySQLPlayerDataBridge ItemStack data
     * @return the deserialized and converted ItemStacks in an array
     * @throws IllegalArgumentException if invalid data was passed
     */
    public ItemStack[] getItemStackFromSerializedData(String data) throws IllegalArgumentException {
        if (data.isEmpty() || data.equals("none")) {
            return new ItemStack[0];
        }
        try {
            return mySqlPlayerDataBridge.getItemStackSerializer().fromBase64(data);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new IllegalArgumentException("Invalid data was passed to the deserializer; " + e.getMessage());
        }
    }

}
