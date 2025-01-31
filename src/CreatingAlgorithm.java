import weka.classifiers.evaluation.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;
import javax.swing.*;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Random;

public class CreatingAlgorithm {
    double kappa;
    double accuracy;
    double rmse;

    public void crossValidateModel(Instances data,int folds) throws Exception{
        if(data.classIndex()==-1){
            data.setClassIndex(data.numAttributes()-1);
        }
        RandomForest classifier=new RandomForest();
        classifier.setNumIterations(500);
        classifier.setMaxDepth(10);


        Evaluation evaluation=new Evaluation(data);
        evaluation.crossValidateModel(classifier, data, folds, new Random(1));

        kappa=evaluation.kappa();
        accuracy=evaluation.pctCorrect();
        rmse=evaluation.rootMeanSquaredError();

        JOptionPane.showMessageDialog(null,evaluation.toSummaryString("\n Model Değerlendirme: ",false));
        JOptionPane.showMessageDialog(null,evaluation.toMatrixString("Karışıklık Matrisi"));

        classifier.buildClassifier(data);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(
                    new FileOutputStream("egitilmis.model"));
            oos.writeObject(classifier);
            oos.flush();
            oos.close();
            System.out.println("Model başarıyla kaydedildi.");
        } catch (Exception ec) {
            System.out.println("Model kaydedilemedi: " + ec.getMessage());
            ec.printStackTrace();
        }
    }
}
