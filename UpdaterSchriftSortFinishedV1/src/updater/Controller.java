package updater;

import java.io.File;

public class Controller {
	
	static GeneralWorker generalWorker = new GeneralWorker();
	
	static int ebene = 0;

	
	public static void master(String pathCOM, String pathSSD) {
		
		comparison(pathCOM, pathSSD);
		
		System.out.println("---finished---");
		System.out.println(generalWorker.getMissingFolders() + ":missingFolders");
		
	}
	
	
	
	private static void comparison(String pathCOM, String pathSSD) {
		
		generalWorker.resetter(ebene);
		generalWorker.SSDmissing(pathCOM, pathSSD);
		
		for(short z = 0; z < generalWorker.existSubSSD.size(); z++) {
			
			String tryPathCOM = pathCOM + "\\" + generalWorker.com.get(generalWorker.existSubSSD.get(z));
			String tryPathSSD = pathSSD + "\\" + generalWorker.com.get(generalWorker.existSubSSD.get(z));
			File superFile = new File(tryPathCOM);
			
			if(superFile.isDirectory()) {
				
				ebene++;
				comparison(tryPathCOM, tryPathSSD);
				
			}

			tryPathCOM = pathCOM;
			tryPathSSD = pathSSD;
			
		}
		
		generalWorker.reverseResetter(ebene);
		ebene--;
		
	}
	
}
