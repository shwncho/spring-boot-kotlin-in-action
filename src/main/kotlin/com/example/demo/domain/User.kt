package com.example.demo.domain

import jakarta.persistence.*

@Entity(name = "USERS")
class User(

    @Column
    var name: String,

    @Column
    var age: Int,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

) {
    fun update(name: String, age: Int){
        updateName(name)
        updateAge(age)
    }

    private fun updateName(name: String){
        if(name.isNotBlank()){
            this.name = name
        }
    }

    private fun updateAge(age: Int){
        if(age>0){
            this.age = age
        }
    }
}