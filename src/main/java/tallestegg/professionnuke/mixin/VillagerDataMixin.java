package tallestegg.professionnuke.mixin;

import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tallestegg.professionnuke.Config;

@Mixin(VillagerData.class)
public class VillagerDataMixin {
    @Shadow
    @Final
    private VillagerType type;

    @Shadow
    @Final
    private int level;

    @Inject(at = @At("RETURN"), method = "setProfession", cancellable = true)
    public void setProfession(VillagerProfession profession, CallbackInfoReturnable<VillagerData> cir) {
        if (Config.COMMON.professionBlacklist.get().contains(profession.name())) {
            cir.setReturnValue(new VillagerData(this.type, VillagerProfession.NONE, this.level));
        }
    }
}
