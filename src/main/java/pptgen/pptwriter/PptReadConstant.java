package pptgen.pptwriter;

/**
 * Created by Thilina on 6/18/2016.
 */
class PptReadConstant {

    //slide numbers
    static final int COVER_SLIDE_NUMBER = 0;
    static final int CONTEXT_SLIDE_NUMBER = 2;
    static final int OBJECTIVES_SLIDE_NUMBER = 3;
    static final int SURVEY_CONSTRUCT_SLIDE_NUMBER = 11;
    static final int DEMOGRAPHY_SLIDE_NUMBER = 17;
    static int AOI_AND_AOS = 28;
    static int KEY_DRIVES = 0;

    //slide gap from aos n aoi to AOI and AOS separate
    static final int AOS_SLIDE = 29;
    static final int AOI_SLIDE= 35;

    //Slide text indicators
    static final String COMPANY_NAME_TOKEN = "<COMPANY_NAME>";
    static final String YEAR_TOKEN = "<YEAR>";
    static final String NUM_OF_STATEMENTS_TOKEN = "<NUM_OF_STATEMENTS>";
    static final String BENCHMARK_TOKEN = "<BENCHMARK>";
    static final String NUMBER_OF_RESPONDENT_TOKEN = "<NOR>";
    static final String NUMBER_OF_EMPLOYEE_TOKEN = "<NOE>";
    static final String NUMBER_OF_NON_RESPONDENT_TOKEN = "<NNOR>";
    static final String MODE_TOKEN = "<MODE>";
    static final String LANGUAGES_TOKEN ="<LANGUAGES>";
    static final String DEMOGRAPHY_TOKEN = "<DEMO>";
    static final String STRENGTH_TOKEN = "<STRENGTH>";
    static final String IMPROVEMENT_TOKEN = "<IMPROVEMENT>";

    //Shape const
    static final String TEXT_BOX = "TextBox";
    static final String TITLE = "Title";

    //setters for change
    static void setAoiAndAOS(int slideNumber){
        //AOI_AND_AOS = slideNumber;
    }

    static void setKeyDrives(int slideNumber){
        //KEY_DRIVES = slideNumber;
    }

}

