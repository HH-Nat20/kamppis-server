package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.Flat
import nat20.kamppisserver.domain.enums.City
import nat20.kamppisserver.domain.enums.Utilities
import nat20.kamppisserver.repository.FlatRepository

class MockFlatsConfig {

    /**
     * Database initializer for adding mock flat data into the database
     */
    fun insertMockFlatsToDatabase(
        flatRepository: FlatRepository,
    ) {
        // These are flats
        val flats = listOf(
            Flat(
                name ="HOAS kolme huonetta",
                description = "Neljä huonetta ja keittiökomero Kalasatamassa. Merellinen viima asunnossa ja metron urbaani äänimaisema luovat ison kaupungin henkeä!",
                location = City.HELSINKI,
                totalRoommates = 4,
                flatUtilities = mutableListOf(Utilities.WIFI, Utilities.BALCONY),
            )
        )

        flatRepository.saveAll(flats)
    }
}