![Project Logo](https://raw.githubusercontent.com/sultanskyman/WildSex/master/wildsex.png "WildSex - Even your sheep need some fun!")

WildSex
=======

A Bukkit plugin that randomly puts wild animals in love mode, creating natural population increase.

Configuration
-------------
The config.yml file supports the following settings:

* mateMode (true by default) pairs animals with a close pair and then sets both on love mode, guaranteeing reproduction in the case where there is an eligible pair close by.
* interval (5 by default) the number between two WildSex runs, in minutes.
* chance (0.2 by default) the possibility of a random animal being set on love mode. 0 disables the plugin and 1 sets every animal on love mode on every run.

Known Bugs
----------
* Does not support CraftBukkit / Spigot versions apart from CB-1.7.9-R0.2
* Mate mode when activated matches two different species together!

To do
----------
* Implement class existence check so that wrong versions can't run the plugin at all.
* Produce copies for CB-1.7.10-R0.1 as well as Spigot
* Implement XP drop removal

License
----------
Protected under the MIT License.

Feel free to download, edit and redistribute, but do not forget to give proper credit.
