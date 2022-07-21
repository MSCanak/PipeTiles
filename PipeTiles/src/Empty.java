//Mustafa Said Çanak	150120020
//MOHAMAD NAEL AYOUBI	150120997
package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Empty extends Tile {

	public Empty(int id, String type, String property) {
		super(id, type, property);
		if (property.equalsIgnoreCase("Free")) 
			tileImg = new ImageView(new Image("Free.png"));
		else 
			tileImg = new ImageView(new Image("none.png"));
	}
	
}