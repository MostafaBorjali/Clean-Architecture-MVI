package com.borjali.domain.utils

import com.borjali.domain.BuildConfig
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest

@Suppress("unused", "UNCHECKED_CAST")
class EventOfCleanApp private constructor() : CoroutineScope {

    private val job = SupervisorJob()

    override val coroutineContext: CoroutineContext = Dispatchers.IO + job

    companion object {

        private const val GLOBAL_CONTEXT = "global:app_event_flow"

        const val TAG = "EventOfBiozoon"

        private var instance: EventOfCleanApp? = null
            get() = if (field == null) {
                field = EventOfCleanApp()
                field
            } else {
                field
            }

        fun <T> registerEvent(
            eventClass: Class<T>,
            contextName: String = GLOBAL_CONTEXT,
            eventDispatcher: CoroutineDispatcher = Dispatchers.Main,
            eventCallback: suspend (event: T) -> Unit
        ) {
            instance?.registerEvent(eventClass, contextName, eventDispatcher, eventCallback)
        }

        fun send(event: Any, delaySend: Long = 0) {
            if (delaySend > 0) {
                instance?.launch {
                    delay(delaySend)
                    instance?.send(event)
                }
            } else {
                instance?.send(event)
            }
        }

        fun unregisterAllEvents() {
            instance?.unregisterAllEvent()
        }

        fun unregisterByContext(contextName: String) {
            instance?.unregisterByContext(contextName)
        }
    }

    private val contextList = mutableMapOf<String, MutableMap<Class<*>, PipeData<*>>>()

    private fun send(event: Any) {
        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, PipeData<*>>>()
        cloneContextList.putAll(contextList)
        for ((_, pipe) in cloneContextList) {
            pipe.keys.firstOrNull { it == event.javaClass || it == event.javaClass.superclass }
                .let { key ->
                    pipe[key]?.send(event)
                }
        }
    }

    private fun <T> registerEvent(
        eventClass: Class<T>,
        contextName: String,
        eventDispatcher: CoroutineDispatcher,
        eventCallback: suspend (T) -> Unit
    ) {
        val pipeList = if (contextList.containsKey(contextName)) {
            contextList[contextName]!!
        } else {
            val eventPipe = mutableMapOf<Class<*>, PipeData<*>>()
            contextList[contextName] = eventPipe
            eventPipe
        }
        val eventData = PipeData(eventDispatcher, eventCallback)
        pipeList[eventClass] = eventData
    }

    private fun unregisterAllEvent() {
        if (BuildConfig.DEBUG) {
            println("$TAG: unregisterAllEvent()")
        }
        coroutineContext.cancelChildren()
        for ((_, pipe) in contextList) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextList.clear()
    }

    private fun unregisterByContext(contextName: String) {
        if (BuildConfig.DEBUG) {
            println("$TAG: unregisterByContext(context: $contextName)")
        }
        val cloneContextList = mutableMapOf<String, MutableMap<Class<*>, PipeData<*>>>()
        cloneContextList.putAll(contextList)
        val pipesInContext = cloneContextList.filter { it.key == contextName }
        for ((_, pipe) in pipesInContext) {
            pipe.values.forEach { it.cancel() }
            pipe.clear()
        }
        contextList.remove(contextName)
    }

    data class PipeData<T>(
        val eventDispatcher: CoroutineDispatcher,
        val onEvent: suspend (T) -> Unit
    ) : CoroutineScope {

        private val sharedFlow = MutableSharedFlow<T>()
        private val job = Job()

        override val coroutineContext: CoroutineContext
            get() = job

        init {
            launch {
                sharedFlow.collectLatest { launch(eventDispatcher) { onEvent(it) } }
            }
        }

        fun send(event: Any) {
            launch {
                delay(1)
                sharedFlow.emit(event as T)
            }
        }

        fun cancel() {
            job.cancel("Canceled by user")
        }
    }
}
