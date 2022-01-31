package com.kotlin.gierreprojectwebservice.mapper

interface Mapper<E,D> {
    fun toDto(entity: E): D
    fun toDtos(listEntities: List<E>): List<D> = listEntities.map { toDto(it) }
    fun toEntity(dto: D): E
    fun toEntities(listDto: List<D>): List<E> = listDto.map { toEntity(it) }
}