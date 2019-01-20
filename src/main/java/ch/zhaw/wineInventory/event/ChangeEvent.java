package ch.zhaw.wineInventory.event;

import org.springframework.context.ApplicationEvent;

public class ChangeEvent extends ApplicationEvent {
	private ChangeEntityEventType changeType;
	
	private static final long serialVersionUID = 1L;

	public ChangeEvent(Object source, ChangeEntityEventType changeType) {
		super(source);
		this.changeType = changeType;
	}
	
	public ChangeEntityEventType getChangeType() {
		return this.changeType;
	}

}
