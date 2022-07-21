//Mustafa Said �anak	150120020
//MOHAMAD NAEL AYOUBI	150120997
package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class End extends Tile {

	public End(int id, String type, String property) {
		super(id, type, property);
		switch (property) {
		    case "Vertical": tileImg = new ImageView(new Image("EndV.png"));
		    break;
		    case "Horizontal": tileImg =  new ImageView(new Image("EndH.png"));
		    break;
		}
	}
	
}
