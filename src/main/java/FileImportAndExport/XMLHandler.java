package FileImportAndExport;
import NUTRiAPP.User;

public class XMLHandler implements FileHandler {

    private HandleXML handler;

    public XMLHandler() {
        this.handler = new HandleXML();
    }

    @Override
    public void exportFile(User user, String filepath) throws Exception {
        handler.exportXML(user, filepath);
    }

    @Override
    public void importFile(User user, String filepath) throws Exception {
        handler.importXML(user, filepath);
    }
    
}
