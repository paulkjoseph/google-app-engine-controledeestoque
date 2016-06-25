/*
 * *****************************************************************************
 * Copyright (c) 2016
 * Propriedade da Paul Joseph LTD.
 * Todos os direitos reservados, com base nas leis brasileiras de copyright
 * Este software é confidencial e de propriedade intelectual da Paul Joseph LTD
 * ****************************************************************************
 * Projeto: Sistema de Controle de Estoque
 * Arquivo: Produto.java
 * ****************************************************************************
 * Histórico de revisões
 * Nome				Data		Descrição
 * ****************************************************************************
 * Paul Joseph		14 de jun de 2016	Versão inicial
 * ****************************************************************************
 */
package br.com.latosensu.ufscar.controleestoque.models;

import java.io.Serializable;

/**
 * Produto
 * 
 * @author Paul Joseph
 * @since 14 de jun de 2016
 */
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	private String sku;
	private String nome;
	private String descricao;
	private float preco;

	public Produto() {
		super();
	}

	public Produto(String nome, String descricao, float preco) {
		super();
		this.nome = nome;
		this.descricao = descricao;
		this.preco = preco;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}

}
