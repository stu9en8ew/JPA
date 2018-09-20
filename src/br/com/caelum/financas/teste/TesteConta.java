package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteConta {
	
	public static void main(String[] args) {
		
		Conta conta = new Conta();
		
		conta.setTitular("Jose");
		conta.setAgencia("Centro");
		conta.setBanco("Bradesco");
		conta.setNumero("123456");
		
		//EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas-mysql");
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		em.persist(conta);
		
		em.getTransaction().commit();
		
		
		em.close();
		
		//emf.close();
		
	}

}
