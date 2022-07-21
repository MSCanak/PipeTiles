//Mustafa Said Çanak	150120020
//MOHAMAD NAEL AYOUBI	150120997
package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Starter extends Tile {

	public Starter(int id, String type, String property) {
		super(id, type, property);
		switch (property) {
		    case "Vertical": tileImg = new ImageView(new Image("StarterV.png"));
		    break;
		    case "Horizontal": tileImg = new ImageView(new Image("StarterH.png"));
		    break;
		}
	}
	
}