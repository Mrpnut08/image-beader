package com.mrpnut08.imagebeader.listener;

import java.awt.Point;
import java.io.File;

public interface PegboardSwitcherListener {
	
	public void onPegboardSwitch (Point index);
	
	public File getPegboardThumbnail(int x, int y);
}
