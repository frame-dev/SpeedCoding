package de.framedev.speedcoding.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.framedev.speedcoding.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class LocationManager {

    private final File file = new File(Main.getInstance().getDataFolder(),"locations.yml");
    public FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
    private boolean jsonFormat = true;

    public void setJsonFormat(boolean jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    public boolean isJsonFormat() {
        return jsonFormat;
    }

    private void save() {
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String locationToString(Location location) {
        return location.getWorld().getName() + ";" + location.getX() + ";" + location.getY() + ";" + location.getZ() + ";" + location.getYaw() + ";" + location.getPitch();
    }

    private Location locationFromString(String text) {
        String[] s = text.split(";");
        return new Location(Bukkit.getWorld(s[0]),Double.parseDouble(s[1]),Double.parseDouble(s[2]),Double.parseDouble(s[3]),Float.parseFloat(s[4]),Float.parseFloat(s[5]));
    }

    public void setLocation(String name, Location location) {
        if(isJsonFormat()) {
            final boolean[] success = new boolean[]{false};
            List<LocationToJson> locationToJsonList = getLocations();
            List<LocationToJson> updated = new ArrayList<>(getLocations());
            locationToJsonList.forEach(locationToJson -> {
                if(locationToJson.getLocationName().equalsIgnoreCase(name)) {
                    updated.remove(locationToJson);
                    updated.add(new LocationToJson(name, location));
                    success[0] = true;
                }
            });
            if(!success[0]) {
                updated.add(new LocationToJson(name, location));
            }
            saveLocations(updated);
        } else {
            cfg.set(name, locationToString(location));
            save();
        }
    }

    private void saveLocations(List<LocationToJson> updated) {
        File file = new File(Main.getInstance().getDataFolder(), "locations.json");
        try {
            if(!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(new GsonBuilder().serializeNulls().setPrettyPrinting().create().toJson(updated));
            fileWriter.flush();
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Location getLocation(String name) {
        if(isJsonFormat()) {
            LocationToJson loc = null;
            for (LocationToJson location : getLocations()) {
                if(location.getLocationName().equalsIgnoreCase(name))
                    loc = location;
            }
            if(loc != null) {
                return loc.getLocation();
            }
            return null;
        } else {
            if (cfg.contains(name))
                return locationFromString(Objects.requireNonNull(cfg.getString(name)));
            return null;
        }
    }

    public List<LocationToJson> getLocations() {
        File file = new File(Main.getInstance().getDataFolder(), "locations.json");
        LocationToJson[] locs = new LocationToJson[0];
        if(file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                locs = new Gson().fromJson(fileReader, LocationToJson[].class);
                fileReader.close();
            } catch (Exception ignored) {

            }
        }
        return Arrays.asList(locs);
    }

    public static class LocationToJson {

        private String locationName;
        private String worldName;
        private double x,y,z;
        private float yaw,pitch;

        public LocationToJson(String locationName, Location location) {
            this.locationName = locationName;
            this.worldName = location.getWorld().getName();
            this.x = location.getX();
            this.y = location.getY();
            this.z = location.getZ();
            this.yaw = location.getYaw();
            this.pitch = location.getPitch();
        }

        public String getLocationName() {
            return locationName;
        }

        public void setLocationName(String locationName) {
            this.locationName = locationName;
        }

        public String getWorldName() {
            return worldName;
        }

        public void setWorldName(String worldName) {
            this.worldName = worldName;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }

        public double getZ() {
            return z;
        }

        public void setZ(double z) {
            this.z = z;
        }

        public float getYaw() {
            return yaw;
        }

        public void setYaw(float yaw) {
            this.yaw = yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public void setPitch(float pitch) {
            this.pitch = pitch;
        }

        public Location getLocation() {
            return new Location(Bukkit.getWorld(worldName),x,y,z,yaw,pitch);
        }
    }
}
