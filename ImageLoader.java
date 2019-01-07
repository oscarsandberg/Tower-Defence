import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Loads image as buffered image from res.
 * Nothing to initialize and does not need a constructor.
 */
public final class ImageLoader
{
    private static final Logger LOGGER = Logger.getLogger(ImageLoader.class.getName());
    private ImageLoader() {}

    /**
     * Loads image as buffered image from res.
     *
     * @param path File location.
     *
     * @return Image.
     */
    public static BufferedImage loadImage(String path) {
	try {
	    BufferedImage bimg = ImageIO.read(new File(ImageLoader.class.getResource(path).toURI()));
	    return bimg;
	} catch (IOException | URISyntaxException e) {
	    LOGGER.log(Level.SEVERE, "ImageLoader errors", e); // Logging errors.
	    System.exit(1);
	    e.printStackTrace();
	}
	return null;
    }
}
