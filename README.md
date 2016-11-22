<p align="center">
  <img src="https://raw.githubusercontent.com/sultanskyman/WildSex/master/wildsex.png" alt="Project Logo" title="WildSex - Even your sheep need some fun!"/>
</p>

WildSex 3.3 ![Build status](https://travis-ci.org/sultanskyman/WildSex.svg)
=======
A Bukkit plugin that randomly puts wild animals in love mode, creating natural population increase.

[Download Now](https://github.com/sultanskyman/WildSex/releases)

Dependencies
-------------
The plugin depends on [BKCommonLib](https://www.spigotmc.org/resources/updated-bkcommonlib.7716/) for the detection of XP creation. If you wish to use the removeXP option, please install that plugin.

Configuration
-------------
The config.yml file supports the following settings:

* mateMode (false by default) pairs animals with a close pair and then sets both on love mode, guaranteeing reproduction in the case where there is an eligible pair close by.
* maxMateDistance (4 by default) the maximum distance two animals can be apart in order for the plugin to pair them
* interval (10 by default) the number between two WildSex runs, in minutes.
* chance (0.1 by default) the possibility of a random animal being set on love mode. 0 disables the plugin and 1 sets every animal on love mode on every run.
* maxAnimalsPerBlock (2 by default) how many animals should there be per one block? this is the maximum value where the population will plateau, effectively stopping reproduction
* maxAnimalsCheckRadius (3 by default) for how many blocks around the individual animal should the above limitation hold? For example, if you specify 2 animals and a check radius of 3, the plugin will check to see that there aren't more than 18 ( = 2 * 3 * 3 ) animals in the 3x3 area around the animal.
* autoUpdate (true by default) updates the plugin automatically whenever a new version is released. All versions will maintain backwards compatibility, so keeping this on is a good idea.
* removeXP (true by default) whether or not the XP produced by the breeding of the animals should be removed automatically.

Supported Versions
-------------------
All CraftBukkit & Spigot releases for:
* 1.10.2
* 1.11

(Older versions have been dropped with WildSex 3.3. You can try earlier versions for support.)

Known Bugs
----------
None right now

To do
----------
* Improve density checking

Special thanks to
----------
* [Gravity](https://github.com/gravitylow), for [Updater](https://github.com/gravitylow/Updater),
* [DrkMatr1984](https://github.com/DrkMatr1984), for helping maintain this project as a contributor.

Source
----------
Available for you to [fork on Github!](https://github.com/sultanskyman/WildSex)

License
----------
Released under the MIT License.

Feel free to download, edit and redistribute, but do not forget to give proper credit.
