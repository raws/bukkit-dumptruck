package com.stratomine.bukkit.plugins;

import java.io.File;
import java.util.logging.Level;

import org.bukkit.World;

public class ChunkGeneratorTask implements Runnable {
	
	private DumptruckPlugin dumptruck;
	private World world;
	private int x, z;
	
	public ChunkGeneratorTask(DumptruckPlugin dumptruck, World world, int x, int z) {
		this.dumptruck = dumptruck;
		this.world = world;
		this.x = x;
		this.z = z;
	}
	
	public void run() {
		File chunkFile = new File(world.getWorldFolder(), "region/r." + x + "." + z + ".mcr");
		
		if ((!world.isChunkLoaded(x, z) || !chunkFile.exists()) && world.loadChunk(x, z, true)) {
			boolean safe = dumptruck.getServer().getOnlinePlayers().length > 0;
			world.unloadChunk(x, z, true, safe);
		}
		
		dumptruck.decrementTaskCount();
	}
	
	private void log(String message) {
		dumptruck.log(message);
	}
	
	private void log(Level level, String message) {
		dumptruck.log(level, message);
	}

}
