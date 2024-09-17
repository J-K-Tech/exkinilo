package kn.jktech.exkinilo;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.*;
import kn.jktech.exkinilo.tools.sieve;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.item.EnumToolMaterial;
import net.minecraft.src.game.item.EnumTools;
import net.minecraft.src.game.item.Item;
import net.minecraft.src.game.item.ItemStack;

import static net.minecraft.src.game.block.Block.soundStone;


public class clinilo extends Mod implements ClientMod {
    public static int ROCK;
    public static int MAGNETITE;
    public static int MAGNET;
    public static int COMPRESSEDCOAL;
    public static int VAPORCOLEK;
    @Override
    public void onInit() {
        RegisteredItemStack woodsieve = registerNewItem(
        "wooden_sieve",new ItemBuilder()
            .setItemName("wooden sieve").setGameItemProvider
            (
                (
                    (id, itemBuilder,ext) ->
                    new sieve(id-256,0, EnumToolMaterial.WOOD,EnumTools.valueOf("SIEVE"))
                )
            )
        ).newRegisteredItemStack();

        RegisteredBlock comppressedcoal=registerNewBlock("compressed_coal",
                new BlockBuilder().setBlockName("compressed coal").setEffectiveTool(RegisteredToolType.PICKAXE)

                        .setBlockHardness(4.0F)
                        .setBlockResistance(20.0F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.STONE));
        RegisteredItemStack ironsieve = registerNewItem("iron_sieve",new ItemBuilder()
                .setItemName("iron sieve").setGameItemProvider(((id, itemBuilder, ext) -> new sieve(id-256,0, EnumToolMaterial.IRON,EnumTools.valueOf("SIEVE"))))
        ).newRegisteredItemStack();
        RegisteredItemStack diamondsieve = registerNewItem("diamond_sieve",new ItemBuilder()
                .setItemName("diamond sieve").setGameItemProvider(((id, itemBuilder, ext) -> new sieve(id-256,0, EnumToolMaterial.EMERALD,EnumTools.valueOf("SIEVE"))))
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
        RegisteredItemStack magnetite = registerNewItem("magnetite",new ItemBuilder()
                .setItemName("magnetite")
        ).newRegisteredItemStack();
        RegisteredItemStack magnet = registerNewItem("magnet",new ItemBuilder()
                .setItemName("magnet")
        ).newRegisteredItemStack();

        RegisteredBlock watergen=registerNewBlock("vapor collector",
                new BlockBuilder().setBlockName("vapor collector").setEffectiveTool(RegisteredToolType.PICKAXE)

                        .setBlockHardness(2.0F)
                        .setBlockResistance(10.0F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.STONE));


        ROCK=rock.getRegisteredItem().getRegisteredItemId();
        MAGNET=magnet.getRegisteredItem().getRegisteredItemId();
        MAGNETITE=magnetite.getRegisteredItem().getRegisteredItemId();
        COMPRESSEDCOAL=comppressedcoal.getRegisteredBlockId();
        VAPORCOLEK=watergen.getRegisteredBlockId();
        woodsieve.setRegisteredStackSize(1);
        ironsieve.setRegisteredStackSize(1);
        diamondsieve.setRegisteredStackSize(1);
        magnet.setRegisteredStackSize(1);
        ironstick.setRegisteredStackSize(64);
        diamondstick.setRegisteredStackSize(64);

        registerRecipe(new ItemStack(Block.cobblestone,1),"MM","MM",'M',rock);

        registerRecipe(new ItemStack(ironstick.getRegisteredItem().getRegisteredItemId(),4),"M","M",'M',Item.ingotIron);
        registerRecipe(diamondstick,"M","M",'M',Item.diamond);
        registerRecipe(magnet,"S","S","M",'S',Item.ingotGold,'M',magnetite);

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

        registerRecipe(new ItemStack(Block.oreIron,1),
        "SSS",
                "SMS",
                "SSS",
                'S',Block.gravel,
                'M',magnet);

        registerRecipe(new ItemStack(comppressedcoal.getRegisteredBlockId(),1),
        "SSS",
                "SSS",
                "SSS",
                'S',Block.blockCoal);
        registerBlastFurnaceRecipe(comppressedcoal.asRegisteredItem(),new ItemStack(Item.diamond));
        registerFreezerRecipe(new ItemStack(Block.stone).getRegisteredItem(),new ItemStack(Block.basalt));
    }


}
