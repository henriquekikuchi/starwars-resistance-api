package br.com.kikuchi.henrique.starwarsapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(targetEntity = Rebel.class)
    private Rebel from;

    @ManyToOne(targetEntity = Rebel.class)
    private Rebel to;

    private Status status;
}
