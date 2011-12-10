package com.stratomine.bukkit.plugins;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class DumptruckPlugin extends JavaPlugin {
	
	private int activeTaskCount, totalTaskCount, progressTaskId;
	
	public void onEnable() {
		activeTaskCount = 0;
		totalTaskCount = 0;
		progressTaskId = -1;
		log("Loaded " + getDescription().getFullName() + ".");
	}
	
	public void onDisable() {
		activeTaskCount = 0;
		totalTaskCount = 0;
		progressTaskId = -1;
	}
	
	protected Logger getLogger() {
		return getServer().getLogger();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("dumptruck")) {
			return onDumptruck(sender, command, label, args);
		}
		
		return false;
	}
	
	private boolean onDumptruck(CommandSender sender, Command command, String label, String[] args) {
		if (progressTaskId > -1) {
			log("Chunk generation is already in progress.");
			return true;
		}
		
		int radius;
		
		if (args.length > 0) {
			radius = Integer.parseInt(args[0]);
			if (radius <= 0) {
				return false;
			}
		} else {
			return false;
		}
		
		List<World> worlds = getServer().getWorlds();
		for (World world : worlds) {
			generateChunks(world, radius);
		}
		
		log("Generating " + getTotalTaskCount() + " chunks in a " + radius + "-chunk radius around the spawn point...");
		
		long period = 20 * 5;
		progressTaskId = getServer().getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			private Date lastProgressCheck = new Date();
			private int lastActiveTaskCount = getTotalTaskCount();
			private DecimalFormat formatter = new DecimalFormat("0.0");
			
			public void run() {
				double progress = 100.0 - (((double)getActiveTaskCount() / getTotalTaskCount()) * 100);
				
				Date now = new Date();
				double rate = (double)(lastActiveTaskCount - getActiveTaskCount()) / ((now.getTime() - lastProgressCheck.getTime()) / 1000); 
				lastProgressCheck = now;
				lastActiveTaskCount = getActiveTaskCount();
				
				log("Generating chunks: " + formatter.format(progress) + "% (" + formatter.format(rate) + " chunks/second)");
			}
		}, period, period);
		
		return true;
	}
	
	private void generateChunks(World world, int radius) {
		BukkitScheduler scheduler = getServer().getScheduler();
		
		for (int x = radius * -1; x <= radius; x++) {
			for (int z = radius * -1; z <= radius; z++) {
				ChunkGeneratorTask task = new ChunkGeneratorTask(this, world, x, z);
				scheduler.scheduleSyncDelayedTask(this, task);
				incrementTaskCount();
			}
		}
	}
	
	private void onChunkGenerationComplete() {
		getServer().getScheduler().cancelTask(progressTaskId);
		progressTaskId = -1;
		log("Chunk generation complete.");
	}
	
	private synchronized int getActiveTaskCount() {
		return activeTaskCount;
	}
	
	private synchronized int getTotalTaskCount() {
		return totalTaskCount;
	}
	
	private synchronized void incrementTaskCount() {
		activeTaskCount++;
		totalTaskCount++;
	}
	
	protected synchronized void decrementTaskCount() {
		activeTaskCount--;
		
		if (activeTaskCount % 1000 == 0) {
			List<World> worlds = getServer().getWorlds();
			for (World world : worlds) {
				world.save();
			}
		}
		
		if (activeTaskCount <= 0) {
			onChunkGenerationComplete();
		}
	}

	public void log(Level level, String message) {
		message = "[" + getDescription().getName() + "] " + message;
		getLogger().log(level, message);
	}
	
	public void log(String message) {
		log(Level.INFO, message);
	}
	
}
