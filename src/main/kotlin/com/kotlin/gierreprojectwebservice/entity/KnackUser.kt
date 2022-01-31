package com.kotlin.gierreprojectwebservice.entity

import javax.persistence.*

@Entity
@Table(name = "KNACK_USER")
class KnackUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,

    @Column(name = "username", nullable = false, unique = true)
    val username: String,

    @Column(name = "password", nullable = false)
    var password: String,

    @Column(name = "role", nullable = false)
    val role: Int,
) {}