package at.gotzi.bungeecloud.data.sql.builder;

import at.gotzi.bungeecloud.BungeeCloud;
import at.gotzi.bungeecloud.data.sql.SQLHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableBuilder {

    private final BungeeCloud htlkSystem;
    private final String perform;

    public TableBuilder(String table, Data[] data, BungeeCloud htlkSystem) {
        this.htlkSystem = htlkSystem;
        StringBuilder stringBuilder = new StringBuilder();
        for (Data d : data) {
            stringBuilder.append(d.getName().toUpperCase()).append(" ").append(d.getDataType()).append("(").append(d.getSize()).append("), ");
        }
        this.perform = "CREATE TABLE IF NOT EXISTS " + table + " (" + stringBuilder + "PRIMARY KEY (" + data[0].getName().toUpperCase() + "))";
        this.create();
    }

    private void create() {
        PreparedStatement ps;
        try {
            ps = this.htlkSystem.getSqlHandler().getConnection().prepareStatement(this.perform + "");
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public record Data(String name, SQLHandler.DataType dataType, int size) {

        public String getDataType() {
            if (this.dataType == SQLHandler.DataType.INT)
                return "int";
            else if (this.dataType == SQLHandler.DataType.DOUBLE)
                return "double";
            else if (this.dataType == SQLHandler.DataType.VARCHAR)
                return "VARCHAR";
            else if (this.dataType == SQLHandler.DataType.TINYINT) {
                return "TINYINT";
            }
            return null;
        }

        public String getName() {
            return name;
        }

        public int getSize() {
            return size;
        }
    }
}
