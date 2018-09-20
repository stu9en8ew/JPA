package br.com.caelum.financas.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import br.com.caelum.financas.enumerator.TipoMovimentacao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;

public class MovimentacaoDao {
	
	private EntityManager em;
	
	public MovimentacaoDao(EntityManager em) {
		this.em = em;
	}
	

	public List<Movimentacao> getMovimentacoesPorContaETipo(Conta conta, TipoMovimentacao entrada){
		
		
		String jpql1 = "Select m from Movimentacao m where m.conta = :pConta and m.tipo = :pTipo ";
		
		Query query1 = em.createQuery(jpql1);
		query1.setParameter("pConta", conta);
		query1.setParameter("pTipo", entrada);
		
		return query1.getResultList();
	}
	
	
	public BigDecimal getSomatorioMovimentacoes(Conta conta, TipoMovimentacao saida){
		
		String jpql2 = "Select sum(m.valor) from Movimentacao m where m.conta = :pConta and m.tipo = :pTipo";
		TypedQuery<BigDecimal> query2 = em.createQuery(jpql2, BigDecimal.class);
		query2.setParameter("pConta", conta);
		query2.setParameter("pTipo", saida);
		
		return query2.getSingleResult();
	}
	
	
	public Double getMediaMovimentacao(Conta conta, TipoMovimentacao saida) {
		
		String jpql3 = "Select avg(m.valor) from Movimentacao m where m.conta = :pConta and m.tipo = :pTipo";
		TypedQuery<Double> query = em.createQuery(jpql3, Double.class);
		query.setParameter("pConta", conta);
		query.setParameter("pTipo", saida);
		
		return query.getSingleResult();
		
	}
	
	
	public BigDecimal getValorMaximoMovimentacao(Conta conta) {
		
		String jpql4 = "Select max(m.valor) from Movimentacao m where m.conta = :pConta";
		TypedQuery<BigDecimal> query = em.createQuery(jpql4, BigDecimal.class);
		query.setParameter("pConta", conta);
		
		return query.getSingleResult();
		
	}
	
	
	public Long getQuantidadeMovimentacoes(Conta conta) {
		
		String jpql5 = "Select count(m) from Movimentacao m where m.conta = :pConta";
		TypedQuery<Long> query = em.createQuery(jpql5, Long.class);
		query.setParameter("pConta", conta);
		
		return query.getSingleResult();
		
	}
	
	public List<Double> getMovimentacaoPorData(Conta conta, TipoMovimentacao saida){
		String jpql6 = "select avg(m.valor) as data from Movimentacao m where m.conta = :pConta and m.tipo = :pTipo group by m.data";
		TypedQuery<Double> query = em.createQuery(jpql6, Double.class);
		query.setParameter("pConta", conta);
		query.setParameter("pTipo", saida);
		
		return query.getResultList();
		
	}
	
	// Using namedQuery
	public List<Movimentacao> getMovimentacoes(){
		
		TypedQuery<Movimentacao> movimentacoes = em.createNamedQuery("buscaMovimentacoes", Movimentacao.class);
		
		return movimentacoes.getResultList();
		
	}
	
	
	public List<Movimentacao> getMovimentacaoPorCriteriaJPA(Conta conta, TipoMovimentacao entrada){
		
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		
		CriteriaQuery<Movimentacao> criteriaQuery = criteriaBuilder.createQuery(Movimentacao.class);
		
		Root<Movimentacao> root = criteriaQuery.from(Movimentacao.class);
		
		Path<Integer> contaPath = root.<Conta>get("conta").<Integer>get("id");
		
		Path<String> tipoPath = root.<String>get("tipo");
		
		List<Predicate> predicates = new ArrayList<>();
		
		if (conta != null) {
			
			Predicate contaIgual = criteriaBuilder.equal(contaPath, conta.getId());
			predicates.add(contaIgual);
			
		}
		
		if (entrada != null) {
			Predicate tipoIgual = criteriaBuilder.equal(tipoPath, entrada);
			predicates.add(tipoIgual);
		}
		
		
		criteriaQuery.where((Predicate[])predicates.toArray(new Predicate[0]));
		
		TypedQuery<Movimentacao> typedQuery = em.createQuery(criteriaQuery);
		
		return typedQuery.getResultList();
		
	}
	
	
	
	public List<Movimentacao> getMovimentacaoPorCriteriaHibernate(Conta conta, TipoMovimentacao saida){
		
		Session session = em.unwrap(Session.class);
		
		Criteria criteria = session.createCriteria(Movimentacao.class);
		
		if (conta != null) {
			
			criteria.add(Restrictions.eq("conta.id", conta.getId()));
		}
		
		if (saida != null) {
			criteria.add(Restrictions.eq("tipo", saida));
		}
		
		return criteria.list();
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
