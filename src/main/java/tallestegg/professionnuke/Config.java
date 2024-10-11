package tallestegg.professionnuke;

import com.google.common.collect.ImmutableList;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Config {
    public static final ModConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        final Pair<CommonConfig, ModConfigSpec> specPair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }

    public static class CommonConfig {
        public final ModConfigSpec.ConfigValue<List<? extends String>> professionBlacklist;
        public CommonConfig(ModConfigSpec.Builder builder) {
            professionBlacklist = builder.defineListAllowEmpty("Professions to be nuked", ImmutableList.of(""), () -> "", obj -> true);
        }
    }
}