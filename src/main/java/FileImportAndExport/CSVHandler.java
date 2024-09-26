package FileImportAndExport;

import NUTRiAPP.User;

public class CSVHandler implements FileHandler {

    private HandleCSV handler;

    public CSVHandler() {
        this.handler = new HandleCSV();
    }

    @Override
    public void exportFile(User user, String filepath) throws Exception {
        handler.exportCSV(user, filepath);
    }

    @Override
    public void importFile(User user, String filepath) throws Exception {
        handler.importCSV(user, filepath);
    }
    
}
