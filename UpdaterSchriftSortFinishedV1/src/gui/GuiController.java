package gui;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
/***/	import updater.Controller;
/***/	import updater.GeneralWorker;

public class GuiController implements Initializable {
	
	@FXML
	private AnchorPane anchorpane;
	
	@FXML
	private Button button;
	
	@FXML
	private TextField pathCOM;
	
	@FXML
	private TextField pathSSD;
	
	@FXML
	private Button triggerFolderCOM;
	
	@FXML
	private Button triggerFolderSSD;
	
	@FXML
	private TreeView<String> treeView;
	
	ArrayList<TreeItem<String>> treeItem = new ArrayList<TreeItem<String>>();
	
	private File tryFileCOM;
	private File tryFileSSD;
	
	private boolean validCOM = false;
	private boolean validSSD = false;
	
	BooleanProperty validCOMproperty = new SimpleBooleanProperty(validCOM);
	BooleanProperty validSSDproperty = new SimpleBooleanProperty(validSSD);
	
	ArrayList<RadioButton> chooseButton = new ArrayList<RadioButton>();
	
	ArrayList<ArrayList<String>> folders = new ArrayList<ArrayList<String>>();
	static char[] pathletters;
	int s = 1;
	
	int chooseButtonYPos = 20;
	int chooseButtonIndex = -1;
	
	int rootCounterIndex = -1;
	
	int ebenenIndex = 0;
	
	
	GeneralWorker generalWorker = new GeneralWorker();
	
	public void triggerCOM() {
		
		Stage stage = (Stage)anchorpane.getScene().getWindow();
		
		DirectoryChooser chooseCOM = new DirectoryChooser();
		File folderCOM = chooseCOM.showDialog(stage);
		pathCOM.setText(folderCOM.getAbsolutePath());
		
	}
	
	public void triggerSSD() {

		Stage stage = (Stage)anchorpane.getScene().getWindow();
		
		DirectoryChooser chooseSSD = new DirectoryChooser();
		File folderSSD = chooseSSD.showDialog(stage);
		pathSSD.setText(folderSSD.getAbsolutePath());
		
	}
	
	
	public void klick() {
		
		String a = pathCOM.getText();
		String b = pathSSD.getText();
		
		Controller.master(a, b);
		
		System.out.println(generalWorker.getMissingFolders());
		System.err.println("missing folders size: " + generalWorker.getMissingFolders().size());
		
		TreeItem<String> root = new TreeItem<String>();
		
		for(int z = 0; z < generalWorker.getMissingFolders().size(); z++) {
			
			treeItem.add(new TreeItem<String>(generalWorker.getMissingFolders().get(z)));
			pathletters = generalWorker.getMissingFolders().get(z).toCharArray();
			
			folders.add(z, new ArrayList<String>());
			
			for(int y = 0; y < pathletters.length; y++) {
				
				if(pathletters[y] == ']') break;
				
				if(pathletters[y] == '\\') {
					
					folders.get(z).add(foldername(y));
					
				}
				
			}
			
			if(z == 0) {
				
				root.getChildren().add(treeItem.get(0));						//ADD the first to root
				
			}
			else if(generalWorker.isFile().get(z)) {							//Element is file
				
				treeItem.get(z - s).getChildren().add(treeItem.get(z));
				s++;
				
			}
			else {
				
				s = 1;
				
				int counter = 0;
				
				for(int y = folders.size() - 2; y >= 0; y--) {
					
					counter = 0;
					
					for(int x = 0; x < folders.get(y).size(); x++) {
						
						if(folders.get(y).get(x).equals(folders.get(z).get(x))) {
							
							counter++;
							
						}
						
						if(!folders.get(y).get(x).equals(folders.get(z).get(x))) break;
						
					}
					
					if(counter == folders.get(y).size() && counter > 0) {
						
						treeItem.get(y).getChildren().add(treeItem.get(z));
						break;
						
					}
					
				}
				
				if(counter < folders.get(0).size()) {
					
					root.getChildren().add(treeItem.get(z));
					
				}
				
			}
			
		}
		
		treeView.setRoot(root);
		treeView.setShowRoot(false);
		
		System.out.println("FINISHED!!");
		
	}
	
	
	
	
	public String foldername(int iterationsIndex) {
		
		ArrayList<Character> folder = new ArrayList<Character>();
		int counter = 1;
		
		while(pathletters[iterationsIndex + counter] != '\\' || pathletters[iterationsIndex + counter] != ']') {
			
			if(pathletters[iterationsIndex + counter] == ']') break;
			if(pathletters[iterationsIndex + counter] == '\\') break;
			
			folder.add(pathletters[iterationsIndex + counter]);
			counter++;
			
		}
		
		char[] wordArray = new char[folder.size()];
		
		for(int y = 0; y < wordArray.length; y++) {
			
			wordArray[y] = folder.get(y);
			
		}
		
		String word = String.valueOf(wordArray);
		
		return word;
		
	}
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		pathCOM.textProperty().addListener(e -> {
			
			tryFileCOM = new File(pathCOM.getText());
			
			if(tryFileCOM.isDirectory()) {
				
				validCOM = true;
				System.err.println(validCOM);
				validCOMproperty.set(validCOM);
				
				System.out.println("com: " + validCOMproperty.get());
				
			}else {
				
				validCOM = false;
				System.err.println(validCOM);
				validCOMproperty.set(validCOM);
				
				System.out.println("com: " + validCOMproperty.get());
				
			}
		});
		
		pathSSD.textProperty().addListener(e -> {
			
			tryFileSSD = new File(pathSSD.getText());
			
			if(tryFileSSD.isDirectory()) {
				
				validSSD = true;
				System.err.println(validSSD);
				validSSDproperty.set(validSSD);
				
				System.out.println("ssd: " + validSSDproperty.get());
				
			}else {
				
				validSSD = false;
				System.err.println(validSSD);
				validSSDproperty.set(validSSD);
				
				System.out.println("ssd: " + validSSDproperty.get());
				
			}
		});
		
		button.disableProperty().bind(Bindings.or(validCOMproperty.not(), validSSDproperty.not()));
		
	}

}
