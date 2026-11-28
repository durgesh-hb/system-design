package design_patterns_3;

import java.util.ArrayList;
import java.util.List;

public class channel implements subject{
	
	private List<observer> list = new ArrayList<>();
	
	private String name;
	
	@Override
	public void add(observer observer) {
		list.add(observer);
	}
	
	@Override
	public void remove(observer observer) {
		list.remove(observer);
	}
	
	@Override
	public void notifyall() {
		for(observer subs : list) {
			subs.update(name);
		}
	}
	
	
	public void uplodevideo(String name) {
		this.name = name;
		notifyall();
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
