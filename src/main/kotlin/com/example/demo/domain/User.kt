package com.example.demo.domain

import jakarta.persistence.*

@Entity(name = "USERS")
class User(

    @Column
    var name: String,

    @Column
    val age: Int,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

) {
}