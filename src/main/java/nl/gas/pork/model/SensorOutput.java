package nl.gas.pork.model;

public class SensorOutput {

	public String id;
	public String name;
	public int temperature;
	
	public SensorOutput(){}
	
	public SensorOutput(String id, String name, int temperature){
		this.temperature = temperature;
		this.name = name;
		this.id = id;
	}
		
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTemperature() {
		return temperature;
	}
	public void setTemperature(int temperature) {
		this.temperature = temperature;
	}
}
