package com.kotlin.gierreprojectwebservice.exception

open class EntityNotFoundIdException(val entity: String, open val id: Long): Exception("Entity ($entity) with id: $id not found") {}