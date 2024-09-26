package FileImportAndExport;
import NUTRiAPP.User;

public interface FileHandler {
    public void exportFile(User user, String filepath) throws Exception;

    public void importFile(User user, String filepath) throws Exception;
}
