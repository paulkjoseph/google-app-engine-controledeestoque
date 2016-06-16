/*
 * *****************************************************************************
 * Copyright (c) 2016
 * Propriedade da Paul Joseph LTD.
 * Todos os direitos reservados, com base nas leis brasileiras de copyright
 * Este software é confidencial e de propriedade intelectual da Paul Joseph LTD
 * ****************************************************************************
 * Projeto: Sistema de Controle de Estoque
 * Arquivo: ProdutoDAO.java
 * ****************************************************************************
 * Histórico de revisões
 * Nome				Data		Descrição
 * ****************************************************************************
 * Paul Joseph		14 de jun de 2016	Versão inicial
 * ****************************************************************************
 */
package br.com.latosensu.ufscar.controleestoque.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.SortDirection;

import br.com.latosensu.ufscar.controleestoque.models.Produto;

/**
 * ProdutoDAO
 * 
 * @author Paul Joseph
 * @since 14 de jun de 2016
 */
public abstract class ProdutoDAO {

	private static final Logger log = Logger.getLogger("ProdutoManager");

	public Produto buscarProduto(String sku) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter skuFilter = new FilterPredicate("Sku", FilterOperator.EQUAL, sku);
		Query query = new Query("Produtos").setFilter(skuFilter);
		Entity ProdutoEntity = datastore.prepare(query).asSingleEntity();
		if (ProdutoEntity != null) {
			Produto Produto = entityToProduto(ProdutoEntity);
			return Produto;
		} else {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	public List<Produto> buscarProdutos() {
		List<Produto> Produtos = new ArrayList<>();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Query query;
		query = new Query("Produtos").addSort("Sku", SortDirection.ASCENDING);
		List<Entity> ProdutosEntities = datastore.prepare(query).asList(FetchOptions.Builder.withDefaults());
		for (Entity ProdutoEntity : ProdutosEntities) {
			Produto Produto = entityToProduto(ProdutoEntity);
			Produtos.add(Produto);
		}
		return Produtos;
	}

	public Produto salvarProduto(Produto Produto) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		if (!checkIfSkuExist(Produto)) {
			Key ProdutoKey = KeyFactory.createKey("Produtos", "ProdutoKey");
			Entity ProdutoEntity = new Entity("Produtos", ProdutoKey);
			ProdutoToEntity(Produto, ProdutoEntity);
			datastore.put(ProdutoEntity);
			Produto.setId(ProdutoEntity.getKey().getId());
		} else {
			throw new WebApplicationException("Já existe um produto cadastrado com o mesmo código", Status.BAD_REQUEST);
		}
		return Produto;
	}

	public Produto deletarProduto(String sku) {
		log.finest("Tentando apagar produto com código=[" + sku + "]");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter skuFilter = new FilterPredicate("Sku", FilterOperator.EQUAL, sku);
		Query query = new Query("Produtos").setFilter(skuFilter);
		Entity ProdutoEntity = datastore.prepare(query).asSingleEntity();
		if (ProdutoEntity != null) {
			datastore.delete(ProdutoEntity.getKey());
			log.info("Produto com código=[" + sku + "] apagado com sucesso");
			Produto Produto = entityToProduto(ProdutoEntity);
			return Produto;
		} else {
			log.severe("Erro ao apagar produto com código=[" + sku + "]. Produto não encontrado!");
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

	public Produto alterarProduto(String sku, Produto Produto) {
		if (Produto.getId() != 0) {
			if (!checkIfSkuExist(Produto)) {
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Filter skuFilter = new FilterPredicate("Sku", FilterOperator.EQUAL, sku);
				Query query = new Query("Produtos").setFilter(skuFilter);
				Entity ProdutoEntity = datastore.prepare(query).asSingleEntity();
				if (ProdutoEntity != null) {
					ProdutoToEntity(Produto, ProdutoEntity);
					datastore.put(ProdutoEntity);
					Produto.setId(ProdutoEntity.getKey().getId());
					return Produto;
				} else {
					throw new WebApplicationException(Status.NOT_FOUND);
				}
			} else {
				throw new WebApplicationException("Já existe um produto cadastrado com o mesmo código",
						Status.BAD_REQUEST);
			}
		} else {
			throw new WebApplicationException("O ID do produto deve ser informado para ser alterado",
					Status.BAD_REQUEST);
		}
	}

	private void ProdutoToEntity(Produto Produto, Entity ProdutoEntity) {
		ProdutoEntity.setProperty("Sku", Produto.getSku());
		ProdutoEntity.setProperty("Nome", Produto.getNome());
		ProdutoEntity.setProperty("Descricao", Produto.getDescricao());
		ProdutoEntity.setProperty("Preco", Produto.getPreco());
	}

	private Produto entityToProduto(Entity ProdutoEntity) {
		Produto Produto = new Produto();
		Produto.setId(ProdutoEntity.getKey().getId());
		Produto.setSku((String) ProdutoEntity.getProperty("Sku"));
		Produto.setNome((String) ProdutoEntity.getProperty("Nome"));
		Produto.setDescricao((String) ProdutoEntity.getProperty("Descricao"));
		Produto.setPreco(Float.parseFloat(ProdutoEntity.getProperty("Preco").toString()));
		return Produto;
	}

	private boolean checkIfSkuExist(Produto Produto) {
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Filter skuFilter = new FilterPredicate("Sku", FilterOperator.EQUAL, Produto.getSku());
		Query query = new Query("Produtos").setFilter(skuFilter);
		Entity ProdutoEntity = datastore.prepare(query).asSingleEntity();
		if (ProdutoEntity == null) {
			return false;
		} else {
			if (ProdutoEntity.getKey().getId() == Produto.getId()) {
				return false;
			} else {
				return true;
			}
		}
	}
}
