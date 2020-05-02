package pl.gda.zsl;

import javax.ejb.Local;

@Local
public interface PlayerLocal {
	int getCounter();
	void setCounter(int counter);
	String getUsername();
	void setUsername(String username);
}
