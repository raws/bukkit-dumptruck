package com.stratomine.bukkit.plugins;

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
		if (world.loadChunk(x, z, true)) {
			world.unloadChunk(x, z);
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
