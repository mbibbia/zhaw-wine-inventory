package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

import ch.zhaw.wineInventory.bean.Image;

/**
 * 
 * @author Christian Jeitziner / Marco Bibbia
 * 
 *         Application Event when a region is saved.
 *
 */
public class ImageDetailsEvent extends ApplicationEvent {

	private Image image;

	private static final long serialVersionUID = 1L;

	public ImageDetailsEvent(Object source, Image image) {
		super(source);
		this.image = image;

	}

	/**
	 * Returns image bean that has been saved.
	 * 
	 * @return Wine
	 */
	public Image getImage() {
		return this.image;
	}
	
}
