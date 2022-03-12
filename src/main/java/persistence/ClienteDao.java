package persistence;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;

import model.Cliente;

public class ClienteDao {

	private GenericDao gDao;

	public ClienteDao(GenericDao gDao) {
		this.gDao = gDao;
	}

	public String iudCliente(String op, Cliente cl) throws SQLException, ClassNotFoundException {
		Connection c = gDao.getConnection();
		
		String sql = "{CALL sp_cliente (?,?,?,?,?,?,?)}";
		CallableStatement cs = c.prepareCall(sql);
		cs.setString(1, op);
		cs.setString(2, cl.getCpf());
		cs.setString(3, cl.getNome());
		cs.setString(4, cl.getEmail());
		cs.setDouble(5, cl.getLimite());
		cs.setString(6, cl.getNascimento().toString());
		cs.registerOutParameter(7, Types.VARCHAR);
		cs.execute();
		
		String saida = cs.getString(7);
		cs.close();
		c.close();
		
		return saida;
	}

}