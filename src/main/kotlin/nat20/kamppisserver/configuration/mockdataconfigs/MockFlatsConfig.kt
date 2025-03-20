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
            Flat( //#1
                name = "Sunny Side Up",
                description = "Bright and airy flat with a sunny balcony. Perfect for morning coffee! Enjoy the natural light streaming in through large windows, creating a warm and inviting atmosphere. Located in a quiet neighborhood with easy access to public transportation.",
                location = City.HELSINKI,
                totalRoommates = 4,
                flatUtilities = mutableListOf(Utilities.WIFI, Utilities.BALCONY, Utilities.DISHWASHER, Utilities.LAUNDRY_MACHINE)
            ),
            Flat( //#2
                name = "Urban Oasis",
                description = "Modern flat with a private bathroom and dishwasher. Close to all amenities. This stylish flat features sleek, contemporary design and high-end appliances. Ideal for those who appreciate convenience and comfort in the heart of the city.",
                location = City.VANTAA,
                totalRoommates = 2,
                flatUtilities = mutableListOf(Utilities.DISHWASHER, Utilities.SEPARATE_BATHROOM_AND_SHOWER, Utilities.WIFI)
            ),
            Flat( //#3
                name = "Sea Breeze",
                description = "Cozy flat with a sea-side view and separate bathroom and shower. Wake up to the sound of waves and enjoy breathtaking views of the ocean. Perfect for those who love the tranquility of coastal living.",
                location = City.ESPOO,
                totalRoommates = 3,
                flatUtilities = mutableListOf(Utilities.LAUNDRY_MACHINE, Utilities.SEPARATE_BATHROOM_AND_SHOWER, Utilities.WIFI)
            ),
            Flat( //#4
                name = "City Lights",
                description = "Stylish flat with a balcony and laundry machine. Enjoy the city view! This chic flat offers a perfect blend of urban living and comfort. The balcony provides a great spot to unwind and take in the vibrant cityscape.",
                location = City.HELSINKI,
                totalRoommates = 4,
                flatUtilities = mutableListOf(Utilities.WIFI, Utilities.BALCONY, Utilities.LAUNDRY_MACHINE)
            ),
            Flat( //#5
                name = "Green Haven",
                description = "Eco-friendly flat with a private bathroom and WiFi. Surrounded by parks. This flat is designed with sustainability in mind, featuring energy-efficient appliances and green spaces. Ideal for nature lovers who want to live in harmony with the environment.",
                location = City.VANTAA,
                totalRoommates = 3,
                flatUtilities = mutableListOf(Utilities.DISHWASHER, Utilities.PRIVATE_BATHROOM, Utilities.WIFI)
            ),
            Flat( //#6
                name = "Sky High",
                description = "Top-floor flat with a balcony and dishwasher. Stunning skyline views! Experience the luxury of high-rise living with panoramic views of the city. The balcony is perfect for entertaining guests or enjoying a quiet evening.",
                location = City.ESPOO,
                totalRoommates = 3,
                flatUtilities = mutableListOf(Utilities.BALCONY, Utilities.DISHWASHER, Utilities.WIFI)
            ),
            Flat( //#7
                name = "Cozy Corner",
                description = "Warm and inviting flat with a laundry machine and WiFi. Perfect for relaxation. This flat offers a comfortable and homely atmosphere, with cozy furnishings and modern amenities. Ideal for unwinding after a long day.",
                location = City.HELSINKI,
                totalRoommates = 2,
                flatUtilities = mutableListOf(Utilities.LAUNDRY_MACHINE, Utilities.WIFI)
            ),
            Flat( //#8
                name = "Modern Retreat",
                description = "Sleek flat with a separate bathroom and shower. Ideal for urban living. This flat features a minimalist design with clean lines and modern finishes. Perfect for those who appreciate contemporary style and convenience.",
                location = City.VANTAA,
                totalRoommates = 3,
                flatUtilities = mutableListOf(Utilities.LAUNDRY_MACHINE, Utilities.SEPARATE_BATHROOM_AND_SHOWER, Utilities.WIFI)
            ),
            Flat( //#9
                name = "Beachside Bliss",
                description = "Flat with a sea-side view and balcony. Enjoy the beach life! This flat offers a serene and picturesque setting, with easy access to the beach. The balcony is perfect for soaking up the sun and enjoying the coastal breeze.",
                location = City.ESPOO,
                totalRoommates = 4,
                flatUtilities = mutableListOf(Utilities.DISHWASHER, Utilities.BALCONY, Utilities.WIFI)
            ),
            Flat( //#10
                name = "Park View",
                description = "Flat with a private bathroom and laundry machine. Overlooking a beautiful park. This flat offers a peaceful and scenic environment, with stunning views of lush greenery. Ideal for those who enjoy outdoor activities and nature.",
                location = City.HELSINKI,
                totalRoommates = 3,
                flatUtilities = mutableListOf(Utilities.PRIVATE_BATHROOM, Utilities.LAUNDRY_MACHINE, Utilities.WIFI)
            ),
            Flat( //#11
                name = "Urban Hideaway",
                description = "Flat with a dishwasher and WiFi. Hidden gem in the city center. This flat offers a perfect blend of convenience and privacy, with modern amenities and a prime location. Ideal for those who want to be close to the action while enjoying a quiet retreat.",
                location = City.VANTAA,
                totalRoommates = 3,
                flatUtilities = mutableListOf(Utilities.DISHWASHER, Utilities.LAUNDRY_MACHINE, Utilities.WIFI)
            ),
            Flat( //#12
                name = "Skyline Serenity",
                description = "Flat with a balcony and separate bathroom and shower. Peaceful and serene. This flat offers a tranquil living space with breathtaking views of the city skyline. Perfect for those who appreciate a calm and relaxing environment.",
                location = City.ESPOO,
                totalRoommates = 2,
                flatUtilities = mutableListOf(Utilities.BALCONY, Utilities.SEPARATE_BATHROOM_AND_SHOWER, Utilities.WIFI)
            ),
            Flat( //#13
                name = "City Escape",
                description = "Flat with a private bathroom and WiFi. Perfect for escaping the hustle and bustle. This flat offers a quiet and comfortable living space, with modern amenities and a convenient location. Ideal for those who want to unwind and recharge.",
                location = City.HELSINKI,
                totalRoommates = 2,
                flatUtilities = mutableListOf(Utilities.PRIVATE_BATHROOM, Utilities.WIFI, Utilities.DISHWASHER)
            ),
            Flat( //#14
                name = "Modern Marvel",
                description = "Flat with a dishwasher and laundry machine. Marvel at the modern amenities! This flat offers a sleek and stylish living space, with high-end appliances and contemporary design. Perfect for those who appreciate luxury and convenience.",
                location = City.VANTAA,
                totalRoommates = 3,
                flatUtilities = mutableListOf(Utilities.DISHWASHER, Utilities.LAUNDRY_MACHINE, Utilities.WIFI)
            ),
            Flat( //#15
                name = "Seaside Sanctuary",
                description = "Flat with a sea-side view and private bathroom. Your sanctuary by the sea. This flat offers a peaceful and picturesque setting, with stunning views of the ocean. Ideal for those who want to live in harmony with nature and enjoy the coastal lifestyle.",
                location = City.ESPOO,
                totalRoommates = 3,
                flatUtilities = mutableListOf(Utilities.PRIVATE_BATHROOM, Utilities.DISHWASHER, Utilities.WIFI)
            )
        )

        flatRepository.saveAll(flats)
    }
}