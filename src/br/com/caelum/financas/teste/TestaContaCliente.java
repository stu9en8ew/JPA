package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Cliente;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TestaContaCliente {
	
	public static void main(String[] args) {
		
		Cliente cliente1 = new Cliente();
		cliente1.setNome("Leonardo");
		cliente1.setProfissao("Professor");
		cliente1.setEndereco("Rua xpto");
		
		Cliente cliente2 = new Cliente();
		cliente2.setNome("Carlos");
		cliente2.setProfissao("Pedreiro");
		cliente2.setEndereco("Rua Pedreira");
		
		Conta conta = new Conta();
		conta.setId(2);
		
		cliente1.setConta(conta);
		cliente2.setConta(conta);
		
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(cliente1);
		//em.persist(cliente2);
		
		em.getTransaction().commit();
		em.close();
	}

}
