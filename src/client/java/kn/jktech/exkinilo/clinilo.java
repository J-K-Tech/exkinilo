package kn.jktech.exkinilo;

import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.*;
import kn.jktech.exkinilo.blocks.mixer;
import kn.jktech.exkinilo.blocks.smelteryCore;
import kn.jktech.exkinilo.blocks.vaporlek;
import kn.jktech.exkinilo.blocks.flowCoolant;
import kn.jktech.exkinilo.blocks.stillCoolant;
import kn.jktech.exkinilo.tools.sieve;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.item.*;


public class clinilo extends Mod implements ClientMod {
    public static int ROCK;
    public static int MAGNETITE;
    public static int MAGNET;
    public static int COMPRESSEDCOAL;
    public static int VAPORCOLEK;
    public static int   MXIDLE;
    public static int   MXACTIVE;
    public static int COOLANTBUCKET;
    public static RegisteredBlock COOLANTFLOW;
    public static RegisteredBlock PEBBLESTONE;
    public static RegisteredBlock BOULDERSTONE;
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
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.STONE)
        );
        RegisteredBlock pebblestone=registerNewBlock("pebblestone",
                new BlockBuilder().setBlockName("pebblestone").setEffectiveTool(RegisteredToolType.PICKAXE)

                        .setBlockHardness(2.0F)
                        .setBlockResistance(10.0F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.STONE)
        );
        RegisteredBlock boulderstone=registerNewBlock("boulderstone",
                new BlockBuilder().setBlockName("boulderstone").setEffectiveTool(RegisteredToolType.PICKAXE)

                        .setBlockHardness(2.0F)
                        .setBlockResistance(10.0F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.STONE)
        );


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
        RegisteredBlock coolantflow=registerNewBlock("coolant_flow",
                new BlockBuilder().setBlockName("coolant_flow")
                        .setGameBlockProvider((id,blockBuilder, ext)->new flowCoolant(id, Material.snow) {
                        })
        );
        RegisteredBlock coolant=registerNewBlock("coolant",
                new BlockBuilder().setBlockName("coolant")
                        .setGameBlockProvider((id,blockBuilder, ext)->new stillCoolant(id, Material.snow) {
                        })
        );
        RegisteredItemStack coolantbucket = registerNewItem("coolant_bucket",new ItemBuilder()
                .setItemName("coolant_bucket").setGameItemProvider(((id, itemBuilder, ext) -> new ItemBucket(id-256,coolantflow.getRegisteredBlockId()))
        )).newRegisteredItemStack();

        RegisteredBlock watergen=registerNewBlock("vapor_collector",
                new BlockBuilder().setBlockName("vapor collector").setEffectiveTool(RegisteredToolType.PICKAXE)
                        .setBlockHardness(2.0F)
                        .setBlockResistance(10.0F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.STONE)
                        .setGameBlockProvider((id,blockBuilder, ext)->new vaporlek(id, Material.iron))
        );
        RegisteredBlock smcore=registerNewBlock("smeltery_core",
                new BlockBuilder().setBlockName("smeltery core").setEffectiveTool(RegisteredToolType.PICKAXE)
                        .setBlockHardness(2.0F)
                        .setBlockResistance(10.0F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.STONE)
                        .setGameBlockProvider((id,blockBuilder, ext)->new smelteryCore(id, Material.iron))

        );
        RegisteredBlock mixeridle=registerNewBlock("mixer_idle",
                new BlockBuilder().setBlockName("mixer_idle").setEffectiveTool(RegisteredToolType.PICKAXE)
                        .setBlockHardness(2.0F)
                        .setBlockResistance(10.0F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.STONE)
                        .setGameBlockProvider((id,blockBuilder, ext)->new mixer(id, Material.iron,false))
        );
        RegisteredBlock mixeractive=registerNewBlock("mixer_active",
                new BlockBuilder().setBlockName("mixer_active").setEffectiveTool(RegisteredToolType.PICKAXE)
                        .setBlockHardness(2.0F)
                        .setBlockResistance(10.0F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.STONE)
                        .setGameBlockProvider((id,blockBuilder, ext)->new mixer(id, Material.iron,true))

        );
        RegisteredItemStack hammer = registerNewItem("hammer",new ItemBuilder()
                .setItemName("hammer").setGameItemProvider(((id, itemBuilder, ext) -> new sieve(id-256,0, EnumToolMaterial.valueOf("HAMMER"),EnumTools.valueOf("HAMMER"))))
        ).newRegisteredItemStack();
        COOLANTBUCKET=coolantbucket.getRegisteredItem().getRegisteredItemId();
        ROCK=rock.getRegisteredItem().getRegisteredItemId();
        MAGNET=magnet.getRegisteredItem().getRegisteredItemId();
        MAGNETITE=magnetite.getRegisteredItem().getRegisteredItemId();
        COMPRESSEDCOAL=comppressedcoal.getRegisteredBlockId();
        VAPORCOLEK=watergen.getRegisteredBlockId();
        MXIDLE=mixeridle.getRegisteredBlockId();
        MXACTIVE=mixeractive.getRegisteredBlockId();
        COOLANTFLOW=coolantflow;
        PEBBLESTONE=pebblestone;
        BOULDERSTONE=boulderstone;
        System.out.println(PEBBLESTONE);
        System.out.println(BOULDERSTONE);
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
        registerRecipe(hammer,"MMM",
                " S ",
                " S ",
                'S',diamondstick,
                'M',Block.blockIron);

        registerRecipe(new ItemStack(Block.oreIron,1),
        "SSS",
                "SMS",
                "SSS",
                'S',Block.gravel,
                'M',magnet);

        registerRecipe(new ItemStack(comppressedcoal.asRegisteredItem().getRegisteredItemId(),1),
        "SSS",
                "SSS",
                "SSS",
                'S',Block.blockCoal);

        registerRecipe(new ItemStack(watergen.asRegisteredItem().getRegisteredItemId(),1),
                "SSS",
                "M M",
                "SSS",
                'S',Block.stone,
                'M',Block.glass);

        registerBlastFurnaceRecipe(comppressedcoal.asRegisteredItem(),new ItemStack(Item.diamond));
        registerFreezerRecipe(new ItemStack(Block.stone).getRegisteredItem(),new ItemStack(Block.basalt));


        registerRecipe(new ItemStack(mixeridle.asRegisteredItem().getRegisteredItemId(),1),
                "SSS",
                "MXM",
                "SSS",
                'S',Block.stone,
                'M',Block.glass,
                'X',Item.ingotIron);

        registerRecipe(new ItemStack(smcore.asRegisteredItem().getRegisteredItemId(),1),
                "MXM",
                "SXS",
                "MSM",
                'S',Block.stone,
                'M',Item.ingotIron,
                'X',comppressedcoal);

    }



}
