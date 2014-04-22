package com.mrpnut08.imagebeader.actions;

import java.awt.Point;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import com.mrpnut08.imagebeader.listener.OnPegboardSwitchListener;

public class PegboardSwitchAction extends AbstractAction {
	
	private static final long serialVersionUID = 1L;
	
	private Point pegboard_index;
	private OnPegboardSwitchListener listener;
	
	public PegboardSwitchAction (OnPegboardSwitchListener listener, int x, int y) {
		this.listener = listener;
		this.pegboard_index = new Point(x,y);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		listener.onPegboardSwitch(this.pegboard_index);
	}

}
