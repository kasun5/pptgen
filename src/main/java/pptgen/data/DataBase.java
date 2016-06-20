package pptgen.data;

import java.io.File;
import java.sql.*;


/**
 * Created by Thilina on 6/8/2016.
 *
 * This class store all data bse connections and table details.
 * This also handle the creation of table and insertion.
 * Keep all data read from excel file in tables and arrays and provide package local methods to the
 * pptgen.data.DataStore to provide data to the other packages.
 *
 */
class DataBase {

    private Connection con;
    private Statement stmnt;
    private int statementId = 0;
    private float allAverages[][] = new float[FileReadConstant.MAX_NUM_OF_AVERAGES+1][2];
    private float averages[][] = new float[FileReadConstant.MAX_NUM_OF_AVERAGES][2];
    private int numberOfRespondents;
    private int cocID = 0;
    private int themeID = 0;

    /**
     * @author Thilina
     *
     * Constructor for the pptgen.data.DataBase class
     *
     * Create a connection to data base and tables withing the database
     *
     * @throws DataBaseException
     * */
    DataBase() throws DataBaseException {

        try {
            this.createDB();
            this.createTables();
        } catch (ClassNotFoundException e) {
            throw new DataBaseException(e);
        } catch (SQLException e) {
            throw new DataBaseException(e);
        }

        System.out.println("Database added");

    }

    /**
     * @author Thilina
     * 
     * Create data base to store tables
     *
     * @throws ClassNotFoundException
     * @throws SQLException
     * */
    private void createDB() throws ClassNotFoundException, SQLException {

        Class.forName("org.sqlite.JDBC");
        this.con = DriverManager.getConnection("jdbc:sqlite:" + DataBaseConstant.DATABASE_NAME);
    }

    //Core Statement table methods
    /**
     * @author Thilina
     * 
     * Create all statement tables
     *
     * @throws SQLException
     * */
    private void createTables() throws SQLException {

        stmnt = con.createStatement();
        String createtablesql = DataBaseConstant.getCreateTableQuarry(DataBaseConstant.TABLE_STATEMENT);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateTableQuarry(DataBaseConstant.OTHER_STATEMENTS_TABLE);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateTableQuarry(DataBaseConstant.GPTW_STATEMENTS);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateCOCORTableQuarry(DataBaseConstant.COC_TABLE);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateThemeTableQuarry(DataBaseConstant.THEME_TABLE);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateSortTableQuarry(DataBaseConstant.SORT_TABLE);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateAOSortQuarry(DataBaseConstant.AOI_TEMP_TABLE);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateAOSortQuarry(DataBaseConstant.AOS_TEMP_TABLE);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateAOTableQuarry(DataBaseConstant.AOI_TABLE);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateAOTableQuarry(DataBaseConstant.AOS_TABLE);
        stmnt.executeUpdate(createtablesql);
        createtablesql = DataBaseConstant.getCreateDemographyTableQuarry(DataBaseConstant.DEMOGRAPHY_TABLE);
        stmnt.executeUpdate(createtablesql);
        stmnt.close();
        System.out.println("Tables created successfully");
    }

    /**
     * @author Thilina
     *
     * Insert single statement into given statement table with the company and bench mark value
     *
     * @param table name of the table that statement insert into
     * @param statement statement.
     * @param company company value of the statement.
     * @param bench company value of the statement.
     *
     * @throws DataBaseException
     * */
    private void insertIntoStatementsTable(String table, String statement, float company, float bench)
            throws DataBaseException {

        try {
            con.setAutoCommit(false);
            String quarry = DataBaseConstant.getPreparedInsertStatement(table);
            PreparedStatement prestatement= con.prepareStatement(quarry);
            prestatement.setInt(1, statementId);
            prestatement.setString(2,statement);
            prestatement.setFloat(3,company);
            prestatement.setFloat(4,bench);

            prestatement.executeUpdate();
            statementId++;
            con.commit();

            System.out.println("Inset successful in to " + table);

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }

    }

    /**
     * @author Thilina
     *
     * insert data into coc or table wich holds cocor values and stdev values for each statement
     *
     * @param statement statement
     * @param coc COC_OR value of the statement
     * @param stdev STDEV of the statement
     *
     * @throws DataBaseException
     *
     * */
    void insetIntoCOCTable(String statement, float coc, float stdev) throws DataBaseException {

        try {
            con.setAutoCommit(false);
            String quarry = DataBaseConstant.getPreparedInsetCOCTable();
            PreparedStatement prestatement= con.prepareStatement(quarry);
            prestatement.setInt(1,cocID);
            prestatement.setString(2,statement);
            prestatement.setFloat(3,coc);
            prestatement.setFloat(4,stdev);

            prestatement.executeUpdate();
            cocID++;
            con.commit();

            System.out.println("Inset successful in to COC or");

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }

    }

    /**
     * @author Thilina
     *
     * insert calculated data into sort table
     *
     * @param statement statement
     * @param theme theme for statement
     *
     * @throws DataBaseException
     *
     * */
    private void insertIntoSortTable(int id, String statement, float cocor, float stdev, float abs,
                                     float pres, String theme)
            throws DataBaseException {

        try {
            con.setAutoCommit(false);
            String quarry = DataBaseConstant.getPreparedInsetSortTable();
            PreparedStatement prestatement= con.prepareStatement(quarry);
            prestatement.setInt(1,id);
            prestatement.setString(2,statement);
            prestatement.setFloat(3,cocor);
            prestatement.setFloat(4,stdev);
            prestatement.setFloat(5,abs);
            prestatement.setFloat(6,pres);
            prestatement.setString(7,theme);
            prestatement.executeUpdate();
            con.commit();

            System.out.println("Sort update");

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    /**
     * @author Thilina
     *
     * insert data into theme table wich map the theme and the sattement
     *
     * @param statement statement
     * @param theme theme for statement
     *
     * @throws DataBaseException
     *
     * */
    void insertIntoThemeTable(String statement, String theme) throws DataBaseException {

        try {
            con.setAutoCommit(false);
            String quarry = DataBaseConstant.getPreparedInsetThemeTable();
            PreparedStatement prestatement= con.prepareStatement(quarry);
            prestatement.setInt(1,themeID);
            prestatement.setString(2,statement);
            prestatement.setString(3,theme);
            prestatement.executeUpdate();
            themeID++;
            con.commit();

            System.out.println("Inset successful in to theme or");

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }



    /**
     * @author Thilina
     *
     * Insert core satement
     *
     * @param statement statement.
     * @param company company value of the statement.
     * @param bench company value of the statement.
     *
     * @throws DataBaseException
     *
     * */
    void insertCoreStatement(String statement, float company, float bench) throws DataBaseException {
        this.insertIntoStatementsTable(DataBaseConstant.TABLE_STATEMENT,statement,company,bench);
    }

    /**
     * @author Thilina
     *
     * Insert other satement
     *
     * @param statement statement.
     * @param company company value of the statement.
     * @param bench company value of the statement.
     *
     * @throws DataBaseException
     *
     * */
    void insertOtherStatement(String statement, float company, float bench) throws DataBaseException {
        this.insertIntoStatementsTable(DataBaseConstant.OTHER_STATEMENTS_TABLE,statement,company,bench);
    }

    /**
     * @author Thilina
     *
     * Insert GPTW satement
     *
     * @param statement statement.
     * @param company company value of the statement.
     * @param bench company value of the statement.
     *
     * @throws DataBaseException
     *
     * */
    void insertGPTWStatement(String statement, float company, float bench) throws DataBaseException {
        this.insertIntoStatementsTable(DataBaseConstant.GPTW_STATEMENTS,statement,company,bench);
    }

    /**
     * @author Thilina
     *
     * Inset allAverages in to avarage array
     *
     * @param companyAverage average value of the company
     * @param benchAverage average value of the bench mark
     * @param numberOfAverages index of average array
     * */
    void insertAverage(float companyAverage, float benchAverage, int numberOfAverages){

        allAverages[numberOfAverages][DataBaseConstant.COMPANY_AVERAGE_INDEX] = companyAverage;
        allAverages[numberOfAverages][DataBaseConstant.BENCH_MARK_AVERAGE_INDEX] = benchAverage;

    }

    void setNumberOfRespondents(int val){
        numberOfRespondents = val;
    }


    /**
     * @author Thilina
     *
     * return result set for a given quary execution
     *
     * @param quarry quarry to be executed
     *
     * @throws SQLException
     * */
     ResultSet runQuarry(String quarry) throws SQLException {

        con.setAutoCommit(false);
        stmnt = con.createStatement();
        ResultSet rs = stmnt.executeQuery(quarry);

        return rs;
    }

    //aoi and aos section
    /**
     * @author Thilina
     *
     * This so the calculation on data and map themes and store them nessr
     *
     *
     * */
     void sortTable(){
        try {
            ResultSet resultSet = this.runQuarry(DataBaseConstant.GET_JOIN_QUARRY);
            //// TODO: 6/15/2016 do sorting and store values in the pptgen.data.DataBaseConstant.SORT_TABLE
            //// TODO: 6/15/2016 This result set contains all necessary data for the calculation and sorting and themes
            while ( resultSet.next() ) {
                int id = resultSet.getInt(DataBaseConstant.ID_COLUMN);
                String statement = resultSet.getString(DataBaseConstant.STATEMENT_COLUMN);
                float company = resultSet.getFloat(DataBaseConstant.COMPANY_COLUMN);
                float bench = resultSet.getFloat(DataBaseConstant.BENCHMARK_COLUMN);
                float cocor = resultSet.getFloat(DataBaseConstant.COC_OR_COLUMN);
                float stdev = resultSet.getFloat(DataBaseConstant.STDEV_COLUMN);
                String theme = resultSet.getString(DataBaseConstant.THEME_COLOUMN);
                float present = ((company - bench)/company)*100;

                this.insertIntoSortTable(id,statement,cocor,stdev,company,present,theme);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (DataBaseException e) {
            e.printStackTrace();
        }
    }

    /**
     * @author Thilina
     *
     * inser into AOS and AOI temp tables to sort and extract top 5 and botom 5 themes
     *
     * @param table Name of the table AOI or AOS
     * @param id id
     * @param statement statement
     * @param coc coc value of the statement
     * @param theme theme of the statement
     *
     * @throws DataBaseException
     * */
    private void insertAOSort(String table, int id, String statement, float coc, String theme) throws DataBaseException {

        try {
            con.setAutoCommit(false);
            String quarry = DataBaseConstant.getPreparedInsetAOSortTable(table);
            PreparedStatement prestatement= con.prepareStatement(quarry);
            prestatement.setInt(1,id);
            prestatement.setString(2,statement);
            prestatement.setFloat(3,coc);
            prestatement.setString(4,theme);
            prestatement.executeUpdate();
            con.commit();

            System.out.println("Inset successful in to " + table);

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    /**
     * @author Thilina
     *
     * insert data into aAOI and AOS table to hold to and botom 5 themes
     *
     * @param table name of the table AOI or AOS
     * @param id id
     * @param theme theme for statement
     *
     * @throws DataBaseException
     *
     * */
    private void insertIntoAOTable(String table, int id, String theme) throws DataBaseException {

        try {
            con.setAutoCommit(false);
            String quarry = DataBaseConstant.getPreparedInsetAOTable(table);
            PreparedStatement prestatement= con.prepareStatement(quarry);
            prestatement.setInt(1,id);
            prestatement.setString(2,theme);
            prestatement.executeUpdate();
            con.commit();

            System.out.println("Inset successful in to "+ table);

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    //insert in to AO temps
    public void insertAOITemp(int id, String statement, float coc, String theme) throws DataBaseException {

        this.insertAOSort(DataBaseConstant.AOI_TEMP_TABLE,id,statement,coc,theme);
    }

    public void insertAOSTemp(int id, String statement, float coc, String theme) throws DataBaseException {
        this.insertAOSort(DataBaseConstant.AOS_TEMP_TABLE,id,statement,coc,theme);
    }

    //inser to AO last
    public void insertAOI(int id, String theme) throws DataBaseException {

        this.insertIntoAOTable(DataBaseConstant.AOI_TABLE,id,theme);
    }

    public void insertAOS(int id, String theme) throws DataBaseException {
        this.insertIntoAOTable(DataBaseConstant.AOS_TABLE,id,theme);
    }

    //demography
    /**
     *
     * @author Thilina
     *
     * Provide method to insert value in to demography table
     *
     * @param id id
     * @param demography demography
     * @param factor factors of the demography
     * @param mean geand mean value for demography factor
     *
     * */
    void insertDemography(int id, String demography, String factor, float mean) throws DataBaseException {

        try {
            con.setAutoCommit(false);
            String quarry = DataBaseConstant.getPreparedInsertDemography();
            PreparedStatement prestatement= con.prepareStatement(quarry);
            prestatement.setInt(1,id);
            prestatement.setString(2,demography);
            prestatement.setString(3,factor);
            prestatement.setFloat(4,mean);
            prestatement.executeUpdate();
            con.commit();

            System.out.println("Inset successful in to " + DataBaseConstant.DEMOGRAPHY_TABLE);

        } catch (SQLException e) {
            throw new DataBaseException(e);
        }
    }

    //other generic DB methods

    /**
     * @author Thilina
     *
     * This will close connection to the Data base
     *
     * */
    boolean closeConnection(){

        boolean closed = false;

        try {

            con.close();
            this.deleteDataBase();
            closed = true;
        } catch (SQLException e) {
            //todo handle
            e.printStackTrace();
        }

        return closed;
    }

    /**
     * @author Thilina
     *
     * Delete database after use
     * */
    private boolean deleteDataBase(){

        boolean isDelete = false;

        try{

            File file = new File(DataBaseConstant.DATABASE_NAME);

            if(file.delete()){
                isDelete = true;
                System.out.println(file.getName() + " is deleted!");
            }else{
                System.out.println("Delete operation is failed.");
            }

        }catch(Exception e){

            //todo handle
            e.printStackTrace();

        }

        return isDelete;
    }

    //methods for data store
    //get methods

    int getNumberOfRespondents(){
        return numberOfRespondents;
    }

    //copy basic averages from all averages
    private void setupAverages(){

        for(int i=0;i<FileReadConstant.MAX_NUM_OF_AVERAGES;i++){
            for(int j=0;j<2;j++){
                averages[i][j] = allAverages[i][j];
            }
        }

    }

    float[][] getAverages(){
        this.setupAverages();
        return averages;

    }

    float getCompanyGrandMean(){
        return allAverages[DataBaseConstant.GRAND_MEAN_INDEX][DataBaseConstant.COMPANY_AVERAGE_INDEX];
    }

    float getBenchMarkGrandMean(){
        return allAverages[DataBaseConstant.GRAND_MEAN_INDEX][DataBaseConstant.BENCH_MARK_AVERAGE_INDEX];
    }

}
