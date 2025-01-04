package tallestegg.professionnuke;

import com.google.common.collect.ImmutableList;
import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

public class Config {
    public static final ForgeConfigSpec COMMON_SPEC;
    public static final CommonConfig COMMON;

    static {
        final Pair<CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON = specPair.getLeft();
        COMMON_SPEC = specPair.getRight();
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.ConfigValue<List<? extends String>> professionBlacklist;
        public CommonConfig(ForgeConfigSpec.Builder builder) {
            professionBlacklist = builder.comment("To deactivate a villager profession, simply add the profession name to this list. If you wanted to remove librarians for example, simply add \"librarian\". If you wanted to add multiple professions to be removed, simply add a comma in between entries.").defineList("Professions to be nuked", ImmutableList.of(""), obj -> true);
        }
    }
}