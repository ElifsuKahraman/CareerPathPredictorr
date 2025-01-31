import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;


public class ExcelSaving {
    public static void ExcelSave(String filePath, Person person, String result) {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath, true))) {

            if (new File(filePath).length() == 0) {
                String[] header = {
                        "Ad", "Yaş", "Alınan Kurs", "İngilizce Seviyesi", "Okuduğu Bölüm", "Programlama Notu",
                        "Veritabanı Notu", "Sistem Analizi Notu", "İşletme Notu", "Proje Yönetimi Notu",
                        "Tahmin Meslek 1", "Tahmin Meslek 2", "Tahmin Meslek 3"
                };
                writer.writeNext(header);
            }

            String[] predictions = result.split("\n");
            String prediction1 = predictions[0].split(":")[1].trim();
            String prediction2 = predictions[1].split(":")[1].trim();
            String prediction3 = predictions[2].split(":")[1].trim();


            String[] personData = {
                    person.getAd(),
                    String.valueOf(person.getAge()),
                    person.getAlinanKurs(),
                    String.valueOf(person.getIng()),
                    person.getOkuduguBolum(),
                    person.getProgramlamaNot(),
                    person.getVeriTabaniNot(),
                    person.getSistemAnaliziNot(),
                    person.getIsletmeNot(),
                    person.getProjeYonetimiNot(),
                    prediction1,
                    prediction2,
                    prediction3
            };

            writer.writeNext(personData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}