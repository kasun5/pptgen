package pptgen.pptwriter;

import pptgen.data.DataStore;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by Thilina on 6/19/2016.
 */
public class PptWriter {

    private static PptCreator pptx;
    private static int numberOfEmployees;
    private static String mode;
    private static String languages;
    private static String benchmark;

    //template
    public static void openPPT(String fileName){
        try {
            pptx = new PptCreator(fileName);
        } catch (IOException e) {
            e.printStackTrace();//// TODO: 6/19/2016
        }
    }

    public static void generateStartingSlides(String companyName){

        pptx.createCover(companyName);
        pptx.createContext(companyName);
        pptx.createObjectives(companyName);

    }

    public static void generateSurveyConstructSlide(String infoFilePath){

        readInfo(infoFilePath);
        int numberOfStatements = DataStore.getNumberOfSattements();
        int numberOfRespondents = DataStore.getNumberOfRespondents();
        pptx.surveyConstruct(numberOfStatements,numberOfEmployees,numberOfRespondents,mode,languages,benchmark);

    }

    public static void generateDemographySlides(){

        int numberOfDemos = DataStore.getNumberOfDemography();
        String demo[] = DataStore.getAllDemography();

        pptx.createDemography(numberOfDemos,demo);
        pptx.createDemographyCharts(numberOfDemos,demo);

    }

    public static void generateAOINAOSList(){

        ArrayList<String> aos = DataStore.getAOSThemes();
        ArrayList<String> aoi = DataStore.getAOIThemes();

        pptx.createAOINAOSList(aoi,aos);
    }

    public static void savePPTX(String fileName){
        pptx.savePPT(fileName);
    }

    private static void readInfo(String filepath){

        FileInputStream fis = null;
        String line = null;

        try {
            fis = new FileInputStream(filepath);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            line = br.readLine(); // date
            line = br.readLine(); // numOfEmp
            numberOfEmployees = Integer.parseInt(line);
            line = br.readLine(); // Mode
            mode =line;
            line = br.readLine(); // language
            languages = line;
            line = br.readLine(); // benchmark
            benchmark = line;

            br.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace(); //// TODO: 6/19/2016
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
