package br.com.caelum.financas.teste;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.financas.dao.MovimentacaoDao;
import br.com.caelum.financas.enumerator.TipoMovimentacao;
import br.com.caelum.financas.modelo.Conta;
import br.com.caelum.financas.modelo.Movimentacao;
import br.com.caelum.financas.util.JPAUtil;

public class TesteFuncoesJPQL {
	
	public static void main(String[] args) {
		
		EntityManager em = new JPAUtil().getEntityManager();
		
		em.getTransaction().begin();
		
		Conta conta = new Conta();
		conta.setId(2);
		
		MovimentacaoDao movimentacaoDao = new MovimentacaoDao(em);
		
		// jpql1
		List<Movimentacao> listaMovimentacoes = new ArrayList<>();
		listaMovimentacoes = movimentacaoDao.getMovimentacoesPorContaETipo(conta, TipoMovimentacao.ENTRADA);
		
		// jpql2 - using sum
		BigDecimal somatorioMovimentacoes = movimentacaoDao.getSomatorioMovimentacoes(conta, TipoMovimentacao.SAIDA);
		
		// jpql3 - using avg
		Double mediaMovimentacao = movimentacaoDao.getMediaMovimentacao(conta, TipoMovimentacao.SAIDA);
		
		// jpql4 - using max
		BigDecimal valorMaximoMovimentacao = movimentacaoDao.getValorMaximoMovimentacao(conta);

		// jpql5 - using count
		Long qtdMovimentacaoes = movimentacaoDao.getQuantidadeMovimentacoes(conta);
		
		// jpql6 - using avg com group by
		List<Double> valoresMovimentacaoPorData = movimentacaoDao.getMovimentacaoPorData(conta, TipoMovimentacao.SAIDA);
		
		// using one namedQuery
		List<Movimentacao> movimentacoes = movimentacaoDao.getMovimentacoes();
		
		
		// using criteria JPA
		List<Movimentacao> movimentacoesPorCriteriaJPA = movimentacaoDao.getMovimentacaoPorCriteriaJPA(conta, TipoMovimentacao.ENTRADA);
		
		// using criteria Hibernate
		List<Movimentacao> movimentacaoPorCriteriaHibernate = movimentacaoDao.getMovimentacaoPorCriteriaHibernate(conta, TipoMovimentacao.SAIDA);
		
		
		
		// jpql1 - Printing result
		System.out.println("----- jpql1 -----");
		for (Movimentacao movimentacao : listaMovimentacoes) {
			System.out.println(movimentacao.toString());
		}
		
		// jpql2 - Printing result
		System.out.println("----- jpql2 - using sum -----");
		System.out.println("A soma é: " + somatorioMovimentacoes);
		
		
		// jpql3 - Printing result
		System.out.println("----- jpql3 - using avg -----");
		System.out.println("A media é: " + mediaMovimentacao);
		
		
		// jpql4 - Printing result
		System.out.println("----- jpql4 - using max -----");
		System.out.println("O valor maximo é: " + valorMaximoMovimentacao);
		
		
		// jpql5 - Printing result
		System.out.println("----- jpql5 - using count -----");
		System.out.println("Total de movimentações: " + qtdMovimentacaoes);
		
		
		// jpql6 - Printing result
		System.out.println("----- jpql6 - using avg com group by -----");
		for (Double valor : valoresMovimentacaoPorData) {
			System.out.println("A média do dia é:" + valor);
		}
		
		// movimentacoes - Printing result
		System.out.println("----- using one namedQuery -----");
		for (Movimentacao movimentacao : movimentacoes) {
			
			System.out.println("Using namedQuery :" + movimentacao.toString());
		}
		
		
		// movimentacoes com criteria JPA
		System.out.println("----- using criteria JPA -----");
		for (Movimentacao movCriteriaJPA : movimentacoesPorCriteriaJPA) {
			System.out.println(movCriteriaJPA.toString());
		}
		
		
		// movimentacoes com criteria Hibernate
		System.out.println("----- using criteria Hibernate -----");
		for (Movimentacao movCriteriaHibernate : movimentacaoPorCriteriaHibernate) {
			System.out.println(movCriteriaHibernate.toString());
			
		}
		
		
		em.getTransaction().commit();
		em.close();
	}

}
