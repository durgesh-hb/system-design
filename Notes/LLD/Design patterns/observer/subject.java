package design_patterns_3;

public interface subject {
	
	void add(observer observer);
	
	void remove(observer observer);
	
	void notifyall();

}
