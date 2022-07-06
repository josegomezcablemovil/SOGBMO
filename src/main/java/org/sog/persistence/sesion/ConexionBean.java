package org.sog.persistence.sesion;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.sql.SQLException;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * @author jjunco
 */
public class ConexionBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private String resource = "configuracion.xml";
    private Reader reader = null;
    private Reader reader2 = null;
    private SqlSessionFactory sqlMapper = null;
    private SqlSessionFactory sqlMapperDB2 = null;

    public ConexionBean() throws SQLException {
        try {
            reader = Resources.getResourceAsReader(resource);
            reader2 = Resources.getResourceAsReader(resource);
            sqlMapper = new SqlSessionFactoryBuilder().build(reader, "DB1");
            sqlMapperDB2 = new SqlSessionFactoryBuilder().build(reader2, "DB2");
        } catch (IOException e) {
            throw new SQLException("Model.conectar()  " + e);
        }
    }

    public SqlSessionFactory getSqlMapper() {
        return sqlMapper;
    }

    public SqlSessionFactory getSqlMapperDB2() {
        return sqlMapperDB2;
    }

}
