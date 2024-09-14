package kn.jktech.exkinilo;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.ItemBuilder;
import com.fox2code.foxloader.registry.RegisteredItemStack;
import kn.jktech.exkinilo.tools.sieve;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.item.EnumToolMaterial;
import net.minecraft.src.game.item.EnumTools;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemStack;


public class clinilo extends Mod implements ClientMod {
    public static int ROCK;
    @Override
    public void onInit() {
        RegisteredItemStack woodsieve = registerNewItem(
        "wooden_sieve",new ItemBuilder()
            .setItemName("wooden sieve").setGameItemProvider
            (
                (
                    (id, itemBuilder,ext) ->
                    new sieve(id-256,128, EnumToolMaterial.WOOD,EnumTools.valueOf("SIEVE"))
                )
            )
        ).newRegisteredItemStack();


        RegisteredItemStack ironsieve = registerNewItem("iron_sieve",new ItemBuilder()
                .setItemName("iron sieve").setGameItemProvider(((id, itemBuilder, ext) -> new sieve(id-256,1024, EnumToolMaterial.IRON,EnumTools.valueOf("SIEVE"))))
        ).newRegisteredItemStack();
        RegisteredItemStack diamondsieve = registerNewItem("diamond_sieve",new ItemBuilder()
                .setItemName("diamond sieve").setGameItemProvider(((id, itemBuilder, ext) -> new sieve(id-256,8196, EnumToolMaterial.EMERALD,EnumTools.valueOf("SIEVE"))))
        ).newRegisteredItemStack();

        RegisteredItemStack ironstick = registerNewItem("iron_stick",new ItemBuilder()
                .setItemName("iron stick")
        ).newRegisteredItemStack();
        RegisteredItemStack diamondstick = registerNewItem("diamond_stick",new ItemBuilder()
                .setItemName("diamond stick")
        ).newRegisteredItemStack();
        RegisteredItemStack rock = registerNewItem("rock",new ItemBuilder()
                .setItemName("rock")
        ).newRegisteredItemStack();
        ROCK=rock.getRegisteredItem().getRegisteredItemId();
        woodsieve.setRegisteredStackSize(1);
        ironsieve.setRegisteredStackSize(1);
        diamondsieve.setRegisteredStackSize(1);
        ironstick.setRegisteredStackSize(64);
        diamondstick.setRegisteredStackSize(64);

        registerRecipe((RegisteredItemStack)(Object)new ItemStack(Block.cobblestone,1),"MM","MM",'M',rock);

        registerRecipe(ironstick,"M","M",'M',Item.ingotIron);
        registerRecipe(diamondstick,"M","M",'M',Item.diamond);

        registerRecipe(woodsieve,"SSS",
                                         "SMS",
                                         " S ",
                'S',Item.stick,
                'M',Item.silk);
        registerRecipe(ironsieve,"SSS",
                                         "SMS",
                                         " S ",
                'S',ironstick,
                'M',Item.silk);
        registerRecipe(diamondsieve,"SSS",
                                         "SMS",
                                         " S ",
                'S',diamondstick,
                'M',Item.silk);
    }
}
