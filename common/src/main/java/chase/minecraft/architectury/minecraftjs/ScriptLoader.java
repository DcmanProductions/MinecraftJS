package chase.minecraft.architectury.minecraftjs;

import chase.minecraft.architectury.minecraftjs.data.Script;
import com.google.gson.Gson;
import dev.architectury.platform.Platform;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static chase.minecraft.architectury.minecraftjs.MinecraftJS.log;

public class ScriptLoader
{
	public static ScriptLoader Instance = new ScriptLoader();
	public HashMap<String, Script> loadedScripts;
	
	private ScriptLoader()
	{
		Instance = this;
		loadedScripts = new HashMap<>();
	}
	
	public void LoadScripts()
	{
		log.info("Loading scripts");
		File scriptsDirectory = Path.of(Platform.getGameFolder().toAbsolutePath().toString(), "scripts").toFile();
		boolean ignored = scriptsDirectory.mkdirs();
		File[] scripts = scriptsDirectory.listFiles(new FileFilter()
		{
			@Override
			public boolean accept(File pathname)
			{
				return pathname.getName().endsWith(".mcjs");
			}
		});
		assert scripts != null;
		int index = 0;
		for (File script : scripts)
		{
			if (LoadScript(script))
			{
				index++;
			}
		}
		log.info("Loaded {} scripts", index);
	}
	
	public boolean LoadScript(File script)
	{
		try
		{
			log.info("Loading script: {}", script.getName());
			int parsed = 0;
			// Open the MinecraftJS archive file
			try (FileInputStream fileInputStream = new FileInputStream(script))
			{
				try (ZipInputStream zipInputStream = new ZipInputStream(fileInputStream))
				{
					ZipEntry entry;
					while ((entry = zipInputStream.getNextEntry()) != null)
					{
						if (!entry.isDirectory())
						{
							if (entry.getName().equalsIgnoreCase("manifest.json"))
							{
								InputStreamReader reader = new InputStreamReader(zipInputStream, StandardCharsets.UTF_8);
								// Parse JSON using GSON
								Gson gson = new Gson();
								Script json = gson.fromJson(reader, Script.class);
								loadedScripts.put(json.id(), json);
								parsed++;
							} else if (entry.getName().equalsIgnoreCase("main.js"))
							{
								InputStreamReader streamReader = new InputStreamReader(zipInputStream, StandardCharsets.UTF_8);
								BufferedReader reader = new BufferedReader(streamReader);
								StringBuilder builder = new StringBuilder();
								String line;
								while ((line = reader.readLine()) != null)
								{
									builder.append(line);
								}
								
								// Load Javascript File
								try (Context context = Context.newBuilder("js").allowAllAccess(true).build())
								{
									// Put the Java object into the JavaScript context
									context.getBindings("js").putMember("minecraft", new MinecraftWrapper());
									
									// Execute the JavaScript code
									Value response = context.eval("js", builder.toString());
									
									if (!response.isNull())
									{
										parsed++;
									}
								}
							}
							
							if (parsed == 2)
							{
								return true;
							}
						}
					}
				}
			}
			return true;
		} catch (Exception e)
		{
			log.error("Unable to load script \"{}\": {}", script.getName(), e.getMessage());
			e.printStackTrace();
		}
		return false;
	}
}
