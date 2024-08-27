package de.revivemc.lobby.modules.color;

import de.revivemc.core.entitiesutils.items.ItemCreator;
import org.bukkit.inventory.ItemStack;

public enum Colors {

    GREEN("§a", "Grün", (new ItemCreator("9cb81a35d2b48d5fd81249369433c078b7c8bf42df5aa9c375c1ac85f4514")).setName("§8§ §a§lGrün").toItemStack()),
    DARK_GREEN("§2", "Dunkelgrün", (new ItemCreator("66d53a57685f25f293c7e9c7b4418d452f6931c53a9849a0b3ca109385577")).setName("§8§ §2§lDunkelgrün").toItemStack()),
    ORANGE("§e", "Gelb", (new ItemCreator("f7a9964f572fd03c32dfa2586155fa3d10e627df779a41f262fde82bfb41ba0")).setName("§8§ §e§lGelb").toItemStack()),
    YELLOW("§6", "Orange", (new ItemCreator("85bec1574de373dfb775ab58b2c1e4621d92c6daac7c6a74d78fb70fff4d0")).setName("§8§ §6§lOrange").toItemStack()),
    BLACK("§0", "Schwarz", (new ItemCreator("2342b9bf9f1f6295842b0efb591697b14451f803a165ae58d0dcebd98eacc")).setName("§8§ §0§lSchwarz").toItemStack()),
    GRAY("§8", "Grau", (new ItemCreator("36dc6d42495817b8d4db1311115d1f5cfbede6832192b52f32d48ef7d8b5d")).setName("§8§ §8§lGrau").toItemStack()),
    PURPLE("§5", "Lila", (new ItemCreator("6c4dc8338415b2b989ab79495986ec7895aa03679d2bcdd6d9ebb633b87fc4")).setName("§8§ §5§lLila").toItemStack()),
    RED("§c", "Rot", (new ItemCreator("f9a51f27d2d938897bc42a3fe2c3135da2671686f57824115f8f8da78a")).setName("§8§ §c§lRot").toItemStack()),
    PINK("§d", "Pink", (new ItemCreator("df387b69f94feea25671fc399eb75dbeac60f512a4bfe78e454171ee3fc0")).setName("§8§ §d§lPink").toItemStack()),
    WHITE("§f", "Weiß", (new ItemCreator("af3eaf4c15ad67c51dff9097bd7abd4a82bab7bed83ab77a6177f2e57b")).setName("§8§ §f§lWeiß").toItemStack()),
    LIGHTGRAY("§7", "Hellgrau", (new ItemCreator("1a4b0eaf5269a34e278cae8bc5c6cb11202344a94bd8f4a79ae6b812cd6cff")).setName("§8§ §7§lHellgrau").toItemStack()),
    BLUE("§9", "Blau", (new ItemCreator("1da76d6ddcdbb4973332155796c030cfa2fe855ce539e813e588c61d4d6bca5c")).setName("§8§ §9§lBlau").toItemStack()),
    HELLBLAU("§3", "Hellblau", (new ItemCreator("481899d07ed8a5441bb3faf1e19dcc97537cbdf91c703ebf85ac31c88198fc")).setName("§8§ §3§lHellblau").toItemStack()),
    CYAN("§b", "Türkis", (new ItemCreator("29e4a399befd967ec282c719fbfd644863281374dcb8137a72f7c4d013a73f")).setName("§8§ §b§lTürkis").toItemStack());

    String code;
    String name;
    ItemStack stack;

    private Colors(String code, String name, ItemStack stack) {
        this.code = code;
        this.name = name;
        this.stack = stack;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public ItemStack getStack() {
        return this.stack;
    }

}
