//Mustafa Said Çanak	150120020
//MOHAMAD NAEL AYOUBI	150120997
package application;


import javafx.scene.image.ImageView;

public class Tile {

	private int id;//keeps the place of the tile
	private String type;//keeps the type of the tile
	private String property;//keeps the property of tile
	protected ImageView tileImg;//keeps the tile image

	public Tile() {
		
	}
	
	public Tile(int id, String type, String property) {
		this.id = id;
		this.type = type;
		this.property = property;
	}

	public ImageView getTileImg() {
		return tileImg;
	}

	public void setTileImg(ImageView tileImg) {
		this.tileImg = tileImg;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}
	
}