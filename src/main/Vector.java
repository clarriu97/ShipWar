package main;

public class Vector {
	public double x;
	public double y;
	
	
	public Vector(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector() {
		this(0,0);
	}
	
	public double getMod() {
		return Math.sqrt(x*x + y*y);
	}
	
	public double getAngle() {
		return Math.atan2(y,x);		
	}
	
	
	public Vector createCopy() {
		return new Vector(x,y);
	}

	public Vector add(Vector v) {
		x += v.x;
		y += v.y;
		return this;
	}
	
	public Vector sub(Vector v) {
		x -= v.x;
		y -= v.y;
		return this;
	}
	
	public Vector reverse() {
		return scale(-1);
	}
	
	public Vector scale(double k) {
		return setMod(k*getMod());
	}
	
	public Vector setModAngle(double r,double phi) {
		x = r * Math.cos(phi);
		y = r * Math.sin(phi);
		return this;
	}
	
	public Vector setMod(double r) {
		return setModAngle(r,getAngle());
	}
	

	@Override
	public String toString() {
		return "Vector: (" + x + "," + y + ")";
	}

}
