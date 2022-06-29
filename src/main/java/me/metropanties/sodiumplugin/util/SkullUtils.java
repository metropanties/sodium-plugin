package me.metropanties.sodiumplugin.util;

import com.cryptomorin.xseries.XMaterial;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.UUID;

public class SkullUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SkullUtils.class);

    @NotNull
    public static ItemStack getSkull(@NotNull String texture) {
        assert XMaterial.PLAYER_HEAD.parseMaterial() != null;
        final ItemStack skull = new ItemStack(XMaterial.PLAYER_HEAD.parseMaterial());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        assert meta != null;
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", texture));
        try {
            Field profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOGGER.error("Failed setting skull texture!", e);
        }

        skull.setItemMeta(meta);
        return skull;
    }

}
