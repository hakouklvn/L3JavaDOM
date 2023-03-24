module guelfen.abdelheq.magasin {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.xml;

    opens guelfen.abdelheq.magasin to javafx.fxml;
    exports guelfen.abdelheq.magasin;
}