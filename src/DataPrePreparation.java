import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.Remove;
import weka.filters.unsupervised.attribute.ReplaceMissingValues;

public class DataPrePreparation {

    public Instances cleanValues(Instances data) throws Exception {
        ReplaceMissingValues replaceMissingValues = new ReplaceMissingValues();
        replaceMissingValues.setInputFormat(data);
        Instances cleandata = Filter.useFilter(data, replaceMissingValues);
        return cleandata;
    }

    public Instances normalizeData(Instances data) throws Exception {
        Normalize normalize = new Normalize();
        normalize.setInputFormat(data);
        Instances normalizedata = Filter.useFilter(data, normalize);
        return normalizedata;
    }

    public Instances nominalToBinary(Instances data)throws Exception{
        NominalToBinary nominalToBinary=new NominalToBinary();
        nominalToBinary.setInputFormat(data);
        Instances binaryData=Filter.useFilter(data,nominalToBinary);
        return binaryData;
    }

    public Instances removeData(Instances data, int[] dataToRemove) throws Exception {
        Remove remove = new Remove();
        remove.setAttributeIndicesArray(dataToRemove);
        remove.setInputFormat(data);
        return Filter.useFilter(data, remove);
    }
}