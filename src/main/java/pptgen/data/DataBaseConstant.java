package pptgen.data;

/**
 * Created by Thilina on 6/8/2016.
 *
 * Holds all data base constants
 */
class DataBaseConstant {

    static final String DATABASE_NAME = "greatplcetowork.db";
    static final String ID_COLUMN = "ID";
    static final String STATEMENT_COLUMN = "STATEMENT";
    static final String COMPANY_COLUMN = "COMPANY";
    static final String BENCHMARK_COLUMN = "BENCHMARK";
    static final String TABLE_STATEMENT = "STATEMENT";


    //average array constant
    static final int COMPANY_AVERAGE_INDEX = 0;
    static final int BENCH_MARK_AVERAGE_INDEX = 1;
    static final int GRAND_MEAN_INDEX = 5;

    //table to store other statements after 5 averages
    //Sri Lanka specific statements.
    static final String OTHER_STATEMENTS_TABLE = "OTHER_STATEMENTS";
    //use same columns as in other statements

    //Grate place to work statements
    static final String GPTW_STATEMENTS = "GPTWSTATEMETS";
    //use same columns as above two

    //COC_OR table
    static final String COC_TABLE = "COCTABLE";
    static final String STDEV_COLUMN = "STDEV";
    static final String COC_OR_COLUMN = "COC_OR";

    //theme table
    static final String THEME_TABLE = "THEMETABLE";
    static final String THEME_COLOUMN = "THEME";

    //sort table
    static final String SORT_TABLE = "SORTTABLE";
    static final String ABS_COLUMN = "ABS";
    static final String PRES_COLUMN = "PRESCOLOUMN";

    //AOI table
    static final String AOI_TEMP_TABLE = "AOITEMPTABLE";
    static final String AOI_TABLE = "AOITABLE";
    //AOS table
    static final String AOS_TEMP_TABLE = "AOSTEMPTABLE";
    static final String AOS_TABLE = "AOSTABLE";

    //Demography table
    static final String DEMOGRAPHY_TABLE = "DEMOGRAPHYTABLE";
    static final String DEMOGRAPHY_COLUMN = "DEMOGRAPHY";
    static final String FACTOR_COLUMN = "FACTORS";
    static final String MEAN_COLUMN = "MEAN";


    //sort table quarry
    static final String GET_JOIN_QUARRY = "SELECT s."+ ID_COLUMN +", s."+ STATEMENT_COLUMN +", s."+COMPANY_COLUMN
            + " , s."+BENCHMARK_COLUMN+", c."+COC_OR_COLUMN+", c."+STDEV_COLUMN+", t."+ THEME_COLOUMN
            + " FROM  "+TABLE_STATEMENT+" s, "+ COC_TABLE +" c , "+THEME_TABLE+" t"
            + " WHERE c."+STATEMENT_COLUMN+" = s."+STATEMENT_COLUMN+" and t."+STATEMENT_COLUMN+" = c."+STATEMENT_COLUMN
            + " ";

    //create tables
    static String getCreateTableQuarry(String tableName){

        return "CREATE TABLE "+tableName+" " +
                "("+ ID_COLUMN +"            INT     PRIMARY KEY    NOT NULL," +
                " "+ STATEMENT_COLUMN +"     TEXT    NOT NULL, " +
                " "+ COMPANY_COLUMN +"       REAL    NOT NULL, " +
                " "+ BENCHMARK_COLUMN +"     REAL    NOT NULL) ";
    }

    static String getCreateCOCORTableQuarry(String tableName){

        return "CREATE TABLE "+tableName+" " +
                "("+ ID_COLUMN +"            INT     PRIMARY KEY    NOT NULL," +
                " "+ STATEMENT_COLUMN +"     TEXT    NOT NULL, " +
                " "+ COC_OR_COLUMN +"       REAL    NOT NULL, " +
                " "+ STDEV_COLUMN +"     REAL    NOT NULL) ";
    }

    static String getCreateThemeTableQuarry(String tableName){

        return "CREATE TABLE "+tableName+" " +
                "("+ ID_COLUMN +"            INT     PRIMARY KEY    NOT NULL," +
                " "+ STATEMENT_COLUMN +"     TEXT    NOT NULL, " +
                " "+ THEME_COLOUMN +"        TEXT    NOT NULL) ";
    }

    static String getCreateSortTableQuarry(String tableName){

        return "CREATE TABLE "+tableName+" " +
                "("+ ID_COLUMN +"           INT     PRIMARY KEY    NOT NULL," +
                " "+ STATEMENT_COLUMN +"    TEXT    NOT NULL, " +
                " "+ COC_OR_COLUMN +"       REAL    NOT NULL, " +
                " "+ STDEV_COLUMN +"        REAL    NOT NULL, " +
                " "+ ABS_COLUMN + "        REAL    NOT NULL, " +
                " "+ PRES_COLUMN + "        REAL    NOT NULL, " +
                " "+ THEME_COLOUMN +"       TEXT    NOT NULL) ";
    }

    static String getCreateAOSortQuarry(String tableName){

        return "CREATE TABLE "+tableName+" " +
                "("+ ID_COLUMN +"            INT     PRIMARY KEY    NOT NULL," +
                " "+ STATEMENT_COLUMN +"     TEXT    NOT NULL, " +
                " "+ COC_OR_COLUMN +"       REAL    NOT NULL, " +
                " "+ THEME_COLOUMN +"        TEXT    NOT NULL) ";
    }

    static String getCreateAOTableQuarry(String tableName){

        return "CREATE TABLE "+tableName+" " +
                "("+ ID_COLUMN +"            INT     PRIMARY KEY    NOT NULL," +
                " "+ THEME_COLOUMN +"        TEXT    NOT NULL) ";
    }

    static String getCreateDemographyTableQuarry(String tableName){

        return "CREATE TABLE "+tableName+" " +
                "("+ ID_COLUMN +"            INT       NOT NULL," +
                " "+ DEMOGRAPHY_COLUMN +"     TEXT    NOT NULL, " +
                " "+ FACTOR_COLUMN +"       TEXT    NOT NULL, " +
                " "+ MEAN_COLUMN +"     REAL    NOT NULL) ";
    }

    //get insert in to table quarry for a given table
    static String getPreparedInsertStatement(String tableName){

        String INSERT_STATEMENT_TABLE_QUARRY = "INSERT INTO "+ tableName +
                "("+ ID_COLUMN +","+ STATEMENT_COLUMN +
                ","+ COMPANY_COLUMN +","+ BENCHMARK_COLUMN +")";

        return INSERT_STATEMENT_TABLE_QUARRY + "VALUES (?,?,?,?)";

    }

    static String getPreparedInsetCOCTable(){

        String INSERT_STATEMENT_TABLE_QUARRY = "INSERT INTO "+ COC_TABLE +
                "("+ ID_COLUMN +","+ STATEMENT_COLUMN +
                ","+ COC_OR_COLUMN +","+ STDEV_COLUMN +")";

        return INSERT_STATEMENT_TABLE_QUARRY + "VALUES (?,?,?,?)";
    }

    static String getPreparedInsetThemeTable(){

        String INSERT_STATEMENT_TABLE_QUARRY = "INSERT INTO "+ THEME_TABLE +
                "("+ ID_COLUMN +","+ STATEMENT_COLUMN +
                ","+ THEME_COLOUMN +")";

        return INSERT_STATEMENT_TABLE_QUARRY + "VALUES (?,?,?)";
    }

    static String getPreparedInsetSortTable(){

        String INSERT_STATEMENT_TABLE_QUARRY = "INSERT INTO "+ SORT_TABLE +
                "("+ ID_COLUMN +","+ STATEMENT_COLUMN +
                ","+ COC_OR_COLUMN +","+ STDEV_COLUMN +","+ ABS_COLUMN +","+PRES_COLUMN +","+ THEME_COLOUMN +")";

        return INSERT_STATEMENT_TABLE_QUARRY + "VALUES (?,?,?,?,?,?,?)";
    }

    static String getPreparedInsetAOSortTable(String table){

        String INSERT_STATEMENT_TABLE_QUARRY = "INSERT INTO "+ table +
                "("+ ID_COLUMN +","+ STATEMENT_COLUMN +
                ","+ COC_OR_COLUMN +","+ THEME_COLOUMN +")";

        return INSERT_STATEMENT_TABLE_QUARRY + "VALUES (?,?,?,?)";
    }

    static String getPreparedInsetAOTable(String table){

        String INSERT_STATEMENT_TABLE_QUARRY = "INSERT INTO "+ table +
                "("+ ID_COLUMN +
                ","+ THEME_COLOUMN +")";

        return INSERT_STATEMENT_TABLE_QUARRY + "VALUES (?,?)";
    }

    static String getPreparedInsertDemography(){

        String INSERT_STATEMENT_TABLE_QUARRY = "INSERT INTO "+ DEMOGRAPHY_TABLE +
                "("+ ID_COLUMN +","+ DEMOGRAPHY_COLUMN +
                ","+ FACTOR_COLUMN +","+ MEAN_COLUMN +")";

        return INSERT_STATEMENT_TABLE_QUARRY + "VALUES (?,?,?,?)";

    }
}
