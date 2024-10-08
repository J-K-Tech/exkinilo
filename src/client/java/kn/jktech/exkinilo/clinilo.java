package kn.jktech.exkinilo;

import com.fox2code.foxloader.client.helpers.BlockPortalHelper;
import com.fox2code.foxloader.loader.ClientMod;
import com.fox2code.foxloader.loader.Mod;
import com.fox2code.foxloader.registry.*;
import kn.jktech.exkinilo.blocks.*;
import kn.jktech.exkinilo.tools.sieve;
import kn.jktech.exkinilo.world.webworld;
import net.minecraft.client.Minecraft;
import net.minecraft.src.game.block.Block;
import net.minecraft.src.game.block.BlockLayerAsh;
import net.minecraft.src.game.block.BlockPortal;
import net.minecraft.src.game.block.Material;
import net.minecraft.src.game.item.*;
import net.minecraft.src.game.item.description.ItemDescMusicDisc;
import net.minecraft.src.game.level.WorldProviderHell;

import java.io.*;
import java.net.URL;


public class clinilo extends Mod implements ClientMod {
    public static int ROCK;
    public static int MAGNETITE;
    public static int MAGNET;
    public static int COMPRESSEDCOAL;
    public static int VAPORCOLEK;
    public static int   MXIDLE;
    public static int   MXACTIVE;
    public static int COOLANTBUCKET;
    public static int PEBBLES;
    public static RegisteredBlock COOLANTFLOW;
    public static RegisteredBlock PEBBLESTONE;
    public static RegisteredBlock BOULDERSTONE;
    public static File RIND_FOLDER;
    public static File SOUNDS;
    public static int FIREWOOD;
    public static int SWARD;


    public void downloadRes(File sound,String sounds) throws IOException {
        byte[] var5 = new byte[4096];
        URL url=new URL("https://raw.githubusercontent.com/J-K-Tech/exkinilo-resources/main/"+sounds);
        DataInputStream dataInputStream = new DataInputStream(url.openStream());
        DataOutputStream dataOutputStream = null;
        try {
            dataOutputStream = new DataOutputStream(new FileOutputStream(sound));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int var9;
        while ((var9 = dataInputStream.read(var5)) >= 0) {
            dataOutputStream.write(var5, 0, var9);
        }

        dataInputStream.close();
        dataOutputStream.close();}

    @Override
    public void onInit() {
        setConfigObject(config.INSTANCE);
        RIND_FOLDER=new File(new File(getConfigFolder().getParent()).getParent());
        Minecraft mc= ClientMod.getGameInstance();
        SOUNDS=new File(RIND_FOLDER,"reindev-sound/");
        File soundfolder=new File(SOUNDS,"sound/kinilo/mixer");
        if (!soundfolder.exists()) {
            soundfolder.mkdirs();
        }
        String[] sounds={
        "kinilo/mixer/mixer_on.ogg","kinilo/mixer/mixer_off.ogg","kinilo/mixer/mixer.ogg",
        "kinilo/mixer/mixer_done.ogg","kinilo/mixer/mixer_eject.ogg","kinilo/mixer/mixer_pour.ogg",
        "kinilo/mixer/mixer_coal.ogg","kinilo/mixer/mixer_bucket.ogg","kinilo/blazing.ogg",
            "kinilo/smelter.ogg"
        };
        //https://raw.githubusercontent.com/J-K-Tech/exkinilo-resources/main/kinilo/mixer/mixer.ogg
        for (int i = 0; i < sounds.length; i++) {
            File sound=new File(SOUNDS,"sound/"+sounds[i]);
            if (!sound.exists()){
                try {
                    downloadRes(sound,sounds[i]);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            ClientMod.getGameInstance().sndManager.addSound(sounds[i],sound);
        }
        ClientMod.getGameInstance().sndManager.addSound("kinilo/mixer/mixer_on.ogg",
                new File(SOUNDS,"sound/kinilo/mixer/mixer_on.ogg"));
        ClientMod.getGameInstance().sndManager.addSound("kinilo/mixer/mixer_off.ogg",
                new File(SOUNDS,"sound/kinilo/mixer/mixer_off.ogg"));
        ClientMod.getGameInstance().sndManager.addSound("kinilo/mixer/mixer.ogg",
                new File(SOUNDS,"sound/kinilo/mixer/mixer.ogg"));
        ClientMod.getGameInstance().sndManager.addSound("kinilo/mixer/mixer_done.ogg",
                new File(SOUNDS,"sound/kinilo/mixer/mixer_done.ogg"));
        ClientMod.getGameInstance().sndManager.addSound("kinilo/mixer/mixer_eject.ogg",
                new File(SOUNDS,"sound/kinilo/mixer/mixer_eject.ogg"));
        ClientMod.getGameInstance().sndManager.addSound("kinilo/mixer/mixer_pour.ogg",
                new File(SOUNDS,"sound/kinilo/mixer/mixer_pour.ogg"));
        ClientMod.getGameInstance().sndManager.addSound("kinilo/mixer/mixer_coal.ogg",
                new File(SOUNDS,"sound/kinilo/mixer/mixer_coal.ogg"));
        ClientMod.getGameInstance().sndManager.addSound("kinilo/mixer/mixer_bucket.ogg",
                new File(SOUNDS,"sound/kinilo/mixer/mixer_bucket.ogg"));
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


        RegisteredBlock firewood=registerNewBlock("firewood",
                new BlockBuilder().setBlockName("firewood").setEffectiveTool(RegisteredToolType.AXE)

                        .setBlockHardness(2.0F)
                        .setBlockResistance(10.0F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.WOOD)
                        .setBurnRate(80,80)
                        .setGameBlockProvider((id,blockBuilder, ext)->new Firewood(id, Material.wood) {
                        })
        );

        RegisteredBlock sward=registerNewBlock("sward",
                new BlockBuilder().setBlockName("sward").setEffectiveTool(RegisteredToolType.SHOVEL)
                        .setBlockMaterial(GameRegistry.BuiltInMaterial.GRASS)
                        .setBlockHardness(.8F)
                        .setBlockStepSounds(GameRegistry.BuiltInStepSounds.GRASS)
        );
        RegisteredBlock portal=registerNewBlock("portal",
                new BlockBuilder().setBlockName("portal")
                        .setBlockHardness(-1)
                        .setGameBlockProvider((id, blockBuilder, ext) ->  new webverseportal(id,
                                new webworld(),"webverse"))
        );
        RegisteredBlock pebbles=registerNewBlock("pebbles",
                new BlockBuilder().setBlockName("pebbles").setEffectiveTool(RegisteredToolType.PICKAXE)
                        .setBlockHardness(1.0F)
                        .setBlockResistance(10.0F)
                        .setGameBlockProvider((id, blockBuilder, ext) ->  new pebbles(id,Material.ground))
        );


        RegisteredItemStack rock = registerNewItem("rock",new ItemBuilder()
                .setItemName("rock")
                .setGameItemProvider(((id, itemBuilder, block) ->new ItemPlaceable(id-256,(Block)pebbles)))
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
        FIREWOOD= firewood.getRegisteredBlockId();
        SWARD=sward.getRegisteredBlockId();
        PEBBLES=pebbles.getRegisteredBlockId();
        System.out.println(PEBBLESTONE);
        System.out.println(BOULDERSTONE);
        woodsieve.setRegisteredStackSize(1);
        ironsieve.setRegisteredStackSize(1);
        diamondsieve.setRegisteredStackSize(1);
        magnet.setRegisteredStackSize(1);
        ironstick.setRegisteredStackSize(1);
        diamondstick.setRegisteredStackSize(1);







        registerRecipe(new ItemStack(Block.cobblestone,1),"MM","MM",'M',rock);
        //registerRecipe(new ItemStack(portal.asRegisteredItem().getRegisteredItemId(),1),"M",'M',Block.dirt);
        registerRecipe(new ItemStack(sward.asRegisteredItem().getRegisteredItemId(),4),"MM","MM",'M',Block.grass);
        registerRecipe(new ItemStack(firewood.asRegisteredItem().getRegisteredItemId(),1),"MM","MM",'M',Block.log);
        registerRecipe(new ItemStack(ironstick.getRegisteredItem().getRegisteredItemId(),4),"M","M",'M',Item.ingotIron);
        registerRecipe(new ItemStack(diamondstick.getRegisteredItem().getRegisteredItemId(),4),"M","M",'M',Item.diamond);
        registerRecipe(new ItemStack(magnet.getRegisteredItem().getRegisteredItemId(),1),"S","S","M",'S',Item.ingotGold,'M',magnetite);

        registerRecipe(ironsieve,"SSS",
                                         "SMS",
                                         " S ",
                'S',ironstick,
                'M',Item.silk);
        registerRecipe(woodsieve,"SSS",
                "SMS",
                " S ",
                'S',Item.stick,
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

        registerRecipe(new ItemStack(smcore.asRegisteredItem().getRegisteredItemId(),1),
                "SMS",
                "SXS",
                "MXM",
                'S',Block.tallGrass,
                'M',Block.blockGold,
                'X',Item.diamond);



    }



}
