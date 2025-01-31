import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class frmCareerPathPredictorr extends JFrame {
    private JButton csvToArffButton;
    private JButton OnIslemeButton;
    private JPanel panel1;
    private JButton selectFileButton;
    private JButton modelEgitmeButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JLabel lblAd;
    private JLabel lblYas;
    private JLabel lblBolum;
    private JLabel lblAldigiKurs;
    private JLabel lblDil;
    private JLabel lblProgramlama;
    private JButton modelTahminButton;
    private JTextField textField5;
    private JTextField txtVeriTabani;
    private JTextField txtIsletmeYonetimi;
    private JTextField txtProgramlama;
    private JTextField txtSistemAnalizi;
    private JLabel lblVeriTabani;
    private JTextField txtProjeYonetimi;
    private File selectedFile;
    private boolean fileSelected = false;
    private ReadFile readFile;
    private Instances data;


    public frmCareerPathPredictorr() {
        add(panel1);
        setSize(600, 600);
        setTitle("Tahmin");
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);

        selectFileButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser files = new JFileChooser();
                int returnVal = files.showOpenDialog(null);
                if (!fileSelected) {
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        selectedFile = files.getSelectedFile();
                        JOptionPane.showMessageDialog(null, "Dosya başarıyla eklendi: " + selectedFile.getName());
                        fileSelected = true;
                        selectFileButton.setEnabled(false);
                        setFile(selectedFile);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Zaten bir dosya seçildi.");
                }
            }
        });
        csvToArffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    try {
                        if (selectedFile.getName().endsWith(".csv")) {
                            readFile = new ReadFile(selectedFile);
                            CsvToArff csvToArff = new CsvToArff(readFile);
                            csvToArff.file();
                            JOptionPane.showMessageDialog(null, "Dosyanız .arff formatına dönüştürüldü.");
                        } else if (selectedFile.getName().endsWith(".arff")) {
                            readFile = new ReadFile(selectedFile);
                        } else {
                            JOptionPane.showMessageDialog(null, "Hata");
                        }
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir dosya seçin.");
                }
            }

        });
        OnIslemeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    try {
                        DataSource source = new DataSource(selectedFile.getAbsolutePath());
                        Instances data = source.getDataSet();
                        if (data == null) {
                            throw new RuntimeException("Veri yüklenemedi.Lütfen dosyanızı kontrol ediniz.");
                        }

                        if (data.classIndex() == -1) {
                            data.setClassIndex(data.numAttributes() - 1);
                        }
                        DataPrePreparation dataPrePreparation = new DataPrePreparation();

                        int[] dataToRemove = {0};
                        Instances filteredData = dataPrePreparation.removeData(data, dataToRemove);
                        //Instances cleanData = dataPrePreparation.cleanValues(data);
//                        Instances normalizedData = dataPrePreparation.normalizeData(cleanData);
//                        Instances finalData = dataPrePreparation.nominalToBinary(normalizedData);
                        filteredData=dataPrePreparation.cleanValues(filteredData);
                        filteredData = dataPrePreparation.normalizeData(filteredData);
                        filteredData = dataPrePreparation.nominalToBinary(filteredData);

                        // Modeli eğit
//                        CreatingAlgorithm algorithm = new CreatingAlgorithm();
//                        algorithm.crossValidateModel(filteredData, 10);

                        DataSaving.saveToCsv(filteredData, "C:/Users/kelif/Desktop/temizlenmis_veri.csv");
                        JOptionPane.showMessageDialog(null, "Veri temizlendi ve kaydedildi");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Önişleme sırasında hata oluştu: " + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Lütfen bir dosya seçin.");
                }
            }
        });
        modelEgitmeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedFile != null) {
                    try {
                        DataSource source = new DataSource(selectedFile.getAbsolutePath());
                        Instances data = source.getDataSet();

                        if (data.classIndex() == -1) {
                            data.setClassIndex(data.numAttributes() - 1);
                        }
                        CreatingAlgorithm creatingAlgorithm = new CreatingAlgorithm();
                        creatingAlgorithm.crossValidateModel(data, 10);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(null, "Model eğitiminde hata" + ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Dosya seçin ve önişleme adımını yapın");
                }
            }
        });
        modelTahminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Person person = new Person(getAd(),getAge(),getProgramlamaNot(),getVeriTabaniNot(), getIsletmeNot(),getSistemAnaliziNot(),getProjeYonetimiNot(),getAlinanKurs(), getIng() ,getOkuduguBolum());
                Guess guess = new Guess(data, frmCareerPathPredictorr.this);
                String prediction = null;
                try {
                    prediction = guess.toGuess();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
                JOptionPane.showMessageDialog(null, prediction);
                //String filePath = "C:/Users/kelif/Desktop/kullanici_bilgileri.csv";
//                ExcelSaving.ExcelSave(filePath, person ,result);

                String result = prediction.toString(); // Tahmin edilen mesleklerin sonucu
                ExcelSaving.ExcelSave("C:/Users/kelif/Desktop/veriler.csv", person, result);

                JOptionPane.showMessageDialog(null, "Veriler başarıyla kaydedildi!");
            }

        });

    }

    public void setFile(File file) {
        this.selectedFile = file;
        this.fileSelected = true;
        this.readFile = new ReadFile(file);
    }

    public int getAge() {
        String age1 = textField2.getText();
        return Integer.parseInt(age1);
    }

    public String getAd() {
        String ad1 = textField1.getText();
        return ad1;
    }

    public String getAlinanKurs() {
        String alinanKurs1 = textField4.getText();
        return alinanKurs1;
    }

    public String getIng() {
        String ing1 = textField5.getText();
        return ing1;
    }

    public String getOkuduguBolum() {
        String okuduguBolum1 = textField3.getText();
        return okuduguBolum1;
    }

    public String getIsletmeNot() {
        String ısletmeYonetimi = txtIsletmeYonetimi.getText();
        return ısletmeYonetimi;
    }

    public String getVeriTabaniNot() {
        String veriTabani = txtVeriTabani.getText();
        return veriTabani;
    }

    public String getProjeYonetimiNot() {
        String projeYonetimi = txtProjeYonetimi.getText();
        return projeYonetimi;
    }

    public String getSistemAnaliziNot() {
        String sistemAnalizi = txtSistemAnalizi.getText();
        return sistemAnalizi;
    }

    public String getProgramlamaNot() {
        String programlamaNot = txtProgramlama.getText();
        return programlamaNot;
    }

    public String[] personArray() {
        return new String[]{
                getAd(),
                String.valueOf(getAge()),
                getOkuduguBolum(),
                getAlinanKurs(),
                getIng(),
                getProgramlamaNot(),
                getVeriTabaniNot(),
                getSistemAnaliziNot(),
                getIsletmeNot(),
                getProjeYonetimiNot()
        };
    }
}