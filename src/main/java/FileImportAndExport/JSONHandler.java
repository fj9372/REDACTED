package FileImportAndExport;

import NUTRiAPP.User;

public class JSONHandler implements FileHandler {

    private HandleJSON handler;

    public JSONHandler() {
        this.handler = new HandleJSON();
    }

    @Override
    public void exportFile(User user, String filepath) throws Exception {
        handler.exportJSON(user, filepath);
    }

    @Override
    public void importFile(User user, String filepath) throws Exception {
        handler.importJSON(user, filepath);
    }
    
}

