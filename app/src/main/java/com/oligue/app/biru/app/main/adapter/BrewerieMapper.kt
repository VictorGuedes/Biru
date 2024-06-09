package com.oligue.app.biru.app.main.adapter

import com.oligue.app.biru.app.main.model.BrewerieUI
import com.oligue.app.biru.core.model.Brewerie
import javax.inject.Inject

class BrewerieMapper @Inject constructor() {

    fun map(listToMap: List<Brewerie>): List<BrewerieUI> {
        return listToMap.map { brewerie ->
            BrewerieUI(
                id = brewerie.id,
                address_1 = brewerie.address_1,
                brewery_type = brewerie.brewery_type,
                city = brewerie.city,
                country = brewerie.country,
                latitude = brewerie.latitude,
                longitude = brewerie.longitude,
                name = brewerie.name,
                phone = brewerie.phone,
                postal_code = brewerie.postal_code,
                state = brewerie.state,
                state_province = brewerie.state_province,
                street = brewerie.street,
                website_url = brewerie.website_url
            )
        }
    }

    fun mapModel(toMap: Brewerie): BrewerieUI {
        return BrewerieUI(
            id = toMap.id,
            address_1 = toMap.address_1,
            brewery_type = toMap.brewery_type,
            city = toMap.city,
            country = toMap.country,
            latitude = toMap.latitude,
            longitude = toMap.longitude,
            name = toMap.name,
            phone = toMap.phone,
            postal_code = toMap.postal_code,
            state = toMap.state,
            state_province = toMap.state_province,
            street = toMap.street,
            website_url = toMap.website_url
        )
    }
}