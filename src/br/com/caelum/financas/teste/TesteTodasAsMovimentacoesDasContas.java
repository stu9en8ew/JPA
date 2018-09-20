package br.com.caelum.financas.teste;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.util.JPAUtil;

public class TesteTodasAsMovimentacoesDasContas {
	
	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		String jpql = "Select distinct c from Conta c left join fetch c.movimentacoes";
		
		//Query query = em.createQuery(jpql);
		
		TypedQuery<Conta> resultados = em.createQuery(jpql, Conta.class);
		
		for (Conta conta : resultados.getResultList()) {
			
			System.out.println(conta.getTitular());
			
			System.out.println(conta.getMovimentacoes());
			
		}
		
		em.getTransaction().commit();
		
		em.close();
	}
	

}
