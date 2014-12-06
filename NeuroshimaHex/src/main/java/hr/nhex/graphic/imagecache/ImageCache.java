package hr.nhex.graphic.imagecache;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that caches images of tiles that are used in current NHex game.
 *
 * <p>It is used to avoid loading images every time from outside files.
 *
 * @author Luka Ruklic
 *
 */

public class ImageCache {

	/**
	 * Map that contains cache of tile pictures used in this NHex game.
	 */
	private Map<String, BufferedImage> imageCache = new HashMap<>();

	/**
	 * Method that adds new image to cache.
	 *
	 * @param imageName name of new image that is being added to cache
	 * @param img instance of BufferedImage that is added to cache
	 */
	public void addToCache(String imageName, BufferedImage img) {
		imageCache.put(imageName, img);
	}

	/**
	 * Getter methods for cached images.
	 *
	 * @param imageName cache name for saved image
	 * @return instance of BufferedImage if image with given name exists, <code>null</code> otherwise
	 */
	public BufferedImage getImage(String imageName) {
		return imageCache.get(imageName);
	}

	/**
	 * Method that clears cache.
	 */
	public void clearCache() {
		imageCache.clear();
	}

}
