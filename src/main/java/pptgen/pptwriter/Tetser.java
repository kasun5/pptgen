package pptgen.pptwriter;

import org.apache.poi.xslf.usermodel.*;

import java.io.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Thilina on 6/18/2016.
 */
public class Tetser {

    public static void main(String arg[]){





        /**XMLSlideShow pptx = null;
        List<XSLFSlide> slides;

        try {

            pptx = new XMLSlideShow(new FileInputStream("ppts//New Presentation format with tags.pptx"));
            slides = pptx.getSlides();
            XSLFSlide slide = slides.get(11);

            List<XSLFShape> sh = slide.getShapes();

            for (XSLFShape aShape : sh) {
                String name = aShape.getShapeName();
                if(name != null && name.contains(PptReadConstant.TEXT_BOX)){
                    if (aShape instanceof XSLFTextShape) {

                        XSLFTextShape textShape = (XSLFTextShape)aShape;
                        String text = textShape.getText();
                        if(text.contains(PptReadConstant.NUM_OF_STATEMENTS_TOKEN)){
                            String num = Integer.toString(80);
                            text = text.replace(PptReadConstant.NUM_OF_STATEMENTS_TOKEN,num);
                            System.out.println(text);

                        }
                        else if(text.contains(PptReadConstant.BENCHMARK_TOKEN)){
                            text = text.replace(PptReadConstant.BENCHMARK_TOKEN,"ksjkajskajska");
                            System.out.println(text);

                        }
                        else if(text.contains(PptReadConstant.NUMBER_OF_EMPLOYEE_TOKEN)){
                            String val = Integer.toString(1000);
                            text = text.replace(PptReadConstant.NUMBER_OF_RESPONDENT_TOKEN,val);
                            val = Integer.toString(80);
                            text = text.replace(PptReadConstant.NUMBER_OF_EMPLOYEE_TOKEN,val);
                            int temp =20;
                            val = Integer.toString(temp);
                            text = text.replace(PptReadConstant.NUMBER_OF_NON_RESPONDENT_TOKEN,val);
                            System.out.println(text);

                        }
                        else if(text.contains(PptReadConstant.MODE_TOKEN)){
                            text = text.replace(PptReadConstant.MODE_TOKEN,"kdkjsd");
                            System.out.println(text);

                        }
                        else if(text.contains(PptReadConstant.LANGUAGES_TOKEN)){
                            text = text.replace(PptReadConstant.LANGUAGES_TOKEN,"jadhjkasdha");
                            System.out.println(text);

                        }
                        textShape.setText(text);
                    }
                }
            }


            OutputStream out = new FileOutputStream("ppts//testout.pptx");
            pptx.write(out);
            out.close();


        } catch (IOException e) {
            e.printStackTrace();
        }

**/
    }
}
