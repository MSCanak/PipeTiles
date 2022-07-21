//Mustafa Said Çanak	150120020
//MOHAMAD NAEL AYOUBI	150120997
package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PipeStatic extends Tile {

	public PipeStatic(int id, String type, String property) {
		super(id, type, property);
		switch (property) {
		case "Vertical": tileImg =  new ImageView(new Image("PipeStaticV.png"));
	    break;
	    case "Horizontal": tileImg = new ImageView(new Image("PipeStaticH.png"));
	    break;
	    case "00": tileImg = new ImageView(new Image("PipeStatic00.png"));
	    break;
	    case "01": tileImg = new ImageView(new Image("PipeStatic01.png"));
	    break;
	    case "10": tileImg = new ImageView(new Image("PipeStatic10.png"));
	    break;
	    case "11": tileImg = new ImageView(new Image("PipeStatic11.png"));
	    break;
		}
	}
	
}