package com.geoactio.sae.hilt

import javax.inject.Qualifier

object HiltQualifiers {
    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class retrofitService
}
