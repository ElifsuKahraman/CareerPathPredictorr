import weka.core.Instance;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.classifiers.Classifier;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.*;
import weka.core.converters.ConverterUtils.DataSource;


public class Guess {
    private Instances data;
    private frmCareerPathPredictorr form;

    public Guess(Instances data, frmCareerPathPredictorr form) {
        this.form = form;
        this.data = data;
    }

    public String toGuess() throws Exception {
        DataSource source = new DataSource("C:/Users/kelif/Desktop/TEZ-VERİ (4).arff");
        Instances data = source.getDataSet();

        if (data.classIndex() == -1) {
            data.setClassIndex(data.numAttributes() - 1);
        }

        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("egitilmis.model"));
        Classifier classifier = (Classifier) ois.readObject();
        ois.close();
        System.out.println("Eğitilmiş model başarıyla yüklendi.");

        String[] personlist = form.personArray();

        Instance instance = new DenseInstance(data.numAttributes());
        instance.setDataset(data);

        instance.setValue(1, Double.parseDouble(personlist[1]));

        for (int i = 2; i < personlist.length; i++) {
            instance.setValue(i, personlist[i]);
        }


        double[] predictions = classifier.distributionForInstance(instance);

        double minPrediction = Double.MAX_VALUE;
        double maxPrediction = Double.MIN_VALUE;

        for (double pred : predictions) {
            if (pred > maxPrediction)
                maxPrediction = pred;
            if (pred < minPrediction)
                minPrediction = pred;
        }


        List<Map.Entry<Integer, Double>> predictionList = new ArrayList<>();
        for (int i = 0; i < predictions.length; i++) {
            predictionList.add(new AbstractMap.SimpleEntry<>(i, predictions[i]));
        }
        predictionList.sort((a, b) -> Double.compare(b.getValue(), a.getValue()));


        double firstPredictionValue = predictionList.get(0).getValue();
        double correctedValue = 0.9 + ((firstPredictionValue - minPrediction) / (maxPrediction - minPrediction)) * 0.07;
        double correctedFirstPrediction = 90 + (correctedValue * (97 - 90)); // Küçük bir dağılım sağlıyoruz


        double minValue = 50;
        double maxValue = 89;

        for (int i = 1; i < 3; i++) {
            double value = predictionList.get(i).getValue();
            double scaledValue = minValue + ((value - minPrediction) / (maxPrediction - minPrediction)) * (maxValue - minValue);
            predictionList.set(i, new AbstractMap.SimpleEntry<>(predictionList.get(i).getKey(), scaledValue));
        }


        StringBuilder result = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int index = predictionList.get(i).getKey();
            double value = (i == 0) ? correctedFirstPrediction : predictionList.get(i).getValue();
            String job = data.classAttribute().value(index);
            result.append(String.format("Meslek %d: %s - %.2f%%\n", i + 1, job, value));
        }

        return result.toString();

    }
}
