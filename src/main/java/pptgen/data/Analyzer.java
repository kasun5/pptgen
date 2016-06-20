package pptgen.data;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Thilina on 6/16/2016.
 *
 * analyze sort table to find top and bottom 5 themes
 *
 */
class Analyzer {

    private static DataBase dataBase;
    private static ResultSet resultset;
    private static int aoID = 0;
    private static int themId = 0;

    static void start(){

        dataBase = ReadFile.getDataBase();
        sortAOSData();
        sortAOIData();

    }

    private static void insertIntoTemptable(String tableName) throws DataBaseException {

        if(resultset == null) {
            System.out.println("result set not initialize");
            throw new DataBaseException("result set not initialize");
        }


        try {

            while(resultset.next()){

                String statement = resultset.getString(DataBaseConstant.STATEMENT_COLUMN);
                float cocor = resultset.getFloat(DataBaseConstant.COC_OR_COLUMN);
                String theme = resultset.getString(DataBaseConstant.THEME_COLOUMN);

                if(tableName.equalsIgnoreCase(DataBaseConstant.AOS_TEMP_TABLE)){
                    dataBase.insertAOSTemp(aoID++,statement,cocor,theme);
                }
                else {
                    dataBase.insertAOITemp(aoID++,statement,cocor,theme);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void insertThemeTable(String tableName) throws DataBaseException {

        if(resultset == null) {
            System.out.println("result set not initialize");
            throw new DataBaseException("result set not initialize");
        }

        try {

            while(resultset.next()){

                String theme = resultset.getString(DataBaseConstant.THEME_COLOUMN);

                if(tableName.equalsIgnoreCase(DataBaseConstant.AOS_TABLE)){
                    dataBase.insertAOS(themId++,theme);
                }
                else {
                    dataBase.insertAOI(aoID++,theme);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private static void sortAOSData(){

        String tableName = DataBaseConstant.AOS_TEMP_TABLE;
        try {

            resultset = dataBase.runQuarry("SELECT * FROM "+ DataBaseConstant.SORT_TABLE +" ORDER BY "
                    + DataBaseConstant.ABS_COLUMN +" DESC LIMIT "+ AnalyzeConstants.AOSNI_SORT_LIMIT +";");

            insertIntoTemptable(tableName);

            resultset = dataBase.runQuarry("SELECT * FROM "+DataBaseConstant.SORT_TABLE+" ORDER BY "
                    + DataBaseConstant.PRES_COLUMN +" DESC LIMIT "+ AnalyzeConstants.AOSNI_SORT_LIMIT +";");

            insertIntoTemptable(tableName);

            resultset = dataBase.runQuarry("SELECT * FROM "+ DataBaseConstant.SORT_TABLE +" ORDER BY "
                    + DataBaseConstant.STDEV_COLUMN +" ASC LIMIT "+ AnalyzeConstants.AOSNI_SORT_LIMIT +";");

            insertIntoTemptable(tableName);

            resultset = dataBase.runQuarry("SELECT DISTINCT "+DataBaseConstant.THEME_COLOUMN+" FROM (SELECT "
                    + DataBaseConstant.THEME_COLOUMN +", "+DataBaseConstant.STATEMENT_COLUMN
                    + ", COUNT("+DataBaseConstant.STATEMENT_COLUMN+"), "+DataBaseConstant.COC_OR_COLUMN
                    + " FROM "+DataBaseConstant.AOS_TEMP_TABLE+" GROUP BY "+DataBaseConstant.STATEMENT_COLUMN
                    + " ORDER BY COUNT("+DataBaseConstant.STATEMENT_COLUMN+") DESC, "+DataBaseConstant.COC_OR_COLUMN
                    + "  DESC) LIMIT "+AnalyzeConstants.AOSNI_THEME_LIMIT+";");

            insertThemeTable(DataBaseConstant.AOS_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }

    }

    private static void sortAOIData(){

        String tableName = DataBaseConstant.AOI_TEMP_TABLE;

        try {

            resultset = dataBase.runQuarry("SELECT * FROM "+DataBaseConstant.SORT_TABLE+" ORDER BY "
                    + DataBaseConstant.ABS_COLUMN+" ASC LIMIT "+ AnalyzeConstants.AOSNI_SORT_LIMIT+";");

            insertIntoTemptable(tableName);

            resultset = dataBase.runQuarry("SELECT * FROM "+DataBaseConstant.SORT_TABLE+" ORDER BY "
                    + DataBaseConstant.PRES_COLUMN+" ASC LIMIT "+AnalyzeConstants.AOSNI_SORT_LIMIT+";");

            insertIntoTemptable(tableName);

            resultset = dataBase.runQuarry("SELECT * FROM "+DataBaseConstant.SORT_TABLE+" ORDER BY "
                    + DataBaseConstant.STDEV_COLUMN+" DESC LIMIT "+AnalyzeConstants.AOSNI_SORT_LIMIT+";");

            insertIntoTemptable(tableName);

            resultset = dataBase.runQuarry("SELECT DISTINCT "+DataBaseConstant.THEME_COLOUMN
                    + " FROM (SELECT "+DataBaseConstant.THEME_COLOUMN+", "+DataBaseConstant.STATEMENT_COLUMN
                    + ", COUNT("+DataBaseConstant.STATEMENT_COLUMN+"), "+DataBaseConstant.COC_OR_COLUMN
                    + " FROM "+DataBaseConstant.AOI_TEMP_TABLE+" GROUP BY "+DataBaseConstant.STATEMENT_COLUMN
                    + " ORDER BY COUNT("+DataBaseConstant.STATEMENT_COLUMN+") DESC, "+DataBaseConstant.COC_OR_COLUMN
                    + " DESC) LIMIT "+AnalyzeConstants.AOSNI_THEME_LIMIT+";");

            insertThemeTable(DataBaseConstant.AOI_TABLE);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }
}
