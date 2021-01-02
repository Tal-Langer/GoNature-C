package GUI;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Client.ClientUI;
import Entities.Order;
import Entities.Person;
import javafx.fxml.Initializable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.fxml.Initializable;

public class OrderScreenController implements Initializable {

	@FXML
	private ComboBox WantedParkCB;

	@FXML
	private DatePicker DateLbl;

	@FXML
	private TextField NumOfVisotrsLbl;

	@FXML
	private ComboBox TimeOfVisitCB;

	@FXML
	private TextField EmailLbl;

	@FXML
	private TextField PhoneNumberLbl;

	@FXML
	private Label IdOfViditorLbl;

	@FXML
	private Label PriceLbl;
	Person t;
	ObservableList<String> listForParks;
	ObservableList<String> listForTimes;

	// Set the park names for the combo box to be able to show them on the screen
	private void setWantedParkCm() {
		ArrayList<String> parks = new ArrayList<String>();
		parks.add("Erh");
		parks.add("ParkB");
		parks.add("ParkC");

		listForParks = FXCollections.observableArrayList(parks);
		WantedParkCB.setItems(listForParks);
	}

	/*
	 * enter to the combo box the times from 8 Am to 12 Pm
	 */
	private void SetTimeParkCm() {
		String half = ":30";
		String whole = ":00";
		String t;
		int flag = 0;
		ArrayList<String> Times = new ArrayList<String>();

		for (int i = 8; i < 20; i++) {
			if (flag == 0) {
				t = Integer.toString(i) + whole;
				Times.add(t);
				flag++;
				i--;
			} else {
				t = Integer.toString(i) + half;
				Times.add(t);
				flag--;
			}
		}
		listForTimes = FXCollections.observableArrayList(Times);
		TimeOfVisitCB.setItems(listForTimes);
	}

	// !!!!!!! NEED TO CHANGE ID BY THE USER CONTROLLER!!!!!
	public void setIdOfMakingOrder() {
		IdOfViditorLbl.setText("31198");
	}

	/*
	 * initialize park times and dates, if the user came back from the confirmation
	 * page, it will show on the screen all of his ditails that he already filled
	 */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setWantedParkCm();
		SetTimeParkCm();
		setIdOfMakingOrder();
		if (!(ClientUI.orderController.isConfirm)) {
			Order o = ClientUI.orderController.order;
			EmailLbl.setText(ClientUI.orderController.getEmail());
			PhoneNumberLbl.setText(ClientUI.orderController.getPhone());
			DateLbl.setValue(o.getDateOfVisit());
			NumOfVisotrsLbl.setText(Integer.toString(o.getNumberOfVisitors()));
			ClientUI.orderController.isConfirm = true;

		}
	}

	@FXML
	void WhenClickCalculatePriceBtn(ActionEvent event) {

	}

	@FXML
	void WhenClickNextBtn(ActionEvent event) throws IOException {
		if (ClientUI.orderController.checkValidValues(PhoneNumberLbl.getText(), EmailLbl.getText())) {
			String temp = (String) TimeOfVisitCB.getValue();
			String[] res = temp.split(":");
			LocalDate date = DateLbl.getValue();
			String wanted = (String) WantedParkCB.getValue();
			int numOfVisitors = Integer.parseInt(NumOfVisotrsLbl.getText());
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			LocalDate d = DateLbl.getValue();
			LocalTime time = LocalTime.of(Integer.parseInt(res[0]), Integer.parseInt(res[1]));

			// here we will send the data we got from the page, we need to use "the type"
			// !!!!!!! TYPE NEED TO BE CHANGED !!!!!!!!
			ClientUI.orderController.setEmailAndPhone(EmailLbl.getText(), PhoneNumberLbl.getText());
			ClientUI.orderController.n_order = true;
			ClientUI.orderController.canMakeOrder(time, date, wanted, "Member", numOfVisitors);

			/*
			 * after knowing if the order is possible or not, showing the right screen/
			 */
			if (ClientUI.orderController.valid) {
				ClientUI.orderController.isConfirm = false;
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/GUI/Confirmation.fxml").openStream());
				Scene scene = new Scene(root);
				stage.setTitle("Confirm Order");
				stage.setScene(scene);
				stage.show();
			} else {
				FXMLLoader loader = new FXMLLoader();
				Pane root = loader.load(getClass().getResource("/GUI/CancellOrder.fxml").openStream());
				Scene scene = new Scene(root);
				stage.setTitle("Unapproved Order");
				stage.setScene(scene);
				stage.show();
			}
		} else {
			Alert a = new Alert(AlertType.NONE, "Email or phone is incorrect!");
			a.setAlertType(AlertType.ERROR);
			a.show();
		}

		// System.out.println("Time: "+timeofVisit+" date: "+date+" wantedPark:
		// "+wanted+" number of visit: "+numOfVisitors);
	}

	@FXML
	void WhenClickPreviusBtn(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		Parent root = FXMLLoader.load(getClass().getResource("WelcomeTraveller.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Welcome traveller");
		stage.setScene(scene);

		stage.show();
	}

}
