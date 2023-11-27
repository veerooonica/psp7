package app;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class SQLService {
    private static final String url = "jdbc:mysql://localhost:3306/human";
    private static final String username = "root";
    private static final String password = "aCin0rev_90";
    private static List<String> searchParams = new ArrayList<>();;
    public static void selectNotes(JTable table,  String lastName, String dolgnost) {
        String sql = "SELECT * FROM humans";
        if (lastName != null) {
            sql += " WHERE last_name = ?";
        }
        if (dolgnost != null) {
            if (lastName != null) {
                sql += " AND";
            } else {
                sql += " WHERE";
            }
            sql += " dolgnost = ?";
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/human", "root", "aCin0rev_90");
            PreparedStatement statement = connection.prepareStatement(sql);
            if (lastName != null) {
                statement.setString(1, lastName);
            }
            if (dolgnost != null) {
                statement.setString(lastName != null ? 2 : 1, dolgnost);
            }
            ResultSet resultSet = statement.executeQuery();
            DefaultTableModel tableModel = new DefaultTableModel();
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            for (int i = 1; i <= columnCount; i++) {
                tableModel.addColumn(metaData.getColumnName(i));
            }
            while (resultSet.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    rowData[i - 1] = resultSet.getString(i);
                }
                tableModel.addRow(rowData);
            }
            table.setModel(tableModel);
            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void addNote(List<String> params){
        String sql = "INSERT humans( last_name, first_name, " +
                "middle_name, gender, phone, address, birth_date, dolgnost, name_divis) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)" ;
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                String param = params.get(i);
                Object value = param;
                //System.out.println(param);
                if(i == 6){
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    value = dateFormat.parse(param);
                }
                statement.setObject(i + 1, value);
                //statement.setString(i + 1, params.get(i));
            }
            int rows = statement.executeUpdate();
            System.out.printf("Добавлено %d строк", rows);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void deleteNote(List<String> params){
        String sql = "DELETE FROM humans WHERE  last_name = ? AND first_name = ? " +
                "AND middle_name = ? " ;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/human", "root", "aCin0rev_90");
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                statement.setString(i + 1, params.get(i));
            }
            int rows = statement.executeUpdate();
            System.out.printf("Удалено %d строк", rows);
            statement.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static boolean checkNote(List<String> params){
        boolean noteIsExist = false;
        String sql = "SELECT * FROM humans WHERE  last_name = ? AND first_name = ? " +
                "AND middle_name = ?" ;
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/human", "root", "aCin0rev_90");
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                statement.setString(i + 1, params.get(i));
            }
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                noteIsExist = true;
                for(String element : params){
                    searchParams.add(element);
                }
            } else {
                noteIsExist = false;
            }
            statement.close();
            connection.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return noteIsExist;
    }
    public static void changeNote(List<String> params){
        int parameterCount = 0;
        String sql = "UPDATE humans SET last_name = ?, first_name= ?, middle_name = ?, " +
                "gender = ?, phone = ?, address = ?, birth_date = ?, dolgnost = ?, name_divis = ?" +
                "WHERE last_name = ? AND first_name = ? AND middle_name = ?";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/human", "root", "aCin0rev_90");
            PreparedStatement statement = connection.prepareStatement(sql);
            for (int i = 0; i < params.size(); i++) {
                String param = params.get(i);
                Object value = param;
                if(i == 6){
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    value = dateFormat.parse(param);
                }
                statement.setObject(i + 1, value);
                parameterCount++;
            }
            for (int i = 0; i < searchParams.size(); i++) {
                statement.setObject(parameterCount + 1, searchParams.get(i));
                parameterCount++;
            }
            int rows = statement.executeUpdate();
            System.out.printf("Изменено %d строк", rows);
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
