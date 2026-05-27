package org.aula.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "partida")
public class Partida {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    private Date data;

    @Column(name = "pontuacao")
    private Integer pontuacao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_jogador")
    private Jogador jogador;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_jogo")
    private Jogo jogo;

    public Jogo getJogo() { return jogo; }
    public void setJogo(Jogo jogo) { this.jogo = jogo; }

    public Jogador getJogador() { return jogador; }
    public void setJogador(Jogador jogador) { this.jogador = jogador; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Date getData() { return data; }
    public void setData(Date data) { this.data = data; }

    public Integer getPontuacao() { return pontuacao; }
    public void setPontuacao(Integer pontuacao) { this.pontuacao = pontuacao; }

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", data=" + data +
                ", pontuacao=" + pontuacao +
                '}';
    }
}