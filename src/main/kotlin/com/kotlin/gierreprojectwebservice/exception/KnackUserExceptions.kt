package com.kotlin.gierreprojectwebservice.exception

class KnackUserUserNotFoundException(val username: String): Exception("User: $username not found.") {}
class KnackUserUserPasswordNotValidException(val username: String): Exception("User: $username, password not valid.") {}