package com.kotlin.gierreprojectwebservice.exception

class UtenzaKnackExceptions(override val message: String): Exception(message)
class UtenzaKnackParamNotValidException(override val param: String): ParameterNotValidException("UtenzaKnack", param)
class UtenzaKnackWithIdNotFound(override val id: Long): EntityNotFoundIdException("UtenzaKnack", id);