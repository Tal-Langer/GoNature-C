package GUI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class WaitingForTravellerController {
	private boolean approval = false;
	private int statusApproval = -1;
	@FXML
	private Button checkBtn;

	@FXML
	void whenclickedOnCheck(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		statusApproval = ClientUI.requestsController.checkIfApproveRequest(ClientUI.userController.traveller.getId(),
				"EnterPark");
		if (statusApproval == -1) {
			Parent root = FXMLLoader.load(getClass().getResource("waiting.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Waiting for enter");
			stage.setScene(scene);
			stage.show();
		} else if (statusApproval == 1) {
			ClientUI.entranceParkController.enterWithoutOrder(LocalTime.now(), LocalDate.now(),
					EnterParkNowController.wantedpark, EnterParkNowController.numOfVisitors, (float) 50);
			ClientUI.entranceParkController.setNumOfVisitorEntringPark(EnterParkNowController.wantedpark,
					EnterParkNowController.numOfVisitors);

			Parent root = FXMLLoader.load(getClass().getResource("ImplementaionEnterPark.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Confirm Enter park");
			stage.setScene(scene);
			stage.show();
		} else {
			Parent root = FXMLLoader.load(getClass().getResource("parkIsFull.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Unapproved");
			stage.setScene(scene);
			stage.show();
		}
	}
	
	/**
	 * This method responislbe of showing an alert
	 * when want to close the application.
	 * @param event
	 */
	  @FXML
	    void WhenClickExitBtn(MouseEvent event) {
		  Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		  alert.setTitle("Exit");
		  alert.setHeaderText("Are you sure you want to exit the application?");
		  alert.setResizable(false);
		  alert.setContentText("Select yes if you want, or not if you want to get back!");
		  ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText("Yes");
		  ((Button) alert.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("No");
		  Optional<ButtonType> result =  alert.showAndWait();
		  if(!result.isPresent())
		    alert.close();
		  else if(result.get() == ButtonType.OK) { 
			  Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
				stage.close();
		  }   
		  else if(result.get() == ButtonType.CANCEL)
			  alert.close();
	    }

}
