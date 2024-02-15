/* COMPSCI 424 Program 1
 * Name:
 */
package compsci424.p1.java;

import java.util.LinkedList;

/**
 * The process control block structure that is used to track a
 * process's parent, first child, younger sibling, and older sibling
 * (if they exist) in Version 2.
 */
public class Version2PCB {
	
	private int pcbID;
	private int parent;
	private int firstChild;
	private int ySibling;
	private int oSibling;
	
	public Version2PCB() {
		pcbID = 1;
		
	}
	
	public Version2PCB(int parent, int pcbID) {
		this.pcbID = pcbID;
		this.parent = parent;
		ySibling = -1;
		oSibling = -1;
		firstChild = -1;
	}
	public int getPCBID() {
		return pcbID;
	}
	public int getParent() {
		return parent;
	}
	public int getYoungerSibling() {
		return ySibling;
	}
	public int getOlderSibling() {
		return oSibling;
	}
	public int getFirstChild() {
		return firstChild;
	}
	
	
	public void setFirstChild(int firstChild) {
		this.firstChild = firstChild;
	}
	public void setYoungerSibling(int ySibling) {
		this.ySibling = ySibling;
	}
	public void setOlderSibling(int oSibling) {
		this.oSibling = oSibling;
	}
	public void setPCBID(int pcbID) {
		this.pcbID = pcbID;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	
}
