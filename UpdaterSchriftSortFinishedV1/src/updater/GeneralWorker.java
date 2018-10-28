package updater;

import java.io.File;

import java.util.ArrayList;
import java.util.Collections;

public class GeneralWorker {
	
	public GeneralWorker(String pathCOM, String pathSSD) {
		
		this.workspaceCOM = new File(pathCOM);
		this.workspaceSSD = new File(pathSSD);
		
	}
	
	public GeneralWorker() {
		
		
	}
	
	
	ArrayList<Integer> missingSSD = new ArrayList<Integer>();
	ArrayList<String> subFolderCOM = new ArrayList<String>();
	ArrayList<String> subFolderSSD = new ArrayList<String>();
	
	
	File workspaceCOM;
	File workspaceSSD;
	
	ArrayList<String> com = new ArrayList <String>();
	ArrayList<String> ssd = new ArrayList <String>();
	
	ArrayList<Integer> existSubSSD = new ArrayList<Integer>();
	
	ArrayList<ArrayList<Integer>> ebenen = new ArrayList<ArrayList<Integer>>();
	ArrayList<ArrayList<String>> ordner = new ArrayList<ArrayList<String>>();
	
	static ArrayList<String> missingFolders = new ArrayList<String>();
	static ArrayList<Boolean> missingFiles = new ArrayList <Boolean>();
	
	
	public void resetter(int ebene) {
		
		ebenen.add(ebene, new ArrayList<Integer>());
		
		for(short z = 0; z < existSubSSD.size(); z++) {
			
			ebenen.get(ebene).add(z, existSubSSD.get(z));
			
		}
		
		existSubSSD.clear();
		
		
		ordner.add(ebene, new ArrayList<String>());
		
		for(short z = 0; z < com.size(); z++) {
			
			ordner.get(ebene).add(z, com.get(z));
			
		}
		
		com.clear();
		
	}
	
	
	
	public void reverseResetter(int ebene) {
		
		existSubSSD.clear();
		
		for(short z = 0; z < ebenen.get(ebene).size(); z++) {
			
			existSubSSD.add(z, ebenen.get(ebene).get(z));
			
		}
		
		
		com.clear();
		
		for(short z = 0; z < ordner.get(ebene).size(); z++) {
			
			com.add(z, ordner.get(ebene).get(z));
			
		}
		
		
	}
	
	public ArrayList<String> getMissingFolders() {
		
		return missingFolders;
		
	}
	
	
	public ArrayList<Boolean> isFile(){
		
		return missingFiles;
		
	}
	
	
	
	public void SSDmissing(String pathCOM, String pathSSD) {		//Method with path's
		
		missingSSD.clear();
		
		subFolderCOM.clear();
		subFolderSSD.clear();
		
		File methodWorkspaceCOM = new File(pathCOM);
		File methodWorkspaceSSD = new File(pathSSD);
		
		Collections.addAll(com, methodWorkspaceCOM.list());
		Collections.addAll(ssd, methodWorkspaceSSD.list());
		
		Collections.addAll(subFolderCOM, methodWorkspaceCOM.list());
		Collections.addAll(subFolderSSD, methodWorkspaceSSD.list());
		
		boolean missing = true;
		int a,b;
		
		int counterExist = 0;
		int counterMissing = 0;
		
		for(a = 0; a < subFolderCOM.size(); a++) {
			
			for(b = 0; b < subFolderSSD.size(); b++) {
				
				if(subFolderCOM.get(a).equals(subFolderSSD.get(b))) {
								
					existSubSSD.add(counterExist, new Integer(a));
					
					counterExist++;
					missing = false;
					break;
					
				}	
				
			}
			
			if(missing) {
				
				missingSSD.add(counterMissing, new Integer(a));
				counterMissing++;
				
			}
			
			missing = true;
			
		}
		
		if(missingSSD.size() != 0) {
			
			missingFiles.add(false);
			missingFolders.add("[" + pathCOM + "] | [" + pathSSD + "]");
			
		}
		
		for(int z = 0; z < missingSSD.size(); z++) {
			
			missingFiles.add(true);
			missingFolders.add(subFolderCOM.get(missingSSD.get(z)));
			
		}
		
	}

}