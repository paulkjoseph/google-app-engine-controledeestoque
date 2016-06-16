/*
 * *****************************************************************************
 * Copyright (c) 2016
 * Propriedade da Paul Joseph LTD.
 * Todos os direitos reservados, com base nas leis brasileiras de copyright
 * Este software é confidencial e de propriedade intelectual da Paul Joseph LTD
 * ****************************************************************************
 * Projeto: Sistema de Controle de Estoque
 * Arquivo: ProdutoManager.java
 * ****************************************************************************
 * Histórico de revisões
 * Nome				Data		Descrição
 * ****************************************************************************
 * Paul Joseph		14 de jun de 2016	Versão inicial
 * ****************************************************************************
 */
package br.com.latosensu.ufscar.controleestoque.services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.latosensu.ufscar.controleestoque.dao.ProdutoDAO;
import br.com.latosensu.ufscar.controleestoque.models.Produto;

/**
 * ProdutoManager
 * 
 * @author Paul Joseph
 * @since 14 de jun de 2016
 */
@Path("/produtos")
public class ProdutoManager extends ProdutoDAO {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{sku}")
	public Produto getProduto(@PathParam("sku") String sku) {
		return super.buscarProduto(sku);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Produto> getProdutos() {
		return super.buscarProdutos();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Produto saveProduto(Produto produto) {
		return super.salvarProduto(produto);
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{sku}")
	public Produto deleteProduto(@PathParam("sku") String sku) {
		return super.deletarProduto(sku);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{sku}")
	public Produto alterProduto(@PathParam("sku") String sku, Produto produto) {
		return super.alterarProduto(sku, produto);
	}

}
