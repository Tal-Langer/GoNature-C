package GUI;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

import Client.ClientUI;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * This GUI will present 'waiting screen' for casual traveller who requests to
 * enter the park
 * 
 * @author Liad Yadin
 *
 */
public class WaitingForTravellerController {
	/**
	 * variable for status approval = -1 request waiting, 0 request repuesed, 1
	 * request approve
	 */
	private int statusApproval = -1;
	/** for data in the previous screen */
	private EnterParkNowController enterparknow = new EnterParkNowController();
	/**
	 * Button for check status of the request
	 */
	@FXML
	private Button checkBtn;

	/**
	 * This method will check in DB if the request is approved or not. if the
	 * request isn't approve - the traveler will get "parkIsFull" screen if the
	 * request is approve - the traveler will get "Implementation enter park" and he
	 * could enter park. in addition, if he could enter park - the number of
	 * visitors will update in DB
	 * 
	 * @param event- when click on check
	 * @throws IOException
	 */
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
					enterparknow.getWantedPark(), enterparknow.getNumOfVisitors(), enterparknow.getPrice());
			ClientUI.entranceParkController.setNumOfVisitorEntringPark(enterparknow.getWantedPark(),
					enterparknow.getNumOfVisitors());

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

}
