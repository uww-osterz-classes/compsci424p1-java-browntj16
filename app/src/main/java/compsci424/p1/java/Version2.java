/* COMPSCI 424 Program 1
 * Name:
 */
package compsci424.p1.java;

import java.util.LinkedList;

/** 
 * Implements the process creation hierarchy for Version 2, which does
 * not use linked lists.
 * 
 * This is a template. Program1.java *must* contain the main class
 * for this program. Otherwise, feel free to edit these files, even
 * these pre-written comments or my provided code. You can add any
 * classes, methods, and data structures that you need to solve the
 * problem and display your solution in the correct format.
 */
public class Version2 {
    // Declare any class/instance variables that you need here.
	Version2PCB[] pcbArr;
    /**
     * Default constructor. Use this to allocate (if needed) and
     * initialize the PCB array, create the PCB for process 0, and do
     * any other initialization that is needed. 
     */
	public Version2() {
    	pcbArr = new Version2PCB[100];
    	pcbArr[0] = new Version2PCB(-1, 0);

    }
    public Version2(int len) {
    	pcbArr = new Version2PCB[len];
    	pcbArr[0] = new Version2PCB(-1, 0);
    	
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
    	//checking to see if there is a free position in the pcb array
    	if (freePos > -1) {
    		int parentIndex = containsPID(parentPid);
    		//checking to see if the pID is found
        	if (parentIndex > -1) {
        		Version2PCB newPCB = new Version2PCB(parentIndex, freePos);
        		// check to see if it has a first child
        		newPCB.setParent(parentIndex); 
        		pcbArr[freePos] = newPCB;
        		addChild(parentIndex, freePos);

        	}
    	}
    	else {
    		System.out.println("PCB array is full.");
    	}
        return 0; // often means "success" or "terminated normally"
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
    	if (parentIndex > -1) {
    		Version2PCB target = pcbArr[parentIndex];
    		//manages relationships between targets siblings
    		manageSiblings(target);
    		//destroys all children
    		destroyChildren(target);
    		//removes target from array
    		pcbArr[parentIndex]=null;
    		
    	}
       return 0; // often means "success" or "terminated normally"
   }

    /**
     * checks to see if a parent id is in array
     * @param pID
     * @return returns index of parent, or -1 if not found
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
     * finds the first free space in the array
     * @return returns first free index, or -1 if no such index exists
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
    * Traverse the process creation hierarchy graph, printing
    * information about each process as you go. See Canvas for the
    * *required* output format. 
    *         
    * You can directly use "System.out.println" statements (or
    * similar) to send the required output to stdout, or you can
    * change the return type of this function to return the text to
    * the main program for printing. It's your choice. 
    */
    public void displayChildren(int node) {
    	int sib = pcbArr[node].getFirstChild();
    	while (sib != -1) {
    		System.out.print(pcbArr[sib].getPCBID() + " ");
    		sib = pcbArr[sib].getYoungerSibling();
    	}
    	System.out.println();
    }
    /**
     * manage siblings preps a given target for removal by altering its siblings.
     * basically, it fixes any missing links caused by removing target
     * @param target: a v2pcb object 
     */
    private void manageSiblings(Version2PCB target) {
    	//checking if older sibling exists
    	if(target.getOlderSibling() > -1) {
			// checking if it also has a younger sibling
			if (target.getYoungerSibling() > -1) {
				pcbArr[target.getOlderSibling()].setYoungerSibling(target.getYoungerSibling()); 
				pcbArr[target.getYoungerSibling()].setOlderSibling(target.getOlderSibling()); 
			}
			//if there is no younger sibling, but target has an older sibling
			else {
				pcbArr[target.getOlderSibling()].setYoungerSibling(-1);
			}
		}
		//if there is no older sibling
		else {
			// if there is no older sibling, but there is a younger
			if(target.getYoungerSibling() > -1) {
				pcbArr[target.getParent()].setFirstChild(target.getYoungerSibling());
				pcbArr[target.getYoungerSibling()].setOlderSibling(-1); 
			}
			// if there are no older siblings, but also no younger
			else {
				// if statement covers the odd case where you delete a pcb that has no parent
				if(target.getParent() > -1) {
					pcbArr[target.getParent()].setFirstChild(-1); 
				}
			}
    }
    }
    /**
     * recursively destroys all children and itself
     * @param target
     */
    private void destroyChildren(Version2PCB target) {
    	//checks to see if target even has any children
    	if(target.getFirstChild() > -1) {
    		int child = target.getFirstChild();	
    		//destroys all of targets child children. destroys targets grandchildren if you will
    		destroyChildren(pcbArr[child]);
    		//destroys all of targets first child siblins then itself
    		destroyYoungerSiblingAndSelf(pcbArr[child]);	
    	}
    }
    /**
     * recursively destroys all of targets younger children then itself
     * @param target
     */
    private void destroyYoungerSiblingAndSelf(Version2PCB target) {
    	//if target has any younger siblings we recursively destroy them
    	if(target.getYoungerSibling() > -1) {
    		destroyYoungerSiblingAndSelf(pcbArr[target.getYoungerSibling()]);
    	}
    	// if target has any children we recursively destroy them
    	if (target.getFirstChild() > -1) {
    		destroyChildren(pcbArr[target.getPCBID()]);
    	}
    	//destroys itself
    	pcbArr[target.getParent()].setFirstChild(-1); 
    	pcbArr[target.getPCBID()] = null;
    	target = null;
    	
    	
    }
    /**
     * prints all siblings
     * @param sib
     */
   private void printAllSiblings(Version2PCB sib) {
	   if(sib.getYoungerSibling() > -1) {
		   System.out.print(sib.getYoungerSibling()+ " ");
		   printAllSiblings(pcbArr[sib.getYoungerSibling()]);
	   }
   }
   /**
    * adds a child to given parent index
    * @param parentIndex
    * @param freePos
    */
   private void addChild(int parentIndex, int freePos) {
	   // if parent already has a first child
	   if(pcbArr[parentIndex].getFirstChild() > -1) {
			int sib = pcbArr[parentIndex].getFirstChild();
			//traversing through all siblings
			while(sib != -1) {
				//checking if we found a space for the youngest sibling
				if (pcbArr[sib].getYoungerSibling() == -1) {
					//mark the former youngest childs younger sibling as the pcb
					pcbArr[sib].setYoungerSibling(freePos); 
					//mark the new pcb's older sibling as the former youngest
					pcbArr[freePos].setOlderSibling(sib); 
					//mark the new pcb's younger sibling as -1 (for not existing)
					pcbArr[freePos].setYoungerSibling(-1); 
					sib=-1;
				}
				else {
					// iterate to the next youngest sibling
					sib = pcbArr[sib].getYoungerSibling();
				}
			}
		}
		//occurs if there is no first child for parent
		else {
			pcbArr[parentIndex].setFirstChild(freePos); 
			pcbArr[freePos].setParent(parentIndex); 
			pcbArr[freePos].setOlderSibling(-1);
			pcbArr[freePos].setYoungerSibling(-1);
		}
   }
   /**
    * prints info about the array 
    */
   void showProcessInfo() {
	   for (int i = 0; i < pcbArr.length; i++) {
		   if (pcbArr[i] != null) {
   			Version2PCB process = pcbArr[i];
   			System.out.print("Process "+i+": parent is " + process.getParent());
   			if(process.getFirstChild() > -1) {
   				System.out.print(" and children are " + process.getFirstChild()+" ");
   				printAllSiblings(pcbArr[process.getFirstChild()]);
   			}
   			else {
   				System.out.print(" and has no children");
   			}
   			System.out.println();
		   }
	   }
   }

   /* If you need or want more methods, feel free to add them. */

}
