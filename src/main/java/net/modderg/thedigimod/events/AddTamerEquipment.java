package net.modderg.thedigimod.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.registries.RegistryObject;
import net.modderg.thedigimod.item.DigiItems;

import java.util.Random;

public class AddTamerEquipment {

    public static RegistryObject<?> chooseItem(RegistryObject<?>[] babies) {
        Random rand = new Random();
        return babies[rand.nextInt(babies.length)];
    }

    static RegistryObject<?>[] babies = {DigiItems.BOTAMON, DigiItems.BOTAMOND, DigiItems.BUBBMON, DigiItems.BUBBMONK, DigiItems.PUNIMON,
            DigiItems.JYARIMON,  DigiItems.PETITMON, DigiItems.PUYOMON, DigiItems.DOKIMON, DigiItems.NYOKIMON, DigiItems.CONOMON, DigiItems.KIIMON};

    static RegistryObject<?>[] vices = {DigiItems.VPET,  DigiItems.DIGIVICE, DigiItems.VITALBRACELET, DigiItems.DIGIVICE_IC, DigiItems.DIGIVICE_BURST};

    @Mod.EventBusSubscriber
    public static class FirstJoinProcedure {
        @SubscribeEvent
        public static void onPlayerLoggedIn(PlayerEvent.PlayerLoggedInEvent event) {
            execute(event.getEntity());
        }

        private static void execute(Entity entity) {
            if (entity == null)
                return;
            if (!(entity.getCapability(TheDigiModVariables.PLAYER_VARIABLES_CAPABILITY, null).orElse(new TheDigiModVariables.PlayerVariables())).firstJoin) {
                {
                    boolean _setval = true;
                    entity.getCapability(TheDigiModVariables.PLAYER_VARIABLES_CAPABILITY, null).ifPresent(capability -> {
                        capability.firstJoin = _setval;
                        capability.syncPlayerVariables(entity);
                    });
                }
                if (entity instanceof Player _player) {
                    ItemStack _setstack = new ItemStack((ItemLike) chooseItem(babies).get());
                    ItemHandlerHelper.giveItemToPlayer(_player, _setstack);
                    _setstack = new ItemStack((ItemLike) chooseItem(vices).get());
                    ItemHandlerHelper.giveItemToPlayer(_player, _setstack);

                    ItemHandlerHelper.giveItemToPlayer(_player, new ItemStack(DigiItems.TRAINING_BAG.get()));
                    ItemHandlerHelper.giveItemToPlayer(_player, new ItemStack(DigiItems.DIGI_MEAT.get(), 20));
                }
            }
        }
    }
}
