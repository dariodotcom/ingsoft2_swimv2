package it.polimi.swim.business.helpers;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.MemoryImageSource;
import java.awt.image.PixelGrabber;
import java.io.IOException;
import java.io.Serializable;

public class SerializableImage implements Serializable {

	private static final long serialVersionUID = -9122355249426542735L;

	private int h, w;
	private int[] pixels;

	public SerializableImage(Image input) throws IOException {
		this.w = input.getWidth(null);
		this.h = input.getHeight(null);

		this.pixels = (input != null ? new int[w * h] : null);

		if (input != null) {
			try {
				PixelGrabber pg = new PixelGrabber(input, 0, 0, w, h,
						this.pixels, 0, w);
				pg.grabPixels();
				if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
					throw new IOException("failed to load image contents");
				}
			} catch (InterruptedException e) {
				throw new IOException("image load interrupted");
			}
		}
	}

	public Image getImage() {
		if (pixels == null) {
			return null;
		}

		Toolkit tk = Toolkit.getDefaultToolkit();
		ColorModel cm = ColorModel.getRGBdefault();
		return tk.createImage(new MemoryImageSource(w, h, cm, pixels, 0, w));
	}
}
