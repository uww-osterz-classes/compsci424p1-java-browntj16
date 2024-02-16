/* COMPSCI 424 Program 1
 * Name:
 */
package compsci424.p1.java;
import java.util.LinkedList;
import java.util.ListIterator; 

/** 
 * Implements the process creation hierarchy for Version 1, which uses
 * linked lists.
 * 
 * This is a template. Program1.java *must* contain the main class
 * for this program. Otherwise, feel free to edit these files, even
 * these pre-written comments or my provided code. You can add any
 * classes, methods, and data structures that you need to solve the
 * problem and display your solution in the correct format.
 */                                                                                
public class Version1 {                                                            
    // Declare any class/instance variables that you need here.
	Version1PCB[] pcbArr;
	
    /**
     * Default constructor. Use this to allocate (if needed) and
     * initialize the PCB array, create the PCB for process 0, and do
     * any other initialization that is needed. 
     */
    public Version1() {
    	pcbArr = new Version1PCB[100];
    	pcbArr[0] = new Version1PCB(-1, 0);

    }
    public Version1(int len) {
    	pcbArr = new Version1PCB[len];
    	pcbArr[0] = new Version1PCB(-1, 0);
    	
    }


    /**
     * Creates a new child process of the process with ID parentPid. 
     * @param parentPid the PID of the new process's parent
     * @return 0 if successful, not 0 if unsuccessful
     */
    int create(int parentPid) {
        // If parentPid is not in the process hierarchy, do nothing; 
        // your code may return an error code or message in this case,
        // but it should not halt
    	int freePos = findFreeSpace();
    	if (freePos > -1) {
    		int index = containsPID(parentPid);
        	if (index > -1) {
        		Version1PCB newPCB = new Version1PCB(index, freePos);
    			pcbArr[index].addChild(freePos);
        		//System.out.println("Parent ID was found and child was created");
        		pcbArr[freePos] = newPCB;
        		
        	}
    	}
    	else {
    		System.out.println("PCB array is full.");
    	}
    	

        // Assuming you've found the PCB for parentPid in the PCB array:
        // 1. Allocate and initialize a free PCB object from the array
        //    of PCB objects

        // 2. Insert the newly allocated PCB object into parentPid's
        //    list of children

        // You can decide what the return value(s), if any, should be.
        // If you change the return type/value(s), update the Javadoc.
        return 0; // often means "success" or "terminated normally"
    }

    private int containsPID(int pID) {
		for (int i = 0; i < pcbArr.length;i++) {
			//System.out.println(pcbArr[i].pcbID + " "+ pID);
			if(pcbArr[i]!=null) {
				if (pcbArr[i].getPCBID() == pID) {
					return i;
				}
			}
			
		}		
		return -1;
	}

	/**
     * Recursively destroys the process with ID parentPid and all of
     * its descendant processes (child, grandchild, etc.).
     * @param targetPid the PID of the process to be destroyed
     * @return 0 if successful, not 0 if unsuccessful
     */
    int destroy (int targetPid) {
         // If targetPid is not in the process hierarchy, do nothing; 
         // your code may return an error code or message in this case,
         // but it should not halt
    	int index = containsPID(targetPid);
    	if (index >= 0) {
    		Version1PCB target = pcbArr[index];
    		sever(target);
    		
    	}
         // Assuming you've found the PCB for targetPid in the PCB array:
         // 1. Recursively destroy all descendants of targetPid, if it
         //    has any, and mark their PCBs as "free" in the PCB array 
         //    (i.e., deallocate them)

         // 2. Remove targetPid from its parent's list of children

         // 3. Deallocate targetPid's PCB and mark its PCB array entry
         //    as "free"

         // You can decide what the return value(s), if any, should be.
         // If you change the return type/value(s), update the Javadoc.
        return 0; // often means "success" or "terminated normally"
    }
    private void sever(Version1PCB node) {
    	// this if statement is just a check to see if the pcb has a parent
    	// for the original pcb object in the array since it has no parent
    	if(node.getParent() > -1) {
    		LinkedList<Integer> list = pcbArr[node.getParent()].getChildren();
    		//searching through children to remove node from list
        	for (int i = 0; i < list.size(); i++) {
        		if (list.get(i) == node.getPCBID()) {
        			list.remove(i);
        		}
        	}
    	}
    	//recursively destroy all children
    	while (!(node.getChildren().isEmpty())) {
			sever(pcbArr[node.popChild()]);
		}
    	node.setParent(-1);
    	pcbArr[node.getPCBID()] = null;
    }
    /**
     * Traverse the process creation hierarchy graph, printing
     * information about each process as you go. See Canvas for the
     * *required* output format. 
     *         
     * You can directly use "System.out.println" statements (or
     * similar) to send the required output to stdout, or you can
     * change the return type of this function to return the text to
     * the main program for printing. It's your choice. 
     */
    public void showProcessInfo() {
    	for (int i = 0; i < pcbArr.length; i++) {
    		if (pcbArr[i] != null) {
    			Version1PCB process = pcbArr[i];
    			LinkedList<Integer> children = pcbArr[i].getChildren();
    			if (children.size() > 0) {
    				System.out.print("Process "+i+": parent is " + process.getParent()+" and children are ");
    				printLinkedList(children);   				
    			}
    			else {
    				System.out.print("Process "+i+": parent is " + process.getParent()+" and has no children.");
    			}
    			System.out.println();
    			
    		}
    	}
    }
    
    private int findFreeSpace() {
    	for (int i = 0; i < pcbArr.length; i++) {
    		if(null == pcbArr[i]) {
    			return i;
    		}
    	}
    	return -1;
    }
    private void printLinkedList(LinkedList<Integer> list) {
    	for (int i = 0; i < list.size(); i++) {
    		System.out.print(list.get(i) + " ");
    	}
    }

    /* If you need or want more methods, feel free to add them. */
    
}
