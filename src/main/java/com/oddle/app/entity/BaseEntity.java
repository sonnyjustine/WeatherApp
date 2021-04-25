package com.oddle.app.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Base entity that contains common fields for all entities
 * @author squillopas
 */

@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @Id
    @Column(nullable = false, updatable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

}

