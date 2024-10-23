module com.bptn.bptn_hearts_game {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.bptn.bptn_hearts_game to javafx.fxml;
    exports com.bptn.bptn_hearts_game;
}
