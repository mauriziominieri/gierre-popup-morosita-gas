package com.kotlin.gierreprojectwebservice.service

import com.kotlin.gierreprojectwebservice.config.KtEnvConfig
import com.kotlin.gierreprojectwebservice.dto.KnackUserDto
import com.kotlin.gierreprojectwebservice.dto.ServerResponseDto
import com.kotlin.gierreprojectwebservice.exception.KnackUserAlreadyPresentException
import com.kotlin.gierreprojectwebservice.exception.KnackUserUserNotFoundException
import com.kotlin.gierreprojectwebservice.exception.KnackUserUserPasswordNotValidException
import com.kotlin.gierreprojectwebservice.deprecated.EmailSender
import com.kotlin.gierreprojectwebservice.mapper.KnackUserMapper
import com.kotlin.gierreprojectwebservice.repository.KnackUserRepository
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.stereotype.Service

@Service
class KnackUserServiceImpl(
    private val userRepository: KnackUserRepository,
    private val userMapper: KnackUserMapper,
    private val emailSender: EmailSender,
    private val ktEnvConfig: KtEnvConfig
): KnackUserService {
    override fun login(username: String, password: String): ServerResponseDto<KnackUserDto> {
        val user = this.userRepository.findDistinctByUsername(username)

        if (user.isEmpty)
            throw KnackUserUserNotFoundException(username)

        if(user.get().password != password)
            throw KnackUserUserPasswordNotValidException(username)

        return ServerResponseDto.ok(this.userMapper.toDto(user.get()))
    }

    override fun getUsers(email: String): ServerResponseDto<List<KnackUserDto>> {

        this.checkAdmin(email)

        val list = this.userRepository.findAll()

        return ServerResponseDto.ok(this.userMapper.toDtos(list))
    }

    override fun getRoles(email: String): ServerResponseDto<List<Int>> {

        this.checkAdmin(email)

        val list = this.userRepository.findAllRoles()

        return ServerResponseDto.ok(list)
    }

    override fun createUser(emailAdmin: String, user: KnackUserDto): ServerResponseDto<KnackUserDto> {

        this.checkAdmin(emailAdmin)

        var entity = this.userMapper.toEntity(user)

        try {
            entity = this.userRepository.save(entity)
        }
        catch (e: DataIntegrityViolationException) {
            throw KnackUserAlreadyPresentException("Email utente già presente");
        }

        /*val link = when(this.ktEnvConfig.env) {
            "prod" -> this.ktEnvConfig.localServerUrl + "/signup?email=${user.username}"
            "local" -> this.ktEnvConfig.prodServerUrl + "/signup?email=${user.username}"
            else -> { "link error" }
        }

        this.emailSender.send(
            user.username,
            "[GIERRE] Registrazione PodPdrMorosità Platform",
            "Benvenuto sulla piattaforma Gierre PodPdrMorosità,\n\n per registrare il tuo account segui il link:\n$link"
        )*/

        
        return ServerResponseDto.ok(this.userMapper.toDto(entity))
        
    }


    override fun register(user: KnackUserDto): ServerResponseDto<KnackUserDto> {

        val userEntity = this.userRepository.findDistinctByUsername(user.username)

        if(userEntity.isEmpty)
            throw KnackUserUserNotFoundException(user.username)

        userEntity.get().apply {
            password = user.username
        }

        val userSaved = this.userRepository.save(userEntity.get())

        return ServerResponseDto.ok(this.userMapper.toDto(userSaved))
    }


    override fun deleteUser(emailAdmin: String, userToDelete: String): ServerResponseDto<KnackUserDto> {
        this.checkAdmin(emailAdmin)

        val user = this.userRepository.findDistinctByUsername(userToDelete)

        if(user.isEmpty)
            throw KnackUserUserNotFoundException(userToDelete)

        this.userRepository.delete(user.get())

        return ServerResponseDto.ok(this.userMapper.toDto(user.get()))
    }

    private fun checkAdmin(email: String) {

        val user = this.userRepository.findDistinctByUsername(email)

        if(user.isEmpty)
            throw KnackUserUserNotFoundException(email)

        if(user.get().role != 0)
            throw KnackUserUserNotFoundException(email)
    }
}