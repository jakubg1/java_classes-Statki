package pl.gda.zsl;

import javax.ejb.Remote;

@Remote
public interface PlayerRemote {
	int getCounter();
	void setCounter(int counter);
	String getUsername();
	void setUsername(String username);
}
