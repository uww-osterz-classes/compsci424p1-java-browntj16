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
     * any other initialization that is needed. initialized array length to 100
     */
    public Version1() {
    	pcbArr = new Version1PCB[100];
    	pcbArr[0] = new Version1PCB(-1, 0);

    }
    /**
     * other constructor in case you want to set array length to a certain length
     * 
     * @param len: length you want array to be
     */
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
       // checking to see if there is any free space in array
    	int freePos = findFreeSpace();
    	if (freePos > -1) {
    		 // If parentPid is not in the process hierarchy, do nothing; 
            // your code may return an error code or message in this case,
            // but it should not halt
    		int parentIndex = containsPID(parentPid);
        	if (parentIndex > -1) {
        		//create new pcb object
        		Version1PCB newPCB = new Version1PCB(parentIndex, freePos);
        		//adds new pcb as child to parent corresponding to parent id
    			pcbArr[parentIndex].addChild(freePos);
        		//System.out.println("Parent ID was found and child was created");
    			//adds newPCB to array
        		pcbArr[freePos] = newPCB;
        		
        	}
    	}
    	// else happens if array is full
    	else {
    		System.out.println("PCB array is full.");
    	}
    	
        return 0; 
    }
    /**
     * containsPID finds a parent id in the array (if it is there)
     * @param pID: the parent id we're trying to find
     * @return returns -1 if we cannot find parent id, otherwise we return parent id's index
     */
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
    	int parentIndex = containsPID(targetPid);
    	if (parentIndex >= 0) {
    		Version1PCB target = pcbArr[parentIndex];
    		sever(target);
    	}
        
        return 0; // often means "success" or "terminated normally"
    }
    /**
     * 
     * @param node: bit of a misnomer as a name. 
     */
    private void sever(Version1PCB node) {
    	// this if statement is just a check to see if the pcb has a parent
    	// for the original pcb object in the array since it has no parent
    	
    	if(node.getParent() > -1) {
    		//if we do find that node has a parent then we find node in its list of 
    		// children and remove it
    		LinkedList<Integer> list = pcbArr[node.getParent()].getChildren();
    		//searching through children to remove node from list
        	for (int i = 0; i < list.size(); i++) {
        		if (list.get(i) == node.getPCBID()) {
        			list.remove(i);
        		}
        	}
    	}
    	//recursively destroy all children
    	//basically we just apply sever to each child of node
    	while (!(node.getChildren().isEmpty())) {
			sever(pcbArr[node.popChild()]);
		}
    	//fairly certain there is no reason to setParent to -1 when we just make it null anyways,
    	// but i'll be damned if it does break something
    	node.setParent(-1);
    	//removes node from array
    	// its worth noting that their pcbid corresponds to their position in the array
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
    		// if a position in the array is null we skip it
    		// since earlier positions can be removed and thus become "null"
    		// to represent their absence we cant skip em
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
    /**
     * finds the first free position of the array
     * 
     * @return returns first free position of the array. -1 if there is none
     */
    private int findFreeSpace() {
    	for (int i = 0; i < pcbArr.length; i++) {
    		if(null == pcbArr[i]) {
    			return i;
    		}
    	}
    	return -1;
    }
    /**
     * prints out the linked list for output purposes
     * @param list
     */
    private void printLinkedList(LinkedList<Integer> list) {
    	for (int i = 0; i < list.size(); i++) {
    		System.out.print(list.get(i) + " ");
    	}
    }

    /* If you need or want more methods, feel free to add them. */
    
}
