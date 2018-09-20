package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteBuscaContaDetached {
	
	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		Conta conta = em.find(Conta.class, 1);
		
		System.out.println(conta.getTitular());
		
		em.getTransaction().commit();
		
		em.close();
		
		
		EntityManager em2 = new JPAUtil().getEntityManager();
		
		em2.getTransaction().begin();
		
		// Estado Detached
		conta.setTitular("Jose Aldo");
		
		//em.persist(conta); // Nao gerenciado pelo DB 
		
		em.merge(conta); // Estado Managed
		
		em2.getTransaction().commit();
		
		em2.close();
		
		
	}

}
