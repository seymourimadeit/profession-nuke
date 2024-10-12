Mod that nukes unwanted villager professions from the game.

The way this mod works is that it mixins into the VillagerData class and modifies the setProfession method to detect if the set profession is on the unwanted list in the config, if it is on the unwanted list it will force the profession to be set as unemployed. It also mixins into the GoToPotentialJobSite class
to forcibly remove the matching job site memory.
