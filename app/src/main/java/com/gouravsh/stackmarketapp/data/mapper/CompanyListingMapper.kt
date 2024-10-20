package com.gouravsh.stackmarketapp.data.mapper

import com.gouravsh.stackmarketapp.data.local.CompanyListingEntity
import com.gouravsh.stackmarketapp.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange,
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange,
        )
}