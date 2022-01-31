package com.kotlin.gierreprojectwebservice.exception

open class ParameterNotValidException(val entity: String, open val param: String): Exception("$entity - Parameter: $param not valid")