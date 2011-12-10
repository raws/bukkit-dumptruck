Dumptruck is a [Bukkit](http://bukkit.org/) plugin for quickly generating a large swath of terrain around your server's spawn point.

Dumptruck focuses on speed and simplicity, and is ideal for previewing map seeds or tweaking custom terrain generation parameters.

### Installation and usage

To install Dumptruck, simply drop `Dumptruck.jar` into your Bukkit server's `plugins` directory.

When loading lots of terrain at speed, the more RAM, the better -- Java's VM will generally siphon up as much as you give it via `-Xms` and `-Xmx`:

    java -Xms4G -Xmx4G -Xincgc -jar craftbukkit.jar

If memory efficiency is your priority over speed, you may want to look into a tool like Morlok8k's useful [Minecraft Land Generator](http://www.minecraftforum.net/topic/187737-minecraft-land-generator/).

Dumptruck is meant to be used on an empty, inaccessible server where all available resources can be put to work creating your world. If there _are_ players connected, Dumptruck will try not to unload any occupied chunks. However, this may incite a performance penalty, and Dumptruck makes no guarantees about chunk safety! Tread carefully on live servers.

Dumptruck exposes one simple command. To generate chunks of terrain around the server's spawn point, use:

    dumptruck <radius>

Where `<radius>` is a number of chunks to generate around the spawn point.

Dumptruck is meant to be used from the server's console, but server ops (and anyone with the `dumptruck` permission) may also use `/dumptruck` in-game.
  
### License <small>(MIT)</small>

<small>Copyright © 2011 Ross Paffett.</small>

<small>Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:</small>

<small>The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.</small>

<small>THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.</small>
