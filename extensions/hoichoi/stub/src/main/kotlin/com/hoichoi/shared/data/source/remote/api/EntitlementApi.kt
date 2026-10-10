package com.hoichoi.shared.data.source.remote.api

import com.hoichoi.shared.data.source.remote.model.entitlement.EntitlementNetworkEntity

abstract class EntitlementApi {
    abstract suspend fun getEntitlementData(
        contentId: String?,
        action: String?,
    ): EntitlementNetworkEntity
}