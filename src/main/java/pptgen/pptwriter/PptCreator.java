package pptgen.pptwriter;

import org.apache.poi.POIXMLDocumentPart;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xslf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.*;
import pptgen.data.DataStore;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Thilina on 6/18/2016.
 */
class PptCreator {

    private XMLSlideShow pptx;
    private List<XSLFSlide> slides;

    PptCreator(String fileName) throws IOException {

        pptx = new XMLSlideShow(new FileInputStream(fileName));
        slides = pptx.getSlides();

    }

    void createCover(String companyName){

        XSLFSlide slide = slides.get(PptReadConstant.COVER_SLIDE_NUMBER);

        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape shape: shapes) {
            if (shape instanceof XSLFTextShape) {
                XSLFTextShape textShape = (XSLFTextShape)shape;
                String slideText = textShape.getText();
                if(slideText.equalsIgnoreCase(PptReadConstant.COMPANY_NAME_TOKEN)){
                    textShape.setText(companyName.toUpperCase());
                    break;
                }
            }
        }
    }

    void createContext(String companyName){

        XSLFSlide slide = slides.get(PptReadConstant.CONTEXT_SLIDE_NUMBER);
        int year = Calendar.getInstance().get(Calendar.YEAR);
        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aShape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) aShape;
                    String text = textShape.getText();
                    text = text.replace(PptReadConstant.COMPANY_NAME_TOKEN, companyName);
                    text = text.replace(PptReadConstant.YEAR_TOKEN,Integer.toString(year));
                    textShape.setText(text);
                }
            }
        }
    }

    void createObjectives(String companyName){

        XSLFSlide slide = slides.get(PptReadConstant.OBJECTIVES_SLIDE_NUMBER);
        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aShape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) aShape;
                    String text = textShape.getText();
                    if(text.contains(PptReadConstant.COMPANY_NAME_TOKEN)){
                        text = text.replace(PptReadConstant.COMPANY_NAME_TOKEN, companyName);
                        textShape.setText(text);
                        break;
                    }
                }
            }
        }
    }

    void surveyConstruct(int numberOfStatements, int numberOfEmployees,
                         int numberOfRespondents, String mode, String languages, String benchmark){

        XSLFSlide slide = slides.get(PptReadConstant.SURVEY_CONSTRUCT_SLIDE_NUMBER);
        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aShape instanceof XSLFTextShape) {

                    XSLFTextShape textShape = (XSLFTextShape)aShape;
                    String text = textShape.getText();
                    if(text.contains(PptReadConstant.NUM_OF_STATEMENTS_TOKEN)){
                        String num = Integer.toString(numberOfStatements);
                        text = text.replace(PptReadConstant.NUM_OF_STATEMENTS_TOKEN,num);
                        System.out.println(text);

                    }
                    else if(text.contains(PptReadConstant.BENCHMARK_TOKEN)){
                        text = text.replace(PptReadConstant.BENCHMARK_TOKEN,benchmark);
                        System.out.println(text);

                    }
                    else if(text.contains(PptReadConstant.NUMBER_OF_EMPLOYEE_TOKEN)){
                        String val = Integer.toString(numberOfRespondents);
                        text = text.replace(PptReadConstant.NUMBER_OF_RESPONDENT_TOKEN,val);
                        val = Integer.toString(numberOfEmployees);
                        text = text.replace(PptReadConstant.NUMBER_OF_EMPLOYEE_TOKEN,val);
                        int temp =numberOfEmployees - numberOfRespondents;
                        val = Integer.toString(temp);
                        text = text.replace(PptReadConstant.NUMBER_OF_NON_RESPONDENT_TOKEN,val);
                        System.out.println(text);

                    }
                    else if(text.contains(PptReadConstant.MODE_TOKEN)){
                        text = text.replace(PptReadConstant.MODE_TOKEN,mode);
                        System.out.println(text);

                    }
                    else if(text.contains(PptReadConstant.LANGUAGES_TOKEN)){
                        text = text.replace(PptReadConstant.LANGUAGES_TOKEN,languages);
                        System.out.println(text);

                    }
                    textShape.setText(text);
                }
            }
        }
    }


    void createDemography(int numberOfDemos, String demo[]){

        XSLFSlide slide = slides.get(PptReadConstant.DEMOGRAPHY_SLIDE_NUMBER);
        PptReadConstant.setAoiAndAOS(PptReadConstant.DEMOGRAPHY_SLIDE_NUMBER + numberOfDemos +1);
        List<XSLFShape> shapes = slide.getShapes();

        //insert demos
        int i = 0;

        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if(numberOfDemos == i){
                    break;
                }
                if (aShape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) aShape;
                    String text = textShape.getText();

                    if(text.contains(PptReadConstant.DEMOGRAPHY_TOKEN)){
                        text = text.replace(PptReadConstant.DEMOGRAPHY_TOKEN,demo[i]);
                        i++;
                    }
                    textShape.setText(text);
                }
            }
        }

        //remove extra demos
        for (int j=0;j<shapes.size();j++) {
            XSLFShape aSh = shapes.get(j);
            String name = aSh.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aSh instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape)aSh;
                    String text = textShape.getText();
                    System.out.println(text);
                    if (text.contains(PptReadConstant.DEMOGRAPHY_TOKEN)) {
                        slide.removeShape(aSh);
                    }

                }
            }
        }

        //remove extra numbers
        i++;
        for (int j=0;j<shapes.size();j++) {
            XSLFShape aSh = shapes.get(j);
            String name = aSh.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aSh instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape)aSh;
                    String text = textShape.getText();
                    System.out.println(text);
                    if (text.contains(Integer.toString(i))){
                        i++;
                        slide.removeShape(aSh);
                    }
                }
            }
        }
    }

    void createAOINAOSList(ArrayList<String> aoi, ArrayList<String> aos){

        if (PptReadConstant.AOI_AND_AOS == 0){
            //// TODO: 6/18/2016 handle this
        }
        XSLFSlide slide = slides.get(PptReadConstant.AOI_AND_AOS);
        List<XSLFShape> shapes = slide.getShapes();
        int i=0;
        int j=0;
        for (XSLFShape aSh : shapes) {

            String name = aSh.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aSh instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape)aSh;
                    String text = textShape.getText();
                    System.out.println(text);
                    if(text.contains(PptReadConstant.STRENGTH_TOKEN)){
                        text = text.replace(PptReadConstant.STRENGTH_TOKEN,aos.get(i++));
                        textShape.setText(text);
                    }
                    else if(text.contains(PptReadConstant.IMPROVEMENT_TOKEN)){
                        text = text.replace(PptReadConstant.IMPROVEMENT_TOKEN,aoi.get(j++));
                        textShape.setText(text);
                    }
                }
            }
        }

        this.createAO(aos,PptReadConstant.STRENGTH_TOKEN,PptReadConstant.AOS_SLIDE);
        this.createAO(aoi,PptReadConstant.IMPROVEMENT_TOKEN,PptReadConstant.AOI_SLIDE);
    }

    private void createAO(ArrayList<String> ao, String token, int slideNum){

        XSLFSlide slide = slides.get(slideNum);
        List<XSLFShape> shapes = slide.getShapes();
        int i=0;
        for (XSLFShape aShape : shapes) {
            String name = aShape.getShapeName();
            if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                if (aShape instanceof XSLFTextShape) {
                    XSLFTextShape textShape = (XSLFTextShape) aShape;
                    String text = textShape.getText();
                    System.out.println(text);
                    if(text.contains(token)){
                        text = text.replace(token,ao.get(i++));
                    }
                    textShape.setText(text);
                }
            }
        }
    }

    public void createKeyDrives(ArrayList<String> kds){

        if (PptReadConstant.KEY_DRIVES == 0){
            // TODO: 6/18/2016 handle this 
        }
        XSLFSlide slide = slides.get(PptReadConstant.KEY_DRIVES);
        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape aSh : shapes) {
            if (aSh instanceof XSLFTable){
                XSLFTable tb = (XSLFTable)aSh;
                tb.getCell(1,0).setText(kds.get(0));
                tb.getCell(2,0).setText(kds.get(1));
                tb.getCell(3,0).setText(kds.get(2));
                tb.getCell(4,0).setText(kds.get(3));
                tb.getCell(5,0).setText(kds.get(4));
                break;
            }
        }
    }

    void createDemographyCharts(int numberOfDemos, String demos[]){

        int start = PptReadConstant.DEMOGRAPHY_SLIDE_NUMBER +1;
        ArrayList<String> factors;
        int i ;
        for (i = 0; i<numberOfDemos && i<10; i++){

            factors = DataStore.getFactorNmean(demos[i]);
            try {
                this.createDemos(demos[i],factors,start+i);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        for (int j=i;j<10;j++){
            slides.remove(j);
        }

    }

    private void createDemos(String demo, ArrayList<String> factors, int slideNo) throws IOException {

        XSLFSlide slide = slides.get(slideNo);

        List<XSLFShape> shapes = slide.getShapes();

        for (XSLFShape shape: shapes) {
            if (shape instanceof XSLFTextShape) {
                String name = shape.getShapeName();
                XSLFTextShape textShape = (XSLFTextShape)shape;
                if(name.contains(PptReadConstant.TITLE)){
                    textShape.setText(demo.toUpperCase());
                    break;
                }
            }
        }
        //slide.getPlaceholders().
        // find chart in the slide
        XSLFChart chart = null;
        for(POIXMLDocumentPart part : slide.getRelations()){
            if(part instanceof XSLFChart){
                chart = (XSLFChart) part;
                break;
            }
        }

        if(chart == null) throw new IllegalStateException("chart not found in the template");

        // embedded Excel workbook that holds the chart data
        POIXMLDocumentPart xlsPart = chart.getRelations().get(0);
        XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet();

        CTChart ctChart = chart.getCTChart();
        CTPlotArea plotArea = ctChart.getPlotArea();

        CTBarChart barChartArray = plotArea.getBarChartArray(0);
        CTBarSer serArray = barChartArray.getSerArray(0);
        CTSerTx tx = serArray.getTx();
        /** Series Text **/
        tx.getStrRef().getStrCache().getPtArray(0).setV(demo);
        sheet.createRow(0).createCell(1).setCellValue(demo);
        String titleRef = new CellReference(sheet.getSheetName(), 0, 1, true, true).formatAsString();
        tx.getStrRef().setF(titleRef);
        // Category Axis Data
        CTAxDataSource cat = serArray.getCat();
        CTStrData strData = cat.getStrRef().getStrCache();
        // Values
        CTNumDataSource val = serArray.getVal();
        CTNumData numData = val.getNumRef().getNumCache();
        strData.setPtArray(null);  // unset old axis text
        numData.setPtArray(null);  // unset old values
        // set model
        int idx = 0;
        int rownum = 1;
        int i = 0;

        while (i < factors.size()) {

            CTNumVal numVal = numData.addNewPt();
            numVal.setIdx(idx);
            numVal.setV(factors.get(i + 1));
            CTStrVal sVal = strData.addNewPt();
            sVal.setIdx(idx);
            sVal.setV(factors.get(i));
            idx++;
            XSSFRow row = sheet.createRow(rownum++);
            row.createCell(0).setCellValue(factors.get(i));
            row.createCell(1).setCellValue(Double.valueOf(factors.get(i + 1)));
            i += 2;
        }
        numData.getPtCount().setVal(idx);
        strData.getPtCount().setVal(idx);
        String numDataRange = new CellRangeAddress(1, rownum - 1, 1, 1).formatAsString(sheet.getSheetName(), true);
        val.getNumRef().setF(numDataRange);
        String axisDataRange = new CellRangeAddress(1, rownum - 1, 0, 0).formatAsString(sheet.getSheetName(), true);
        cat.getStrRef().setF(axisDataRange);
        // updated the embedded workbook with the data
        OutputStream xlsOut = xlsPart.getPackagePart().getOutputStream();
        try {
            wb.write(xlsOut);
        } finally {
            xlsOut.close();
            wb.close();
        }
    }

    void savePPT(String fileName){

        try {
            OutputStream out = new FileOutputStream(fileName);
            pptx.write(out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

