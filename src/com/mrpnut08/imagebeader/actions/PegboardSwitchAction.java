package com.mrpnut08.imagebeader.actions;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import com.mrpnut08.imagebeader.listener.PegboardSwitcherListener;

public class PegboardSwitchAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	private Point pegboard_index;
	private PegboardSwitcherListener listener;
	
	public PegboardSwitchAction (PegboardSwitcherListener listener, int x, int y) {
		super("",new ImageIcon(listener.getPegboardThumbnail(x, y).getPath()));
		this.listener = listener;
		this.pegboard_index = new Point(x,y);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		listener.onPegboardSwitch(this.pegboard_index);
	}

}
