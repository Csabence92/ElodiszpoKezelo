package berwin.StockHandler.DataLayer;

import android.annotation.SuppressLint;
import android.os.StrictMode;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import berwin.StockHandler.DataLayer.Connection.SQLConnection;
import berwin.StockHandler.DataLayer.Interfaces.IConnectable;
import berwin.StockHandler.DataLayer.Enums.ServerState;

public abstract class DAOBase {

    @SuppressLint("SimpleDateFormat")
    protected final DateFormat dateFormatHosszu = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    @SuppressLint("SimpleDateFormat")
    protected final DateFormat dateFormatRovid = new SimpleDateFormat("yyyy.MM.dd");

    protected String querry;
    protected ArrayList<String> querries;

    private PreparedStatement prepStatement;
    private PreparedStatement prepStatementHelper;

    private ResultSet resultSet;
    protected ResultSet getResultSet() { return this.resultSet; }

    private ResultSet resultSetHelper;
    protected ResultSet getResultSetHelper() { return this.resultSetHelper; }

    protected void dispose() {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                //log error
            }
        }
        if (prepStatement != null) {
            try {
                prepStatement.close();
            } catch (SQLException e) {
                //log error
            }
        }
    }

    protected void disposeHelper() {
        if (resultSetHelper != null) {
            try {
                resultSetHelper.close();
            } catch (SQLException e) {
                //log error
            }
        }
        if (prepStatementHelper != null) {
            try {
                prepStatementHelper.close();
            } catch (SQLException e) {
                //log error
            }
        }
    }

    public  boolean reconnectBeforeReRun() {
        try {
            if (SQLConnection.getServerStatus() == ServerState.Offline) {
                SQLConnection.connectToServers();
                return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    protected void runHUNGARYQuerry(String kod) throws SQLException{
        this.prepStatement = SQLConnection.conHUNGARY.prepareStatement(kod);
        this.resultSet =  this.prepStatement.executeQuery();
    }

    protected void runSOPQuerry(String kod) throws SQLException {
        this.prepStatement = SQLConnection.conSOP.prepareStatement(kod);
        this.resultSet =  this.prepStatement.executeQuery();
    }

    protected void runSOPQuerryHelper(String kod) throws SQLException {
        this.prepStatementHelper = SQLConnection.conSOP.prepareStatement(kod);
        this.resultSetHelper = this.prepStatementHelper.executeQuery();
    }

    protected void runHUNGARYQuerryHelper(String kod) throws SQLException{
        this.prepStatementHelper = SQLConnection.conHUNGARY.prepareStatement(kod);
        this.resultSetHelper =  this.prepStatementHelper.executeQuery();
    }

    protected void runHUNGARYUpload(String kod) throws SQLException {
        this.prepStatement = SQLConnection.conHUNGARY.prepareStatement(kod);
        this.prepStatement.executeUpdate();
    }

    protected void runSOPUpload(String kod) throws SQLException {
        this.prepStatement = SQLConnection.conSOP.prepareStatement(kod);
        this.prepStatement.executeUpdate();
    }

    protected void runHUNGARYUploadHelper(String kod) throws SQLException {
        this.prepStatementHelper = SQLConnection.conHUNGARY.prepareStatement(kod);
        this.prepStatementHelper.execute();
    }

    protected void runSOPUploadHelper(String kod) throws SQLException {
        this.prepStatementHelper = SQLConnection.conSOP.prepareStatement(kod);
        this.prepStatementHelper.executeUpdate();
    }
}
