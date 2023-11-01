import java.sql.Connection ;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.ResultSetMetaData;

public class Test2
{

    static void createTable(String tableName, String colNames[], String colDTypes[], Connection connection) throws Exception
    {
        String queryString = "CREATE TABLE IF NOT EXISTS "+tableName + "(";
        for(int i =0 ; i < colNames.length ; i++)
        {
            queryString = queryString + colNames[i] + " "+colDTypes[i] ;
            if(i!=colNames.length-1)
            {
                queryString = queryString + ",";
            }
        }

        queryString = queryString + ")";

        Statement statement = connection.createStatement();

        statement.execute(queryString);

        System.out.println("Table created successfully");

        statement.close();


    }

    static void createTable(Connection connection) throws Exception
    {
        String tableName = "TestTable";
        String colNames[] = {"test_col_1", "test_col_2", "test_col_3"};
        String colDTypes[] = {"INT","FLOAT","VARCHAR(5)"};
        createTable(tableName,colNames,colDTypes,connection);
    }

    static void dropTable(String tableName, Connection connection) throws Exception
    {
        String queryString = "DROP TABLE "+tableName;
        Statement statement = connection.createStatement();
        statement.execute(queryString);
        statement.close();
        System.out.println("Table Deleted Successfully");
    }

    static void dropTable(Connection connection) throws Exception
    {
        String tableName = "TestTable";
        dropTable(tableName,connection);
    }

    static void insertEntry(String tableName, String data[], Connection connection) throws Exception
    {
        String insertQuery = "INSERT INTO " + tableName + " VALUES " + "( ";
        for(int i = 0 ; i < data.length ; i++)
        {
            insertQuery = insertQuery + data[i] ;
            if(i!=data.length-1)
            {
                insertQuery = insertQuery + ",";
            }
        }

        insertQuery = insertQuery + ")";
        
        Statement statement = connection.createStatement();

        statement.execute(insertQuery);
    }

    static void insertData(Connection connection) throws Exception
    {
        String data[][] = {{"1","1.1","1"},{"2","2.2","2"},{"3","3.3","3"},{"4","4.4","4"}};

        for(int i = 0 ; i < data.length ; i++)
        {
            insertEntry("TestTable", data[i], connection);
        }

        System.out.println("Data inserted Successfully");
    }

    static void displayData(String tableName, Connection connection) throws Exception
    {
        String selectQuery = "SELECT * FROM " + tableName;
        
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery(selectQuery);

        ResultSetMetaData resultSetMetaData = resultSet.getMetaData();

        int colCnt = resultSetMetaData.getColumnCount();

        while(resultSet.next())
        {
            String row = "";

            for(int i = 1 ; i <= colCnt ; i++)
            {
                row = row + resultSet.getObject(i) + "  ";
            }

            System.out.println(row);
        }


    }

    static void displayData(Connection connection) throws Exception
    {
        String tableName = "TestTable";

        displayData(tableName,connection);
    }

    static void updateData(Connection connection, String tableName, String col1, String val1, String col2, String val2) throws Exception
    {
        Statement statement = connection.createStatement();
        String updateQuery = "UPDATE " + tableName + " SET " + col1 + " = " + val1 + " WHERE " + col2 + " = " + val2 ;
        statement.executeUpdate(updateQuery);

        statement.close();
    }

    static void updateData(Connection connection) throws Exception
    {
        String tableName = "TestTable";
        String col1 = "test_col_1";
        String val1 = "20";
        String col2 = "test_col_3";
        String val2 = "'2'";

        updateData(connection,tableName,col1,val1,col2,val2);

        System.out.println("Table updated successfully");
    }

    static void deleteData(Connection connection, String tableName, String col, String val ) throws Exception
    {
        Statement statement = connection.createStatement();
        String deleteQuery = "DELETE FROM " + tableName + " WHERE "+col+" = " + val;

        statement.execute(deleteQuery);
        statement.close();
        System.out.println("Data deleted successfully");
    }

    static void deleteData(Connection connection) throws Exception
    {
        String tableName = "TestTable";
        String col = "test_col_1";
        String val = "3";
        deleteData(connection, tableName, col, val);        
    }

    public static void main(String [] argv)
    {

        try
        {
            String driver = "com.mysql.cj.jdbc.Driver";
            Class.forName(driver);
            
            String url = "jdbc:mysql://localhost:3306/test_user_db";
            String username = "test_user";
            String password = "test_user";

            Connection connection = DriverManager.getConnection(url,username,password);

            createTable(connection);
            insertData(connection);
            displayData(connection);
            updateData(connection);
            displayData(connection);
            deleteData(connection);
            displayData(connection);
            dropTable(connection);

            connection.close();
    
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }


}