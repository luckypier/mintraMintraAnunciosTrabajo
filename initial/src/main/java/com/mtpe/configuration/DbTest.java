package com.mtpe.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * Created by precuay on 4/28/15.
 */
public class DbTest {


    //public static void main(String[] args)
    public static void rename(String[] args)
    {
        try
        {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection("jdbc:h2:/zyx/BD/mtpe/mtpe", "admin", "123" );
            Statement stmt = con.createStatement();
            //stmt.executeUpdate( "DROP TABLE users" );
            //stmt.executeUpdate( "CREATE TABLE users ( id INT, email VARCHAR(50), name VARCHAR(50) )" );
            stmt.executeUpdate( "INSERT INTO users ( id, email, name ) VALUES (3,'a@a.com', 'Claudio' )" );
            stmt.executeUpdate( "INSERT INTO users ( id, email, name ) VALUES (4,'b@a.com', 'Danitza' )" );

            ResultSet rs = stmt.executeQuery("SELECT * FROM users");
            while( rs.next() )
            {
                //String name = rs.getString("id");
                int id = rs.getInt("id");
                System.out.println( id );
            }
            stmt.close();
            con.close();
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
        }
    }

    public static void XXXX(String[] args)
    {
        try
        {
            Class.forName("org.h2.Driver");
            Connection con = DriverManager.getConnection("jdbc:h2:/zyx/BD/mtpe/mtpe", "admin", "123" );
            Statement stmt = con.createStatement();
            //stmt.executeUpdate( "DROP TABLE users" );
            stmt.executeUpdate( "CREATE TABLE JOBANNOUNCEMENT ( " +
                    "id INT, " +
                    "numreg VARCHAR(10), " +
                    "codinst VARCHAR(10)," +
                    "link VARCHAR(150)," +
                    "institucion VARCHAR(100)," +
                    "cargo VARCHAR(35)," +
                    "requisitos VARCHAR(500)," +
                    "descripcion VARCHAR(500)," +
                    "ocupacion VARCHAR(250)," +
                    "nroVacantes VARCHAR(50)," +
                    "nroConvocatoria VARCHAR(50)," +
                    "anotacion VARCHAR(500)" +
                    " )" );
            //stmt.executeUpdate( "INSERT INTO users ( id, email, name ) VALUES (3,'a@a.com', 'Claudio' )" );
            //stmt.executeUpdate( "INSERT INTO users ( id, email, name ) VALUES (4,'b@a.com', 'Danitza' )" );

            ResultSet rs = stmt.executeQuery("SELECT * FROM JOBANNOUNCEMENT");
            while( rs.next() )
            {
                //String name = rs.getString("id");
                int id = rs.getInt("id");
                System.out.println( id );
            }
            stmt.close();
            con.close();
        }
        catch( Exception e )
        {
            System.out.println( e.getMessage() );
        }
    }

}
