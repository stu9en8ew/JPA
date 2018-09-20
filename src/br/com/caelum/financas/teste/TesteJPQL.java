package br.com.caelum.financas.teste;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.com.caelum.financas.enumerator.TipoMovimentacao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteJPQL {
	
	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		Conta conta = new Conta();
		conta.setId(2);
		
		String jpql = "Select m from Movimentacao m "; 
			   jpql+="where m.conta = :pConta ";
			   jpql+="and m.tipo = :pTipo ";
			   jpql +="order by m.valor desc ";
		
		   
		Query query = em.createQuery(jpql);
		query.setParameter("pConta", conta);
		query.setParameter("pTipo", TipoMovimentacao.ENTRADA);
		
		List<Movimentacao> result = query.getResultList();
		
		for (Movimentacao movimentacao : result) {
			
			System.out.println(movimentacao.toString());
		}
		
		
		/*
		TypedQuery<Movimentacao> query = em.createQuery(jpql, Movimentacao.class);
		query.setParameter("pConta", conta);
		query.setParameter("pTipo", TipoMovimentacao.ENTRADA);
		
		
		for (Movimentacao movimentacao : query.getResultList()) {
			System.out.println(movimentacao.toString());
		}
		*/
		
		em.getTransaction().commit();
		em.close();
	}
	
	

}
