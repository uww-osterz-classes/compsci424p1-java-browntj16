/* COMPSCI 424 Program 1
 * Name:
 */
package compsci424.p1.java;
import java.util.LinkedList;
/**
 * The process control block structure that is used to track a
 * process's parent and children (if any) in Version 1.
 */

	
public class Version1PCB {
	private int pcbID;
	private int parent;
	private LinkedList<Integer> children;
	
	public Version1PCB() {
		pcbID = 1;
		
	}
	
	public Version1PCB(int parent, int pcbID) {
		this.pcbID = pcbID;
		this.parent = parent;
		children = new LinkedList<Integer>();
	}
	/**
	 * 
	 * lots of getters and setters!
	 */
	public int getPCBID() {
		return pcbID;
	}
	public int getParent() {
		return parent;
	}
	public LinkedList<Integer> getChildren() {
		return children;
	}
	public void setPCBID(int pcbID) {
		this.pcbID = pcbID;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public void setChildren(LinkedList<Integer> children) {
		this.children = children;
	}
	/**
	 * adds child to list
	 * @param pcb
	 */
	public void addChild(int pcb) {
		children.add(pcb);
	}
	/**
	 * removes child from list and returns it
	 * @return child position (int)
	 */
	public int popChild() {
		return children.pop();
	}
}



