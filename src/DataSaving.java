import weka.core.Instances;
import weka.core.converters.CSVSaver;
import java.io.File;

public class DataSaving {
    public static void saveToCsv(Instances data,String filePath) throws Exception{
        CSVSaver csvSaver=new CSVSaver();
        csvSaver.setInstances(data);
        csvSaver.setFile(new File(filePath));
        csvSaver.writeBatch();
    }
}
