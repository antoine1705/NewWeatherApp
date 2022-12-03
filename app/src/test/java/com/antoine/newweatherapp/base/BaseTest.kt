package com.antoine.newweatherapp.base

import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.Koin
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent

@RunWith(JUnit4::class)
abstract class BaseTest : KoinComponent {
    private val di = KoinApplication.init()

    final override fun getKoin(): Koin {
        return di.koin
    }

    @Before
    open fun setUp() {
    }

    @After
    open fun tearDown() {
        di.close()
    }

}
