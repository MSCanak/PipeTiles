//Mustafa Said Çanak	150120020
//MOHAMAD NAEL AYOUBI	150120997
package application;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Pipe extends Tile {

	public Pipe(int id, String type, String property) {
		super(id, type, property);
		switch (property) {
		    case "Vertical": tileImg =  new ImageView(new Image("PipeV.png"));
		    break;
		    case "Horizontal": tileImg = new ImageView(new Image("PipeH.png"));
		    break;
		    case "00": tileImg = new ImageView(new Image("Pipe00.png"));
		    break;
		    case "01": tileImg = new ImageView(new Image("Pipe01.png"));
		    break;
		    case "10": tileImg = new ImageView(new Image("Pipe10.png"));
		    break;
		    case "11": tileImg = new ImageView(new Image("Pipe11.png"));
		    break;
		}
	}
	
}
