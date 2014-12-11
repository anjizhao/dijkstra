// *****************************
// Anji Zhao (az2324)
// COMS W3134 - Homework 5
// CityNotFoundException.java
// an exception.. for when a city.. is not found.... 
// *****************************

public class CityNotFoundException extends Exception {
	
	public CityNotFoundException(String message) {
		super(message); 
	}
	public CityNotFoundException(String message, Throwable throwable) {
		super(message, throwable); 
	}

}