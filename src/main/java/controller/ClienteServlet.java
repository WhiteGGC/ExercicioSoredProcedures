package controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Cliente;
import persistence.ClienteDao;
import persistence.GenericDao;

@WebServlet("/cliente")
public class ClienteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ClienteServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cpf = request.getParameter("cpf");
		String nome = request.getParameter("nome");
		String email = request.getParameter("email");
		String limite = request.getParameter("limite");
		String nascimento = request.getParameter("nascimento");
		String botao = request.getParameter("botao");
		String op = botao.substring(0, 1);
		if (op.equals("A")) {
			op = "U";
		}
		
		Cliente c = new Cliente();
		c = validaCliente(cpf, nome, email, limite, nascimento);
		String erro = "";
		String saida = "";
		try {
			if (c == null) {
				erro = "Preencha os campos corretamente";
			} else {
				GenericDao gDao = new GenericDao();
				ClienteDao cDao = new ClienteDao(gDao);
				saida = cDao.iudCliente(op, c);
			}
		} catch (SQLException | ClassNotFoundException e) {
			erro = e.getMessage();
		} finally {
			RequestDispatcher rd = request.getRequestDispatcher("index.jsp");
			request.setAttribute("erro", erro);
			request.setAttribute("saida", saida);
			rd.forward(request, response);
		}
	}

	private Cliente validaCliente(String cpf, String nome, String email, String limite, String nascimento) {
		Cliente c = new Cliente();
		if (cpf.equals("") || nome.equals("") || email.equals("") || limite.equals("") || nascimento.equals("")) {
			c = null;
		} else {
			c.setCpf(cpf);
			c.setNome(nome);
			c.setEmail(email);
			c.setLimite(Double.parseDouble(limite));
			c.setNascimento(nascimento);
		}
		return c;
	}

}
