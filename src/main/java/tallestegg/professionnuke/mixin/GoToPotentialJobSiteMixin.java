package tallestegg.professionnuke.mixin;

import net.minecraft.core.GlobalPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.GoToPotentialJobSite;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tallestegg.professionnuke.Config;

import java.util.Map;
import java.util.Optional;

@Mixin(GoToPotentialJobSite.class)
public abstract class GoToPotentialJobSiteMixin extends Behavior<Villager> {
    public GoToPotentialJobSiteMixin(Map<MemoryModuleType<?>, MemoryStatus> entryCondition) {
        super(entryCondition);
    }

    @Inject(at = @At("RETURN"), method = "tick(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/npc/Villager;J)V", cancellable = true)
    protected void tick(ServerLevel level, Villager owner, long gameTime, CallbackInfo ci) {
        GlobalPos jobSitePos = owner.getBrain().getMemory(MemoryModuleType.POTENTIAL_JOB_SITE).get();
        Optional.ofNullable(level.getLevel())
                .flatMap(serverLevel -> serverLevel.getPoiManager().getType(jobSitePos.pos()))
                .flatMap(
                        poiTypeHolder -> BuiltInRegistries.VILLAGER_PROFESSION
                                .stream()
                                .filter(profession -> Config.COMMON.professionBlacklist.get().contains(profession.name()) && profession.heldJobSite().test(poiTypeHolder))
                                .findFirst()
                )
                .ifPresent(profession -> {
                    owner.getBrain().eraseMemory(MemoryModuleType.POTENTIAL_JOB_SITE);
                    owner.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
                    owner.getBrain().eraseMemory(MemoryModuleType.LOOK_TARGET);
                });
    }
}
