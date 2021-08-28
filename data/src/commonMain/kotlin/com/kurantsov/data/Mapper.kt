package com.kurantsov.data

interface Mapper<T, E> {
    fun convert(input: T): E
    fun convertBack(input: E): T

    fun convertAll(input: List<T>): List<E> {
        return input.map(::convert)
    }

    fun convertAllBack(input: List<E>): List<T> {
        return input.map(::convertBack)
    }
}