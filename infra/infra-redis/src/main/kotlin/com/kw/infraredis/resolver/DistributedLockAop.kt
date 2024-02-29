package com.kw.infraredis.resolver

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.reflect.MethodSignature
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Aspect
@Component
class DistributedLockAop(
    private val redissonClient: RedissonClient,
    private val aopForTransaction: AopForTransaction
) {
    companion object {
        private const val REDISSON_LOCK_PREFIX = "LOCK:"
    }

    private val logger = LoggerFactory.getLogger(DistributedLockAop::class.java)

    @Around("@annotation(DistributedLock)")
    fun lock(joinPoint: ProceedingJoinPoint): Any? {
        val signature = joinPoint.signature as MethodSignature
        val method = signature.method
        val distributedLock = method.getAnnotation(DistributedLock::class.java)

        val key = REDISSON_LOCK_PREFIX + distributedLock.key
        val rLock: RLock = redissonClient.getLock(key)

        return try {
            val available = rLock.tryLock(distributedLock.waitTime, distributedLock.leaseTime, distributedLock.timeUnit)
            if (!available) {
                return false
            }

            aopForTransaction.proceed(joinPoint)
        } catch (e: InterruptedException) {
            throw InterruptedException()
        } finally {
            try {
                rLock.unlock()
            } catch (e: IllegalMonitorStateException) {
                logger.info("Redisson Lock Already UnLock serviceName=${method.name} key=$key")
            }
        }
    }
}
