package nl.gas.pork.model;

import java.util.List;

public class PorkMessage {
	
	public String roastId;
	public String roastName;
	public String time;
	public List<SensorOutput> sensors;
	
	public PorkMessage(){}
	
	public PorkMessage(String time, List<SensorOutput> sensors, List<String> tips) {
		super();
		this.time = time;
		this.sensors = sensors;
		this.tips = tips;
	}
	
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<SensorOutput> getSensors() {
		return sensors;
	}
	public void setSensors(List<SensorOutput> sensors) {
		this.sensors = sensors;
	}
	public List<String> getTips() {
		return tips;
	}
	public void setTips(List<String> tips) {
		this.tips = tips;
	}
	public List<String> tips;
	

}
